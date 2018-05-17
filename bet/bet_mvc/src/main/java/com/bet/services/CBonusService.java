package com.bet.services;

import com.lrcall.common.models.ReturnInfo;

public interface CBonusService
{
	/**
	 * 计算用户买中的奖金
	 * 
	 * @param betId
	 *            下注ID
	 * @return
	 */
	public ReturnInfo checkAndUpdateUserBetBonusJob(String betId);

	/**
	 * 计算每场比赛的奖金<br>
	 * 根据比赛ID查询波胆信息，然后根据波胆信息去查询用户下注信息
	 * 
	 * @param phaseId
	 *            比赛的ID
	 * @return
	 */
	// public ReturnInfo checkAndUpdateMatchBonusJob(String phaseId);
	/**
	 * 计算团队奖金<br>
	 * 根据用户买中的记录返
	 * 
	 * @param betId
	 *            下注ID
	 * @return
	 */
	public ReturnInfo checkAndUpdateUserBetTeamBonusJob(String betId);

	/**
	 * 汇总每场比赛的团队奖金<br>
	 * 根据每场比赛用户下注的团队奖金进行汇总
	 * 
	 * @param phaseId
	 *            比赛的ID
	 * @return
	 */
	public ReturnInfo checkAndUpdateMatchTeamBonusJob(String phaseId);

	/**
	 * 发放用户的团队奖金<br>
	 * 
	 * @param bonusId
	 *            奖金ID
	 * @return
	 */
	public ReturnInfo checkAndUpdateUserBonusInfoJob(String bonusId);

	/**
	 * 统计赛事的金额<br>
	 * 
	 * @param phaseId
	 *            比赛的ID
	 * @return
	 */
	public ReturnInfo checkAndUpdateMatchStatisticsJob(String phaseId);

	/**
	 * 获取用户下级投注总额
	 * 
	 * @param userId
	 * @return
	 */
	public long getSubUserBetMoney(String phaseId, String userId);

	/**
	 * 统计每天的数据
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public ReturnInfo checkAndUpdateDayStatisticsJob(int year, int month, int day);

	/**
	 * 统计每月的数据
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public ReturnInfo checkAndUpdateMonthStatisticsJob(int year, int month);

	/**
	 * 统计用户每天的数据
	 * 
	 * @param userId
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public ReturnInfo checkAndUpdateDayUserStatisticsJob(String userId, int year, int month, int day);

	/**
	 * 撤回赛事的奖金
	 * 
	 * @param phaseId
	 *            赛事ID
	 * @return
	 */
	public ReturnInfo checkAndUpdateRemoveMatchBonusJob(String phaseId);

	/**
	 * 撤回赛事的奖金
	 * 
	 * @param betId
	 *            下注ID
	 * @return
	 */
	public ReturnInfo checkAndUpdateRemoveBetBonusJob(String betId);
}
