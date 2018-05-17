package com.bet.daos;

import java.util.Map;

import com.bet.orms.DayUserStatisticsLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IDayUserStatisticsLogInfoDao extends IAbstractDao<DayUserStatisticsLogInfo>
{
	public static final String ID = "id";
	public static final String LOG_ID = "logId";
	public static final String DAY_ID = "dayId";
	public static final String USER_ID = "userId";
	public static final String USER_BET_MONEY = "userBetMoney";
	public static final String USER_RECHARGE_MONEY = "userRechargeMoney";
	public static final String USER_WITHDRAW_MONEY = "userWithdrawMoney";
	public static final String FINAL_BONUS_MONEY = "finalBonusMoney";
	public static final String HALF_BONUS_MONEY = "halfBonusMoney";
	public static final String COUNT_BONUS_MONEY = "countBonusMoney";
	public static final String SHARE_BONUS_MONEY = "shareBonusMoney";
	public static final String AGENT_BONUS_MONEY = "agentBonusMoney";
	public static final String SAME_LEVEL1_BONUS_MONEY = "sameLevel1BonusMoney";
	public static final String SAME_LEVEL2_BONUS_MONEY = "sameLevel2BonusMoney";
	public static final String BENEFIT_BONUS_MONEY = "benefitBonusMoney";
	public static final String TEAM_MONEY = "teamMoney";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteDayUserStatisticsLogInfoByLogId(String logId) throws HibernateJsonResultException;

	public DayUserStatisticsLogInfo getDayUserStatisticsLogInfoByLogId(String logId) throws HibernateJsonResultException;

	public long updateValueByLogId(String logId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
