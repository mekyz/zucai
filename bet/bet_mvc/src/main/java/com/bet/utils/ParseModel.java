package com.bet.utils;

import java.util.List;

import com.bet.orms.AdviceInfo;
import com.bet.orms.AdviceReplyInfo;
import com.bet.orms.AdviceTypeInfo;
import com.bet.orms.DayStatisticsLogInfo;
import com.bet.orms.DayUserStatisticsLogInfo;
import com.bet.orms.MatchInfo;
import com.bet.orms.MatchProfitInfo;
import com.bet.orms.MatchProfitTemplateInfo;
import com.bet.orms.MatchStatisticsLogInfo;
import com.bet.orms.MonthStatisticsLogInfo;
import com.bet.orms.MsgInfo;
import com.bet.orms.NewsInfo;
import com.bet.orms.NewsSortInfo;
import com.bet.orms.PayConfigInfo;
import com.bet.orms.PayeeInfo;
import com.bet.orms.TeamInfo;
import com.bet.orms.TeamLeaderProfitConfigInfo;
import com.bet.orms.TeamLeaderProfitInfo;
import com.bet.orms.TeamProfitInfo;
import com.bet.orms.TeamProfitRateInfo;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.orms.UserBetInfo;
import com.bet.orms.UserBetReturnInfo;
import com.bet.orms.UserBonusInfo;
import com.bet.orms.UserExchangeInfo;
import com.bet.orms.UserInfo;
import com.bet.orms.UserRechargeInfo;
import com.bet.orms.UserTypeConditionInfo;
import com.bet.orms.WithdrawInfo;
import com.lrcall.common.utils.StringTools;

/**
 * 处理敏感信息然后返回给客户端
 * 
 * @author libit
 */
public class ParseModel
{
	public static UserInfo parseUserInfo(UserInfo userInfo)
	{
		if (userInfo != null)
		{
			userInfo.setPassword(null);
			userInfo.setPassword2(null);
			if (StringTools.isNull(userInfo.getPicUrl()))
			{
				userInfo.setPicUrl("skin/common/images/ic_header.png");
			}
		}
		return userInfo;
	}

	public static List<UserInfo> parseUserInfoList(List<UserInfo> userInfoList)
	{
		if (userInfoList != null && userInfoList.size() > 0)
		{
			for (UserInfo userInfo : userInfoList)
			{
				parseUserInfo(userInfo);
			}
		}
		return userInfoList;
	}

	public static UserBalanceLogInfo parseUserBalanceLogInfo(UserBalanceLogInfo userBalanceLogInfo)
	{
		if (userBalanceLogInfo != null)
		{
		}
		return userBalanceLogInfo;
	}

	public static List<UserBalanceLogInfo> parseUserBalanceLogInfoList(List<UserBalanceLogInfo> userBalanceLogInfoList)
	{
		if (userBalanceLogInfoList != null && userBalanceLogInfoList.size() > 0)
		{
			for (UserBalanceLogInfo userBalanceLogInfo : userBalanceLogInfoList)
			{
				parseUserBalanceLogInfo(userBalanceLogInfo);
			}
		}
		return userBalanceLogInfoList;
	}

	public static TeamInfo parseTeamInfo(TeamInfo teamInfo)
	{
		if (teamInfo != null)
		{
		}
		return teamInfo;
	}

	public static List<TeamInfo> parseTeamInfoList(List<TeamInfo> teamInfoList)
	{
		if (teamInfoList != null && teamInfoList.size() > 0)
		{
			for (TeamInfo teamInfo : teamInfoList)
			{
				parseTeamInfo(teamInfo);
			}
		}
		return teamInfoList;
	}

	public static MatchInfo parseMatchInfo(MatchInfo matchInfo)
	{
		if (matchInfo != null)
		{
		}
		return matchInfo;
	}

	public static List<MatchInfo> parseMatchInfoList(List<MatchInfo> matchInfoList)
	{
		if (matchInfoList != null && matchInfoList.size() > 0)
		{
			for (MatchInfo matchInfo : matchInfoList)
			{
				parseMatchInfo(matchInfo);
			}
		}
		return matchInfoList;
	}

