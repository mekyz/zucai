package com.bet.daos;

import java.util.Map;

import com.bet.orms.CityInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface ICityInfoDao extends IAbstractDao<CityInfo>
{
	public static final String ID = "id";
	public static final String CITY_ID = "cityId";
	public static final String NAME = "name";
	public static final String CODE = "code";
	public static final String PROVINCE_ID = "provinceId";

	public void deleteCityInfoByName(String name) throws HibernateJsonResultException;

	public CityInfo getCityInfoByName(String name) throws HibernateJsonResultException;

	public long updateValueByName(String name, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteCityInfoByCityId(String cityId) throws HibernateJsonResultException;

	public CityInfo getCityInfoByCityId(String cityId) throws HibernateJsonResultException;

	public long updateValueByCityId(String cityId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
