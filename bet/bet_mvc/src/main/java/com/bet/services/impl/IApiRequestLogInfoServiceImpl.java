package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IApiRequestLogInfoDao;
import com.bet.orms.ApiRequestLogInfo;
import com.bet.services.IApiRequestLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

@Repository("apiRequestLogInfoService")
public class IApiRequestLogInfoServiceImpl implements IApiRequestLogInfoService
{
	@Autowired
	private IApiRequestLogInfoDao apiRequestLogInfoDao;

	@Override
	public String genId()
	{
		return apiRequestLogInfoDao.genId();
	}

	@Override
	public ApiRequestLogInfo addApiRequestLogInfo(ApiRequestLogInfo apiRequestLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (apiRequestLogInfo.getAddDateLong() < 1)
		{
			apiRequestLogInfo.setAddDateLong(current);
		}
		return apiRequestLogInfoDao.add(apiRequestLogInfo);
	}

	@Override
	public ApiRequestLogInfo updateApiRequestLogInfo(ApiRequestLogInfo apiRequestLogInfo) throws HibernateJsonResultException
	{
		return apiRequestLogInfoDao.update(apiRequestLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return apiRequestLogInfoDao.updateValue(ApiRequestLogInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteApiRequestLogInfo(ApiRequestLogInfo apiRequestLogInfo) throws HibernateJsonResultException
	{
		apiRequestLogInfoDao.delete(apiRequestLogInfo);
	}

	@Override
	public void deleteApiRequestLogInfoById(int id) throws HibernateJsonResultException
	{
		apiRequestLogInfoDao.deleteApiRequestLogInfoById(id);
	}

	@Override
	public ApiRequestLogInfo getApiRequestLogInfoById(int id) throws HibernateJsonResultException
	{
		return apiRequestLogInfoDao.getApiRequestLogInfoById(id);
	}

	@Override
	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IApiRequestLogInfoDao.ID, id);
		return apiRequestLogInfoDao.updateValue(ApiRequestLogInfo.class, valueMap, whereMap) > 0;
	}

	public List<ApiRequestLogInfo> getApiRequestLogInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return apiRequestLogInfoDao.getList(ApiRequestLogInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<ApiRequestLogInfo> getApiRequestLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return apiRequestLogInfoDao.getList(ApiRequestLogInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getApiRequestLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return apiRequestLogInfoDao.getListCount(ApiRequestLogInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