	public static NewsInfo parseNewsInfo(NewsInfo newsInfo)
	{
		if (newsInfo != null)
		{
		}
		return newsInfo;
	}

	public static List<NewsInfo> parseNewsInfoList(List<NewsInfo> newsInfoList)
	{
		if (newsInfoList != null && newsInfoList.size() > 0)
		{
			for (NewsInfo newsInfo : newsInfoList)
			{
				parseNewsInfo(newsInfo);
			}
		}
		return newsInfoList;
	}

	public static NewsSortInfo parseNewsSortInfo(NewsSortInfo newsSortInfo)
	{
		if (newsSortInfo != null)
		{
		}
		return newsSortInfo;
	}

	public static List<NewsSortInfo> parseNewsSortInfoList(List<NewsSortInfo> newsSortInfoList)
	{
		if (newsSortInfoList != null && newsSortInfoList.size() > 0)
		{
			for (NewsSortInfo newsSortInfo : newsSortInfoList)
			{
				parseNewsSortInfo(newsSortInfo);
			}
		}
		return newsSortInfoList;
	}

	public static WithdrawInfo parseWithdrawInfo(WithdrawInfo withdrawInfo)
	{
		if (withdrawInfo != null)
		{
		}
		return withdrawInfo;
	}

	public static List<WithdrawInfo> parseWithdrawInfoList(List<WithdrawInfo> withdrawInfoList)
	{
		if (withdrawInfoList != null && withdrawInfoList.size() > 0)
		{
			for (WithdrawInfo withdrawInfo : withdrawInfoList)
			{
				parseWithdrawInfo(withdrawInfo);
			}
		}
		return withdrawInfoList;
	}

	public static PayeeInfo parsePayeeInfo(PayeeInfo payeeInfo)
	{
		if (payeeInfo != null)
		{
		}
		return payeeInfo;
	}

	public static List<PayeeInfo> parsePayeeInfoList(List<PayeeInfo> payeeInfoList)
	{
		if (payeeInfoList != null && payeeInfoList.size() > 0)
		{
			for (PayeeInfo payeeInfo : payeeInfoList)
			{
				parsePayeeInfo(payeeInfo);
			}
		}
		return payeeInfoList;
	}

	public static UserRechargeInfo parseUserRechargeInfo(UserRechargeInfo userRechargeInfo)
	{
		if (userRechargeInfo != null)
		{
		}
		return userRechargeInfo;
	}

	public static List<UserRechargeInfo> parseUserRechargeInfoList(List<UserRechargeInfo> userRechargeInfoList)
	{
		if (userRechargeInfoList != null && userRechargeInfoList.size() > 0)
		{
			for (UserRechargeInfo userRechargeInfo : userRechargeInfoList)
			{
				parseUserRechargeInfo(userRechargeInfo);
			}
		}
		return userRechargeInfoList;
	}

	public static UserExchangeInfo parseUserExchangeInfo(UserExchangeInfo userExchangeInfo)
	{
		if (userExchangeInfo != null)
		{
		}
		return userExchangeInfo;
	}

	public static List<UserExchangeInfo> parseUserExchangeInfoList(List<UserExchangeInfo> userExchangeInfoList)
	{
		if (userExchangeInfoList != null && userExchangeInfoList.size() > 0)
		{
			for (UserExchangeInfo userExchangeInfo : userExchangeInfoList)
			{
				parseUserExchangeInfo(userExchangeInfo);
			}
		}
		return userExchangeInfoList;
	}

	public static MatchProfitInfo parseMatchProfitInfo(MatchProfitInfo matchProfitInfo)
	{
		if (matchProfitInfo != null)
		{
		}
		return matchProfitInfo;
	}

	public static List<MatchProfitInfo> parseMatchProfitInfoList(List<MatchProfitInfo> matchProfitInfoList)
	{
		if (matchProfitInfoList != null && matchProfitInfoList.size() > 0)
		{
			for (MatchProfitInfo matchProfitInfo : matchProfitInfoList)
			{
				parseMatchProfitInfo(matchProfitInfo);
			}
		}
		return matchProfitInfoList;
	}

	public static UserBetInfo parseUserBetInfo(UserBetInfo userBetInfo)
	{
		if (userBetInfo != null)
		{
		}
		return userBetInfo;
	}

