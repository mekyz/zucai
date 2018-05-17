package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.DayUserStatisticsLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IDayUserStatisticsLogInfoService
{
	public String genId();

	public DayUserStatisticsLogInfo addDayUserStatisticsLogInfo(DayUserStatisticsLogInfo dayUserStatisticsLogInfo) throws HibernateJsonResultException;

	public DayUserStatisticsLogInfo updateDayUserStatisticsLogInfo(DayUserStatisticsLogInfo dayUserStatisticsLogInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteDayUserStatisticsLogInfo(DayUserStatisticsLogInfo dayUserStatisticsLogInfo) throws HibernateJsonResultException;

	public void deleteDayUserStatisticsLogInfoByLogId(String logId) throws HibernateJsonResultException;

	public DayUserStatisticsLogInfo getDayUserStatisticsLogInfoByLogId(String logId) throws HibernateJsonResultException;

	public boolean updateValueByLogId(String logId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<DayUserStatisticsLogInfo> getDayUserStatisticsLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getDayUserStatisticsLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
