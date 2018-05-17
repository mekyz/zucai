package com.bet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IPayeeInfoDao;
import com.bet.orms.PayeeInfo;
import com.bet.services.IPayeeInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.enums.SqlOrderType;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("payeeInfoService")
public class IPayeeInfoServiceImpl implements IPayeeInfoService
{
	@Autowired
	private IPayeeInfoDao payeeInfoDao;

	@Override
	public String genId()
	{
		return payeeInfoDao.genId();
	}

	@Override
	public PayeeInfo addPayeeInfo(PayeeInfo payeeInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (payeeInfo.getAddDateLong() < 1)
		{
			payeeInfo.setAddDateLong(current);
		}
		if (payeeInfo.getUpdateDateLong() < 1)
		{
			payeeInfo.setUpdateDateLong(current);
		}
		return payeeInfoDao.add(payeeInfo);
	}

	@Override
	public PayeeInfo updatePayeeInfo(PayeeInfo payeeInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (payeeInfo.getUpdateDateLong() < 1)
		{
			payeeInfo.setUpdateDateLong(current);
		}
		return payeeInfoDao.update(payeeInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return payeeInfoDao.updateValue(PayeeInfo.class, valueMap, valueMap);
	}

	@Override
	public void deletePayeeInfo(PayeeInfo payeeInfo) throws HibernateJsonResultException
	{
		payeeInfoDao.delete(payeeInfo);
	}

	@Override
	public void deletePayeeInfoByPayeeId(String payeeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { payeeId }, new String[] { "payeeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		payeeInfoDao.deletePayeeInfoByPayeeId(payeeId);
	}

	@Override
	public PayeeInfo getPayeeInfoByPayeeId(String payeeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { payeeId }, new String[] { "payeeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return payeeInfoDao.getPayeeInfoByPayeeId(payeeId);
	}

	@Override
	public boolean updateValueByPayeeId(String payeeId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IPayeeInfoDao.PAYEE_ID, payeeId);
		return payeeInfoDao.updateValue(PayeeInfo.class, valueMap, whereMap) > 0;
	}

	public List<PayeeInfo> getPayeeInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return payeeInfoDao.getList(PayeeInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<PayeeInfo> getPayeeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return payeeInfoDao.getList(PayeeInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getPayeeInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return payeeInfoDao.getListCount(PayeeInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public PayeeInfo getPayeeInfoByPayeeType(String payeeType) throws HibernateJsonResultException
	{
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		orderInfos.add(new TableOrderInfo(IPayeeInfoDao.SORT_INDEX, SqlOrderType.DESC.getType()));
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IPayeeInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
		String whereClause = String.format("where %s = :%s", IPayeeInfoDao.TYPE, IPayeeInfoDao.TYPE);
		Map<String, Object> params = new HashMap<>();
		params.put(IPayeeInfoDao.TYPE, payeeType);
		List<PayeeInfo> list = payeeInfoDao.getList(PayeeInfo.class, whereClause, params, 0, 1, orderInfos, searchInfos);
		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}
}
