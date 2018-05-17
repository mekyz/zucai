package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IConfigInfoDao;
import com.bet.orms.ConfigInfo;
import com.bet.services.IConfigInfoService;
import com.bet.utils.BetConstValues;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("configInfoService")
public class IConfigInfoServiceImpl implements IConfigInfoService
{
	@Autowired
	private IConfigInfoDao configInfoDao;

	@Override
	public String genId()
	{
		return configInfoDao.genId();
	}

	@Override
	public ConfigInfo addConfigInfo(ConfigInfo configInfo) throws HibernateJsonResultException
	{
		return configInfoDao.add(configInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return configInfoDao.updateValue(ConfigInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteConfigInfo(ConfigInfo configInfo) throws HibernateJsonResultException
	{
		configInfoDao.delete(configInfo);
	}

	@Override
	public void deleteConfigInfoByConfigName(String configName) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configName }, new String[] { "configName不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		configInfoDao.deleteConfigInfoByConfigName(configName);
	}

	@Override
	public ConfigInfo getConfigInfoByConfigName(String configName) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configName }, new String[] { "configName不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return configInfoDao.getConfigInfoByConfigName(configName);
	}

	@Override
	public boolean updateValueByConfigName(String configName, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IConfigInfoDao.CONFIG_NAME, configName);
		return configInfoDao.updateValue(ConfigInfo.class, valueMap, whereMap) > 0;
	}

