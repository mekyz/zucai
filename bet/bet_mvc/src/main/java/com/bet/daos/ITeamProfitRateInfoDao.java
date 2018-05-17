package com.bet.daos;

import java.util.Map;

import com.bet.orms.TeamProfitRateInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface ITeamProfitRateInfoDao extends IAbstractDao<TeamProfitRateInfo>
{
	public static final String ID = "id";
	public static final String RATE_ID = "rateId";
	public static final String NAME = "name";
	public static final String TYPE1 = "type1";
	public static final String TYPE2 = "type2";
	public static final String TYPE3 = "type3";
	public static final String TYPE4 = "type4";
	public static final String TYPE5 = "type5";
	public static final String TYPE6 = "type6";
	public static final String TYPE7 = "type7";
	public static final String SAME_LEVEL = "sameLevel";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteTeamProfitRateInfoByRateId(String rateId) throws HibernateJsonResultException;

	public TeamProfitRateInfo getTeamProfitRateInfoByRateId(String rateId) throws HibernateJsonResultException;

	public long updateValueByRateId(String rateId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
