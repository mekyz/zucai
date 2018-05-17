package com.bet.daos;

import java.util.Map;

import com.bet.orms.AdminInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IAdminInfoDao extends IAbstractDao<AdminInfo>
{
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String PASSWORD = "password";
	public static final String USER_LEVEL = "userLevel";
	public static final String NAME = "name";
	public static final String PIC_URL = "picUrl";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteAdminInfoByUserId(String userId) throws HibernateJsonResultException;

	public AdminInfo getAdminInfoByUserId(String userId) throws HibernateJsonResultException;

	public long updateValueByUserId(String userId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
