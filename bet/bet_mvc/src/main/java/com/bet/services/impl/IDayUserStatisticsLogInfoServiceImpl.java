package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IDayUserStatisticsLogInfoDao;
import com.bet.orms.DayUserStatisticsLogInfo;
import com.bet.services.IDayUserStatisticsLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("dayUserStatisticsLogInfoService")
public class IDayUserStatisticsLogInfoServiceImpl implements IDayUserStatisticsLogInfoService
{
	@Autowired
	private IDayUserStatisticsLogInfoDao dayUserStatisticsLogInfoDao;

	@Override
	public String genId()
	{
		return dayUserStatisticsLogInfoDao.genId();
	}

	@Override
	public DayUserStatisticsLogInfo addDayUserStatisticsLogInfo(DayUserStatisticsLogInfo dayUserStatisticsLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (dayUserStatisticsLogInfo.getAddDateLong() < 1)
		{
			dayUserStatisticsLogInfo.setAddDateLong(current);
		}
		if (dayUserStatisticsLogInfo.getUpdateDateLong() < 1)
		{
			dayUserStatisticsLogInfo.setUpdateDateLong(current);
		}
		return dayUserStatisticsLogInfoDao.add(dayUserStatisticsLogInfo);
	}

	@Override
	public DayUserStatisticsLogInfo updateDayUserStatisticsLogInfo(DayUserStatisticsLogInfo dayUserStatisticsLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (dayUserStatisticsLogInfo.getUpdateDateLong() < 1)
		{
			dayUserStatisticsLogInfo.setUpdateDateLong(current);
		}
		return dayUserStatisticsLogInfoDao.update(dayUserStatisticsLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return dayUserStatisticsLogInfoDao.updateValue(DayUserStatisticsLogInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteDayUserStatisticsLogInfo(DayUserStatisticsLogInfo dayUserStatisticsLogInfo) throws HibernateJsonResultException
	{
		dayUserStatisticsLogInfoDao.delete(dayUserStatisticsLogInfo);
	}

	@Override
	public void deleteDayUserStatisticsLogInfoByLogId(String logId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { logId }, new String[] { "logId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		dayUserStatisticsLogInfoDao.deleteDayUserStatisticsLogInfoByLogId(logId);
	}

	@Override
	public DayUserStatisticsLogInfo getDayUserStatisticsLogInfoByLogId(String logId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { logId }, new String[] { "logId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return dayUserStatisticsLogInfoDao.getDayUserStatisticsLogInfoByLogId(logId);
	}

	@Override
	public boolean updateValueByLogId(String logId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IDayUserStatisticsLogInfoDao.LOG_ID, logId);
		return dayUserStatisticsLogInfoDao.updateValue(DayUserStatisticsLogInfo.class, valueMap, whereMap) > 0;
	}

	public List<DayUserStatisticsLogInfo> getDayUserStatisticsLogInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos,
		List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return dayUserStatisticsLogInfoDao.getList(DayUserStatisticsLogInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<DayUserStatisticsLogInfo> getDayUserStatisticsLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return dayUserStatisticsLogInfoDao.getList(DayUserStatisticsLogInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getDayUserStatisticsLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return dayUserStatisticsLogInfoDao.getListCount(DayUserStatisticsLogInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
