package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserBalanceLogInfoDao;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.services.IUserBalanceLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userBalanceLogInfoService")
public class IUserBalanceLogInfoServiceImpl implements IUserBalanceLogInfoService
{
	@Autowired
	private IUserBalanceLogInfoDao userBalanceLogInfoDao;

	@Override
	public String genId()
	{
		return userBalanceLogInfoDao.genId();
	}

	@Override
	public UserBalanceLogInfo addUserBalanceLogInfo(UserBalanceLogInfo userBalanceLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userBalanceLogInfo.getAddDateLong() < 1)
		{
			userBalanceLogInfo.setAddDateLong(current);
		}
		return userBalanceLogInfoDao.add(userBalanceLogInfo);
	}

	@Override
	public UserBalanceLogInfo updateUserBalanceLogInfo(UserBalanceLogInfo userBalanceLogInfo) throws HibernateJsonResultException
	{
		return userBalanceLogInfoDao.update(userBalanceLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return userBalanceLogInfoDao.updateValue(UserBalanceLogInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteUserBalanceLogInfo(UserBalanceLogInfo userBalanceLogInfo) throws HibernateJsonResultException
	{
		userBalanceLogInfoDao.delete(userBalanceLogInfo);
	}

	@Override
	public void deleteUserBalanceLogInfoById(int id) throws HibernateJsonResultException
	{
		userBalanceLogInfoDao.deleteUserBalanceLogInfoById(id);
	}

	@Override
	public UserBalanceLogInfo getUserBalanceLogInfoById(int id) throws HibernateJsonResultException
	{
		return userBalanceLogInfoDao.getUserBalanceLogInfoById(id);
	}

	@Override
	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserBalanceLogInfoDao.ID, id);
		return userBalanceLogInfoDao.updateValue(UserBalanceLogInfo.class, valueMap, whereMap) > 0;
	}

	public List<UserBalanceLogInfo> getUserBalanceLogInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return userBalanceLogInfoDao.getList(UserBalanceLogInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserBalanceLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userBalanceLogInfoDao.getListCount(UserBalanceLogInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public List<UserBalanceLogInfo> getUserBalanceLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userBalanceLogInfoDao.getList(UserBalanceLogInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<UserBalanceLogInfo> getUserBalanceLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException
	{
		String whereClause = userBalanceLogInfoDao.buildHqlTimeWhere(IUserBalanceLogInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userBalanceLogInfoDao.getList(UserBalanceLogInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserBalanceLogInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = userBalanceLogInfoDao.buildHqlTimeWhere(IUserBalanceLogInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userBalanceLogInfoDao.getListCount(UserBalanceLogInfo.class, whereClause, params, searchInfos);
	}

	@Override
	public long getUserLogTotalMoney(byte moneyUnit, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = userBalanceLogInfoDao.buildSqlTimeWhere(IUserBalanceLogInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (%s = :%s)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "), StringTools.getTableColumnName(IUserBalanceLogInfoDao.MONEY_UNIT),
			IUserBalanceLogInfoDao.MONEY_UNIT);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserBalanceLogInfoDao.MONEY_UNIT, moneyUnit);
		return userBalanceLogInfoDao.getUserLogTotalMoney(whereClause, params, searchInfos);
	}
}
