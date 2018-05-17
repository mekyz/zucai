package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.ConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IConfigInfoService
{
	public String genId();

	public ConfigInfo addConfigInfo(ConfigInfo configInfo) throws HibernateJsonResultException;

	public ConfigInfo updateConfigInfo(ConfigInfo configInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteConfigInfo(ConfigInfo configInfo) throws HibernateJsonResultException;

	public void deleteConfigInfoByConfigName(String configName) throws HibernateJsonResultException;

	public ConfigInfo getConfigInfoByConfigName(String configName) throws HibernateJsonResultException;

	public boolean updateValueByConfigName(String configName, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public ConfigInfo getConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public boolean updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<ConfigInfo> getConfigInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getConfigInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	/**
	 * 更新配置
	 * 
	 * @param configId
	 *            配置ID
	 * @param value
	 * @return
	 */
	public ReturnInfo updateConfig(String configId, String value);

	/**
	 * 获取默认配置的值
	 * 
	 * @param configId
	 *            配置ID
	 * @return
	 */
	public String getDefaultValue(String configId);

	/**
	 * 获取配置的值
	 * 
	 * @param configId
	 *            配置ID
	 * @return
	 */
	public String getValue(String configId);

	/**
	 * 获取配置的值
	 * 
	 * @param configId
	 *            配置ID
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public String getValue(String configId, String defaultValue);

	/**
	 * 取配置值int类型
	 * 
	 * @param configId
	 *            配置名
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public int getIntValue(String configId, int defaultValue);

	/**
	 * 取配置值int类型
	 * 
	 * @param configId
	 *            配置名
	 * @return
	 */
	public int getIntValue(String configId);

	/**
	 * 取配置值double类型
	 * 
	 * @param configId
	 *            配置名
	 * @return
	 */
	public double getDoubleValue(String configId);

	/**
	 * 取配置值double类型
	 * 
	 * @param configId
	 *            配置名
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public double getDoubleValue(String configId, double defaultValue);

	/**
	 * 取配置值boolean类型
	 * 
	 * @param configId
	 *            配置名
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public boolean getBooleanValue(String configId, boolean defaultValue);

	/**
	 * 取配置值boolean类型
	 * 
	 * @param configId
	 *            配置名
	 * @return
	 */
	public boolean getBooleanValue(String configId);

	/**
	 * 获取本项目地址
	 * 
	 * @return
	 */
	public String getServerUrl();
}