	@Override
	public void deleteConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		configInfoDao.deleteConfigInfoByConfigId(configId);
	}

	@Override
	public ConfigInfo getConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return configInfoDao.getConfigInfoByConfigId(configId);
	}

	@Override
	public boolean updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IConfigInfoDao.CONFIG_ID, configId);
		return configInfoDao.updateValue(ConfigInfo.class, valueMap, whereMap) > 0;
	}

	public List<ConfigInfo> getConfigInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return configInfoDao.getList(ConfigInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<ConfigInfo> getConfigInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return configInfoDao.getList(ConfigInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getConfigInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return configInfoDao.getListCount(ConfigInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	private static Map<String, Object> configMap = new HashMap<>();

	@Override
	public ConfigInfo updateConfigInfo(ConfigInfo configInfo) throws HibernateJsonResultException
	{
		if (configInfo != null)
		{
			configMap.remove(configInfo.getConfigId());
		}
		return configInfoDao.update(configInfo);
	}

	@Override
	public ReturnInfo updateConfig(String configId, String value)
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId, value }, new String[] { "配置ID不能为空！", "配置内容不能为空！" });
		if (returnInfo != null)
		{
			return returnInfo;
		}
		configMap.remove(configId);
		ConfigInfo configInfo = configInfoDao.getConfigInfoByConfigId(configId);
		if (configInfo != null)
		{
			if (BetConstValues.CONFIG_PERCENT_DOUBLE_MAP.containsKey(configId))
			{
				try
				{
					double d = Double.parseDouble(value);
					if (d < 0 || d > 1)
					{
						return new ReturnInfo(ErrorInfo.PARAM_ERROR, "请输入0-1之间的小数！");
					}
				}
				catch (Exception e)
				{
					return new ReturnInfo(ErrorInfo.PARAM_ERROR, "请输入0-1之间的小数！");
				}
			}
			configInfo.setContent(value);
			if (configInfoDao.update(configInfo) == null)
			{
				return new ReturnInfo(ErrorInfo.HIBERNATE_ERROR, "更新失败！");
			}
			else
			{
				// configMap.put(configId, value);
				return new ReturnInfo(ErrorInfo.SUCCESS, "更新成功！");
			}
		}
		else
		{
			configInfo = new ConfigInfo(configId, configId, value, StatusType.ENABLED.getStatus());
			if (configInfoDao.add(configInfo) == null)
			{
				return new ReturnInfo(ErrorInfo.HIBERNATE_ERROR, "更新失败！");
			}
			else
			{
				// configMap.put(configId, value);
				return new ReturnInfo(ErrorInfo.SUCCESS, "更新成功！");
			}
		}
	}

	@Override
	public String getDefaultValue(String configId)
	{
		if (StringTools.isNull(configId))
		{
			return "";
		}
		String content = BetConstValues.CONFIG_STRING_MAP.get(configId);
		if (content != null)
		{
			return content;
		}
		return "";
	}

	@Override
	public String getValue(String configId, String defaultValue)
	{
		if (StringTools.isNull(configId))
		{
			return defaultValue;
		}
		String result = (String) configMap.get(configId);
		if (result != null)
		{
			return result;
		}
		ConfigInfo configInfo = getConfigInfoByConfigId(configId);
		if (configInfo != null && !StringTools.isNull(configInfo.getContent()))
		{
			result = configInfo.getContent();
			configMap.put(configId, result);
			return result;
		}
		result = BetConstValues.CONFIG_STRING_MAP.get(configId);
		if (result != null)
		{
			configMap.put(configId, result);
			return result;
		}
		return defaultValue;
	}

	@Override
	public String getValue(String configId)
	{
		return getValue(configId, "");
	}

	@Override
	public int getIntValue(String configId, int defaultValue)
	{
		if (StringTools.isNull(configId))
		{
			return defaultValue;
		}
		Integer result = (Integer) configMap.get(configId);
		if (result != null)
		{
			return result;
		}
		ConfigInfo configInfo = getConfigInfoByConfigId(configId);
		if (configInfo != null && !StringTools.isNull(configInfo.getContent()))
		{
			try
			{
				result = Integer.parseInt(configInfo.getContent());
				configMap.put(configId, result);
				return result;
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
		}
		result = BetConstValues.CONFIG_INTEGER_MAP.get(configId);
		if (result != null)
		{
			configMap.put(configId, result);
			return result;
		}
		return defaultValue;
	}

	@Override
	public int getIntValue(String configId)
	{
		return getIntValue(configId, 0);
	}

	@Override
	public double getDoubleValue(String configId, double defaultValue)
	{
		if (StringTools.isNull(configId))
		{
			return defaultValue;
		}
		Double result = (Double) configMap.get(configId);
		if (result != null)
		{
			return result;
		}
		ConfigInfo configInfo = getConfigInfoByConfigId(configId);
		if (configInfo != null && !StringTools.isNull(configInfo.getContent()))
		{
			try
			{
				result = Double.parseDouble(configInfo.getContent());
				configMap.put(configId, result);
				return result;
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
		}
		result = BetConstValues.CONFIG_DOUBLE_MAP.get(configId);
		if (result == null)
		{
			result = BetConstValues.CONFIG_PERCENT_DOUBLE_MAP.get(configId);
		}
		if (result != null)
		{
			configMap.put(configId, result);
			return result;
		}
		return defaultValue;
	}

	@Override
	public double getDoubleValue(String configId)
	{
		return getDoubleValue(configId, 0);
	}

	@Override
	public boolean getBooleanValue(String configId, boolean defaultValue)
	{
		if (StringTools.isNull(configId))
		{
			return defaultValue;
		}
		Boolean result = (Boolean) configMap.get(configId);
		if (result != null)
		{
			return result;
		}
		ConfigInfo configInfo = getConfigInfoByConfigId(configId);
		if (configInfo != null && !StringTools.isNull(configInfo.getContent()))
		{
			result = configInfo.getContent().equalsIgnoreCase("true");
			configMap.put(configId, result);
			return result;
		}
		result = BetConstValues.CONFIG_BOOLEAN_MAP.get(configId);
		if (result != null)
		{
			configMap.put(configId, result);
			return result;
		}
		return defaultValue;
	}

	@Override
	public boolean getBooleanValue(String configId)
	{
		return getBooleanValue(configId, true);
	}

	@Override
	public String getServerUrl()
	{
		String server = getValue(BetConstValues.CONFIG_SERVER_URL, BetConstValues.CONFIG_STRING_MAP.get(BetConstValues.CONFIG_SERVER_URL));
		if (!StringTools.isNull(server) && !server.endsWith("/"))
		{
			server += "/";
		}
		return server;
	}
}