	public static List<UserBetInfo> parseUserBetInfoList(List<UserBetInfo> userBetInfoList)
	{
		if (userBetInfoList != null && userBetInfoList.size() > 0)
		{
			for (UserBetInfo userBetInfo : userBetInfoList)
			{
				parseUserBetInfo(userBetInfo);
			}
		}
		return userBetInfoList;
	}

	public static UserBonusInfo parseUserBonusInfo(UserBonusInfo userBonusInfo)
	{
		if (userBonusInfo != null)
		{
		}
		return userBonusInfo;
	}

	public static List<UserBonusInfo> parseUserBonusInfoList(List<UserBonusInfo> userBonusInfoList)
	{
		if (userBonusInfoList != null && userBonusInfoList.size() > 0)
		{
			for (UserBonusInfo userBonusInfo : userBonusInfoList)
			{
				parseUserBonusInfo(userBonusInfo);
			}
		}
		return userBonusInfoList;
	}

	public static PayConfigInfo parsePayConfigInfo(PayConfigInfo payConfigInfo)
	{
		if (payConfigInfo != null)
		{
		}
		return payConfigInfo;
	}

	public static List<PayConfigInfo> parsePayConfigInfoList(List<PayConfigInfo> payConfigInfoList)
	{
		if (payConfigInfoList != null && payConfigInfoList.size() > 0)
		{
			for (PayConfigInfo payConfigInfo : payConfigInfoList)
			{
				parsePayConfigInfo(payConfigInfo);
			}
		}
		return payConfigInfoList;
	}

	public static MatchProfitTemplateInfo parseMatchProfitTemplateInfo(MatchProfitTemplateInfo matchProfitTemplateInfo)
	{
		if (matchProfitTemplateInfo != null)
		{
		}
		return matchProfitTemplateInfo;
	}

	public static List<MatchProfitTemplateInfo> parseMatchProfitTemplateInfoList(List<MatchProfitTemplateInfo> matchProfitTemplateInfoList)
	{
		if (matchProfitTemplateInfoList != null && matchProfitTemplateInfoList.size() > 0)
		{
			for (MatchProfitTemplateInfo matchProfitTemplateInfo : matchProfitTemplateInfoList)
			{
				parseMatchProfitTemplateInfo(matchProfitTemplateInfo);
			}
		}
		return matchProfitTemplateInfoList;
	}

	public static TeamProfitRateInfo parseTeamProfitRateInfo(TeamProfitRateInfo teamProfitRateInfo)
	{
		if (teamProfitRateInfo != null)
		{
		}
		return teamProfitRateInfo;
	}

	public static List<TeamProfitRateInfo> parseTeamProfitRateInfoList(List<TeamProfitRateInfo> teamProfitRateInfoList)
	{
		if (teamProfitRateInfoList != null && teamProfitRateInfoList.size() > 0)
		{
			for (TeamProfitRateInfo teamProfitRateInfo : teamProfitRateInfoList)
			{
				parseTeamProfitRateInfo(teamProfitRateInfo);
			}
		}
		return teamProfitRateInfoList;
	}

	public static TeamProfitInfo parseTeamProfitInfo(TeamProfitInfo teamProfitInfo)
	{
		if (teamProfitInfo != null)
		{
		}
		return teamProfitInfo;
	}

	public static List<TeamProfitInfo> parseTeamProfitInfoList(List<TeamProfitInfo> teamProfitInfoList)
	{
		if (teamProfitInfoList != null && teamProfitInfoList.size() > 0)
		{
			for (TeamProfitInfo teamProfitInfo : teamProfitInfoList)
			{
				parseTeamProfitInfo(teamProfitInfo);
			}
		}
		return teamProfitInfoList;
	}

	public static TeamLeaderProfitInfo parseTeamLeaderProfitInfo(TeamLeaderProfitInfo teamLeaderProfitInfo)
	{
		if (teamLeaderProfitInfo != null)
		{
		}
		return teamLeaderProfitInfo;
	}

