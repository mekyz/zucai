package com.bet.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserInfoDao;
import com.bet.daos.IWithdrawInfoDao;
import com.bet.enums.ApplyStatus;
import com.bet.enums.MoneyUnit;
import com.bet.enums.UserBalanceLogType;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.orms.UserInfo;
import com.bet.orms.WithdrawInfo;
import com.bet.services.CWithdrawService;
import com.bet.services.IConfigInfoService;
import com.bet.services.IUserBalanceLogInfoService;
import com.bet.services.IUserInfoService;
import com.bet.services.IWithdrawInfoService;
import com.bet.utils.BetConstValues;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.utils.GsonTools;

@Repository("cWithdrawService")
public class CWithdrawServiceImpl implements CWithdrawService
{
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IWithdrawInfoService withdrawInfoService;
	@Autowired
	private IUserBalanceLogInfoService userBalanceLogInfoService;
	@Autowired
	private IConfigInfoService configInfoService;

	@Override
	public ReturnInfo addWidthdrawInfo(WithdrawInfo withdrawInfo)
	{
		if (withdrawInfo.getMoney() % 10000 != 0)
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "提现金额必须是100的整数倍！");
		}
		double minWithdrawMoney = configInfoService.getDoubleValue(BetConstValues.CONFIG_MIN_WITHDRAW);
		if (withdrawInfo.getMoney() < minWithdrawMoney)
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, String.format("最低提现金额为%.2f元，请重新输入！", minWithdrawMoney / 100));
		}
		UserInfo userInfo = userInfoService.getUserInfoByUserId(withdrawInfo.getUserId());
		if (userInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "用户ID不存在！");
		}
		if (userInfo.getSysStatus() == StatusType.ENABLED.getStatus())
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "您是系统用户，不能提现！");
		}
		if (withdrawInfo.getMoney() > userInfo.getPoint())
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "用户可用彩金不足！");
		}
		int fee = configInfoService.getIntValue(BetConstValues.CONFIG_WITHDRAW_FEE);
		withdrawInfo.setWithdrawId(withdrawInfoService.genId());
		withdrawInfo.setUserId(userInfo.getUserId());
		withdrawInfo.setFee(withdrawInfo.getMoney() * fee / 100);
		withdrawInfo.setUserMoney(withdrawInfo.getMoney() - withdrawInfo.getFee());
		withdrawInfo.setBankName(userInfo.getBankName());
		withdrawInfo.setBankCardId(userInfo.getBankCardId());
		withdrawInfo.setPayeeName(userInfo.getPayeeName());
		withdrawInfo.setStatus(ApplyStatus.APPLY.getStatus());
		withdrawInfo = withdrawInfoService.addWithdrawInfo(withdrawInfo);
		if (withdrawInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加用户提现信息失败！");
		}
		// 减少用户余额
		if (!userInfoService.updatePointConvert(IUserInfoDao.POINT, IUserInfoDao.FREEZE_POINT, withdrawInfo.getUserId(), withdrawInfo.getMoney()))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "扣除金额失败！");
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, GsonTools.toJson(withdrawInfo));
	}

	@Override
	public ReturnInfo updateWidthdrawInfoVerify(String withdrawId, String remark, byte status)
	{
		if (status != ApplyStatus.VERIFY_SUCCESS.getStatus() && status != ApplyStatus.VERIFY_FAIL.getStatus())
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "审核状态错误！");
		}
		WithdrawInfo withdrawInfo = withdrawInfoService.getWithdrawInfoByWithdrawId(withdrawId);
		if (withdrawInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "提现ID不存在！");
		}
		if (withdrawInfo.getStatus() == ApplyStatus.VERIFY_FAIL.getStatus())
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "审核失败已成功，不能再次审核！");
		}
		UserInfo userInfo = userInfoService.getUserInfoByUserId(withdrawInfo.getUserId());
		if (userInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "用户ID不存在！");
		}
		// 更新提现状态
		long current = System.currentTimeMillis();
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(IWithdrawInfoDao.PAY_REMARK, remark);
		valueMap.put(IWithdrawInfoDao.STATUS, status);
		valueMap.put(IWithdrawInfoDao.VERIFY_DATE_LONG, current);
		valueMap.put(IWithdrawInfoDao.UPDATE_DATE_LONG, current);
		if (!withdrawInfoService.updateValueByWithdrawId(withdrawId, valueMap))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新提现状态失败！");
		}
		if (status == ApplyStatus.VERIFY_FAIL.getStatus())
		{
			// 更新用户余额信息
			if (!userInfoService.updatePointConvert(IUserInfoDao.FREEZE_POINT, IUserInfoDao.POINT, withdrawInfo.getUserId(), withdrawInfo.getMoney()))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户金额失败！");
			}
		}
		// withdrawInfo.setPayRemark(remark);
		// withdrawInfo.setStatus(status);
		// withdrawInfo.setVerifyDateLong(current);
		// withdrawInfo.setUpdateDateLong(current);
		return new ReturnInfo(ErrorInfo.SUCCESS, "审核成功！");
	}

	@Override
	public ReturnInfo updateWidthdrawInfoProcessed(String withdrawId)
	{
		WithdrawInfo withdrawInfo = withdrawInfoService.getWithdrawInfoByWithdrawId(withdrawId);
		if (withdrawInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "提现ID不存在！");
		}
		if (withdrawInfo.getStatus() != ApplyStatus.VERIFY_SUCCESS.getStatus())
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "审核状态错误！");
		}
		UserInfo userInfo = userInfoService.getUserInfoByUserId(withdrawInfo.getUserId());
		if (userInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "用户ID不存在！");
		}
		// 更新用户余额信息
		if (!userInfoService.updatePoint(IUserInfoDao.FREEZE_POINT, withdrawInfo.getUserId(), -withdrawInfo.getMoney()))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户金额失败！");
		}
		// 更新提现状态
		long current = System.currentTimeMillis();
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(IWithdrawInfoDao.STATUS, ApplyStatus.PROCESSED.getStatus());
		valueMap.put(IWithdrawInfoDao.RECEIVE_DATE_LONG, current);
		valueMap.put(IWithdrawInfoDao.UPDATE_DATE_LONG, current);
		if (!withdrawInfoService.updateStatusValueByWithdrawId(withdrawId, ApplyStatus.VERIFY_SUCCESS.getStatus(), valueMap))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新提现状态失败！");
		}
		// 写入日志--这里要提现成功了才写入日志
		userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(withdrawInfo.getUserId(), -withdrawInfo.getMoney(), MoneyUnit.POINT.getType(), UserBalanceLogType.WITHDRAW.getType(),
			withdrawInfo.getWithdrawId(), null, null, null, String.format("用户提现%.2f元。", (double) withdrawInfo.getMoney() / 100), current));
		// withdrawInfo.setStatus(ApplyStatus.PROCESSED.getStatus());
		return new ReturnInfo(ErrorInfo.SUCCESS, "处理成功！");
	}
}
