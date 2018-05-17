package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IApiRequestLogInfoDao;
import com.bet.orms.ApiRequestLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("apiRequestLogInfoDao")
public class IApiRequestLogInfoDaoMySqlImpl extends IAbstractDaoImpl<ApiRequestLogInfo> implements IApiRequestLogInfoDao
{
	@Override
	public ReturnInfo checkParams(ApiRequestLogInfo apiRequestLogInfo)
	{
		if (apiRequestLogInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "ApiRequestLogInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { apiRequestLogInfo.getAddDateLong() + "" }, new String[] { "addDateLong不能为空！" });
		return returnInfo;
	}

	@Override
	public ApiRequestLogInfo add(ApiRequestLogInfo apiRequestLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(apiRequestLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(apiRequestLogInfo);
	}

	@Override
	public ApiRequestLogInfo update(ApiRequestLogInfo apiRequestLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(apiRequestLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(apiRequestLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(ApiRequestLogInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteApiRequestLogInfoById(int id) throws HibernateJsonResultException
	{
		ApiRequestLogInfo apiRequestLogInfo = getApiRequestLogInfoById(id);
		if (apiRequestLogInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "ApiRequestLogInfo不存在！");
		}
		delete(apiRequestLogInfo);
	}

	@Override
	public ApiRequestLogInfo getApiRequestLogInfoById(int id) throws HibernateJsonResultException
	{
		String hql = String.format("from %s where %s = :%s", ApiRequestLogInfo.class.getSimpleName(), IApiRequestLogInfoDao.ID, IApiRequestLogInfoDao.ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IApiRequestLogInfoDao.ID, id);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IApiRequestLogInfoDao.ID, id);
		return updateValue(ApiRequestLogInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
