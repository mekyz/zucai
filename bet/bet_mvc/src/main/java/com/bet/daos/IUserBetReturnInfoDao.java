package com.bet.daos;

import java.util.Map;

import com.bet.orms.UserBetReturnInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IUserBetReturnInfoDao extends IAbstractDao<UserBetReturnInfo>
{
	public static final String ID = "id";
	public static final String LOG_ID = "logId";
	public static final String BET_ID = "betId";
	public static final String USER_ID = "userId";
	public static final String PROFIT_ID = "profitId";
	public static final String PHASE_ID = "phaseId";
	public static final String MATCH_NAME = "matchName";
	public static final String MATCH_TYPE = "matchType";
	public static final String SCORE1 = "score1";
	public static final String SCORE2 = "score2";
	public static final String MONEY = "money";
	public static final String MONEY_UNIT = "moneyUnit";
	public static final String REMARK = "remark";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteUserBetReturnInfoByBetId(String betId) throws HibernateJsonResultException;

	public UserBetReturnInfo getUserBetReturnInfoByBetId(String betId) throws HibernateJsonResultException;

	public long updateValueByBetId(String betId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteUserBetReturnInfoByLogId(String logId) throws HibernateJsonResultException;

	public UserBetReturnInfo getUserBetReturnInfoByLogId(String logId) throws HibernateJsonResultException;

	public long updateValueByLogId(String logId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
