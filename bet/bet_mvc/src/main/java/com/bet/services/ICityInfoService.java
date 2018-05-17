package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.CityInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface ICityInfoService
{
	public String genId();

	public CityInfo addCityInfo(CityInfo cityInfo) throws HibernateJsonResultException;

	public CityInfo updateCityInfo(CityInfo cityInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteCityInfo(CityInfo cityInfo) throws HibernateJsonResultException;

	public void deleteCityInfoByName(String name) throws HibernateJsonResultException;

	public CityInfo getCityInfoByName(String name) throws HibernateJsonResultException;

	public boolean updateValueByName(String name, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteCityInfoByCityId(String cityId) throws HibernateJsonResultException;

	public CityInfo getCityInfoByCityId(String cityId) throws HibernateJsonResultException;

	public boolean updateValueByCityId(String cityId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<CityInfo> getCityInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getCityInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
