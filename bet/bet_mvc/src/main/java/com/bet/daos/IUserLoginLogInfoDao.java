package com.bet.daos;

import java.util.Map;

import com.bet.orms.UserLoginLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IUserLoginLogInfoDao extends IAbstractDao<UserLoginLogInfo>
{
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String IP = "ip";
	public static final String AGENT_ID = "agentId";
	public static final String PLATFORM = "platform";
	public static final String DEVICE_NAME = "deviceName";
	public static final String SYS_VERSION = "sysVersion";
	public static final String VERSION_CODE = "versionCode";
	public static final String SIGN = "sign";
	public static final String V = "v";
	public static final String SESSION_ID = "sessionId";
	public static final String PARAMS = "params";
	public static final String STATUS = "status";
	public static final String REMARK = "remark";
	public static final String ADD_DATE_LONG = "addDateLong";

	public void deleteUserLoginLogInfoById(int id) throws HibernateJsonResultException;

	public UserLoginLogInfo getUserLoginLogInfoById(int id) throws HibernateJsonResultException;

	public long updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
