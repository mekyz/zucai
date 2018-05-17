package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.ICityInfoDao;
import com.bet.orms.CityInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("cityInfoDao")
public class ICityInfoDaoMySqlImpl extends IAbstractDaoImpl<CityInfo> implements ICityInfoDao
{
	@Override
	public ReturnInfo checkParams(CityInfo cityInfo)
	{
		if (cityInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "CityInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { cityInfo.getName(), cityInfo.getCityId() }, new String[] { "name不能为空！", "cityId不能为空！" });
		return returnInfo;
	}

	@Override
	public CityInfo add(CityInfo cityInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(cityInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(cityInfo);
	}

	@Override
	public CityInfo update(CityInfo cityInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(cityInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(cityInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(CityInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteCityInfoByName(String name) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { name }, new String[] { "name不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		CityInfo cityInfo = getCityInfoByName(name);
		if (cityInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "CityInfo不存在！");
		}
		delete(cityInfo);
	}

	@Override
	public CityInfo getCityInfoByName(String name) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { name }, new String[] { "name不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", CityInfo.class.getSimpleName(), ICityInfoDao.NAME, ICityInfoDao.NAME);
		Map<String, Object> params = new HashMap<>();
		params.put(ICityInfoDao.NAME, name);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByName(String name, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ICityInfoDao.NAME, name);
		return updateValue(CityInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteCityInfoByCityId(String cityId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { cityId }, new String[] { "cityId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		CityInfo cityInfo = getCityInfoByCityId(cityId);
		if (cityInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "CityInfo不存在！");
		}
		delete(cityInfo);
	}

	@Override
	public CityInfo getCityInfoByCityId(String cityId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { cityId }, new String[] { "cityId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", CityInfo.class.getSimpleName(), ICityInfoDao.CITY_ID, ICityInfoDao.CITY_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(ICityInfoDao.CITY_ID, cityId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByCityId(String cityId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ICityInfoDao.CITY_ID, cityId);
		return updateValue(CityInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
