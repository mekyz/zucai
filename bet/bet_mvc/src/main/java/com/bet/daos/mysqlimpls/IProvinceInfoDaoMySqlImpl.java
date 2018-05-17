package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IProvinceInfoDao;
import com.bet.orms.ProvinceInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("provinceInfoDao")
public class IProvinceInfoDaoMySqlImpl extends IAbstractDaoImpl<ProvinceInfo> implements IProvinceInfoDao
{
	@Override
	public ReturnInfo checkParams(ProvinceInfo provinceInfo)
	{
		if (provinceInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "ProvinceInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { provinceInfo.getName(), provinceInfo.getProvinceId() }, new String[] { "name不能为空！", "provinceId不能为空！" });
		return returnInfo;
	}

	@Override
	public ProvinceInfo add(ProvinceInfo provinceInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(provinceInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(provinceInfo);
	}

	@Override
	public ProvinceInfo update(ProvinceInfo provinceInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(provinceInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(provinceInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(ProvinceInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteProvinceInfoByName(String name) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { name }, new String[] { "name不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		ProvinceInfo provinceInfo = getProvinceInfoByName(name);
		if (provinceInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "ProvinceInfo不存在！");
		}
		delete(provinceInfo);
	}

	@Override
	public ProvinceInfo getProvinceInfoByName(String name) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { name }, new String[] { "name不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", ProvinceInfo.class.getSimpleName(), IProvinceInfoDao.NAME, IProvinceInfoDao.NAME);
		Map<String, Object> params = new HashMap<>();
		params.put(IProvinceInfoDao.NAME, name);
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
		whereMap.put(IProvinceInfoDao.NAME, name);
		return updateValue(ProvinceInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteProvinceInfoByProvinceId(String provinceId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { provinceId }, new String[] { "provinceId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		ProvinceInfo provinceInfo = getProvinceInfoByProvinceId(provinceId);
		if (provinceInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "ProvinceInfo不存在！");
		}
		delete(provinceInfo);
	}

	@Override
	public ProvinceInfo getProvinceInfoByProvinceId(String provinceId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { provinceId }, new String[] { "provinceId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", ProvinceInfo.class.getSimpleName(), IProvinceInfoDao.PROVINCE_ID, IProvinceInfoDao.PROVINCE_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IProvinceInfoDao.PROVINCE_ID, provinceId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByProvinceId(String provinceId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IProvinceInfoDao.PROVINCE_ID, provinceId);
		return updateValue(ProvinceInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
