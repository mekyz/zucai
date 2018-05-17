package com.bet.daos;

import java.util.Map;

import com.bet.orms.TeamLeaderProfitConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface ITeamLeaderProfitConfigInfoDao extends IAbstractDao<TeamLeaderProfitConfigInfo>
{
	public static final String ID = "id";
	public static final String CONFIG_ID = "configId";
	public static final String NAME = "name";
	public static final String USER_TYPE = "userType";
	public static final String TEAM_BET_MONEY = "teamBetMoney";
	public static final String USER_BET_MONEY = "userBetMoney";
	public static final String PROFIT_PERCENT = "profitPercent";
	public static final String PROFIT_MAX_MONEY = "profitMaxMoney";
	public static final String FEE = "fee";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteTeamLeaderProfitConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public TeamLeaderProfitConfigInfo getTeamLeaderProfitConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public long updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
