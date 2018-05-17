package com.bet.daos;

import java.util.Map;

import com.bet.orms.UserTypeConditionInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IUserTypeConditionInfoDao extends IAbstractDao<UserTypeConditionInfo>
{
	public static final String ID = "id";
	public static final String USER_TYPE = "userType";
	public static final String DIRECT_COUNT = "directCount";
	public static final String TEAM_COUNT = "teamCount";
	public static final String MONEY = "money";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteUserTypeConditionInfoByUserType(byte userType) throws HibernateJsonResultException;

	public UserTypeConditionInfo getUserTypeConditionInfoByUserType(byte userType) throws HibernateJsonResultException;

	public long updateValueByUserType(byte userType, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
