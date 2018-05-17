package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.MonthStatisticsLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IMonthStatisticsLogInfoService
{
	public String genId();

	public MonthStatisticsLogInfo addMonthStatisticsLogInfo(MonthStatisticsLogInfo monthStatisticsLogInfo) throws HibernateJsonResultException;

	public MonthStatisticsLogInfo updateMonthStatisticsLogInfo(MonthStatisticsLogInfo monthStatisticsLogInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteMonthStatisticsLogInfo(MonthStatisticsLogInfo monthStatisticsLogInfo) throws HibernateJsonResultException;

	public void deleteMonthStatisticsLogInfoByDayId(String dayId) throws HibernateJsonResultException;

	public MonthStatisticsLogInfo getMonthStatisticsLogInfoByDayId(String dayId) throws HibernateJsonResultException;

	public boolean updateValueByDayId(String dayId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<MonthStatisticsLogInfo> getMonthStatisticsLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getMonthStatisticsLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