	public static List<TeamLeaderProfitInfo> parseTeamLeaderProfitInfoList(List<TeamLeaderProfitInfo> teamLeaderProfitInfoList)
	{
		if (teamLeaderProfitInfoList != null && teamLeaderProfitInfoList.size() > 0)
		{
			for (TeamLeaderProfitInfo teamLeaderProfitInfo : teamLeaderProfitInfoList)
			{
				parseTeamLeaderProfitInfo(teamLeaderProfitInfo);
			}
		}
		return teamLeaderProfitInfoList;
	}

	public static MatchStatisticsLogInfo parseMatchStatisticsLogInfo(MatchStatisticsLogInfo matchStatisticsLogInfo)
	{
		if (matchStatisticsLogInfo != null)
		{
		}
		return matchStatisticsLogInfo;
	}

	public static List<MatchStatisticsLogInfo> parseMatchStatisticsLogInfoList(List<MatchStatisticsLogInfo> matchStatisticsLogInfoList)
	{
		if (matchStatisticsLogInfoList != null && matchStatisticsLogInfoList.size() > 0)
		{
			for (MatchStatisticsLogInfo matchStatisticsLogInfo : matchStatisticsLogInfoList)
			{
				parseMatchStatisticsLogInfo(matchStatisticsLogInfo);
			}
		}
		return matchStatisticsLogInfoList;
	}

	public static TeamLeaderProfitConfigInfo parseTeamLeaderProfitConfigInfo(TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo)
	{
		if (teamLeaderProfitConfigInfo != null)
		{
		}
		return teamLeaderProfitConfigInfo;
	}

	public static List<TeamLeaderProfitConfigInfo> parseTeamLeaderProfitConfigInfoList(List<TeamLeaderProfitConfigInfo> teamLeaderProfitConfigInfoList)
	{
		if (teamLeaderProfitConfigInfoList != null && teamLeaderProfitConfigInfoList.size() > 0)
		{
			for (TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo : teamLeaderProfitConfigInfoList)
			{
				parseTeamLeaderProfitConfigInfo(teamLeaderProfitConfigInfo);
			}
		}
		return teamLeaderProfitConfigInfoList;
	}

	public static DayStatisticsLogInfo parseDayStatisticsLogInfo(DayStatisticsLogInfo dayStatisticsLogInfo)
	{
		if (dayStatisticsLogInfo != null)
		{
		}
		return dayStatisticsLogInfo;
	}

	public static List<DayStatisticsLogInfo> parseDayStatisticsLogInfoList(List<DayStatisticsLogInfo> dayStatisticsLogInfoList)
	{
		if (dayStatisticsLogInfoList != null && dayStatisticsLogInfoList.size() > 0)
		{
			for (DayStatisticsLogInfo dayStatisticsLogInfo : dayStatisticsLogInfoList)
			{
				parseDayStatisticsLogInfo(dayStatisticsLogInfo);
			}
		}
		return dayStatisticsLogInfoList;
	}

	public static MonthStatisticsLogInfo parseMonthStatisticsLogInfo(MonthStatisticsLogInfo monthStatisticsLogInfo)
	{
		if (monthStatisticsLogInfo != null)
		{
		}
		return monthStatisticsLogInfo;
	}

	public static List<MonthStatisticsLogInfo> parseMonthStatisticsLogInfoList(List<MonthStatisticsLogInfo> monthStatisticsLogInfoList)
	{
		if (monthStatisticsLogInfoList != null && monthStatisticsLogInfoList.size() > 0)
		{
			for (MonthStatisticsLogInfo monthStatisticsLogInfo : monthStatisticsLogInfoList)
			{
				parseMonthStatisticsLogInfo(monthStatisticsLogInfo);
			}
		}
		return monthStatisticsLogInfoList;
	}

	public static DayUserStatisticsLogInfo parseDayUserStatisticsLogInfo(DayUserStatisticsLogInfo dayUserStatisticsLogInfo)
	{
		if (dayUserStatisticsLogInfo != null)
		{
		}
		return dayUserStatisticsLogInfo;
	}

