package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.ICityInfoDao;
import com.bet.orms.CityInfo;
import com.bet.services.ICityInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("cityInfoService")
public class ICityInfoServiceImpl implements ICityInfoService
{
	@Autowired
	private ICityInfoDao cityInfoDao;

	@Override
	public String genId()
	{
		return cityInfoDao.genId();
	}

	@Override
	public CityInfo addCityInfo(CityInfo cityInfo) throws HibernateJsonResultException
	{
		return cityInfoDao.add(cityInfo);
	}

	@Override
	public CityInfo updateCityInfo(CityInfo cityInfo) throws HibernateJsonResultException
	{
		return cityInfoDao.update(cityInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return cityInfoDao.updateValue(CityInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteCityInfo(CityInfo cityInfo) throws HibernateJsonResultException
	{
		cityInfoDao.delete(cityInfo);
	}

	@Override
	public void deleteCityInfoByName(String name) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { name }, new String[] { "name不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		cityInfoDao.deleteCityInfoByName(name);
	}

	@Override
	public CityInfo getCityInfoByName(String name) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { name }, new String[] { "name不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return cityInfoDao.getCityInfoByName(name);
	}

	@Override
	public boolean updateValueByName(String name, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ICityInfoDao.NAME, name);
		return cityInfoDao.updateValue(CityInfo.class, valueMap, whereMap) > 0;
	}

	@Override
	public void deleteCityInfoByCityId(String cityId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { cityId }, new String[] { "cityId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		cityInfoDao.deleteCityInfoByCityId(cityId);
	}

	@Override
	public CityInfo getCityInfoByCityId(String cityId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { cityId }, new String[] { "cityId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return cityInfoDao.getCityInfoByCityId(cityId);
	}

	@Override
	public boolean updateValueByCityId(String cityId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ICityInfoDao.CITY_ID, cityId);
		return cityInfoDao.updateValue(CityInfo.class, valueMap, whereMap) > 0;
	}

	public List<CityInfo> getCityInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return cityInfoDao.getList(CityInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<CityInfo> getCityInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return cityInfoDao.getList(CityInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getCityInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return cityInfoDao.getListCount(CityInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
