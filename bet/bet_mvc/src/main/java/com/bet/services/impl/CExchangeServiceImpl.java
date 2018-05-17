package com.bet.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserInfoDao;
import com.bet.enums.ApplyStatus;
import com.bet.enums.MoneyUnit;
import com.bet.enums.UserBalanceLogType;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.orms.UserExchangeInfo;
import com.bet.orms.UserInfo;
import com.bet.services.CExchangeService;
import com.bet.services.IConfigInfoService;
import com.bet.services.IUserBalanceLogInfoService;
import com.bet.services.IUserExchangeInfoService;
import com.bet.services.IUserInfoService;
import com.bet.utils.BetConstValues;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.utils.GsonTools;

@Repository("cExchangeService")
public class CExchangeServiceImpl implements CExchangeService
{
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IUserExchangeInfoService userExchangeInfoService;
	@Autowired
	private IUserBalanceLogInfoService userBalanceLogInfoService;
	@Autowired
	private IConfigInfoService configInfoService;

	@Override
	public ReturnInfo addUserExchangeInfo(UserExchangeInfo userExchangeInfo)
	{
		if (configInfoService.getBooleanValue(BetConstValues.CONFIG_CLOSE_EXCHANGE))
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "系统已暂停用户转账！");
		}
		if (userExchangeInfo.getUserId().equals(userExchangeInfo.getReceiveUserId()))
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "您不能自己跟自己转账！");
		}
		int minMoney = configInfoService.getIntValue(BetConstValues.CONFIG_MIN_EXCHANGE);
		if (minMoney > userExchangeInfo.getMoney())
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, String.format("最低转账金额为%.2f，请重新输入！", (double) minMoney / 100));
		}
		UserInfo userInfo = userInfoService.getUserInfoByUserId(userExchangeInfo.getUserId());
		if (userInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "转账用户ID不存在！");
		}
		if (userInfo.getSysStatus() == StatusType.ENABLED.getStatus())
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "您是系统用户，不能转账！");
		}
		if (userInfo.getPoint() < userExchangeInfo.getMoney())
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "用户可用彩金不足，不能转账！");
		}
		UserInfo receiveUserInfo = userInfoService.getUserInfoByUserId(userExchangeInfo.getReceiveUserId());
		if (receiveUserInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "接收用户ID不存在！");
		}
		long current = System.currentTimeMillis();
		userExchangeInfo.setExchangeId(userExchangeInfoService.genId());
		userExchangeInfo.setFee(0);
		userExchangeInfo.setUserMoney(userExchangeInfo.getMoney() - userExchangeInfo.getFee());
		userExchangeInfo.setStatus(ApplyStatus.APPLY.getStatus());
		userExchangeInfo = userExchangeInfoService.addUserExchangeInfo(userExchangeInfo);
		if (userExchangeInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加用户转账信息失败！");
		}
		// 更新用户余额信息
		if (!userInfoService.updatePoint(IUserInfoDao.POINT, userExchangeInfo.getUserId(), -userExchangeInfo.getMoney()))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新转账用户金额失败！");
		}
		if (!userInfoService.updatePoint(IUserInfoDao.POINT, userExchangeInfo.getReceiveUserId(), userExchangeInfo.getMoney()))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新接收用户金额失败！");
		}
		// 写入日志--这里要充值成功了才写入日志
		userBalanceLogInfoService
			.addUserBalanceLogInfo(new UserBalanceLogInfo(userExchangeInfo.getUserId(), -userExchangeInfo.getMoney(), MoneyUnit.POINT.getType(), UserBalanceLogType.EXCHANGE_OUT.getType(),
				userExchangeInfo.getExchangeId(), null, null, null, String.format("用户转账%.2f元到用户%s。", (double) userExchangeInfo.getMoney() / 100, userExchangeInfo.getReceiveUserId()), current));
		userBalanceLogInfoService
			.addUserBalanceLogInfo(new UserBalanceLogInfo(userExchangeInfo.getReceiveUserId(), userExchangeInfo.getMoney(), MoneyUnit.POINT.getType(), UserBalanceLogType.EXCHANGE_IN.getType(),
				userExchangeInfo.getExchangeId(), null, null, null, String.format("接收用户%s转账%.2f元。", userExchangeInfo.getUserId(), (double) userExchangeInfo.getMoney() / 100), current));
		return new ReturnInfo(ErrorInfo.SUCCESS, GsonTools.toJson(userExchangeInfo));
	}
}
