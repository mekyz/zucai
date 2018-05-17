package com.bet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.ISmsCodeConfigInfoDao;
import com.bet.orms.SmsCodeConfigInfo;
import com.bet.services.ISmsCodeConfigInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.enums.SqlOrderType;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("smsCodeConfigInfoService")
public class ISmsCodeConfigInfoServiceImpl implements ISmsCodeConfigInfoService
{
	@Autowired
	private ISmsCodeConfigInfoDao smsCodeConfigInfoDao;

	@Override
	public String genId()
	{
		return smsCodeConfigInfoDao.genId();
	}

	@Override
	public SmsCodeConfigInfo addSmsCodeConfigInfo(SmsCodeConfigInfo smsCodeConfigInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (smsCodeConfigInfo.getAddDateLong() < 1)
		{
			smsCodeConfigInfo.setAddDateLong(current);
		}
		if (smsCodeConfigInfo.getUpdateDateLong() < 1)
		{
			smsCodeConfigInfo.setUpdateDateLong(current);
		}
		return smsCodeConfigInfoDao.add(smsCodeConfigInfo);
	}

	@Override
	public SmsCodeConfigInfo updateSmsCodeConfigInfo(SmsCodeConfigInfo smsCodeConfigInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (smsCodeConfigInfo.getUpdateDateLong() < 1)
		{
			smsCodeConfigInfo.setUpdateDateLong(current);
		}
		return smsCodeConfigInfoDao.update(smsCodeConfigInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return smsCodeConfigInfoDao.updateValue(SmsCodeConfigInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteSmsCodeConfigInfo(SmsCodeConfigInfo smsCodeConfigInfo) throws HibernateJsonResultException
	{
		smsCodeConfigInfoDao.delete(smsCodeConfigInfo);
	}

	@Override
	public void deleteSmsCodeConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		smsCodeConfigInfoDao.deleteSmsCodeConfigInfoByConfigId(configId);
	}

	@Override
	public SmsCodeConfigInfo getSmsCodeConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return smsCodeConfigInfoDao.getSmsCodeConfigInfoByConfigId(configId);
	}

	@Override
	public boolean updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ISmsCodeConfigInfoDao.CONFIG_ID, configId);
		return smsCodeConfigInfoDao.updateValue(SmsCodeConfigInfo.class, valueMap, whereMap) > 0;
	}

	public List<SmsCodeConfigInfo> getSmsCodeConfigInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return smsCodeConfigInfoDao.getList(SmsCodeConfigInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<SmsCodeConfigInfo> getSmsCodeConfigInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return smsCodeConfigInfoDao.getList(SmsCodeConfigInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getSmsCodeConfigInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return smsCodeConfigInfoDao.getListCount(SmsCodeConfigInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public SmsCodeConfigInfo getSmsCodeConfigInfoByType(String type) throws HibernateJsonResultException
	{
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		orderInfos.add(new TableOrderInfo(ISmsCodeConfigInfoDao.SORT_INDEX, SqlOrderType.DESC.getType()));
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(ISmsCodeConfigInfoDao.SMS_TYPE, type, true, false));
		searchInfos.add(new TableSearchInfo(ISmsCodeConfigInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
		List<SmsCodeConfigInfo> list = smsCodeConfigInfoDao.getList(SmsCodeConfigInfo.class, null, null, 0, 1, orderInfos, searchInfos);
		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}
}
