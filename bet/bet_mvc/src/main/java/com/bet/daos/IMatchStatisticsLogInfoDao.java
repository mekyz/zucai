package com.bet.daos;

import java.util.Map;

import com.bet.orms.MatchStatisticsLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IMatchStatisticsLogInfoDao extends IAbstractDao<MatchStatisticsLogInfo>
{
	public static final String ID = "id";
	public static final String PHASE_ID = "phaseId";
	public static final String USER_BET_MONEY = "userBetMoney";
	public static final String FINAL_BET_MONEY = "finalBetMoney";
	public static final String FINAL_BONUS_MONEY = "finalBonusMoney";
	public static final String FINAL_WIN_MONEY = "finalWinMoney";
	public static final String HALF_BET_MONEY = "halfBetMoney";
	public static final String HALF_BONUS_MONEY = "halfBonusMoney";
	public static final String HALF_WIN_MONEY = "halfWinMoney";
	public static final String COUNT_BET_MONEY = "countBetMoney";
	public static final String COUNT_BONUS_MONEY = "countBonusMoney";
	public static final String COUNT_WIN_MONEY = "countWinMoney";
	public static final String SHARE_BONUS_MONEY = "shareBonusMoney";
	public static final String AGENT_BONUS_MONEY = "agentBonusMoney";
	public static final String SAME_LEVEL1_BONUS_MONEY = "sameLevel1BonusMoney";
	public static final String SAME_LEVEL2_BONUS_MONEY = "sameLevel2BonusMoney";
	public static final String BENEFIT_BONUS_MONEY = "benefitBonusMoney";
	public static final String TEAM_MONEY = "teamMoney";
	public static final String COMPANY_MONEY = "companyMoney";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteMatchStatisticsLogInfoByPhaseId(String phaseId) throws HibernateJsonResultException;

	public MatchStatisticsLogInfo getMatchStatisticsLogInfoByPhaseId(String phaseId) throws HibernateJsonResultException;

	public long updateValueByPhaseId(String phaseId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
