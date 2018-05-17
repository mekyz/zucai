package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.ApiRequestLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IApiRequestLogInfoService
{
	public String genId();

	public ApiRequestLogInfo addApiRequestLogInfo(ApiRequestLogInfo apiRequestLogInfo) throws HibernateJsonResultException;

	public ApiRequestLogInfo updateApiRequestLogInfo(ApiRequestLogInfo apiRequestLogInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteApiRequestLogInfo(ApiRequestLogInfo apiRequestLogInfo) throws HibernateJsonResultException;

	public void deleteApiRequestLogInfoById(int id) throws HibernateJsonResultException;

	public ApiRequestLogInfo getApiRequestLogInfoById(int id) throws HibernateJsonResultException;

	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<ApiRequestLogInfo> getApiRequestLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getApiRequestLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
