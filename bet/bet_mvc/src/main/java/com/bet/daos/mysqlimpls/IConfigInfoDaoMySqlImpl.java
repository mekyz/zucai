package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IConfigInfoDao;
import com.bet.orms.ConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("configInfoDao")
public class IConfigInfoDaoMySqlImpl extends IAbstractDaoImpl<ConfigInfo> implements IConfigInfoDao
{
	@Override
	public ReturnInfo checkParams(ConfigInfo configInfo)
	{
		if (configInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "ConfigInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configInfo.getContent(), configInfo.getStatus() + "", configInfo.getConfigId(), configInfo.getConfigName() },
			new String[] { "content不能为空！", "status不能为空！", "configId不能为空！", "configName不能为空！" });
		return returnInfo;
	}

	@Override
	public ConfigInfo add(ConfigInfo configInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(configInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(configInfo);
	}

	@Override
	public ConfigInfo update(ConfigInfo configInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(configInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(configInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(ConfigInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteConfigInfoByConfigName(String configName) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configName }, new String[] { "configName不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		ConfigInfo configInfo = getConfigInfoByConfigName(configName);
		if (configInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "ConfigInfo不存在！");
		}
		delete(configInfo);
	}

	@Override
	public ConfigInfo getConfigInfoByConfigName(String configName) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configName }, new String[] { "configName不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", ConfigInfo.class.getSimpleName(), IConfigInfoDao.CONFIG_NAME, IConfigInfoDao.CONFIG_NAME);
		Map<String, Object> params = new HashMap<>();
		params.put(IConfigInfoDao.CONFIG_NAME, configName);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByConfigName(String configName, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IConfigInfoDao.CONFIG_NAME, configName);
		return updateValue(ConfigInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		ConfigInfo configInfo = getConfigInfoByConfigId(configId);
		if (configInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "ConfigInfo不存在！");
		}
		delete(configInfo);
	}

	@Override
	public ConfigInfo getConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", ConfigInfo.class.getSimpleName(), IConfigInfoDao.CONFIG_ID, IConfigInfoDao.CONFIG_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IConfigInfoDao.CONFIG_ID, configId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IConfigInfoDao.CONFIG_ID, configId);
		return updateValue(ConfigInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
