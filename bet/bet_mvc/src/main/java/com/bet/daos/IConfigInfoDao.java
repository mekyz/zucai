package com.bet.daos;

import java.util.Map;

import com.bet.orms.ConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IConfigInfoDao extends IAbstractDao<ConfigInfo>
{
	public static final String ID = "id";
	public static final String CONFIG_ID = "configId";
	public static final String CONFIG_NAME = "configName";
	public static final String CONTENT = "content";
	public static final String STATUS = "status";
	public static final String REMARK = "remark";

	public void deleteConfigInfoByConfigName(String configName) throws HibernateJsonResultException;

	public ConfigInfo getConfigInfoByConfigName(String configName) throws HibernateJsonResultException;

	public long updateValueByConfigName(String configName, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public ConfigInfo getConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public long updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
