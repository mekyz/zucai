package com.bet.daos;

import java.util.List;
import java.util.Map;

import com.bet.orms.TeamLeaderProfitInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.daos.IAbstractDao;

public interface ITeamLeaderProfitInfoDao extends IAbstractDao<TeamLeaderProfitInfo>
{
	public static final String ID = "id";
	public static final String PROFIT_ID = "profitId";
	public static final String PHASE_ID = "phaseId";
	public static final String USER_ID = "userId";
	public static final String USER_BET_MONEY = "userBetMoney";
	public static final String TEAM_BET_MONEY = "teamBetMoney";
	public static final String PROFIT_TYPE = "profitType";
	public static final String PROFIT_MONEY = "profitMoney";
	public static final String PROFIT_USER_MONEY = "profitUserMoney";
	public static final String FEE = "fee";
	public static final String PROFIT_STATUS = "profitStatus";
	public static final String SYS_STATUS = "sysStatus";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteTeamLeaderProfitInfoByProfitId(String profitId) throws HibernateJsonResultException;

	public TeamLeaderProfitInfo getTeamLeaderProfitInfoByProfitId(String profitId) throws HibernateJsonResultException;

	public long updateValueByProfitId(String profitId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */

	public long getSum(String colName, String whereClause, Map<String, Object> params, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
}
