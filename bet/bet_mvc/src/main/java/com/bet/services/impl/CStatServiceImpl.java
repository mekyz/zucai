package com.bet.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IMatchInfoDao;
import com.bet.daos.IUserBetInfoDao;
import com.bet.daos.IUserBonusInfoDao;
import com.bet.orms.MatchInfo;
import com.bet.orms.UserBetInfo;
import com.bet.orms.UserBonusInfo;
import com.bet.orms.UserInfo;
import com.bet.services.CBonusService;
import com.bet.services.CStatService;
import com.bet.services.IConfigInfoService;
import com.bet.services.IMatchInfoService;
import com.bet.services.IUserBetInfoService;
import com.bet.services.IUserBonusInfoService;
import com.bet.services.IUserInfoService;
import com.bet.utils.BetConstValues;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.GsonTools;
import com.lrcall.common.utils.LogTools;
import com.lrcall.common.utils.TimeTools;

@Repository("cStatService")
public class CStatServiceImpl implements CStatService
{
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IMatchInfoService matchInfoService;
	@Autowired
	private CBonusService cBonusService;
	@Autowired
	private IUserBetInfoService userBetInfoService;
	@Autowired
	private IUserBonusInfoService userBonusInfoService;
	@Autowired
	private IConfigInfoService configInfoService;
	// @Autowired
	// private IMatchProfitInfoService matchProfitInfoService;

	@Override
	public void updateStatJob()
	{
		updateMatchStatisticsJob();
		updateDayStatisticsJob();
		updateMonthStatisticsJob();
		updateDayUserStatisticsJob();
	}

