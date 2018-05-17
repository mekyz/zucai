package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IMinlePayNotifyLogInfoDao;
import com.bet.orms.MinlePayNotifyLogInfo;
import com.bet.services.IMinlePayNotifyLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("minlePayNotifyLogInfoService")
public class IMinlePayNotifyLogInfoServiceImpl implements IMinlePayNotifyLogInfoService
{
	@Autowired
	private IMinlePayNotifyLogInfoDao minlePayNotifyLogInfoDao;

	@Override
	public String genId()
	{
		return minlePayNotifyLogInfoDao.genId();
	}

	@Override
	public MinlePayNotifyLogInfo addMinlePayNotifyLogInfo(MinlePayNotifyLogInfo minlePayNotifyLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (minlePayNotifyLogInfo.getAddDateLong() < 1)
		{
			minlePayNotifyLogInfo.setAddDateLong(current);
		}
		if (minlePayNotifyLogInfo.getUpdateDateLong() < 1)
		{
			minlePayNotifyLogInfo.setUpdateDateLong(current);
		}
		return minlePayNotifyLogInfoDao.add(minlePayNotifyLogInfo);
	}

	@Override
	public MinlePayNotifyLogInfo updateMinlePayNotifyLogInfo(MinlePayNotifyLogInfo minlePayNotifyLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (minlePayNotifyLogInfo.getUpdateDateLong() < 1)
		{
			minlePayNotifyLogInfo.setUpdateDateLong(current);
		}
		return minlePayNotifyLogInfoDao.update(minlePayNotifyLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return minlePayNotifyLogInfoDao.updateValue(MinlePayNotifyLogInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteMinlePayNotifyLogInfo(MinlePayNotifyLogInfo minlePayNotifyLogInfo) throws HibernateJsonResultException
	{
		minlePayNotifyLogInfoDao.delete(minlePayNotifyLogInfo);
	}

	@Override
	public void deleteMinlePayNotifyLogInfoByNotifyId(String notifyId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { notifyId }, new String[] { "notifyId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		minlePayNotifyLogInfoDao.deleteMinlePayNotifyLogInfoByNotifyId(notifyId);
	}

	@Override
	public MinlePayNotifyLogInfo getMinlePayNotifyLogInfoByNotifyId(String notifyId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { notifyId }, new String[] { "notifyId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return minlePayNotifyLogInfoDao.getMinlePayNotifyLogInfoByNotifyId(notifyId);
	}

	@Override
	public boolean updateValueByNotifyId(String notifyId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMinlePayNotifyLogInfoDao.NOTIFY_ID, notifyId);
		return minlePayNotifyLogInfoDao.updateValue(MinlePayNotifyLogInfo.class, valueMap, whereMap) > 0;
	}

	public List<MinlePayNotifyLogInfo> getMinlePayNotifyLogInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos,
		List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return minlePayNotifyLogInfoDao.getList(MinlePayNotifyLogInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<MinlePayNotifyLogInfo> getMinlePayNotifyLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return minlePayNotifyLogInfoDao.getList(MinlePayNotifyLogInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getMinlePayNotifyLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return minlePayNotifyLogInfoDao.getListCount(MinlePayNotifyLogInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
