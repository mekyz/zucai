package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.MinlePayLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IMinlePayLogInfoService
{
	public String genId();

	public MinlePayLogInfo addMinlePayLogInfo(MinlePayLogInfo minlePayLogInfo) throws HibernateJsonResultException;

	public MinlePayLogInfo updateMinlePayLogInfo(MinlePayLogInfo minlePayLogInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteMinlePayLogInfo(MinlePayLogInfo minlePayLogInfo) throws HibernateJsonResultException;

	public void deleteMinlePayLogInfoByLogId(String logId) throws HibernateJsonResultException;

	public MinlePayLogInfo getMinlePayLogInfoByLogId(String logId) throws HibernateJsonResultException;

	public boolean updateValueByLogId(String logId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<MinlePayLogInfo> getMinlePayLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getMinlePayLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
