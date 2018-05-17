package com.bet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IPayConfigInfoDao;
import com.bet.enums.PayPlatform;
import com.bet.orms.PayConfigInfo;
import com.bet.services.IPayConfigInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.enums.SqlOrderType;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("payConfigInfoService")
public class IPayConfigInfoServiceImpl implements IPayConfigInfoService
{
	@Autowired
	private IPayConfigInfoDao payConfigInfoDao;

	@Override
	public String genId()
	{
		return payConfigInfoDao.genId();
	}

	@Override
	public PayConfigInfo addPayConfigInfo(PayConfigInfo payConfigInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (payConfigInfo.getAddDateLong() < 1)
		{
			payConfigInfo.setAddDateLong(current);
		}
		if (payConfigInfo.getUpdateDateLong() < 1)
		{
			payConfigInfo.setUpdateDateLong(current);
		}
		return payConfigInfoDao.add(payConfigInfo);
	}

	@Override
	public PayConfigInfo updatePayConfigInfo(PayConfigInfo payConfigInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (payConfigInfo.getUpdateDateLong() < 1)
		{
			payConfigInfo.setUpdateDateLong(current);
		}
		return payConfigInfoDao.update(payConfigInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return payConfigInfoDao.updateValue(PayConfigInfo.class, valueMap, valueMap);
	}

	@Override
	public void deletePayConfigInfo(PayConfigInfo payConfigInfo) throws HibernateJsonResultException
	{
		payConfigInfoDao.delete(payConfigInfo);
	}

	@Override
	public void deletePayConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		payConfigInfoDao.deletePayConfigInfoByConfigId(configId);
	}

	@Override
	public PayConfigInfo getPayConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return payConfigInfoDao.getPayConfigInfoByConfigId(configId);
	}

	@Override
	public boolean updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IPayConfigInfoDao.CONFIG_ID, configId);
		return payConfigInfoDao.updateValue(PayConfigInfo.class, valueMap, whereMap) > 0;
	}

	public List<PayConfigInfo> getPayConfigInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return payConfigInfoDao.getList(PayConfigInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<PayConfigInfo> getPayConfigInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return payConfigInfoDao.getList(PayConfigInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getPayConfigInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return payConfigInfoDao.getListCount(PayConfigInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public PayConfigInfo getMinlePayConfigInfo() throws HibernateJsonResultException
	{
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		orderInfos.add(new TableOrderInfo(IPayConfigInfoDao.SORT_INDEX, SqlOrderType.DESC.getType()));
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IPayConfigInfoDao.PLATFORM, PayPlatform.MINLE_PAY.getType(), true, false));
		searchInfos.add(new TableSearchInfo(IPayConfigInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
		List<PayConfigInfo> list = payConfigInfoDao.getList(PayConfigInfo.class, null, null, 0, 1, orderInfos, searchInfos);
		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}
}
