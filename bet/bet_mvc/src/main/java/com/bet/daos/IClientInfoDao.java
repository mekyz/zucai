package com.bet.daos;

import java.util.Map;

import com.bet.orms.ClientInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IClientInfoDao extends IAbstractDao<ClientInfo>
{
	public static final String ID = "id";
	public static final String PLATFORM = "platform";
	public static final String VERSION_NAME = "versionName";
	public static final String VERSION_CODE = "versionCode";
	public static final String AGENT_ID = "agentId";
	public static final String DEBUG_SIGN_KEY = "debugSignKey";
	public static final String RELEASE_SIGN_KEY = "releaseSignKey";
	public static final String URL = "url";
	public static final String CONTENT = "content";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteClientInfoById(int id) throws HibernateJsonResultException;

	public ClientInfo getClientInfoById(int id) throws HibernateJsonResultException;

	public long updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