	public static List<DayUserStatisticsLogInfo> parseDayUserStatisticsLogInfoList(List<DayUserStatisticsLogInfo> dayUserStatisticsLogInfoList)
	{
		if (dayUserStatisticsLogInfoList != null && dayUserStatisticsLogInfoList.size() > 0)
		{
			for (DayUserStatisticsLogInfo dayUserStatisticsLogInfo : dayUserStatisticsLogInfoList)
			{
				parseDayUserStatisticsLogInfo(dayUserStatisticsLogInfo);
			}
		}
		return dayUserStatisticsLogInfoList;
	}

	public static UserTypeConditionInfo parseUserTypeConditionInfo(UserTypeConditionInfo userTypeConditionInfo)
	{
		if (userTypeConditionInfo != null)
		{
		}
		return userTypeConditionInfo;
	}

	public static List<UserTypeConditionInfo> parseUserTypeConditionInfoList(List<UserTypeConditionInfo> userTypeConditionInfoList)
	{
		if (userTypeConditionInfoList != null && userTypeConditionInfoList.size() > 0)
		{
			for (UserTypeConditionInfo userTypeConditionInfo : userTypeConditionInfoList)
			{
				parseUserTypeConditionInfo(userTypeConditionInfo);
			}
		}
		return userTypeConditionInfoList;
	}

	public static UserBetReturnInfo parseUserBetReturnInfo(UserBetReturnInfo userBetReturnInfo)
	{
		if (userBetReturnInfo != null)
		{
		}
		return userBetReturnInfo;
	}

	public static List<UserBetReturnInfo> parseUserBetReturnInfoList(List<UserBetReturnInfo> userBetReturnInfoList)
	{
		if (userBetReturnInfoList != null && userBetReturnInfoList.size() > 0)
		{
			for (UserBetReturnInfo userBetReturnInfo : userBetReturnInfoList)
			{
				parseUserBetReturnInfo(userBetReturnInfo);
			}
		}
		return userBetReturnInfoList;
	}

	public static MsgInfo parseMsgInfo(MsgInfo msgInfo)
	{
		if (msgInfo != null)
		{
		}
		return msgInfo;
	}

	public static List<MsgInfo> parseMsgInfoList(List<MsgInfo> msgInfoList)
	{
		if (msgInfoList != null && msgInfoList.size() > 0)
		{
			for (MsgInfo msgInfo : msgInfoList)
			{
				parseMsgInfo(msgInfo);
			}
		}
		return msgInfoList;
	}

	public static AdviceInfo parseAdviceInfo(AdviceInfo adviceInfo)
	{
		if (adviceInfo != null)
		{
		}
		return adviceInfo;
	}

	public static List<AdviceInfo> parseAdviceInfoList(List<AdviceInfo> adviceInfoList)
	{
		if (adviceInfoList != null && adviceInfoList.size() > 0)
		{
			for (AdviceInfo adviceInfo : adviceInfoList)
			{
				parseAdviceInfo(adviceInfo);
			}
		}
		return adviceInfoList;
	}

	public static AdviceReplyInfo parseAdviceReplyInfo(AdviceReplyInfo adviceReplyInfo)
	{
		if (adviceReplyInfo != null)
		{
		}
		return adviceReplyInfo;
	}

	public static List<AdviceReplyInfo> parseAdviceReplyInfoList(List<AdviceReplyInfo> adviceReplyInfoList)
	{
		if (adviceReplyInfoList != null && adviceReplyInfoList.size() > 0)
		{
			for (AdviceReplyInfo adviceReplyInfo : adviceReplyInfoList)
			{
				parseAdviceReplyInfo(adviceReplyInfo);
			}
		}
		return adviceReplyInfoList;
	}

	public static AdviceTypeInfo parseAdviceTypeInfo(AdviceTypeInfo adviceTypeInfo)
	{
		if (adviceTypeInfo != null)
		{
		}
		return adviceTypeInfo;
	}

	public static List<AdviceTypeInfo> parseAdviceTypeInfoList(List<AdviceTypeInfo> adviceTypeInfoList)
	{
		if (adviceTypeInfoList != null && adviceTypeInfoList.size() > 0)
		{
			for (AdviceTypeInfo adviceTypeInfo : adviceTypeInfoList)
			{
				parseAdviceTypeInfo(adviceTypeInfo);
			}
		}
		return adviceTypeInfoList;
	}
}
