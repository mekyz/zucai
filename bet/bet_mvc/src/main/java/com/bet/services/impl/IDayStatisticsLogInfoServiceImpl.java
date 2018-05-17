package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IDayStatisticsLogInfoDao;
import com.bet.orms.DayStatisticsLogInfo;
import com.bet.services.IDayStatisticsLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("dayStatisticsLogInfoService")
public class IDayStatisticsLogInfoServiceImpl implements IDayStatisticsLogInfoService
{
	@Autowired
	private IDayStatisticsLogInfoDao dayStatisticsLogInfoDao;

	@Override
	public String genId()
	{
		return dayStatisticsLogInfoDao.genId();
	}

	@Override
	public DayStatisticsLogInfo addDayStatisticsLogInfo(DayStatisticsLogInfo dayStatisticsLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (dayStatisticsLogInfo.getAddDateLong() < 1)
		{
			dayStatisticsLogInfo.setAddDateLong(current);
		}
		if (dayStatisticsLogInfo.getUpdateDateLong() < 1)
		{
			dayStatisticsLogInfo.setUpdateDateLong(current);
		}
		return dayStatisticsLogInfoDao.add(dayStatisticsLogInfo);
	}

	@Override
	public DayStatisticsLogInfo updateDayStatisticsLogInfo(DayStatisticsLogInfo dayStatisticsLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (dayStatisticsLogInfo.getUpdateDateLong() < 1)
		{
			dayStatisticsLogInfo.setUpdateDateLong(current);
		}
		return dayStatisticsLogInfoDao.update(dayStatisticsLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return dayStatisticsLogInfoDao.updateValue(DayStatisticsLogInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteDayStatisticsLogInfo(DayStatisticsLogInfo dayStatisticsLogInfo) throws HibernateJsonResultException
	{
		dayStatisticsLogInfoDao.delete(dayStatisticsLogInfo);
	}

	@Override
	public void deleteDayStatisticsLogInfoByDayId(String dayId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { dayId }, new String[] { "dayId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		dayStatisticsLogInfoDao.deleteDayStatisticsLogInfoByDayId(dayId);
	}

	@Override
	public DayStatisticsLogInfo getDayStatisticsLogInfoByDayId(String dayId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { dayId }, new String[] { "dayId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return dayStatisticsLogInfoDao.getDayStatisticsLogInfoByDayId(dayId);
	}

	@Override
	public boolean updateValueByDayId(String dayId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IDayStatisticsLogInfoDao.DAY_ID, dayId);
		return dayStatisticsLogInfoDao.updateValue(DayStatisticsLogInfo.class, valueMap, whereMap) > 0;
	}

	public List<DayStatisticsLogInfo> getDayStatisticsLogInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos,
		List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return dayStatisticsLogInfoDao.getList(DayStatisticsLogInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<DayStatisticsLogInfo> getDayStatisticsLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return dayStatisticsLogInfoDao.getList(DayStatisticsLogInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getDayStatisticsLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return dayStatisticsLogInfoDao.getListCount(DayStatisticsLogInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
