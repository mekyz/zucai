package com.bet.services;

import com.lrcall.common.models.ReturnInfo;

public interface CRechargeService
{
	/**
	 * 更新充值审核状态
	 * 
	 * @param rechargeId
	 * @param status
	 * @return
	 */
	public ReturnInfo updateUserRechargeInfoVerify(String rechargeId, byte status);
}
