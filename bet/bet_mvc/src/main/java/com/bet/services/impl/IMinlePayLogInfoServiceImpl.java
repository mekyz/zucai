package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IMinlePayLogInfoDao;
import com.bet.orms.MinlePayLogInfo;
import com.bet.services.IMinlePayLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("minlePayLogInfoService")
public class IMinlePayLogInfoServiceImpl implements IMinlePayLogInfoService
{
	@Autowired
	private IMinlePayLogInfoDao minlePayLogInfoDao;

	@Override
	public String genId()
	{
		return minlePayLogInfoDao.genId();
	}

	@Override
	public MinlePayLogInfo addMinlePayLogInfo(MinlePayLogInfo minlePayLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (minlePayLogInfo.getAddDateLong() < 1)
		{
			minlePayLogInfo.setAddDateLong(current);
		}
		if (minlePayLogInfo.getUpdateDateLong() < 1)
		{
			minlePayLogInfo.setUpdateDateLong(current);
		}
		return minlePayLogInfoDao.add(minlePayLogInfo);
	}

	@Override
	public MinlePayLogInfo updateMinlePayLogInfo(MinlePayLogInfo minlePayLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (minlePayLogInfo.getUpdateDateLong() < 1)
		{
			minlePayLogInfo.setUpdateDateLong(current);
		}
		return minlePayLogInfoDao.update(minlePayLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return minlePayLogInfoDao.updateValue(MinlePayLogInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteMinlePayLogInfo(MinlePayLogInfo minlePayLogInfo) throws HibernateJsonResultException
	{
		minlePayLogInfoDao.delete(minlePayLogInfo);
	}

	@Override
	public void deleteMinlePayLogInfoByLogId(String logId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { logId }, new String[] { "logId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		minlePayLogInfoDao.deleteMinlePayLogInfoByLogId(logId);
	}

	@Override
	public MinlePayLogInfo getMinlePayLogInfoByLogId(String logId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { logId }, new String[] { "logId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return minlePayLogInfoDao.getMinlePayLogInfoByLogId(logId);
	}

	@Override
	public boolean updateValueByLogId(String logId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMinlePayLogInfoDao.LOG_ID, logId);
		return minlePayLogInfoDao.updateValue(MinlePayLogInfo.class, valueMap, whereMap) > 0;
	}

	public List<MinlePayLogInfo> getMinlePayLogInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return minlePayLogInfoDao.getList(MinlePayLogInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<MinlePayLogInfo> getMinlePayLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return minlePayLogInfoDao.getList(MinlePayLogInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getMinlePayLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return minlePayLogInfoDao.getListCount(MinlePayLogInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
