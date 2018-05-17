package com.bet.services;

import com.bet.orms.WithdrawInfo;
import com.lrcall.common.models.ReturnInfo;

public interface CWithdrawService
{
	/**
	 * 用户申请提现
	 * 
	 * @param withdrawInfo
	 * @return
	 */
	public ReturnInfo addWidthdrawInfo(WithdrawInfo withdrawInfo);

	/**
	 * 更新提现审核状态
	 * 
	 * @param withdrawId
	 * @param remark
	 * @param status
	 * @return
	 */
	public ReturnInfo updateWidthdrawInfoVerify(String withdrawId, String remark, byte status);

	/**
	 * 更新提现已完成
	 * 
	 * @param withdrawId
	 * @return
	 */
	public ReturnInfo updateWidthdrawInfoProcessed(String withdrawId);
}
