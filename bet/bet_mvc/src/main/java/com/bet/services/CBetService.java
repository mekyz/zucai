package com.bet.services;

import com.bet.orms.UserBetInfo;
import com.lrcall.common.models.ReturnInfo;

public interface CBetService
{
	/**
	 * 会员下注
	 * 
	 * @param userBetInfo
	 * @return
	 */
	public ReturnInfo addUserBetInfo(UserBetInfo userBetInfo);

	/**
	 * 会员撤回下注
	 * 
	 * @param betId
	 *            下注ID
	 * @param remark
	 *            备注信息
	 * @return
	 */
	public ReturnInfo deleteUserBetInfo(String betId, String remark);

	/**
	 * 把赛事下注的记录都撤回
	 * 
	 * @param phaseId
	 *            赛事ID
	 * @param remark
	 *            备注信息
	 * @return
	 */
	public ReturnInfo deleteUserBetInfosByPhaseId(String phaseId, String remark);
}
