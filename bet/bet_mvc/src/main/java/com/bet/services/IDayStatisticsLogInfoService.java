package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.DayStatisticsLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IDayStatisticsLogInfoService
{
	public String genId();

	public DayStatisticsLogInfo addDayStatisticsLogInfo(DayStatisticsLogInfo dayStatisticsLogInfo) throws HibernateJsonResultException;

	public DayStatisticsLogInfo updateDayStatisticsLogInfo(DayStatisticsLogInfo dayStatisticsLogInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteDayStatisticsLogInfo(DayStatisticsLogInfo dayStatisticsLogInfo) throws HibernateJsonResultException;

	public void deleteDayStatisticsLogInfoByDayId(String dayId) throws HibernateJsonResultException;

	public DayStatisticsLogInfo getDayStatisticsLogInfoByDayId(String dayId) throws HibernateJsonResultException;

	public boolean updateValueByDayId(String dayId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<DayStatisticsLogInfo> getDayStatisticsLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getDayStatisticsLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
