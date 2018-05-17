package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IMonthStatisticsLogInfoDao;
import com.bet.orms.MonthStatisticsLogInfo;
import com.bet.services.IMonthStatisticsLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("monthStatisticsLogInfoService")
public class IMonthStatisticsLogInfoServiceImpl implements IMonthStatisticsLogInfoService
{
	@Autowired
	private IMonthStatisticsLogInfoDao monthStatisticsLogInfoDao;

	@Override
	public String genId()
	{
		return monthStatisticsLogInfoDao.genId();
	}

	@Override
	public MonthStatisticsLogInfo addMonthStatisticsLogInfo(MonthStatisticsLogInfo monthStatisticsLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (monthStatisticsLogInfo.getAddDateLong() < 1)
		{
			monthStatisticsLogInfo.setAddDateLong(current);
		}
		if (monthStatisticsLogInfo.getUpdateDateLong() < 1)
		{
			monthStatisticsLogInfo.setUpdateDateLong(current);
		}
		return monthStatisticsLogInfoDao.add(monthStatisticsLogInfo);
	}

	@Override
	public MonthStatisticsLogInfo updateMonthStatisticsLogInfo(MonthStatisticsLogInfo monthStatisticsLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (monthStatisticsLogInfo.getUpdateDateLong() < 1)
		{
			monthStatisticsLogInfo.setUpdateDateLong(current);
		}
		return monthStatisticsLogInfoDao.update(monthStatisticsLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return monthStatisticsLogInfoDao.updateValue(MonthStatisticsLogInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteMonthStatisticsLogInfo(MonthStatisticsLogInfo monthStatisticsLogInfo) throws HibernateJsonResultException
	{
		monthStatisticsLogInfoDao.delete(monthStatisticsLogInfo);
	}

	@Override
	public void deleteMonthStatisticsLogInfoByDayId(String dayId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { dayId }, new String[] { "dayId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		monthStatisticsLogInfoDao.deleteMonthStatisticsLogInfoByDayId(dayId);
	}

	@Override
	public MonthStatisticsLogInfo getMonthStatisticsLogInfoByDayId(String dayId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { dayId }, new String[] { "dayId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return monthStatisticsLogInfoDao.getMonthStatisticsLogInfoByDayId(dayId);
	}

	@Override
	public boolean updateValueByDayId(String dayId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMonthStatisticsLogInfoDao.DAY_ID, dayId);
		return monthStatisticsLogInfoDao.updateValue(MonthStatisticsLogInfo.class, valueMap, whereMap) > 0;
	}

	public List<MonthStatisticsLogInfo> getMonthStatisticsLogInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos,
		List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return monthStatisticsLogInfoDao.getList(MonthStatisticsLogInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<MonthStatisticsLogInfo> getMonthStatisticsLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return monthStatisticsLogInfoDao.getList(MonthStatisticsLogInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getMonthStatisticsLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return monthStatisticsLogInfoDao.getListCount(MonthStatisticsLogInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
