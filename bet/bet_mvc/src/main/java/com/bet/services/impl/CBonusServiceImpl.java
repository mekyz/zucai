package com.bet.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IDayStatisticsLogInfoDao;
import com.bet.daos.IDayUserStatisticsLogInfoDao;
import com.bet.daos.IMatchInfoDao;
import com.bet.daos.IMonthStatisticsLogInfoDao;
import com.bet.daos.ITeamLeaderProfitConfigInfoDao;
import com.bet.daos.ITeamLeaderProfitInfoDao;
import com.bet.daos.ITeamProfitInfoDao;
import com.bet.daos.IUserBalanceLogInfoDao;
import com.bet.daos.IUserBetInfoDao;
import com.bet.daos.IUserBonusInfoDao;
import com.bet.daos.IUserInfoDao;
import com.bet.daos.IUserRechargeInfoDao;
import com.bet.daos.IWithdrawInfoDao;
import com.bet.enums.BodanType;
import com.bet.enums.MoneyUnit;
import com.bet.enums.MsgType;
import com.bet.enums.TeamProfitType;
import com.bet.enums.UserBalanceLogType;
import com.bet.enums.UserType;
import com.bet.models.UserTeamProfit;
import com.bet.orms.DayStatisticsLogInfo;
import com.bet.orms.DayUserStatisticsLogInfo;
import com.bet.orms.MatchInfo;
import com.bet.orms.MatchProfitInfo;
import com.bet.orms.MatchStatisticsLogInfo;
import com.bet.orms.MonthStatisticsLogInfo;
import com.bet.orms.MsgInfo;
import com.bet.orms.TeamLeaderProfitConfigInfo;
import com.bet.orms.TeamLeaderProfitInfo;
import com.bet.orms.TeamProfitInfo;
import com.bet.orms.TeamProfitRateInfo;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.orms.UserBetInfo;
import com.bet.orms.UserBonusInfo;
import com.bet.orms.UserInfo;
import com.bet.services.CBonusService;
import com.bet.services.IConfigInfoService;
import com.bet.services.IDayStatisticsLogInfoService;
import com.bet.services.IDayUserStatisticsLogInfoService;
import com.bet.services.IMatchInfoService;
import com.bet.services.IMatchProfitInfoService;
import com.bet.services.IMatchStatisticsLogInfoService;
import com.bet.services.IMonthStatisticsLogInfoService;
import com.bet.services.IMsgInfoService;
import com.bet.services.ITeamLeaderProfitConfigInfoService;
import com.bet.services.ITeamLeaderProfitInfoService;
import com.bet.services.ITeamProfitInfoService;
import com.bet.services.ITeamProfitRateInfoService;
import com.bet.services.IUserBalanceLogInfoService;
import com.bet.services.IUserBetInfoService;
import com.bet.services.IUserBonusInfoService;
import com.bet.services.IUserInfoService;
import com.bet.services.IUserRechargeInfoService;
import com.bet.services.IWithdrawInfoService;
import com.bet.utils.BetConstValues;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.GsonTools;
import com.lrcall.common.utils.LogTools;
import com.lrcall.common.utils.StringTools;
import com.lrcall.lrweb.common.enums.SqlOrderType;

@Repository("cBonusService")
public class CBonusServiceImpl implements CBonusService
{
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IUserBetInfoService userBetInfoService;
	@Autowired
	private IUserBalanceLogInfoService userBalanceLogInfoService;
	@Autowired
	private IMatchInfoService matchInfoService;
	@Autowired
	private IMatchProfitInfoService matchProfitInfoService;
	@Autowired
	private IConfigInfoService configInfoService;
	@Autowired
	private ITeamProfitRateInfoService teamProfitRateInfoService;
	@Autowired
	private ITeamProfitInfoService teamProfitInfoService;
	@Autowired
	private IUserBonusInfoService userBonusInfoService;
	@Autowired
	private ITeamLeaderProfitConfigInfoService teamLeaderProfitConfigInfoService;
	@Autowired
	private ITeamLeaderProfitInfoService teamLeaderProfitInfoService;
	@Autowired
	private IMatchStatisticsLogInfoService matchStatisticsLogInfoService;
	@Autowired
	private IDayStatisticsLogInfoService dayStatisticsLogInfoService;
	@Autowired
	private IMonthStatisticsLogInfoService monthStatisticsLogInfoService;
	@Autowired
	private IDayUserStatisticsLogInfoService dayUserStatisticsLogInfoService;
	@Autowired
	private IWithdrawInfoService withdrawInfoService;
	@Autowired
	private IUserRechargeInfoService userRechargeInfoService;
	@Autowired
	private IMsgInfoService msgInfoService;

