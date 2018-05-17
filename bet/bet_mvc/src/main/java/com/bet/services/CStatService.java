package com.bet.services;

import com.lrcall.common.models.ReturnInfo;

/**
 * 数据统计服务类
 * 
 * @author libit
 */
public interface CStatService
{
	/**
	 * 总的定时任务
	 */
	public void updateStatJob();

	/**
	 * 检查用户有效期任务
	 */
	public ReturnInfo updateCheckUserValideDateJob();

	/**
	 * 检查用户父节点任务
	 */
	public ReturnInfo updateCheckUserParentsJob();

	/**
	 * 计算用户下注的奖金
	 */
	public ReturnInfo updateCheckUserBetBonusJob();

	/**
	 * 计算下注的团队奖金
	 */
	public ReturnInfo updateCheckUserBetTeamBonusJob();

	/**
	 * 计算每场比赛下注的团队奖金
	 */
	public ReturnInfo updateCheckTeamBonusJob();

	/**
	 * 发放用户的团队奖金
	 */
	public ReturnInfo updateTeamBonusJob();

	/**
	 * 统计赛事的金额
	 */
	public ReturnInfo updateMatchStatisticsJob();

	/**
	 * 定时任务统计每天数据
	 */
	public ReturnInfo updateDayStatisticsJob();

	/**
	 * 定时任务统计每月数据
	 */
	public ReturnInfo updateMonthStatisticsJob();

	/**
	 * 定时任务统计用户每天数据
	 */
	public ReturnInfo updateDayUserStatisticsJob();
}
