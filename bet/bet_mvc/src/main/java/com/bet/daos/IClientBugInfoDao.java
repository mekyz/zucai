package com.bet.daos;

import java.util.Map;

import com.bet.orms.ClientBugInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IClientBugInfoDao extends IAbstractDao<ClientBugInfo>
{
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String PLATFORM = "platform";
	public static final String VERSION_CODE = "versionCode";
	public static final String SYS_VERSION = "sysVersion";
	public static final String DEVICE_NAME = "deviceName";
	public static final String CONTENT = "content";
	public static final String URL = "url";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";

	public void deleteClientBugInfoById(int id) throws HibernateJsonResultException;

	public ClientBugInfo getClientBugInfoById(int id) throws HibernateJsonResultException;

	public long updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