	@Override
	public ReturnInfo updateCheckUserValideDateJob()
	{
		LogTools.getInstance().info("updateCheckUserValidateDateJob", "开始检查用户有效期情况");
		int start = 0;
		int size = 100;
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		List<UserInfo> userInfoList = null;
		int successCount = 0, failCount = 0;
		while (true)
		{
			userInfoList = userInfoService.getUserInfoList(start, size, orderInfos, searchInfos);
			if (userInfoList == null || userInfoList.size() < 1)
			{
				break;
			}
			for (UserInfo userInfo : userInfoList)
			{
				try
				{
					ReturnInfo returnInfo = userInfoService.checkAndUpdateUserInfoJob(userInfo.getUserId());
					LogTools.getInstance().info("updateCheckUserValidateDateJob", "用户有效期情况:" + GsonTools.toJson(userInfo) + "，检查结果：" + GsonTools.toJson(returnInfo));
					if (ReturnInfo.isSuccess(returnInfo))
					{
						successCount++;
					}
					else
					{
						failCount++;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					failCount++;
				}
			}
			if (userInfoList.size() < size)// 取完了结束循环
			{
				break;
			}
			else
			{
				start += size;
			}
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，失败：%d个。", successCount, failCount));
	}

	@Override
	public ReturnInfo updateCheckUserBetBonusJob()
	{
		LogTools.getInstance().info("updateCheckUserBetBonusJob", "开始计算用户下注结果");
		int start = 0;
		int size = 100;
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.TEAM_PROFIT_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		List<UserBetInfo> userBetInfoList = null;
		int successCount = 0, failCount = 0;
		while (true)
		{
			userBetInfoList = userBetInfoService.getUserBetInfoList(start, size, orderInfos, searchInfos);
			if (userBetInfoList == null || userBetInfoList.size() < 1)
			{
				break;
			}
			for (UserBetInfo userBetInfo : userBetInfoList)
			{
				try
				{
					ReturnInfo returnInfo = cBonusService.checkAndUpdateUserBetBonusJob(userBetInfo.getBetId());
					LogTools.getInstance().info("updateCheckUserBetBonusJob", "计算用户下注:" + GsonTools.toJson(userBetInfo) + "的奖金结果：" + GsonTools.toJson(returnInfo));
					if (ReturnInfo.isSuccess(returnInfo))
					{
						successCount++;
					}
					else
					{
						failCount++;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					failCount++;
				}
			}
			if (userBetInfoList.size() < size)// 取完了结束循环
			{
				break;
			}
			else
			{
				start += size;
			}
		}
		// try
		// {
		// start = 0;
		// size = 10;
		// orderInfos.clear();
		// List<MatchProfitInfo> matchProfitInfoList = null;
		// while (true)
		// {
		// matchProfitInfoList = matchProfitInfoService.getMatchProfitInfoList(start, size, orderInfos, searchInfos);
		// if (matchProfitInfoList == null || matchProfitInfoList.size() < 1)
		// {
		// break;
		// }
		// for (MatchProfitInfo matchProfitInfo : matchProfitInfoList)
		// {
		// try
		// {
		// boolean b = matchProfitInfoService.checkAndUpdateSaledAmount(matchProfitInfo.getProfitId());
		// LogTools.getInstance().info("updateCheckUserBetBonusJob", "计算用户下注:" + GsonTools.toJson(matchProfitInfo) + "的奖金结果：" + GsonTools.toJson(b));
		// // if (b)
		// // {
		// // successCount++;
		// // }
		// // else
		// // {
		// // failCount++;
		// // }
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// failCount++;
		// }
		// }
		// if (userBetInfoList.size() < size)// 取完了结束循环
		// {
		// break;
		// }
		// else
		// {
		// start += size;
		// }
		// }
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// }
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，失败：%d个。", successCount, failCount));
	}

	@Override
	public ReturnInfo updateCheckUserBetTeamBonusJob()
	{
		LogTools.getInstance().info("updateCheckUserBetTeamBonusJob", "开始计算用户下注团队奖金结果");
		int start = 0;
		int size = 100;
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.TEAM_PROFIT_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		List<UserBetInfo> userBetInfoList = null;
		int successCount = 0, failCount = 0;
		while (true)
		{
			userBetInfoList = userBetInfoService.getUserBetInfoList(start, size, orderInfos, searchInfos);
			if (userBetInfoList == null || userBetInfoList.size() < 1)
			{
				break;
			}
			for (UserBetInfo userBetInfo : userBetInfoList)
			{
				try
				{
					ReturnInfo returnInfo = cBonusService.checkAndUpdateUserBetTeamBonusJob(userBetInfo.getBetId());
					LogTools.getInstance().info("updateCheckMatchBonusJob", "计算用户下注:" + GsonTools.toJson(userBetInfo) + "的奖金结果：" + GsonTools.toJson(returnInfo));
					if (ReturnInfo.isSuccess(returnInfo))
					{
						successCount++;
					}
					else
					{
						failCount++;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					failCount++;
				}
			}
			if (userBetInfoList.size() < size)// 取完了结束循环
			{
				break;
			}
			else
			{
				start += size;
			}
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，未处理：%d个。", successCount, failCount));
	}

	@Override
	public ReturnInfo updateCheckTeamBonusJob()
	{
		LogTools.getInstance().info("updateCheckTeamBonusJob", "开始计算比赛的团队奖金情况");
		int start = 0;
		int size = 100;
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IMatchInfoDao.HALF_PROFIT_STATUS, StatusType.DISABLED.getStatus() + "", true, true));
		searchInfos.add(new TableSearchInfo(IMatchInfoDao.FINAL_PROFIT_STATUS, StatusType.DISABLED.getStatus() + "", true, true));
		searchInfos.add(new TableSearchInfo(IMatchInfoDao.LEADER_PROFIT_STATUS, StatusType.DISABLED.getStatus() + "", true, true));
		List<MatchInfo> matchInfoList = null;
		int successCount = 0, failCount = 0;
		while (true)
		{
			long current = System.currentTimeMillis();
			matchInfoList = matchInfoService.getMatchInfoList(start, size, orderInfos, searchInfos,
				current - configInfoService.getIntValue(BetConstValues.CONFIG_CHECK_HOUR) * BetConstValues.ONE_DAY_TIME_LONG / 24, current);
			if (matchInfoList == null || matchInfoList.size() < 1)
			{
				break;
			}
			for (MatchInfo matchInfo : matchInfoList)
			{
				try
				{
					ReturnInfo returnInfo = cBonusService.checkAndUpdateMatchTeamBonusJob(matchInfo.getPhaseId());
					LogTools.getInstance().info("updateCheckTeamBonusJob", "计算比赛:" + GsonTools.toJson(matchInfo) + "的团队奖金结果：" + GsonTools.toJson(returnInfo));
					if (ReturnInfo.isSuccess(returnInfo))
					{
						successCount++;
					}
					else
					{
						failCount++;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					failCount++;
				}
			}
			if (matchInfoList.size() < size)// 取完了结束循环
			{
				break;
			}
			else
			{
				start += size;
			}
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，未处理：%d个。", successCount, failCount));
	}

	@Override
	public ReturnInfo updateTeamBonusJob()
	{
		LogTools.getInstance().info("updateTeamBonusJob", "开始发放团队奖金");
		int start = 0;
		int size = 100;
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IUserBonusInfoDao.STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		List<UserBonusInfo> userBonusInfoList = null;
		int successCount = 0, failCount = 0;
		while (true)
		{
			userBonusInfoList = userBonusInfoService.getUserBonusInfoList(start, size, orderInfos, searchInfos);
			if (userBonusInfoList == null || userBonusInfoList.size() < 1)
			{
				break;
			}
			for (UserBonusInfo userBonusInfo : userBonusInfoList)
			{
				try
				{
					ReturnInfo returnInfo = cBonusService.checkAndUpdateUserBonusInfoJob(userBonusInfo.getBonusId());
					LogTools.getInstance().info("updateTeamBonusJob", "发放团队奖金:" + GsonTools.toJson(userBonusInfo) + "的团队奖金结果：" + GsonTools.toJson(returnInfo));
					if (ReturnInfo.isSuccess(returnInfo))
					{
						successCount++;
					}
					else
					{
						failCount++;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					failCount++;
				}
			}
			if (userBonusInfoList.size() < size)// 取完了结束循环
			{
				break;
			}
			else
			{
				start += size;
			}
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，失败：%d个。", successCount, failCount));
	}

	@Override
	public ReturnInfo updateMatchStatisticsJob()
	{
		LogTools.getInstance().info("updateMatchStatisticsJob", "开始统计赛事的金额");
		int start = 0;
		int size = 100;
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IMatchInfoDao.FINAL_PROFIT_STATUS, StatusType.ENABLED.getStatus() + "", true, false));
		searchInfos.add(new TableSearchInfo(IMatchInfoDao.HALF_PROFIT_STATUS, StatusType.ENABLED.getStatus() + "", true, false));
		searchInfos.add(new TableSearchInfo(IMatchInfoDao.LEADER_PROFIT_STATUS, StatusType.ENABLED.getStatus() + "", true, false));
		List<MatchInfo> matchInfoList = null;
		int successCount = 0, failCount = 0;
		int count = configInfoService.getIntValue(BetConstValues.CONFIG_STATISTICS_CHECK_DAY);
		while (true)
		{
			long current = System.currentTimeMillis();
			matchInfoList = matchInfoService.getMatchInfoList(start, size, orderInfos, searchInfos, current - count * BetConstValues.ONE_DAY_TIME_LONG, current);
			if (matchInfoList == null || matchInfoList.size() < 1)
			{
				break;
			}
			for (MatchInfo matchInfo : matchInfoList)
			{
				try
				{
					ReturnInfo returnInfo = cBonusService.checkAndUpdateMatchStatisticsJob(matchInfo.getPhaseId());
					LogTools.getInstance().info("updateCheckMatchBonusJob", "统计比赛:" + GsonTools.toJson(matchInfo) + "的结果：" + GsonTools.toJson(returnInfo));
					if (ReturnInfo.isSuccess(returnInfo))
					{
						successCount++;
					}
					else
					{
						failCount++;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					failCount++;
				}
			}
			if (matchInfoList.size() < size)// 取完了结束循环
			{
				break;
			}
			else
			{
				start += size;
			}
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，未处理：%d个。", successCount, failCount));
	}

	@Override
	public ReturnInfo updateDayStatisticsJob()
	{
		LogTools.getInstance().info("updateDayStatisticsJob", "更新每天数据统计");
		// 更新昨天的信息
		long current = System.currentTimeMillis();
		int successCount = 0, failCount = 0;
		int count = configInfoService.getIntValue(BetConstValues.CONFIG_STATISTICS_CHECK_DAY);
		int index = 0;
		while (index <= count)
		{
			try
			{
				current = System.currentTimeMillis() - index * BetConstValues.ONE_DAY_TIME_LONG;
				int year = TimeTools.getYearInTime(current);
				int month = TimeTools.getMonthInTime(current);
				int day = TimeTools.getDayInTime(current);
				ReturnInfo returnInfo = cBonusService.checkAndUpdateDayStatisticsJob(year, month, day);
				LogTools.getInstance().info("updateCheckMatchBonusJob", "统计每天数据:" + GsonTools.toJson(year + "" + month + "" + day) + "的结果：" + GsonTools.toJson(returnInfo));
				if (ReturnInfo.isSuccess(returnInfo))
				{
					successCount++;
				}
				else
				{
					failCount++;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				failCount++;
			}
			index++;
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，失败：%d个。", successCount, failCount));
	}

	@Override
	public ReturnInfo updateMonthStatisticsJob()
	{
		LogTools.getInstance().info("updateMonthStatisticsJob", "更新每月数据统计");
		long current = System.currentTimeMillis();
		int successCount = 0, failCount = 0;
		// int count = configInfoService.getIntValue(BetConstValues.CONFIG_STATISTICS_CHECK_DAY);
		// int index = 1;
		// while (index <= count)
		{
			try
			{
				int year = TimeTools.getYearInTime(current);
				int month = TimeTools.getMonthInTime(current);
				ReturnInfo returnInfo = cBonusService.checkAndUpdateMonthStatisticsJob(year, month);
				LogTools.getInstance().info("updateMonthStatisticsJob", "统计每月数据:" + GsonTools.toJson(year + "" + month) + "的结果：" + GsonTools.toJson(returnInfo));
				if (ReturnInfo.isSuccess(returnInfo))
				{
					successCount++;
				}
				else
				{
					failCount++;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				failCount++;
			}
			// index++;
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，失败：%d个。", successCount, failCount));
	}

	@Override
	public ReturnInfo updateDayUserStatisticsJob()
	{
		LogTools.getInstance().info("updateDayUserStatisticsJob", "更新用户每天数据统计");
		int start = 0;
		int size = 100;
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		List<UserInfo> userInfoList = null;
		int successCount = 0, failCount = 0;
		int count = configInfoService.getIntValue(BetConstValues.CONFIG_STATISTICS_CHECK_DAY);
		while (true)
		{
			userInfoList = userInfoService.getUserInfoList(start, size, orderInfos, searchInfos);
			if (userInfoList == null || userInfoList.size() < 1)
			{
				break;
			}
			for (UserInfo userInfo : userInfoList)
			{
				try
				{
					long current = System.currentTimeMillis();
					int index = 1;
					while (index <= count)
					{
						try
						{
							current = System.currentTimeMillis() - index * BetConstValues.ONE_DAY_TIME_LONG;
							int year = TimeTools.getYearInTime(current);
							int month = TimeTools.getMonthInTime(current);
							int day = TimeTools.getDayInTime(current);
							ReturnInfo returnInfo = cBonusService.checkAndUpdateDayUserStatisticsJob(userInfo.getUserId(), year, month, day);
							LogTools.getInstance().info("updateDayUserStatisticsJob",
								"统计用户每天数据:" + GsonTools.toJson(userInfo.getUserId() + "->" + year + "" + month + "" + day) + "的结果：" + GsonTools.toJson(returnInfo));
							if (ReturnInfo.isSuccess(returnInfo))
							{
								successCount++;
							}
							else
							{
								failCount++;
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
							failCount++;
						}
						index++;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					failCount++;
				}
			}
			if (userInfoList.size() < size)// 取完了结束循环
			{
				break;
			}
			else
			{
				start += size;
			}
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，失败：%d个。", successCount, failCount));
	}

	@Override
	public ReturnInfo updateCheckUserParentsJob()
	{
		LogTools.getInstance().info("updateCheckUserParentsJob", "开始检查用户父节点情况");
		int start = 0;
		int size = 100;
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		List<UserInfo> userInfoList = null;
		int successCount = 0, failCount = 0;
		while (true)
		{
			userInfoList = userInfoService.getUserInfoList(start, size, orderInfos, searchInfos);
			if (userInfoList == null || userInfoList.size() < 1)
			{
				break;
			}
			for (UserInfo userInfo : userInfoList)
			{
				try
				{
					ReturnInfo returnInfo = userInfoService.checkAndUpdateUserParentInfoJob(userInfo.getUserId());
					LogTools.getInstance().info("updateCheckUserParentsJob", "用户父节点:" + GsonTools.toJson(userInfo) + "，检查结果：" + GsonTools.toJson(returnInfo));
					if (ReturnInfo.isSuccess(returnInfo))
					{
						successCount++;
					}
					else
					{
						failCount++;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					failCount++;
				}
			}
			if (userInfoList.size() < size)// 取完了结束循环
			{
				break;
			}
			else
			{
				start += size;
			}
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，失败：%d个。", successCount, failCount));
	}
}
