package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.MinlePayNotifyLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IMinlePayNotifyLogInfoService
{
	public String genId();

	public MinlePayNotifyLogInfo addMinlePayNotifyLogInfo(MinlePayNotifyLogInfo minlePayNotifyLogInfo) throws HibernateJsonResultException;

	public MinlePayNotifyLogInfo updateMinlePayNotifyLogInfo(MinlePayNotifyLogInfo minlePayNotifyLogInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteMinlePayNotifyLogInfo(MinlePayNotifyLogInfo minlePayNotifyLogInfo) throws HibernateJsonResultException;

	public void deleteMinlePayNotifyLogInfoByNotifyId(String notifyId) throws HibernateJsonResultException;

	public MinlePayNotifyLogInfo getMinlePayNotifyLogInfoByNotifyId(String notifyId) throws HibernateJsonResultException;

	public boolean updateValueByNotifyId(String notifyId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<MinlePayNotifyLogInfo> getMinlePayNotifyLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getMinlePayNotifyLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
