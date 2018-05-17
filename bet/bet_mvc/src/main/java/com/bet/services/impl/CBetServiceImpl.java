package com.bet.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserBetInfoDao;
import com.bet.daos.IUserInfoDao;
import com.bet.enums.ApplyStatus;
import com.bet.enums.BodanType;
import com.bet.enums.MoneyUnit;
import com.bet.enums.UserBalanceLogType;
import com.bet.orms.MatchInfo;
import com.bet.orms.MatchProfitInfo;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.orms.UserBetInfo;
import com.bet.orms.UserBetReturnInfo;
import com.bet.orms.UserInfo;
import com.bet.services.CBetService;
import com.bet.services.IConfigInfoService;
import com.bet.services.IMatchInfoService;
import com.bet.services.IMatchProfitInfoService;
import com.bet.services.IUserBalanceLogInfoService;
import com.bet.services.IUserBetInfoService;
import com.bet.services.IUserBetReturnInfoService;
import com.bet.services.IUserInfoService;
import com.bet.utils.BetConstValues;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.GsonTools;
import com.lrcall.common.utils.LogTools;

@Repository("cBetService")
public class CBetServiceImpl implements CBetService
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
	private IUserBetReturnInfoService userBetReturnInfoService;

	@Override
	public ReturnInfo addUserBetInfo(UserBetInfo userBetInfo)
	{
		if (userBetInfo.getMoney() % 10000 != 0)
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "下注金额必须是100的整数倍！");
		}
		long current = System.currentTimeMillis();
		int minMoney = configInfoService.getIntValue(BetConstValues.CONFIG_MIN_BET);
		if (minMoney > userBetInfo.getMoney())
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, String.format("最低下注金额为%.2f，请重新输入！", (double) minMoney / 100));
		}
		MatchProfitInfo matchProfitInfo = matchProfitInfoService.getMatchProfitInfoByProfitId(userBetInfo.getProfitId());
		if (matchProfitInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "赛事波胆ID不存在！");
		}
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(matchProfitInfo.getPhaseId());
		if (matchInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "赛事ID不存在！");
		}
		if (matchInfo.getTimeEndsale() < current)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "赛事已停售！");
		}
		if (matchProfitInfo.getAmount() - matchProfitInfo.getSaledAmount() < userBetInfo.getMoney())
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "可下注份额不足！");
		}
		if (matchProfitInfo.getMatchType().equals(BodanType.HALF.getType()))
		{
			if (matchInfo.getHalfResultStatus() == StatusType.ENABLED.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "赛事已开奖，不能下注！");
			}
		}
		else if (matchProfitInfo.getMatchType().equals(BodanType.FULL.getType()))
		{
			if (matchInfo.getFinalResultStatus() == StatusType.ENABLED.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "赛事已开奖，不能下注！");
			}
		}
		UserInfo userInfo = userInfoService.getUserInfoByUserId(userBetInfo.getUserId());
		if (userInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "用户ID不存在！");
		}
		if (userBetInfo.getMoneyUnit() == MoneyUnit.POINT.getType())
		{
			if (userInfo.getPoint() < userBetInfo.getMoney())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "可用彩金不足，不能下注！");
			}
		}
		else if (userBetInfo.getMoneyUnit() == MoneyUnit.GIVE_POINT.getType())
		{
			if (userInfo.getGiveValidateDateLong() == null || userInfo.getGiveValidateDateLong() < current)
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "体验彩金已到期，不能下注！");
			}
			if (userInfo.getGivePoint() < userBetInfo.getMoney())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "体验彩金不足，不能下注！");
			}
		}
		userBetInfo.setBetId(userBetInfoService.genId());
		userBetInfo.setPhaseId(matchInfo.getPhaseId());
		userBetInfo.setMatchName(matchInfo.getMatchName());
		userBetInfo.setMatchType(matchProfitInfo.getMatchType());
		userBetInfo.setScore1(matchProfitInfo.getScore1());
		userBetInfo.setScore2(matchProfitInfo.getScore2());
		userBetInfo.setProfitStatus(StatusType.DISABLED.getStatus());
		userBetInfo.setStatus(ApplyStatus.PAYED.getStatus());
		userBetInfo = userBetInfoService.addUserBetInfo(userBetInfo);
		if (userBetInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加投注信息失败！");
		}
		// 更新用户余额信息
		if (userBetInfo.getMoneyUnit() == MoneyUnit.POINT.getType())
		{
			if (!userInfoService.updatePoint(IUserInfoDao.POINT, userBetInfo.getUserId(), -userBetInfo.getMoney()))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户彩金钱包失败！");
			}
		}
		else if (userBetInfo.getMoneyUnit() == MoneyUnit.GIVE_POINT.getType())
		{
			if (!userInfoService.updatePoint(IUserInfoDao.GIVE_POINT, userBetInfo.getUserId(), -userBetInfo.getMoney()))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户体验彩金失败！");
			}
		}
		// 更新已交易量
		if (!matchProfitInfoService.updateSaledAmount(userBetInfo.getProfitId(), userBetInfo.getMoney()))
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新可用份数失败！");
		}
		// 写入日志
		userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBetInfo.getUserId(), -userBetInfo.getMoney(), userBetInfo.getMoneyUnit(), UserBalanceLogType.BET.getType(),
			userBetInfo.getBetId(), null, null, null, String.format("用户下注%.2f元。", (double) userBetInfo.getMoney() / 100), current));
		return new ReturnInfo(ErrorInfo.SUCCESS, GsonTools.toJson(userBetInfo));
	}

	@Override
	public ReturnInfo deleteUserBetInfo(String betId, String remark)
	{
		UserBetInfo userBetInfo = userBetInfoService.getUserBetInfoByBetId(betId);
		if (userBetInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户下注信息不存在！");
		}
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(userBetInfo.getPhaseId());
		if (matchInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "赛事ID不存在！");
		}
		if (userBetInfo.getMatchType().equals(BodanType.HALF.getType()))
		{
			if (matchInfo.getHalfResultStatus() == StatusType.ENABLED.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "赛事已开奖，不能下注！");
			}
			if (matchInfo.getHalfScore1() != null || matchInfo.getHalfScore2() != null)
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "赛事已开奖，不能下注！");
			}
		}
		else if (userBetInfo.getMatchType().equals(BodanType.FULL.getType()))
		{
			if (matchInfo.getFinalResultStatus() == StatusType.ENABLED.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "赛事已开奖，不能下注！");
			}
			if (matchInfo.getFinalScore1() != null || matchInfo.getFinalScore2() != null)
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "赛事已开奖，不能下注！");
			}
		}
		long current = System.currentTimeMillis();
		// 添加撤回记录
		String logId = userBetReturnInfoService.genId();
		UserBetReturnInfo userBetReturnInfo = new UserBetReturnInfo(logId, betId, userBetInfo.getUserId(), userBetInfo.getProfitId(), userBetInfo.getPhaseId(), userBetInfo.getMatchName(),
			userBetInfo.getMatchType(), userBetInfo.getScore1(), userBetInfo.getScore2(), userBetInfo.getMoney(), userBetInfo.getMoneyUnit(), remark, StatusType.ENABLED.getStatus(), current, current);
		userBetReturnInfo = userBetReturnInfoService.addUserBetReturnInfo(userBetReturnInfo);
		// 返还本金
		if (userBetInfo.getMoneyUnit() == MoneyUnit.POINT.getType())
		{
			if (!userInfoService.updatePoint(IUserInfoDao.POINT, userBetInfo.getUserId(), userBetInfo.getMoney()))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "返还用户彩金失败！");
			}
		}
		else if (userBetInfo.getMoneyUnit() == MoneyUnit.GIVE_POINT.getType())
		{
			if (!userInfoService.updatePoint(IUserInfoDao.GIVE_POINT, userBetInfo.getUserId(), userBetInfo.getMoney()))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "返还用户体验彩金失败！");
			}
		}
		// 添加日志
		userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userBetInfo.getUserId(), userBetInfo.getMoney(), userBetInfo.getMoneyUnit(), UserBalanceLogType.BET_RETURN.getType(),
			betId, logId, null, null, String.format("用户撤回下注赛事%s“%s VS %s”(%s)。", matchInfo.getMatchName(), matchInfo.getHomeTeam(), matchInfo.getAwayTeam(), remark), current));
		// 删除下注记录
		userBetInfoService.deleteUserBetInfoByBetId(betId);
		return new ReturnInfo(ErrorInfo.SUCCESS, "");
	}

	@Override
	public ReturnInfo deleteUserBetInfosByPhaseId(String phaseId, String remark)
	{
		int start = 0;
		int size = 100;
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IUserBetInfoDao.PHASE_ID, phaseId, true, false));
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
				ReturnInfo returnInfo = deleteUserBetInfo(userBetInfo.getBetId(), remark);
				LogTools.getInstance().info("deleteUserBetInfosByPhaseId", "撤回用户下注:" + GsonTools.toJson(userBetInfo) + "的结果：" + GsonTools.toJson(returnInfo));
				if (ReturnInfo.isSuccess(returnInfo))
				{
					successCount++;
				}
				else
				{
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
		if (failCount > 0)
		{
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, String.format("撤回用户下注失败：%d个，已回滚事务！", failCount));
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, String.format("成功：%d个，失败：%d个。", successCount, failCount));
	}
}
