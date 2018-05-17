package com.bet.daos;

import java.util.Map;

import com.bet.orms.ApiRequestLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IApiRequestLogInfoDao extends IAbstractDao<ApiRequestLogInfo>
{
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String IP = "ip";
	public static final String URL = "url";
	public static final String AGENT_ID = "agentId";
	public static final String PLATFORM = "platform";
	public static final String DEVICE_NAME = "deviceName";
	public static final String SYS_VERSION = "sysVersion";
	public static final String VERSION_CODE = "versionCode";
	public static final String SIGN = "sign";
	public static final String V = "v";
	public static final String SESSION_ID = "sessionId";
	public static final String PARAMS = "params";
	public static final String ADD_DATE_LONG = "addDateLong";

	public void deleteApiRequestLogInfoById(int id) throws HibernateJsonResultException;

	public ApiRequestLogInfo getApiRequestLogInfoById(int id) throws HibernateJsonResultException;

	public long updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
