package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IWithdrawInfoDao;
import com.bet.orms.WithdrawInfo;
import com.bet.services.IWithdrawInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("withdrawInfoService")
public class IWithdrawInfoServiceImpl implements IWithdrawInfoService
{
	@Autowired
	private IWithdrawInfoDao withdrawInfoDao;

	@Override
	public String genId()
	{
		return withdrawInfoDao.genId();
	}

	@Override
	public WithdrawInfo addWithdrawInfo(WithdrawInfo withdrawInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (withdrawInfo.getAddDateLong() < 1)
		{
			withdrawInfo.setAddDateLong(current);
		}
		if (withdrawInfo.getUpdateDateLong() < 1)
		{
			withdrawInfo.setUpdateDateLong(current);
		}
		return withdrawInfoDao.add(withdrawInfo);
	}

	@Override
	public WithdrawInfo updateWithdrawInfo(WithdrawInfo withdrawInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (withdrawInfo.getUpdateDateLong() < 1)
		{
			withdrawInfo.setUpdateDateLong(current);
		}
		return withdrawInfoDao.update(withdrawInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return withdrawInfoDao.updateValue(WithdrawInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteWithdrawInfo(WithdrawInfo withdrawInfo) throws HibernateJsonResultException
	{
		withdrawInfoDao.delete(withdrawInfo);
	}

	@Override
	public void deleteWithdrawInfoByWithdrawId(String withdrawId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { withdrawId }, new String[] { "withdrawId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		withdrawInfoDao.deleteWithdrawInfoByWithdrawId(withdrawId);
	}

	@Override
	public WithdrawInfo getWithdrawInfoByWithdrawId(String withdrawId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { withdrawId }, new String[] { "withdrawId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return withdrawInfoDao.getWithdrawInfoByWithdrawId(withdrawId);
	}

	@Override
	public boolean updateValueByWithdrawId(String withdrawId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IWithdrawInfoDao.WITHDRAW_ID, withdrawId);
		return withdrawInfoDao.updateValue(WithdrawInfo.class, valueMap, whereMap) > 0;
	}

	public List<WithdrawInfo> getWithdrawInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return withdrawInfoDao.getList(WithdrawInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getWithdrawInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return withdrawInfoDao.getListCount(WithdrawInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public List<WithdrawInfo> getWithdrawInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return withdrawInfoDao.getList(WithdrawInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<WithdrawInfo> getWithdrawInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException
	{
		String whereClause = withdrawInfoDao.buildHqlTimeWhere(IWithdrawInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return withdrawInfoDao.getList(WithdrawInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getWithdrawInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = withdrawInfoDao.buildHqlTimeWhere(IWithdrawInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return withdrawInfoDao.getListCount(WithdrawInfo.class, whereClause, params, searchInfos);
	}

	@Override
	public long getSum(String colName, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = withdrawInfoDao.buildSqlTimeWhere(IWithdrawInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return withdrawInfoDao.getSum(colName, whereClause, params, searchInfos, startDateLong, endDateLong);
	}

	@Override
	public boolean updateStatusValueByWithdrawId(String withdrawId, byte status, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IWithdrawInfoDao.WITHDRAW_ID, withdrawId);
		whereMap.put(IWithdrawInfoDao.STATUS, status);
		return withdrawInfoDao.updateValue(WithdrawInfo.class, valueMap, whereMap) > 0;
	}
}
