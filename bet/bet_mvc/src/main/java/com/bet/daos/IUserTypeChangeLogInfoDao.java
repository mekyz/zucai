package com.bet.daos;

import java.util.Map;

import com.bet.orms.UserTypeChangeLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IUserTypeChangeLogInfoDao extends IAbstractDao<UserTypeChangeLogInfo>
{
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String ADMIN_ID = "adminId";
	public static final String USER_TYPE = "userType";
	public static final String ADD_DATE_LONG = "addDateLong";

	public void deleteUserTypeChangeLogInfoById(int id) throws HibernateJsonResultException;

	public UserTypeChangeLogInfo getUserTypeChangeLogInfoById(int id) throws HibernateJsonResultException;

	public long updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
