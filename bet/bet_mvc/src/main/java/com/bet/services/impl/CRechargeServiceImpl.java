package com.bet.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserInfoDao;
import com.bet.daos.IUserRechargeInfoDao;
import com.bet.enums.ApplyStatus;
import com.bet.enums.MoneyUnit;
import com.bet.enums.UserBalanceLogType;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.orms.UserInfo;
import com.bet.orms.UserRechargeInfo;
import com.bet.services.CRechargeService;
import com.bet.services.IUserBalanceLogInfoService;
import com.bet.services.IUserInfoService;
import com.bet.services.IUserRechargeInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.utils.GsonTools;

@Repository("cRechargeService")
public class CRechargeServiceImpl implements CRechargeService
{
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IUserRechargeInfoService userRechargeInfoService;
	@Autowired
	private IUserBalanceLogInfoService userBalanceLogInfoService;

	@Override
	public ReturnInfo updateUserRechargeInfoVerify(String rechargeId, byte status)
	{
		if (status != ApplyStatus.VERIFY_SUCCESS.getStatus() && status != ApplyStatus.VERIFY_FAIL.getStatus())
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "审核状态错误！");
		}
		UserRechargeInfo userRechargeInfo = userRechargeInfoService.getUserRechargeInfoByRechargeId(rechargeId);
		if (userRechargeInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "充值ID不存在！");
		}
		UserInfo userInfo = userInfoService.getUserInfoByUserId(userRechargeInfo.getUserId());
		if (userInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "用户ID不存在！");
		}
		// 更新提现状态
		long current = System.currentTimeMillis();
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(IUserRechargeInfoDao.STATUS, status);
		valueMap.put(IUserRechargeInfoDao.VERIFY_DATE_LONG, current);
		valueMap.put(IUserRechargeInfoDao.UPDATE_DATE_LONG, current);
		if (!userRechargeInfoService.updateStatusValueByRechargeId(rechargeId, ApplyStatus.APPLY.getStatus(), valueMap))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新充值状态失败！");
		}
		if (status == ApplyStatus.VERIFY_SUCCESS.getStatus())
		{
			// 更新用户余额信息
			if (!userInfoService.updatePoint(IUserInfoDao.POINT, userRechargeInfo.getUserId(), userRechargeInfo.getMoney()))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户金额失败！");
			}
			// 写入日志--这里要充值成功了才写入日志
			userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userRechargeInfo.getUserId(), userRechargeInfo.getMoney(), MoneyUnit.POINT.getType(),
				UserBalanceLogType.RECHARGE.getType(), userRechargeInfo.getRechargeId(), null, null, null, String.format("用户充值%.2f元。", (double) userRechargeInfo.getMoney() / 100), current));
		}
		userRechargeInfo.setStatus(status);
		return new ReturnInfo(ErrorInfo.SUCCESS, GsonTools.toJson(userRechargeInfo));
	}
}
