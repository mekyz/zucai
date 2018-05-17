package com.bet.daos;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserBetInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.daos.IAbstractDao;

public interface IUserBetInfoDao extends IAbstractDao<UserBetInfo>
{
	public static final String ID = "id";
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
	public static final String WIN_MONEY = "winMoney";
	public static final String USER_MONEY = "userMoney";
	public static final String FEE = "fee";
	public static final String WIN_STATUS = "winStatus";
	public static final String PROFIT_STATUS = "profitStatus";
	public static final String TEAM_PROFIT_STATUS = "teamProfitStatus";
	public static final String SYS_STATUS = "sysStatus";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteUserBetInfoByBetId(String betId) throws HibernateJsonResultException;

	public UserBetInfo getUserBetInfoByBetId(String betId) throws HibernateJsonResultException;

	public long updateValueByBetId(String betId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */

	public List<UserBetInfo> getUserListByUserType(byte userType, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getSum(String colName, String whereClause, Map<String, Object> params, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	/**
	 * 获取团队下注总金额
	 * 
	 * @param userId
	 * @param searchInfos
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public long getTeamUserTotalMoney(String userId, String whereClause, Map<String, Object> params, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
}
