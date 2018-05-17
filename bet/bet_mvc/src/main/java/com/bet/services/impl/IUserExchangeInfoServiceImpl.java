package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserExchangeInfoDao;
import com.bet.orms.UserExchangeInfo;
import com.bet.services.IUserExchangeInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userExchangeInfoService")
public class IUserExchangeInfoServiceImpl implements IUserExchangeInfoService
{
	@Autowired
	private IUserExchangeInfoDao userExchangeInfoDao;

	@Override
	public String genId()
	{
		return userExchangeInfoDao.genId();
	}

	@Override
	public UserExchangeInfo addUserExchangeInfo(UserExchangeInfo userExchangeInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userExchangeInfo.getAddDateLong() < 1)
		{
			userExchangeInfo.setAddDateLong(current);
		}
		if (userExchangeInfo.getUpdateDateLong() < 1)
		{
			userExchangeInfo.setUpdateDateLong(current);
		}
		return userExchangeInfoDao.add(userExchangeInfo);
	}

	@Override
	public UserExchangeInfo updateUserExchangeInfo(UserExchangeInfo userExchangeInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userExchangeInfo.getUpdateDateLong() < 1)
		{
			userExchangeInfo.setUpdateDateLong(current);
		}
		return userExchangeInfoDao.update(userExchangeInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return userExchangeInfoDao.updateValue(UserExchangeInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteUserExchangeInfo(UserExchangeInfo userExchangeInfo) throws HibernateJsonResultException
	{
		userExchangeInfoDao.delete(userExchangeInfo);
	}

	@Override
	public void deleteUserExchangeInfoByExchangeId(String exchangeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { exchangeId }, new String[] { "exchangeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		userExchangeInfoDao.deleteUserExchangeInfoByExchangeId(exchangeId);
	}

	@Override
	public UserExchangeInfo getUserExchangeInfoByExchangeId(String exchangeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { exchangeId }, new String[] { "exchangeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return userExchangeInfoDao.getUserExchangeInfoByExchangeId(exchangeId);
	}

	@Override
	public boolean updateValueByExchangeId(String exchangeId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserExchangeInfoDao.EXCHANGE_ID, exchangeId);
		return userExchangeInfoDao.updateValue(UserExchangeInfo.class, valueMap, whereMap) > 0;
	}

	public List<UserExchangeInfo> getUserExchangeInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return userExchangeInfoDao.getList(UserExchangeInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserExchangeInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userExchangeInfoDao.getListCount(UserExchangeInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public List<UserExchangeInfo> getUserExchangeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userExchangeInfoDao.getList(UserExchangeInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<UserExchangeInfo> getUserExchangeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException
	{
		String whereClause = userExchangeInfoDao.buildHqlTimeWhere(IUserExchangeInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userExchangeInfoDao.getList(UserExchangeInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserExchangeInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = userExchangeInfoDao.buildHqlTimeWhere(IUserExchangeInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userExchangeInfoDao.getListCount(UserExchangeInfo.class, whereClause, params, searchInfos);
	}
}
