package com.bet.daos;

import java.util.Map;

import com.bet.orms.TeamInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface ITeamInfoDao extends IAbstractDao<TeamInfo>
{
	public static final String ID = "id";
	public static final String TEAM_ID = "teamId";
	public static final String NAME = "name";
	public static final String PIC_URL = "picUrl";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteTeamInfoByTeamId(String teamId) throws HibernateJsonResultException;

	public TeamInfo getTeamInfoByTeamId(String teamId) throws HibernateJsonResultException;

	public long updateValueByTeamId(String teamId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
