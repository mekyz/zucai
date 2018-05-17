package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IProvinceInfoDao;
import com.bet.orms.ProvinceInfo;
import com.bet.services.IProvinceInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("provinceInfoService")
public class IProvinceInfoServiceImpl implements IProvinceInfoService
{
	@Autowired
	private IProvinceInfoDao provinceInfoDao;

	@Override
	public String genId()
	{
		return provinceInfoDao.genId();
	}

	@Override
	public ProvinceInfo addProvinceInfo(ProvinceInfo provinceInfo) throws HibernateJsonResultException
	{
		return provinceInfoDao.add(provinceInfo);
	}

	@Override
	public ProvinceInfo updateProvinceInfo(ProvinceInfo provinceInfo) throws HibernateJsonResultException
	{
		return provinceInfoDao.update(provinceInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return provinceInfoDao.updateValue(ProvinceInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteProvinceInfo(ProvinceInfo provinceInfo) throws HibernateJsonResultException
	{
		provinceInfoDao.delete(provinceInfo);
	}

	@Override
	public void deleteProvinceInfoByName(String name) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { name }, new String[] { "name不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		provinceInfoDao.deleteProvinceInfoByName(name);
	}

	@Override
	public ProvinceInfo getProvinceInfoByName(String name) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { name }, new String[] { "name不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return provinceInfoDao.getProvinceInfoByName(name);
	}

	@Override
	public boolean updateValueByName(String name, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IProvinceInfoDao.NAME, name);
		return provinceInfoDao.updateValue(ProvinceInfo.class, valueMap, whereMap) > 0;
	}

	@Override
	public void deleteProvinceInfoByProvinceId(String provinceId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { provinceId }, new String[] { "provinceId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		provinceInfoDao.deleteProvinceInfoByProvinceId(provinceId);
	}

	@Override
	public ProvinceInfo getProvinceInfoByProvinceId(String provinceId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { provinceId }, new String[] { "provinceId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return provinceInfoDao.getProvinceInfoByProvinceId(provinceId);
	}

	@Override
	public boolean updateValueByProvinceId(String provinceId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IProvinceInfoDao.PROVINCE_ID, provinceId);
		return provinceInfoDao.updateValue(ProvinceInfo.class, valueMap, whereMap) > 0;
	}

	public List<ProvinceInfo> getProvinceInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return provinceInfoDao.getList(ProvinceInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<ProvinceInfo> getProvinceInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return provinceInfoDao.getList(ProvinceInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getProvinceInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return provinceInfoDao.getListCount(ProvinceInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