	@Override
	public ReturnInfo checkAndUpdateUserBetBonusJob(String betId)
	{
		UserBetInfo userBetInfo = userBetInfoService.getUserBetInfoByBetId(betId);
		if (userBetInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "下注ID不存在！");
		}
		if (userBetInfo.getProfitStatus() != null && userBetInfo.getProfitStatus() == StatusType.ENABLED.getStatus())
		{
			return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "奖金已发放！");
		}
		MatchProfitInfo matchProfitInfo = matchProfitInfoService.getMatchProfitInfoByProfitId(userBetInfo.getProfitId());
		if (matchProfitInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "比赛波胆ID不存在！");
		}
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(matchProfitInfo.getPhaseId());
		if (matchInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "比赛ID不存在！");
		}
		UserInfo userInfo = userInfoService.getUserInfoByUserId(userBetInfo.getUserId());
		if (userInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "用户ID不存在！");
		}
		boolean isWin = false;
		boolean is4 = false;
		if (matchProfitInfo.getMatchType().equals(BodanType.FULL.getType()))
		{
			if (matchInfo.getFinalScore1() == null || matchInfo.getFinalScore2() == null)
			{
				return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "全场比赛还未结束！");
			}
			if (matchProfitInfo.getScore1() == 4 && matchProfitInfo.getScore2() == 4)
			{
				is4 = true;
			}
			if (matchProfitInfo.getScore1() != matchInfo.getFinalScore1() || matchProfitInfo.getScore2() != matchInfo.getFinalScore2())
			{
				isWin = true;
			}
		}
		else if (matchProfitInfo.getMatchType().equals(BodanType.HALF.getType()))
		{
			if (matchInfo.getHalfScore1() == null || matchInfo.getHalfScore2() == null)
			{
				return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "半场比赛还未结束！");
			}
			if (matchProfitInfo.getScore1() == 4 && matchProfitInfo.getScore2() == 4)
			{
				is4 = true;
			}
			if (matchProfitInfo.getScore1() != matchInfo.getHalfScore1() || matchProfitInfo.getScore2() != matchInfo.getHalfScore2())
			{
				isWin = true;
			}
		}
		long current = System.currentTimeMillis();
		Map<String, Object> valueMap = new HashMap<>();
		valueMap.put(IUserBetInfoDao.WIN_STATUS, isWin ? StatusType.ENABLED.getStatus() : StatusType.DISABLED.getStatus());
		long userMoney = 0;
		if (is4)
		{
			if (isWin)
			{
				int rate = configInfoService.getIntValue(BetConstValues.CONFIG_BET_FEE);
				long winMoney = (int) (userBetInfo.getMoney() * matchProfitInfo.getProfitPercent() / 10000);
				// if (userBetInfo.getMoneyUnit() == MoneyUnit.GIVE_POINT.getType())
				// {
				// if (userInfo.getGivePoint() < 0)
				// {
				// winMoney = 0;
				// }
				// }
				// else
				// {
				// if (userInfo.getPoint() < 0)
				// {
				// winMoney = 0;
				// }
				// }
				long fee = winMoney * rate / 100;
				userMoney = winMoney - fee;// 奖金
				valueMap.put(IUserBetInfoDao.WIN_MONEY, winMoney);
				valueMap.put(IUserBetInfoDao.USER_MONEY, userBetInfo.getMoney() + userMoney);
				valueMap.put(IUserBetInfoDao.FEE, fee);
			}
			else
			{
				valueMap.put(IUserBetInfoDao.WIN_MONEY, 0);
				valueMap.put(IUserBetInfoDao.USER_MONEY, userBetInfo.getMoney());
				valueMap.put(IUserBetInfoDao.FEE, 0);
			}
		}
		else
		{
			if (isWin)
			{
				int rate = configInfoService.getIntValue(BetConstValues.CONFIG_BET_FEE);
				long winMoney = (int) (userBetInfo.getMoney() * matchProfitInfo.getProfitPercent() / 10000);
				// if (userBetInfo.getMoneyUnit() == MoneyUnit.GIVE_POINT.getType())
				// {
				// if (userInfo.getGivePoint() < 0)
				// {
				// winMoney = 0;
				// }
				// }
				// else
				// {
				// if (userInfo.getPoint() < 0)
				// {
				// winMoney = 0;
				// }
				// }
				long fee = winMoney * rate / 100;
				userMoney = winMoney - fee;// 奖金
				valueMap.put(IUserBetInfoDao.WIN_MONEY, winMoney);
				valueMap.put(IUserBetInfoDao.USER_MONEY, userBetInfo.getMoney() + userMoney);
				valueMap.put(IUserBetInfoDao.FEE, fee);
			}
			else
			{
				valueMap.put(IUserBetInfoDao.WIN_MONEY, 0);
				valueMap.put(IUserBetInfoDao.USER_MONEY, 0);
				valueMap.put(IUserBetInfoDao.FEE, 0);
			}
		}
		valueMap.put(IUserBetInfoDao.PROFIT_STATUS, StatusType.ENABLED.getStatus());
		if (userBetInfo.getMoneyUnit() == MoneyUnit.GIVE_POINT.getType())
		{
			valueMap.put(IUserBetInfoDao.TEAM_PROFIT_STATUS, StatusType.ENABLED.getStatus());
		}
		if (!userBetInfoService.updateProfitValueByBetId(betId, valueMap))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新下注结果失败！");
		}
		// 如果中了返到用户余额中
		if (is4)
		{
			if (userBetInfo.getMoneyUnit() == MoneyUnit.POINT.getType())
			{
				if (!userInfoService.updatePoint(IUserInfoDao.POINT, userBetInfo.getUserId(), userBetInfo.getMoney()))
				{
					throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新奖金到用户彩金钱包失败！");
				}
				// 写入日志
				userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBetInfo.getUserId(), userBetInfo.getMoney(), MoneyUnit.POINT.getType(),
					UserBalanceLogType.WIN_BET_RETURN.getType(), betId, userBetInfo.getPhaseId(), null, null,
					String.format("用户投注比赛%s的%s波胆%d:%d获奖返还本金。", matchInfo.getMatchName(), matchProfitInfo.getMatchType(), matchProfitInfo.getScore1(), matchProfitInfo.getScore2()), current));
			}
			else if (userBetInfo.getMoneyUnit() == MoneyUnit.GIVE_POINT.getType())
			{
				if (!userInfoService.updatePoint(IUserInfoDao.GIVE_POINT, userBetInfo.getUserId(), userBetInfo.getMoney()))
				{
					throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新奖金到用户彩金钱包失败！");
				}
				// 写入日志
				userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBetInfo.getUserId(), userBetInfo.getMoney(), MoneyUnit.GIVE_POINT.getType(),
					UserBalanceLogType.WIN_BET_RETURN.getType(), betId, userBetInfo.getPhaseId(), null, null,
					String.format("用户投注比赛%s的%s波胆%d:%d获奖返还体验彩金。", matchInfo.getMatchName(), matchProfitInfo.getMatchType(), matchProfitInfo.getScore1(), matchProfitInfo.getScore2()), current));
			}
			if (isWin && userMoney > 0)
			{
				if (userBetInfo.getMoneyUnit() == MoneyUnit.POINT.getType())
				{
					if (!userInfoService.updatePoint(IUserInfoDao.POINT, userBetInfo.getUserId(), userMoney))
					{
						throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新奖金到用户彩金钱包失败！");
					}
					// 写入日志
					userBalanceLogInfoService.addUserBalanceLogInfo(
						new UserBalanceLogInfo(userBetInfo.getUserId(), userMoney, MoneyUnit.POINT.getType(), UserBalanceLogType.WIN_BET.getType(), betId, userBetInfo.getPhaseId(), null, null,
							String.format("用户投注比赛%s的%s波胆%d:%d获奖。", matchInfo.getMatchName(), matchProfitInfo.getMatchType(), matchProfitInfo.getScore1(), matchProfitInfo.getScore2()), current));
				}
				else if (userBetInfo.getMoneyUnit() == MoneyUnit.GIVE_POINT.getType())
				{
					if (!userInfoService.updatePoint(IUserInfoDao.POINT, userBetInfo.getUserId(), userMoney))
					{
						throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新奖金到用户彩金钱包失败！");
					}
					// 写入日志
					userBalanceLogInfoService.addUserBalanceLogInfo(
						new UserBalanceLogInfo(userBetInfo.getUserId(), userMoney, MoneyUnit.POINT.getType(), UserBalanceLogType.WIN_BET.getType(), betId, userBetInfo.getPhaseId(), null, null,
							String.format("用户投注比赛%s的%s波胆%d:%d获奖。", matchInfo.getMatchName(), matchProfitInfo.getMatchType(), matchProfitInfo.getScore1(), matchProfitInfo.getScore2()), current));
				}
			}
		}
		else
		{
			if (userMoney > 0)
			{
				if (userBetInfo.getMoneyUnit() == MoneyUnit.POINT.getType())
				{
					if (!userInfoService.updatePoint(IUserInfoDao.POINT, userBetInfo.getUserId(), userBetInfo.getMoney() + userMoney))
					{
						throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新奖金到用户彩金钱包失败！");
					}
					// 写入日志
					userBalanceLogInfoService.addUserBalanceLogInfo(
						new UserBalanceLogInfo(userBetInfo.getUserId(), userMoney, MoneyUnit.POINT.getType(), UserBalanceLogType.WIN_BET.getType(), betId, userBetInfo.getPhaseId(), null, null,
							String.format("用户投注比赛%s的%s波胆%d:%d获奖。", matchInfo.getMatchName(), matchProfitInfo.getMatchType(), matchProfitInfo.getScore1(), matchProfitInfo.getScore2()), current));
					userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBetInfo.getUserId(), userBetInfo.getMoney(), MoneyUnit.POINT.getType(),
						UserBalanceLogType.WIN_BET_RETURN.getType(), betId, userBetInfo.getPhaseId(), null, null,
						String.format("用户投注比赛%s的%s波胆%d:%d获奖返还本金。", matchInfo.getMatchName(), matchProfitInfo.getMatchType(), matchProfitInfo.getScore1(), matchProfitInfo.getScore2()), current));
				}
				else if (userBetInfo.getMoneyUnit() == MoneyUnit.GIVE_POINT.getType())
				{
					if (!userInfoService.updatePoint(IUserInfoDao.POINT, userBetInfo.getUserId(), userMoney))
					{
						throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新奖金到用户彩金钱包失败！");
					}
					if (!userInfoService.updatePoint(IUserInfoDao.GIVE_POINT, userBetInfo.getUserId(), userBetInfo.getMoney()))
					{
						throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新奖金到用户彩金钱包失败！");
					}
					// 写入日志
					userBalanceLogInfoService.addUserBalanceLogInfo(
						new UserBalanceLogInfo(userBetInfo.getUserId(), userMoney, MoneyUnit.POINT.getType(), UserBalanceLogType.WIN_BET.getType(), betId, userBetInfo.getPhaseId(), null, null,
							String.format("用户投注比赛%s的%s波胆%d:%d获奖。", matchInfo.getMatchName(), matchProfitInfo.getMatchType(), matchProfitInfo.getScore1(), matchProfitInfo.getScore2()), current));
					userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBetInfo.getUserId(), userBetInfo.getMoney(), MoneyUnit.GIVE_POINT.getType(),
						UserBalanceLogType.WIN_BET_RETURN.getType(), betId, userBetInfo.getPhaseId(), null, null,
						String.format("用户投注比赛%s的%s波胆%d:%d获奖返还体验彩金。", matchInfo.getMatchName(), matchProfitInfo.getMatchType(), matchProfitInfo.getScore1(), matchProfitInfo.getScore2()), current));
				}
			}
		}
		if (isWin)
		{
			if (userBetInfo.getMoneyUnit() == MoneyUnit.GIVE_POINT.getType())
			{
				// 检查盈利是否已达到200
				List<TableSearchInfo> searchInfos = new ArrayList<>();
				searchInfos.add(new TableSearchInfo(IUserBetInfoDao.USER_ID, userBetInfo.getUserId(), true, false));
				searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MONEY_UNIT, MoneyUnit.GIVE_POINT.getType() + "", true, false));
				long wMoney = userBetInfoService.getSum(IUserBetInfoDao.WIN_MONEY, searchInfos, null, null);
				if (wMoney >= 20000)
				{
					Map<String, Object> valueMap1 = new HashMap<>();
					valueMap1.put(IUserInfoDao.GIVE_POINT, 0);
					if (!userInfoService.updateValueByUserId(userBetInfo.getUserId(), valueMap1))
					{
						throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户体验彩金失败！");
					}
					// 写入日志
					userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBetInfo.getUserId(),
						userInfo.getGivePoint() + (userBetInfo.getMoneyUnit() == MoneyUnit.GIVE_POINT.getType() ? userBetInfo.getMoney() : 0), MoneyUnit.GIVE_POINT.getType(),
						UserBalanceLogType.GIVE_POINT_INVALIDE.getType(), betId, userBetInfo.getPhaseId(), null, null, String.format("用户体验金盈利已达%.2f彩金，体验金被收回。", (double) wMoney / 100), current));
				}
			}
			// 添加到消息
			try
			{
				String userId = userBetInfo.getUserId();
				if (userId.length() > 5)
				{
					int endIndex = (userId.length() - 2) / 2 + (userId.length() % 2 == 0 ? 0 : 1);
					userId = String.format("%s**%s", userId.substring(0, endIndex), userId.substring(endIndex + 2));
				}
				String title = String.format("恭喜用户%s刚刚在%s赛事赢了%.2f彩金。", userId, matchInfo.getMatchName(), ((double) userMoney) / 100);
				msgInfoService.addMsgInfo(new MsgInfo(msgInfoService.genId(), MsgType.BET_WIN.getType(), title, userBetInfo.getUserId(), title, StatusType.ENABLED.getStatus(), current, current));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, "更新下注结果成功！");
	}

	@Override
	public ReturnInfo checkAndUpdateUserBetTeamBonusJob(String betId)
	{
		UserBetInfo userBetInfo = userBetInfoService.getUserBetInfoByBetId(betId);
		if (userBetInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "下注ID不存在！");
		}
		if (userBetInfo.getTeamProfitStatus() == StatusType.ENABLED.getStatus())
		{
			return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "团队奖金已发放！");
		}
		if (userBetInfo.getWinStatus() == null)// userBetInfo.getWinStatus() != StatusType.ENABLED.getStatus() ||亏损的也返
		{
			return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "还未开奖或未中奖！");
		}
		MatchProfitInfo matchProfitInfo = matchProfitInfoService.getMatchProfitInfoByProfitId(userBetInfo.getProfitId());
		if (matchProfitInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "比赛波胆ID不存在！");
		}
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(matchProfitInfo.getPhaseId());
		if (matchInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "比赛ID不存在！");
		}
		long current = System.currentTimeMillis();
		// 计算团队奖
		TeamProfitRateInfo agentTeamProfitRateInfo = teamProfitRateInfoService.getTeamProfitRateInfoByRateId(BetConstValues.CONFIG_TEAM_AGENT_RATE);
		TeamProfitRateInfo benefitTeamProfitRateInfo = teamProfitRateInfoService.getTeamProfitRateInfoByRateId(BetConstValues.CONFIG_TEAM_BENEFIT_RATE);
		int shareProfitRate = configInfoService.getIntValue(BetConstValues.CONFIG_SHARE_PROFT_RATE);// 分享佣金
		// int sameLevelProfitRate = configInfoService.getIntValue(BetConstValues.CONFIG_SAME_LEVEL_PROFIT_RATE);// 平级奖
		long winMoney = userBetInfo.getWinMoney();
		// if (winMoney < 1)
		// {
		// return new ReturnInfo(ErrorInfo.SUCCESS, "金额太小，无需返利！");
		// }
		UserInfo userInfo = userInfoService.getUserInfoByUserId(userBetInfo.getUserId());
		if (userInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "用户ID不存在！");
		}
		if (StringTools.isNull(userInfo.getReferrerId()))// 没有团队，直接更新结果返回
		{
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserBetInfoDao.TEAM_PROFIT_STATUS, StatusType.ENABLED.getStatus());
			if (!userBetInfoService.updateTeamProfitValueByBetId(betId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新下注结果团队返利失败！");
			}
			return new ReturnInfo(ErrorInfo.SUCCESS, "用户没有团队！");
		}
		UserInfo parentUserInfo = userInfoService.getUserInfoByUserId(userInfo.getReferrerId());
		List<UserInfo> parentUserInfoList = new ArrayList<>();
		if (userBetInfo.getWinStatus() == StatusType.ENABLED.getStatus())
		{
			if (parentUserInfo != null)// 分享佣金
			{
				if (parentUserInfo.getUserType() >= UserType.AGENT1.getType())// 代理才返利
				{
					long profitMoney = 0, profitUserMoney = 0, fee = 0;
					profitMoney = winMoney * 100 * shareProfitRate / 100;// 以0.01分为单位
					profitUserMoney = profitMoney - fee;
					TeamProfitInfo teamProfitInfo = new TeamProfitInfo(teamProfitInfoService.genId(), betId, userBetInfo.getUserId(), matchInfo.getPhaseId(), parentUserInfo.getUserId(),
						userBetInfo.getWinMoney() * 100, TeamProfitType.SHARE.getType(), profitMoney, profitUserMoney, fee, StatusType.DISABLED.getStatus(), parentUserInfo.getSysStatus(),
						StatusType.ENABLED.getStatus(), current, current);
					teamProfitInfoService.addTeamProfitInfo(teamProfitInfo);
				}
			}
			if (agentTeamProfitRateInfo != null && agentTeamProfitRateInfo.getStatus() == StatusType.ENABLED.getStatus())// 代理佣金
			{
				int totalPrecent = agentTeamProfitRateInfo.getType7();// 总的比率
				while (parentUserInfo != null)
				{
					int sharePrecent = 0;
					boolean canProfit = true, isSameLevel = false;
					if (parentUserInfo.getUserType() >= UserType.AGENT1.getType())// 代理才返利
					{
						UserInfo lastParentUserInfo = null;
						if (parentUserInfoList.size() > 0)
						{
							int index = 0;
							for (UserInfo userInfo1 : parentUserInfoList)
							{
								if (userInfo1.getUserType() > parentUserInfo.getUserType())// 如果级别小于直接父节点就不返
								{
									canProfit = false;
									break;
								}
								if (index == parentUserInfoList.size() - 1)
								{
									lastParentUserInfo = parentUserInfoList.get(index);
									if (parentUserInfo.getUserType() == userInfo1.getUserType())
									{
										isSameLevel = true;
									}
								}
								index++;
							}
						}
						if (canProfit)
						{
							long profitMoney = 0, profitUserMoney = 0, fee = 0;
							TeamProfitInfo teamProfitInfo = new TeamProfitInfo(teamProfitInfoService.genId(), betId, userBetInfo.getUserId(), matchInfo.getPhaseId(), parentUserInfo.getUserId(),
								userBetInfo.getWinMoney() * 100, TeamProfitType.AGENT.getType(), profitMoney, profitUserMoney, fee, StatusType.DISABLED.getStatus(), parentUserInfo.getSysStatus(),
								StatusType.ENABLED.getStatus(), current, current);
							int mostPrecent = totalPrecent - sharePrecent;
							int realPrecent = 0;
							if (mostPrecent < 0)
							{
								mostPrecent = 0;
							}
							else
							{
								if (isSameLevel)
								{
									realPrecent = agentTeamProfitRateInfo.getSameLevel();
									teamProfitInfo.setProfitType(TeamProfitType.SAME_LEVEL1.getType());
								}
								else
								{
									byte parentUserType = parentUserInfo.getUserType();
									if (parentUserType == UserType.AGENT1.getType())
									{
										realPrecent = agentTeamProfitRateInfo.getType1();
									}
									else if (parentUserType == UserType.AGENT2.getType())
									{
										realPrecent = agentTeamProfitRateInfo.getType2();
									}
									else if (parentUserType == UserType.AGENT3.getType())
									{
										realPrecent = agentTeamProfitRateInfo.getType3();
									}
									else if (parentUserType == UserType.AGENT4.getType())
									{
										realPrecent = agentTeamProfitRateInfo.getType4();
									}
									else if (parentUserType == UserType.AGENT5.getType())
									{
										realPrecent = agentTeamProfitRateInfo.getType5();
									}
									else if (parentUserType == UserType.AGENT6.getType())
									{
										realPrecent = agentTeamProfitRateInfo.getType6();
									}
									else if (parentUserType == UserType.AGENT7.getType())
									{
										realPrecent = agentTeamProfitRateInfo.getType7();
									}
									if (lastParentUserInfo != null)
									{
										byte lastParentUserType = lastParentUserInfo.getUserType();
										if (lastParentUserType == UserType.AGENT1.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType1();
										}
										else if (lastParentUserType == UserType.AGENT2.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType2();
										}
										else if (lastParentUserType == UserType.AGENT3.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType3();
										}
										else if (lastParentUserType == UserType.AGENT4.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType4();
										}
										else if (lastParentUserType == UserType.AGENT5.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType5();
										}
										else if (lastParentUserType == UserType.AGENT6.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType6();
										}
										else if (lastParentUserType == UserType.AGENT7.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType7();
										}
									}
								}
							}
							if (realPrecent > mostPrecent)
							{
								realPrecent = mostPrecent;
							}
							sharePrecent += realPrecent;
							if (realPrecent > 0)
							{
								profitMoney = winMoney * 100 * realPrecent / 10000;// 以0.01分为单位
								profitUserMoney = profitMoney - fee;
								teamProfitInfo.setProfitMoney(profitMoney);
								teamProfitInfo.setProfitUserMoney(profitUserMoney);
								teamProfitInfo.setFee(fee);
								teamProfitInfoService.addTeamProfitInfo(teamProfitInfo);
							}
						}
						parentUserInfoList.add(parentUserInfo);
					}
					if (sharePrecent >= totalPrecent)// 如果已经分完了则退出
					{
						break;
					}
					// 向上查找
					if (StringTools.isNull(parentUserInfo.getReferrerId()))
					{
						break;
					}
					parentUserInfo = userInfoService.getUserInfoByUserId(parentUserInfo.getReferrerId());
				}
			}
		}
		else if (userBetInfo.getWinStatus() == StatusType.DISABLED.getStatus())// 福利奖，排除4:4的福利，只计算未中奖的
		{
			if (benefitTeamProfitRateInfo != null && benefitTeamProfitRateInfo.getStatus() == StatusType.ENABLED.getStatus() && !(matchProfitInfo.getScore1() == 4 && matchProfitInfo.getScore2() == 4))
			{
				parentUserInfoList.clear();
				parentUserInfo = userInfoService.getUserInfoByUserId(userInfo.getReferrerId());
				int totalPrecent = benefitTeamProfitRateInfo.getType7();// 总的比率
				while (parentUserInfo != null)
				{
					int sharePrecent = 0;
					boolean canProfit = true, isSameLevel = false;
					if (parentUserInfo.getUserType() >= UserType.AGENT1.getType())// 代理才返利
					{
						UserInfo lastParentUserInfo = null;
						if (parentUserInfoList.size() > 0)
						{
							int index = 0;
							for (UserInfo userInfo1 : parentUserInfoList)
							{
								if (userInfo1.getUserType() > parentUserInfo.getUserType())// 如果级别小于直接父节点就不返
								{
									canProfit = false;
									break;
								}
								if (index == parentUserInfoList.size() - 1)
								{
									lastParentUserInfo = parentUserInfoList.get(index);
									if (parentUserInfo.getUserType() == userInfo1.getUserType())
									{
										isSameLevel = true;
									}
								}
								index++;
							}
						}
						if (canProfit)
						{
							long profitMoney = 0, profitUserMoney = 0, fee = 0;
							TeamProfitInfo teamProfitInfo = new TeamProfitInfo(teamProfitInfoService.genId(), betId, userBetInfo.getUserId(), matchInfo.getPhaseId(), parentUserInfo.getUserId(),
								userBetInfo.getWinMoney() * 100, TeamProfitType.BENEFIT.getType(), profitMoney, profitUserMoney, fee, StatusType.DISABLED.getStatus(), parentUserInfo.getSysStatus(),
								StatusType.ENABLED.getStatus(), current, current);
							int mostPrecent = totalPrecent - sharePrecent;
							int realPrecent = 0;
							if (mostPrecent < 0)
							{
								mostPrecent = 0;
							}
							else
							{
								if (isSameLevel)
								{
									realPrecent = benefitTeamProfitRateInfo.getSameLevel();
									teamProfitInfo.setProfitType(TeamProfitType.SAME_LEVEL2.getType());
								}
								else
								{
									if (parentUserInfo.getUserType() == UserType.AGENT1.getType())
									{
										realPrecent = benefitTeamProfitRateInfo.getType1();
									}
									else if (parentUserInfo.getUserType() == UserType.AGENT2.getType())
									{
										realPrecent = benefitTeamProfitRateInfo.getType2();
									}
									else if (parentUserInfo.getUserType() == UserType.AGENT3.getType())
									{
										realPrecent = benefitTeamProfitRateInfo.getType3();
									}
									else if (parentUserInfo.getUserType() == UserType.AGENT4.getType())
									{
										realPrecent = benefitTeamProfitRateInfo.getType4();
									}
									else if (parentUserInfo.getUserType() == UserType.AGENT5.getType())
									{
										realPrecent = benefitTeamProfitRateInfo.getType5();
									}
									else if (parentUserInfo.getUserType() == UserType.AGENT6.getType())
									{
										realPrecent = benefitTeamProfitRateInfo.getType6();
									}
									else if (parentUserInfo.getUserType() == UserType.AGENT7.getType())
									{
										realPrecent = benefitTeamProfitRateInfo.getType7();
									}
									if (lastParentUserInfo != null)
									{
										byte lastParentUserType = lastParentUserInfo.getUserType();
										if (lastParentUserType == UserType.AGENT1.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType1();
										}
										else if (lastParentUserType == UserType.AGENT2.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType2();
										}
										else if (lastParentUserType == UserType.AGENT3.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType3();
										}
										else if (lastParentUserType == UserType.AGENT4.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType4();
										}
										else if (lastParentUserType == UserType.AGENT5.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType5();
										}
										else if (lastParentUserType == UserType.AGENT6.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType6();
										}
										else if (lastParentUserType == UserType.AGENT7.getType())
										{
											realPrecent = realPrecent - agentTeamProfitRateInfo.getType7();
										}
									}
								}
							}
							if (realPrecent > mostPrecent)
							{
								realPrecent = mostPrecent;
							}
							sharePrecent += realPrecent;
							if (realPrecent > 0)
							{
								profitMoney = userBetInfo.getMoney() * 100 * realPrecent / 10000;// 以0.01分为单位
								profitUserMoney = profitMoney - fee;
								teamProfitInfo.setProfitMoney(profitMoney);
								teamProfitInfo.setProfitUserMoney(profitUserMoney);
								teamProfitInfo.setFee(fee);
								teamProfitInfoService.addTeamProfitInfo(teamProfitInfo);
							}
							else
							{
								// break;
							}
						}
						parentUserInfoList.add(parentUserInfo);
					}
					if (sharePrecent >= totalPrecent)// 如果已经分完了则退出
					{
						break;
					}
					// 向上查找
					if (StringTools.isNull(parentUserInfo.getReferrerId()))
					{
						break;
					}
					parentUserInfo = userInfoService.getUserInfoByUserId(parentUserInfo.getReferrerId());
				}
			}
		}
		Map<String, Object> valueMap = new HashMap<>();
		valueMap.put(IUserBetInfoDao.TEAM_PROFIT_STATUS, StatusType.ENABLED.getStatus());
		if (!userBetInfoService.updateTeamProfitValueByBetId(betId, valueMap))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新下注结果团队返利失败！");
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, "");
	}

	@Override
	public ReturnInfo checkAndUpdateMatchTeamBonusJob(String phaseId)
	{
		long current = System.currentTimeMillis();
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(phaseId);
		if (matchInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "比赛ID不存在！");
		}
		if (matchInfo.getMatchDate() > current)
		{
			return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "比赛还未到时间！");
		}
		if (matchInfo.getFinalScore1() == null || matchInfo.getFinalScore2() == null || matchInfo.getHalfScore1() == null || matchInfo.getHalfScore2() == null)
		{
			return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "比赛还未开奖！");
		}
		if (matchInfo.getHalfProfitStatus() == StatusType.ENABLED.getStatus() && matchInfo.getFinalProfitStatus() == StatusType.ENABLED.getStatus()
			&& matchInfo.getLeaderProfitStatus() == StatusType.ENABLED.getStatus())// 如果全场、半场、领导奖都发放过
		{
			return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "比赛已经计算过奖金！");
		}
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
		long profitCount = userBetInfoService.getUserBetInfoListCount(searchInfos);
		if (profitCount < 1)
		{
			// 比赛没有人押注，直接设成已返利
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IMatchInfoDao.HALF_PROFIT_STATUS, StatusType.ENABLED.getStatus());
			valueMap.put(IMatchInfoDao.FINAL_PROFIT_STATUS, StatusType.ENABLED.getStatus());
			valueMap.put(IMatchInfoDao.LEADER_PROFIT_STATUS, StatusType.ENABLED.getStatus());
			if (!matchInfoService.updateProfitValueByPhaseId(phaseId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新比赛奖金发放状态失败！");
			}
			return new ReturnInfo(ErrorInfo.SUCCESS, "没有人押注！");
		}
		if (matchInfo.getLeaderProfitStatus() != StatusType.ENABLED.getStatus())
		{
			// 计算领导奖
			orderInfos.clear();
			searchInfos.clear();
			orderInfos.add(new TableOrderInfo(ITeamLeaderProfitConfigInfoDao.USER_TYPE, SqlOrderType.ASC.getType()));
			searchInfos.add(new TableSearchInfo(ITeamLeaderProfitConfigInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
			List<TeamLeaderProfitConfigInfo> teamLeaderProfitConfigInfoList = teamLeaderProfitConfigInfoService.getTeamLeaderProfitConfigInfoList(0, -1, orderInfos, searchInfos);
			for (int i = 0; i < teamLeaderProfitConfigInfoList.size(); i++)
			{
				TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo = teamLeaderProfitConfigInfoList.get(i);
				// 找出5级代理，然后查看其投注总额，再看他下面的团队投注总额
				orderInfos.clear();
				searchInfos.clear();
				searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
				List<UserBetInfo> userBetInfoList = userBetInfoService.getUserListByUserType(teamLeaderProfitConfigInfo.getUserType(), 0, -1, orderInfos, searchInfos);
				if (userBetInfoList != null && userBetInfoList.size() > 0)
				{
					List<String> userIdList = new ArrayList<>();
					boolean canProfit = true;
					for (UserBetInfo userBetInfo : userBetInfoList)
					{
						if (userIdList.size() > 0)
						{
							for (String userId1 : userIdList)
							{
								if (userBetInfo.getUserId().equals(userId1))
								{
									canProfit = false;
								}
							}
						}
						long userBetMoney = 0;
						if (canProfit)
						{
							searchInfos.clear();
							searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
							searchInfos.add(new TableSearchInfo(IUserBetInfoDao.USER_ID, userBetInfo.getUserId(), true, false));
							searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MONEY_UNIT, MoneyUnit.POINT.getType() + "", true, false));
							userBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, null, null);
							if (userBetMoney < teamLeaderProfitConfigInfo.getUserBetMoney())
							{
								canProfit = false;
							}
						}
						if (canProfit)
						{
							long teamBetMoney = getSubUserBetMoney(phaseId, userBetInfo.getUserId());
							if (teamBetMoney >= teamLeaderProfitConfigInfo.getTeamBetMoney())// 符合条件，发放
							{
								long profitMoney = (long) (1L * teamBetMoney * teamLeaderProfitConfigInfo.getProfitPercent() / 10000);
								if (teamLeaderProfitConfigInfo.getProfitMaxMoney() < profitMoney)
								{
									profitMoney = teamLeaderProfitConfigInfo.getProfitMaxMoney();
								}
								searchInfos.clear();
								searchInfos.add(new TableSearchInfo(ITeamLeaderProfitInfoDao.USER_ID, userBetInfo.getUserId(), true, false));
								searchInfos.add(new TableSearchInfo(ITeamLeaderProfitInfoDao.PHASE_ID, phaseId, true, false));
								long count = teamLeaderProfitInfoService.getTeamLeaderProfitInfoListCount(searchInfos);
								if (count < 1)
								{
									String profitId = teamLeaderProfitInfoService.genId();
									UserInfo userInfo = userInfoService.getUserInfoByUserId(userBetInfo.getUserId());
									TeamLeaderProfitInfo teamLeaderProfitInfo = new TeamLeaderProfitInfo(profitId, phaseId, userBetInfo.getUserId(), userBetMoney, teamBetMoney,
										teamLeaderProfitConfigInfo.getConfigId(), profitMoney, profitMoney, 0, StatusType.ENABLED.getStatus(), userInfo.getSysStatus(), StatusType.ENABLED.getStatus(),
										current, current);
									teamLeaderProfitInfoService.addTeamLeaderProfitInfo(teamLeaderProfitInfo);
									// 增加到账户余额
									if (!userInfoService.updatePoint(IUserInfoDao.POINT, userBetInfo.getUserId(), profitMoney))
									{
										throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户彩金钱包失败！");
									}
									String logType = "team_l" + teamLeaderProfitConfigInfo.getUserType() + "_benfit";
									if (teamLeaderProfitConfigInfo.getUserType() == UserType.AGENT5.getType())
									{
										logType = UserBalanceLogType.TEAM_LEADER5_BENFIT.getType();
									}
									else if (teamLeaderProfitConfigInfo.getUserType() == UserType.AGENT6.getType())
									{
										logType = UserBalanceLogType.TEAM_LEADER6_BENFIT.getType();
									}
									else if (teamLeaderProfitConfigInfo.getUserType() == UserType.AGENT7.getType())
									{
										logType = UserBalanceLogType.TEAM_LEADER7_BENFIT.getType();
									}
									userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBetInfo.getUserId(), profitMoney, MoneyUnit.POINT.getType(), logType, profitId, null,
										null, null, String.format("比赛%s的%s。", matchInfo.getMatchName(), teamLeaderProfitConfigInfo.getName()), current));
								}
							}
						}
					}
				}
			}
		}
		List<UserTeamProfit> userTeamProfitList = new ArrayList<>();
		if (matchInfo.getHalfProfitStatus() != StatusType.ENABLED.getStatus() || matchInfo.getFinalProfitStatus() != StatusType.ENABLED.getStatus())
		{
			// 计算全场和半场的团队奖
			orderInfos.clear();
			searchInfos.clear();
			searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
			searchInfos.add(new TableSearchInfo(IUserBetInfoDao.TEAM_PROFIT_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
			long unTeamProfitCount = userBetInfoService.getUserBetInfoListCount(searchInfos);
			if (unTeamProfitCount > 0)
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "用户下注的记录还未计算出团队奖！");
			}
			searchInfos.clear();
			searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PHASE_ID, phaseId, true, false));
			List<TeamProfitInfo> teamProfitInfoList = null;
			int start = 0;
			int size = 100;
			while (true)
			{
				teamProfitInfoList = teamProfitInfoService.getTeamProfitInfoList(start, size, orderInfos, searchInfos);
				if (teamProfitInfoList == null || teamProfitInfoList.size() < 1)
				{
					break;
				}
				for (TeamProfitInfo teamProfitInfo : teamProfitInfoList)
				{
					try
					{
						UserTeamProfit userTeamProfit = null;
						if (userTeamProfitList.size() > 0)
						{
							for (UserTeamProfit userTeamProfit1 : userTeamProfitList)
							{
								if (userTeamProfit1.getUserId().equals(teamProfitInfo.getUserId()))
								{
									userTeamProfit = userTeamProfit1;
									break;
								}
							}
						}
						if (userTeamProfit == null)
						{
							userTeamProfit = new UserTeamProfit(teamProfitInfo.getUserId(), 0, 0, 0, 0, 0, 0, 0, 0);
						}
						if (teamProfitInfo.getProfitType() == TeamProfitType.SHARE.getType())
						{
							userTeamProfit.setShareBonusMoney(userTeamProfit.getShareBonusMoney() + teamProfitInfo.getProfitUserMoney());
						}
						else if (teamProfitInfo.getProfitType() == TeamProfitType.AGENT.getType())
						{
							userTeamProfit.setAgentBonusMoney(userTeamProfit.getAgentBonusMoney() + teamProfitInfo.getProfitUserMoney());
						}
						else if (teamProfitInfo.getProfitType() == TeamProfitType.SAME_LEVEL1.getType())
						{
							userTeamProfit.setSameLevel1BonusMoney(userTeamProfit.getSameLevel1BonusMoney() + teamProfitInfo.getProfitUserMoney());
						}
						else if (teamProfitInfo.getProfitType() == TeamProfitType.BENEFIT.getType())
						{
							userTeamProfit.setBenefitBonusMoney(userTeamProfit.getBenefitBonusMoney() + teamProfitInfo.getProfitUserMoney());
						}
						else if (teamProfitInfo.getProfitType() == TeamProfitType.SAME_LEVEL2.getType())
						{
							userTeamProfit.setSameLevel2BonusMoney(userTeamProfit.getSameLevel2BonusMoney() + teamProfitInfo.getProfitUserMoney());
						}
						Map<String, Object> valueMap = new HashMap<>();
						valueMap.put(ITeamProfitInfoDao.PROFIT_STATUS, StatusType.ENABLED.getStatus());
						if (!teamProfitInfoService.updateValueByProfitId(teamProfitInfo.getProfitId(), valueMap))
						{
							throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新团队奖金发放状态失败！");
						}
						userTeamProfitList.add(userTeamProfit);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				if (teamProfitInfoList.size() < size)// 取完了结束循环
				{
					break;
				}
				else
				{
					start += size;
				}
			}
		}
		for (UserTeamProfit userTeamProfit : userTeamProfitList)
		{
			searchInfos.clear();
			searchInfos.add(new TableSearchInfo(IUserBonusInfoDao.USER_ID, userTeamProfit.getUserId(), true, false));
			searchInfos.add(new TableSearchInfo(IUserBonusInfoDao.PHASE_ID, phaseId, true, false));
			long count = userBonusInfoService.getUserBonusInfoListCount(searchInfos);
			if (count < 1)// 检查是不是已经插入过了
			{
				userBonusInfoService.addUserBonusInfo(new UserBonusInfo(userBonusInfoService.genId(), userTeamProfit.getUserId(), phaseId, (int) userTeamProfit.getFinalBonusMoney(),
					(int) userTeamProfit.getHalfBonusMoney(), (int) userTeamProfit.getCountBonusMoney(), (int) (userTeamProfit.getShareBonusMoney() / 100),
					(int) (userTeamProfit.getAgentBonusMoney() / 100), (int) (userTeamProfit.getSameLevel1BonusMoney() / 100), (int) (userTeamProfit.getSameLevel2BonusMoney() / 100),
					(int) (userTeamProfit.getBenefitBonusMoney() / 100), 0, 0, (int) ((userTeamProfit.getShareBonusMoney() + userTeamProfit.getAgentBonusMoney()
						+ userTeamProfit.getSameLevel1BonusMoney() + userTeamProfit.getSameLevel2BonusMoney() + userTeamProfit.getBenefitBonusMoney()) / 100),
					StatusType.DISABLED.getStatus(), current, current));
			}
		}
		Map<String, Object> valueMap = new HashMap<>();
		valueMap.put(IMatchInfoDao.HALF_PROFIT_STATUS, StatusType.ENABLED.getStatus());
		valueMap.put(IMatchInfoDao.FINAL_PROFIT_STATUS, StatusType.ENABLED.getStatus());
		valueMap.put(IMatchInfoDao.LEADER_PROFIT_STATUS, StatusType.ENABLED.getStatus());
		if (!matchInfoService.updateProfitValueByPhaseId(phaseId, valueMap))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新比赛奖金发放状态失败！");
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, "");
	}

	@Override
	public ReturnInfo checkAndUpdateUserBonusInfoJob(String bonusId)
	{
		UserBonusInfo userBonusInfo = userBonusInfoService.getUserBonusInfoByBonusId(bonusId);
		if (userBonusInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "用户奖金ID不存在！");
		}
		if (userBonusInfo.getStatus() == StatusType.ENABLED.getStatus())
		{
			return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "用户奖金已发放！");
		}
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(userBonusInfo.getPhaseId());
		if (matchInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "比赛ID不存在！");
		}
		if (!userInfoService.updatePoint(IUserInfoDao.POINT, userBonusInfo.getUserId(), userBonusInfo.getUserMoney()))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户彩金钱包失败！");
		}
		long current = System.currentTimeMillis();
		if (userBonusInfo.getShareBonusMoney() > 0)
		{
			userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBonusInfo.getUserId(), userBonusInfo.getShareBonusMoney(), MoneyUnit.POINT.getType(),
				UserBalanceLogType.TEAM_SHARE.getType(), bonusId, null, null, null, String.format("比赛%s的分享佣金。", matchInfo.getMatchName()), current));
		}
		if (userBonusInfo.getAgentBonusMoney() > 0)
		{
			userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBonusInfo.getUserId(), userBonusInfo.getAgentBonusMoney(), MoneyUnit.POINT.getType(),
				UserBalanceLogType.TEAM_AGENT.getType(), bonusId, null, null, null, String.format("比赛%s的代理佣金。", matchInfo.getMatchName()), current));
		}
		if (userBonusInfo.getSameLevel1BonusMoney() > 0)
		{
			userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBonusInfo.getUserId(), userBonusInfo.getSameLevel1BonusMoney(), MoneyUnit.POINT.getType(),
				UserBalanceLogType.TEAM_AGENT_SAME_LEVEL.getType(), bonusId, null, null, null, String.format("比赛%s的代理佣金平级奖。", matchInfo.getMatchName()), current));
		}
		if (userBonusInfo.getBenefitBonusMoney() > 0)
		{
			userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBonusInfo.getUserId(), userBonusInfo.getBenefitBonusMoney(), MoneyUnit.POINT.getType(),
				UserBalanceLogType.TEAM_BENFIT.getType(), bonusId, null, null, null, String.format("比赛%s的福利奖。", matchInfo.getMatchName()), current));
		}
		if (userBonusInfo.getSameLevel2BonusMoney() > 0)
		{
			userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBonusInfo.getUserId(), userBonusInfo.getSameLevel2BonusMoney(), MoneyUnit.POINT.getType(),
				UserBalanceLogType.TEAM_BENFIT_SAME_LEVEL.getType(), bonusId, null, null, null, String.format("比赛%s的福利平级奖。", matchInfo.getMatchName()), current));
		}
		Map<String, Object> valueMap = new HashMap<>();
		valueMap.put(IUserBonusInfoDao.STATUS, StatusType.ENABLED.getStatus());
		if (!userBonusInfoService.updateStatusValueByBonusId(bonusId, valueMap))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户奖金发放状态失败！");
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, "");
	}

	@Override
	public long getSubUserBetMoney(String phaseId, String userId)
	{
		long teamBetMoney = 0;
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserInfoDao.REFERRER_ID, userId, true, false));
		List<UserInfo> subUserInfoList = userInfoService.getUserInfoList(0, -1, orderInfos, searchInfos);
		if (subUserInfoList != null && subUserInfoList.size() > 0)
		{
			for (UserInfo userInfo1 : subUserInfoList)
			{
				searchInfos.clear();
				searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
				searchInfos.add(new TableSearchInfo(IUserBetInfoDao.USER_ID, userInfo1.getUserId(), true, false));
				searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MONEY_UNIT, MoneyUnit.POINT.getType() + "", true, false));
				long betMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, null, null);
				teamBetMoney += betMoney;
				teamBetMoney += getSubUserBetMoney(phaseId, userInfo1.getUserId());
			}
		}
		return teamBetMoney;
	}

	@Override
	public ReturnInfo checkAndUpdateMatchStatisticsJob(String phaseId)
	{
		long current = System.currentTimeMillis();
		// if (matchStatisticsLogInfoService.getMatchStatisticsLogInfoByPhaseId(phaseId) != null)
		// {
		// return new ReturnInfo(ErrorInfo.EXIST_ERROR, "比赛ID已统计！");
		// }
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(phaseId);
		if (matchInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "比赛ID不存在！");
		}
		if (matchInfo.getMatchDate() > current)
		{
			return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "比赛还未到时间！");
		}
		if (matchInfo.getFinalScore1() == null || matchInfo.getFinalScore2() == null || matchInfo.getHalfScore1() == null || matchInfo.getHalfScore2() == null)
		{
			return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "比赛还未开奖！");
		}
		if (matchInfo.getHalfProfitStatus() != StatusType.ENABLED.getStatus() || matchInfo.getFinalProfitStatus() != StatusType.ENABLED.getStatus()
			|| matchInfo.getLeaderProfitStatus() != StatusType.ENABLED.getStatus())// 如果全场、半场、领导奖都发放过
		{
			return new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, "比赛还未计算过奖金！");
		}
		// List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
		long profitCount = userBetInfoService.getUserBetInfoListCount(searchInfos);
		MatchStatisticsLogInfo matchStatisticsLogInfo = matchStatisticsLogInfoService.getMatchStatisticsLogInfoByPhaseId(phaseId);
		boolean isExist = (matchStatisticsLogInfo != null);
		long userBetMoney = 0, finalBetMoney = 0, finalBonusMoney = 0, finalWinMoney = 0, halfBetMoney = 0, halfBonusMoney = 0, halfWinMoney = 0, countBetMoney = 0, countBonusMoney = 0,
			countWinMoney = 0, shareBonusMoney = 0, agentBonusMoney = 0, sameLevel1BonusMoney = 0, sameLevel2BonusMoney = 0, benefitBonusMoney = 0, teamMoney = 0, companyMoney = 0;
		if (matchStatisticsLogInfo == null)
		{
			matchStatisticsLogInfo = new MatchStatisticsLogInfo(phaseId, userBetMoney, finalBetMoney, finalBonusMoney, finalWinMoney, halfBetMoney, halfBonusMoney, halfWinMoney, countBetMoney,
				countBonusMoney, countWinMoney, shareBonusMoney, agentBonusMoney, sameLevel1BonusMoney, sameLevel2BonusMoney, benefitBonusMoney, teamMoney, companyMoney,
				StatusType.ENABLED.getStatus(), current, current);
		}
		else
		{
			matchStatisticsLogInfo.setUpdateDateLong(current);
		}
		if (profitCount < 1)
		{
			if (!isExist)
			{
				matchStatisticsLogInfoService.addMatchStatisticsLogInfo(matchStatisticsLogInfo);
			}
			return new ReturnInfo(ErrorInfo.SUCCESS, "没有人押注！");
		}
		// 取用户下注金额
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		userBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setUserBetMoney(userBetMoney);
		// 取用户下注金额(全场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.FULL.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		finalBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setFinalBetMoney(finalBetMoney);
		// 取用户中奖金额(全场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.FULL.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		finalBonusMoney = userBetInfoService.getSum(IUserBetInfoDao.USER_MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setFinalBonusMoney(finalBonusMoney);
		// 取用户赢的中奖金额(全场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.FULL.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		finalWinMoney = userBetInfoService.getSum(IUserBetInfoDao.WIN_MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setFinalWinMoney(finalWinMoney);
		// 取用户下注金额(半场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.HALF.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		halfBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setHalfBetMoney(halfBetMoney);
		// 取用户中奖金额(半场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.HALF.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		halfBonusMoney = userBetInfoService.getSum(IUserBetInfoDao.USER_MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setHalfBonusMoney(halfBonusMoney);
		// 取用户赢的中奖金额(半场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.HALF.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		halfWinMoney = userBetInfoService.getSum(IUserBetInfoDao.WIN_MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setHalfWinMoney(halfWinMoney);
		// 取用户下注金额(球数)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.SCORE.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		countBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setCountBetMoney(countBetMoney);
		// 取用户中奖金额(球数)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.SCORE.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		countBonusMoney = userBetInfoService.getSum(IUserBetInfoDao.USER_MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setCountBonusMoney(countBonusMoney);
		// 取用户赢的中奖金额(球数)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.SCORE.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		countWinMoney = userBetInfoService.getSum(IUserBetInfoDao.WIN_MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setCountWinMoney(countWinMoney);
		// 分享佣金
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.SHARE.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		shareBonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setShareBonusMoney(shareBonusMoney / 100);
		// 代理佣金
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.AGENT.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		agentBonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setAgentBonusMoney(agentBonusMoney / 100);
		// 代理佣金平级奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.SAME_LEVEL1.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		sameLevel1BonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setSameLevel1BonusMoney(sameLevel1BonusMoney / 100);
		// 福利平级奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.SAME_LEVEL2.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		sameLevel2BonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setSameLevel2BonusMoney(sameLevel2BonusMoney / 100);
		// 福利奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.BENEFIT.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		benefitBonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setBenefitBonusMoney(benefitBonusMoney / 100);
		// 团队业绩奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamLeaderProfitInfoDao.PHASE_ID, phaseId, true, false));
		searchInfos.add(new TableSearchInfo(ITeamLeaderProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		teamMoney = teamLeaderProfitInfoService.getSum(ITeamLeaderProfitInfoDao.PROFIT_USER_MONEY, searchInfos, null, null);
		matchStatisticsLogInfo.setTeamMoney(teamMoney);
		companyMoney = userBetMoney
			- (finalBonusMoney + halfBonusMoney + countBonusMoney + shareBonusMoney + agentBonusMoney + sameLevel1BonusMoney + sameLevel2BonusMoney + benefitBonusMoney + teamMoney);
		matchStatisticsLogInfo.setCompanyMoney(companyMoney);
		if (!isExist)
		{
			matchStatisticsLogInfo = matchStatisticsLogInfoService.addMatchStatisticsLogInfo(matchStatisticsLogInfo);
		}
		else
		{
			matchStatisticsLogInfo = matchStatisticsLogInfoService.updateMatchStatisticsLogInfo(matchStatisticsLogInfo);
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, GsonTools.toJson(matchStatisticsLogInfo));
	}

	@Override
	public ReturnInfo checkAndUpdateDayStatisticsJob(int year, int month, int day) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0, 0);
		long startTime = calendar.getTimeInMillis();
		long endTime = startTime + BetConstValues.ONE_DAY_TIME_LONG - 1;
		String dayId = String.format("%04d%02d%02d", year, month, day);
		long userBetMoney = 0, userRechargeMoney = 0, userWithdrawMoney = 0, finalBetMoney = 0, finalBonusMoney = 0, finalWinMoney = 0, halfBetMoney = 0, halfBonusMoney = 0, halfWinMoney = 0,
			countBetMoney = 0, countBonusMoney = 0, countWinMoney = 0, shareBonusMoney = 0, agentBonusMoney = 0, sameLevel1BonusMoney = 0, sameLevel2BonusMoney = 0, benefitBonusMoney = 0,
			teamMoney = 0;
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		// 取用户下注金额
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		userBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, startTime, endTime);
		// 取用户下注金额(全场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.FULL.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		finalBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, startTime, endTime);
		// 取用户中奖金额(全场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.FULL.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		finalBonusMoney = userBetInfoService.getSum(IUserBetInfoDao.USER_MONEY, searchInfos, startTime, endTime);
		// 取用户中奖赢的金额(全场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.FULL.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		finalWinMoney = userBetInfoService.getSum(IUserBetInfoDao.WIN_MONEY, searchInfos, startTime, endTime);
		// 取用户下注金额(半场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.HALF.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		halfBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, startTime, endTime);
		// 取用户中奖金额(半场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.HALF.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		halfBonusMoney = userBetInfoService.getSum(IUserBetInfoDao.USER_MONEY, searchInfos, startTime, endTime);
		// 取用户中奖赢的金额(半场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.HALF.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		halfWinMoney = userBetInfoService.getSum(IUserBetInfoDao.WIN_MONEY, searchInfos, startTime, endTime);
		// 取用户下注金额(球数)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.SCORE.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		countBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, startTime, endTime);
		// 取用户中奖金额(球数)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.SCORE.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		countBonusMoney = userBetInfoService.getSum(IUserBetInfoDao.USER_MONEY, searchInfos, startTime, endTime);
		// 取用户中奖赢的金额(球数)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.SCORE.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		countWinMoney = userBetInfoService.getSum(IUserBetInfoDao.WIN_MONEY, searchInfos, startTime, endTime);
		// 分享佣金
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.SHARE.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		shareBonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 代理佣金
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.AGENT.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		agentBonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 代理佣金平级奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.SAME_LEVEL1.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		sameLevel1BonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 福利平级奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.SAME_LEVEL2.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		sameLevel2BonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 福利奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.BENEFIT.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		benefitBonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 团队业绩奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamLeaderProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		teamMoney = teamLeaderProfitInfoService.getSum(ITeamLeaderProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime);
		// 充值
		searchInfos.clear();
		userRechargeMoney = userRechargeInfoService.getSum(IUserRechargeInfoDao.MONEY, searchInfos, startTime, endTime);
		// 提现
		searchInfos.clear();
		userWithdrawMoney = withdrawInfoService.getSum(IWithdrawInfoDao.MONEY, searchInfos, startTime, endTime);
		long companyMoney = userBetMoney
			- (finalBonusMoney + halfBonusMoney + countBonusMoney + shareBonusMoney + agentBonusMoney + sameLevel1BonusMoney + sameLevel2BonusMoney + benefitBonusMoney + teamMoney);
		DayStatisticsLogInfo dayStatisticsLogInfo = dayStatisticsLogInfoService.getDayStatisticsLogInfoByDayId(dayId);
		if (dayStatisticsLogInfo != null)
		{
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IDayStatisticsLogInfoDao.USER_BET_MONEY, userBetMoney);
			valueMap.put(IDayStatisticsLogInfoDao.USER_RECHARGE_MONEY, userRechargeMoney);
			valueMap.put(IDayStatisticsLogInfoDao.USER_WITHDRAW_MONEY, userWithdrawMoney);
			valueMap.put(IDayStatisticsLogInfoDao.FINAL_BET_MONEY, finalBetMoney);
			valueMap.put(IDayStatisticsLogInfoDao.FINAL_BONUS_MONEY, finalBonusMoney);
			valueMap.put(IDayStatisticsLogInfoDao.FINAL_WIN_MONEY, finalWinMoney);
			valueMap.put(IDayStatisticsLogInfoDao.HALF_BET_MONEY, halfBetMoney);
			valueMap.put(IDayStatisticsLogInfoDao.HALF_BONUS_MONEY, halfBonusMoney);
			valueMap.put(IDayStatisticsLogInfoDao.HALF_WIN_MONEY, halfWinMoney);
			valueMap.put(IDayStatisticsLogInfoDao.COUNT_BET_MONEY, countBetMoney);
			valueMap.put(IDayStatisticsLogInfoDao.COUNT_BONUS_MONEY, countBonusMoney);
			valueMap.put(IDayStatisticsLogInfoDao.COUNT_WIN_MONEY, countWinMoney);
			valueMap.put(IDayStatisticsLogInfoDao.SHARE_BONUS_MONEY, shareBonusMoney);
			valueMap.put(IDayStatisticsLogInfoDao.AGENT_BONUS_MONEY, agentBonusMoney);
			valueMap.put(IDayStatisticsLogInfoDao.SAME_LEVEL1_BONUS_MONEY, sameLevel1BonusMoney);
			valueMap.put(IDayStatisticsLogInfoDao.SAME_LEVEL2_BONUS_MONEY, sameLevel2BonusMoney);
			valueMap.put(IDayStatisticsLogInfoDao.BENEFIT_BONUS_MONEY, benefitBonusMoney);
			valueMap.put(IDayStatisticsLogInfoDao.TEAM_MONEY, teamMoney);
			valueMap.put(IDayStatisticsLogInfoDao.COMPANY_MONEY, companyMoney);
			if (!dayStatisticsLogInfoService.updateValueByDayId(dayId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新每天比赛数据失败！");
			}
		}
		else
		{
			dayStatisticsLogInfo = new DayStatisticsLogInfo(dayId, userBetMoney, userRechargeMoney, userWithdrawMoney, finalBetMoney, finalBonusMoney, finalWinMoney, halfBetMoney, halfBonusMoney,
				halfWinMoney, countBetMoney, countBonusMoney, countWinMoney, shareBonusMoney, agentBonusMoney, sameLevel1BonusMoney, sameLevel2BonusMoney, benefitBonusMoney, teamMoney, companyMoney,
				StatusType.ENABLED.getStatus(), current, current);
			dayStatisticsLogInfo = dayStatisticsLogInfoService.addDayStatisticsLogInfo(dayStatisticsLogInfo);
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, GsonTools.toJson(dayStatisticsLogInfo));
	}

	// public static void main(String[] args)
	// {
	// Calendar calendar = Calendar.getInstance();
	// calendar.set(2018, 1 - 1, 1, 0, 0, 0);
	// System.out.println("当前时间：" + calendar.getTimeInMillis() + "," + TimeTools.getDateTimeString(calendar.getTimeInMillis()));
	// }
	@Override
	public ReturnInfo checkAndUpdateMonthStatisticsJob(int year, int month)
	{
		long current = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1, 0, 0, 0);
		long startTime = calendar.getTimeInMillis();
		calendar.set(year, month, 1, 0, 0, 0);
		long endTime = calendar.getTimeInMillis() + BetConstValues.ONE_DAY_TIME_LONG - 1;
		String dayId = String.format("%04d%02d", year, month);
		long userBetMoney = 0, userRechargeMoney = 0, userWithdrawMoney = 0, finalBetMoney = 0, finalBonusMoney = 0, finalWinMoney = 0, halfBetMoney = 0, halfBonusMoney = 0, halfWinMoney = 0,
			countBetMoney = 0, countBonusMoney = 0, countWinMoney = 0, shareBonusMoney = 0, agentBonusMoney = 0, sameLevel1BonusMoney = 0, sameLevel2BonusMoney = 0, benefitBonusMoney = 0,
			teamMoney = 0;
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		// 取用户下注金额
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		userBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, startTime, endTime);
		// 取用户下注金额(全场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.FULL.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		finalBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, startTime, endTime);
		// 取用户中奖金额(全场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.FULL.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		finalBonusMoney = userBetInfoService.getSum(IUserBetInfoDao.USER_MONEY, searchInfos, startTime, endTime);
		// 取用户中奖赢的金额(全场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.FULL.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		finalWinMoney = userBetInfoService.getSum(IUserBetInfoDao.WIN_MONEY, searchInfos, startTime, endTime);
		// 取用户下注金额(半场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.HALF.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		halfBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, startTime, endTime);
		// 取用户中奖金额(半场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.HALF.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		halfBonusMoney = userBetInfoService.getSum(IUserBetInfoDao.USER_MONEY, searchInfos, startTime, endTime);
		// 取用户中奖赢的金额(半场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.HALF.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		halfWinMoney = userBetInfoService.getSum(IUserBetInfoDao.WIN_MONEY, searchInfos, startTime, endTime);
		// 取用户下注金额(球数)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.SCORE.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		countBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, startTime, endTime);
		// 取用户中奖金额(球数)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.SCORE.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		countBonusMoney = userBetInfoService.getSum(IUserBetInfoDao.USER_MONEY, searchInfos, startTime, endTime);
		// 取用户中奖赢的金额(球数)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.SCORE.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		countWinMoney = userBetInfoService.getSum(IUserBetInfoDao.WIN_MONEY, searchInfos, startTime, endTime);
		// 分享佣金
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.SHARE.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		shareBonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 代理佣金
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.AGENT.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		agentBonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 代理佣金平级奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.SAME_LEVEL1.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		sameLevel1BonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 福利平级奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.SAME_LEVEL2.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		sameLevel2BonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 福利奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.BENEFIT.getType() + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		benefitBonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 团队业绩奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamLeaderProfitInfoDao.SYS_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		teamMoney = teamLeaderProfitInfoService.getSum(ITeamLeaderProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime);
		// 充值
		searchInfos.clear();
		userRechargeMoney = userRechargeInfoService.getSum(IUserRechargeInfoDao.MONEY, searchInfos, startTime, endTime);
		// 提现
		searchInfos.clear();
		userWithdrawMoney = withdrawInfoService.getSum(IWithdrawInfoDao.MONEY, searchInfos, startTime, endTime);
		long companyMoney = userBetMoney
			- (finalBonusMoney + halfBonusMoney + countBonusMoney + shareBonusMoney + agentBonusMoney + sameLevel1BonusMoney + sameLevel2BonusMoney + benefitBonusMoney + teamMoney);
		MonthStatisticsLogInfo monthStatisticsLogInfo = monthStatisticsLogInfoService.getMonthStatisticsLogInfoByDayId(dayId);
		if (monthStatisticsLogInfo != null)
		{
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IMonthStatisticsLogInfoDao.USER_BET_MONEY, userBetMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.USER_RECHARGE_MONEY, userRechargeMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.USER_WITHDRAW_MONEY, userWithdrawMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.FINAL_BET_MONEY, finalBetMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.FINAL_BONUS_MONEY, finalBonusMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.FINAL_WIN_MONEY, finalWinMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.HALF_BET_MONEY, halfBetMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.HALF_BONUS_MONEY, halfBonusMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.HALF_WIN_MONEY, halfWinMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.COUNT_BET_MONEY, countBetMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.COUNT_BONUS_MONEY, countBonusMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.COUNT_WIN_MONEY, countWinMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.SHARE_BONUS_MONEY, shareBonusMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.AGENT_BONUS_MONEY, agentBonusMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.SAME_LEVEL1_BONUS_MONEY, sameLevel1BonusMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.SAME_LEVEL2_BONUS_MONEY, sameLevel2BonusMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.BENEFIT_BONUS_MONEY, benefitBonusMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.TEAM_MONEY, teamMoney);
			valueMap.put(IMonthStatisticsLogInfoDao.COMPANY_MONEY, companyMoney);
			if (!monthStatisticsLogInfoService.updateValueByDayId(dayId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新每月比赛数据失败！");
			}
		}
		else
		{
			monthStatisticsLogInfo = new MonthStatisticsLogInfo(dayId, userBetMoney, userRechargeMoney, userWithdrawMoney, finalBetMoney, finalBonusMoney, finalWinMoney, halfBetMoney, halfBonusMoney,
				halfWinMoney, countBetMoney, countBonusMoney, countWinMoney, shareBonusMoney, agentBonusMoney, sameLevel1BonusMoney, sameLevel2BonusMoney, benefitBonusMoney, teamMoney, companyMoney,
				StatusType.ENABLED.getStatus(), current, current);
			monthStatisticsLogInfo = monthStatisticsLogInfoService.updateMonthStatisticsLogInfo(monthStatisticsLogInfo);
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, GsonTools.toJson(monthStatisticsLogInfo));
	}

	@Override
	public ReturnInfo checkAndUpdateDayUserStatisticsJob(String userId, int year, int month, int day)
	{
		long current = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0, 0);
		long startTime = calendar.getTimeInMillis();
		long endTime = startTime + BetConstValues.ONE_DAY_TIME_LONG - 1;
		String dayId = String.format("%04d%02d%02d", year, month, day);
		long userBetMoney = 0, userRechargeMoney = 0, userWithdrawMoney = 0, finalBonusMoney = 0, halfBonusMoney = 0, countBonusMoney = 0, shareBonusMoney = 0, agentBonusMoney = 0,
			sameLevel1BonusMoney = 0, sameLevel2BonusMoney = 0, benefitBonusMoney = 0, teamMoney = 0;
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		// 取用户下注金额
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.USER_ID, userId, true, false));
		userBetMoney = userBetInfoService.getSum(IUserBetInfoDao.MONEY, searchInfos, startTime, endTime);
		// 取用户中奖金额(全场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.USER_ID, userId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.FULL.getType(), true, false));
		finalBonusMoney = userBetInfoService.getSum(IUserBetInfoDao.USER_MONEY, searchInfos, startTime, endTime);
		// 取用户中奖金额(半场)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.USER_ID, userId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.HALF.getType(), true, false));
		halfBonusMoney = userBetInfoService.getSum(IUserBetInfoDao.USER_MONEY, searchInfos, startTime, endTime);
		// 取用户中奖金额(球数)
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.USER_ID, userId, true, false));
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.MATCH_TYPE, BodanType.SCORE.getType(), true, false));
		countBonusMoney = userBetInfoService.getSum(IUserBetInfoDao.USER_MONEY, searchInfos, startTime, endTime);
		// 分享佣金
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.USER_ID, userId, true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.SHARE.getType() + "", true, false));
		shareBonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 代理佣金
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.USER_ID, userId, true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.AGENT.getType() + "", true, false));
		agentBonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 代理佣金平级奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.USER_ID, userId, true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.SAME_LEVEL1.getType() + "", true, false));
		sameLevel1BonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 福利平级奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.USER_ID, userId, true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.SAME_LEVEL2.getType() + "", true, false));
		sameLevel2BonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 福利奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.USER_ID, userId, true, false));
		searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, TeamProfitType.BENEFIT.getType() + "", true, false));
		benefitBonusMoney = teamProfitInfoService.getSum(ITeamProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime) / 100;
		// 团队业绩奖
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(ITeamLeaderProfitInfoDao.USER_ID, userId, true, false));
		teamMoney = teamLeaderProfitInfoService.getSum(ITeamLeaderProfitInfoDao.PROFIT_USER_MONEY, searchInfos, startTime, endTime);
		// 充值
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserRechargeInfoDao.USER_ID, userId, true, false));
		userRechargeMoney = userRechargeInfoService.getSum(IUserRechargeInfoDao.MONEY, searchInfos, startTime, endTime);
		// 提现
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IWithdrawInfoDao.USER_ID, userId, true, false));
		userWithdrawMoney = withdrawInfoService.getSum(IWithdrawInfoDao.MONEY, searchInfos, startTime, endTime);
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IDayUserStatisticsLogInfoDao.DAY_ID, dayId, true, false));
		searchInfos.add(new TableSearchInfo(IDayUserStatisticsLogInfoDao.USER_ID, userId, true, false));
		List<DayUserStatisticsLogInfo> dayUserStatisticsLogInfoList = dayUserStatisticsLogInfoService.getDayUserStatisticsLogInfoList(0, 1, null, searchInfos);
		DayUserStatisticsLogInfo dayUserStatisticsLogInfo = null;
		if (dayUserStatisticsLogInfoList != null && dayUserStatisticsLogInfoList.size() > 0)
		{
			dayUserStatisticsLogInfo = dayUserStatisticsLogInfoList.get(0);
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IDayUserStatisticsLogInfoDao.USER_BET_MONEY, userBetMoney);
			valueMap.put(IDayUserStatisticsLogInfoDao.USER_RECHARGE_MONEY, userRechargeMoney);
			valueMap.put(IDayUserStatisticsLogInfoDao.USER_WITHDRAW_MONEY, userWithdrawMoney);
			valueMap.put(IDayUserStatisticsLogInfoDao.FINAL_BONUS_MONEY, finalBonusMoney);
			valueMap.put(IDayUserStatisticsLogInfoDao.HALF_BONUS_MONEY, halfBonusMoney);
			valueMap.put(IDayUserStatisticsLogInfoDao.COUNT_BONUS_MONEY, countBonusMoney);
			valueMap.put(IDayUserStatisticsLogInfoDao.SHARE_BONUS_MONEY, shareBonusMoney);
			valueMap.put(IDayUserStatisticsLogInfoDao.AGENT_BONUS_MONEY, agentBonusMoney);
			valueMap.put(IDayUserStatisticsLogInfoDao.SAME_LEVEL1_BONUS_MONEY, sameLevel1BonusMoney);
			valueMap.put(IDayUserStatisticsLogInfoDao.SAME_LEVEL2_BONUS_MONEY, sameLevel2BonusMoney);
			valueMap.put(IDayUserStatisticsLogInfoDao.BENEFIT_BONUS_MONEY, benefitBonusMoney);
			valueMap.put(IDayUserStatisticsLogInfoDao.TEAM_MONEY, teamMoney);
			if (!dayUserStatisticsLogInfoService.updateValueByLogId(dayUserStatisticsLogInfo.getLogId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新每天比赛数据失败！");
			}
		}
		else
		{
			dayUserStatisticsLogInfo = new DayUserStatisticsLogInfo(dayUserStatisticsLogInfoService.genId(), dayId, userId, userBetMoney, userRechargeMoney, userWithdrawMoney, finalBonusMoney,
				halfBonusMoney, countBonusMoney, shareBonusMoney, agentBonusMoney, sameLevel1BonusMoney, sameLevel2BonusMoney, benefitBonusMoney, teamMoney, StatusType.ENABLED.getStatus(), current,
				current);
			dayUserStatisticsLogInfo = dayUserStatisticsLogInfoService.addDayUserStatisticsLogInfo(dayUserStatisticsLogInfo);
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, GsonTools.toJson(dayUserStatisticsLogInfo));
	}

	@Override
	public ReturnInfo checkAndUpdateRemoveMatchBonusJob(String phaseId)
	{
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(phaseId);
		if (matchInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "比赛ID不存在！");
		}
		int start = 0;
		int size = 100;
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		List<UserBetInfo> userBetInfoList = null;
		int successCount = 0, failCount = 0;
		while (true)
		{
			searchInfos.clear();
			searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
			userBetInfoList = userBetInfoService.getUserBetInfoList(start, size, orderInfos, searchInfos);
			if (userBetInfoList == null || userBetInfoList.size() < 1)
			{
				break;
			}
			for (UserBetInfo userBetInfo : userBetInfoList)
			{
				try
				{
					ReturnInfo returnInfo = checkAndUpdateRemoveBetBonusJob(userBetInfo.getBetId());
					if (ReturnInfo.isSuccess(returnInfo))
					{
						successCount++;
					}
					else
					{
						failCount++;
					}
					successCount++;
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
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，失败：%d个。", successCount, failCount));
	}

	@Override
	public ReturnInfo checkAndUpdateRemoveBetBonusJob(String betId)
	{
		UserBetInfo userBetInfo = userBetInfoService.getUserBetInfoByBetId(betId);
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		int successCount = 0, failCount = 0;
		try
		{
			// 更新用户状态
			searchInfos.clear();
			searchInfos.add(new TableSearchInfo(IUserBalanceLogInfoDao.SERVER_ID, userBetInfo.getBetId(), true, false));
			List<UserBalanceLogInfo> userBalanceLogInfoList = userBalanceLogInfoService.getUserBalanceLogInfoList(0, -1, null, searchInfos);
			for (UserBalanceLogInfo userBalanceLogInfo : userBalanceLogInfoList)
			{
				LogTools.getInstance().info("checkAndUpdateRemoveBetBonusJob", GsonTools.toJson(userBalanceLogInfo));
				if (userBalanceLogInfo.getLogType().equals(UserBalanceLogType.WIN_BET.getType()) || userBalanceLogInfo.getLogType().equals(UserBalanceLogType.WIN_BET_RETURN.getType()))
				{
					// 修改用户金额
					UserInfo userInfo = userInfoService.getUserInfoByUserId(userBalanceLogInfo.getUserId());
					if (userBalanceLogInfo.getMoneyUnit() == MoneyUnit.POINT.getType())
					{
						userInfo.setPoint(userInfo.getPoint() - userBalanceLogInfo.getMoney());
					}
					else if (userBalanceLogInfo.getMoneyUnit() == MoneyUnit.GIVE_POINT.getType())
					{
						userInfo.setGivePoint(userInfo.getGivePoint() - userBalanceLogInfo.getMoney());
					}
					userInfo = userInfoService.updateUserInfo(userInfo);
					// 删除日志
					userBalanceLogInfoService.deleteUserBalanceLogInfo(userBalanceLogInfo);
					LogTools.getInstance().info("checkAndUpdateRemoveBetBonusJob", "删除日志" + GsonTools.toJson(userBalanceLogInfo));
				}
			}
			userBetInfo.setProfitStatus(StatusType.DISABLED.getStatus());
			userBetInfo = userBetInfoService.updateUserBetInfo(userBetInfo);
			successCount++;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			failCount++;
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，失败：%d个。", successCount, failCount));
	}
}
