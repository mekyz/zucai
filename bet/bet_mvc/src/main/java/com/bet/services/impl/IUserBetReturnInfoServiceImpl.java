package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserBetReturnInfoDao;
import com.bet.orms.UserBetReturnInfo;
import com.bet.services.IUserBetReturnInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userBetReturnInfoService")
public class IUserBetReturnInfoServiceImpl implements IUserBetReturnInfoService
{
	@Autowired
	private IUserBetReturnInfoDao userBetReturnInfoDao;

	@Override
	public String genId()
	{
		return userBetReturnInfoDao.genId();
	}

	@Override
	public UserBetReturnInfo addUserBetReturnInfo(UserBetReturnInfo userBetReturnInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userBetReturnInfo.getAddDateLong() < 1)
		{
			userBetReturnInfo.setAddDateLong(current);
		}
		if (userBetReturnInfo.getUpdateDateLong() < 1)
		{
			userBetReturnInfo.setUpdateDateLong(current);
		}
		return userBetReturnInfoDao.add(userBetReturnInfo);
	}

	@Override
	public UserBetReturnInfo updateUserBetReturnInfo(UserBetReturnInfo userBetReturnInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userBetReturnInfo.getUpdateDateLong() < 1)
		{
			userBetReturnInfo.setUpdateDateLong(current);
		}
		return userBetReturnInfoDao.update(userBetReturnInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return userBetReturnInfoDao.updateValue(UserBetReturnInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteUserBetReturnInfo(UserBetReturnInfo userBetReturnInfo) throws HibernateJsonResultException
	{
		userBetReturnInfoDao.delete(userBetReturnInfo);
	}

	@Override
	public void deleteUserBetReturnInfoByBetId(String betId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { betId }, new String[] { "betId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		userBetReturnInfoDao.deleteUserBetReturnInfoByBetId(betId);
	}

	@Override
	public UserBetReturnInfo getUserBetReturnInfoByBetId(String betId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { betId }, new String[] { "betId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return userBetReturnInfoDao.getUserBetReturnInfoByBetId(betId);
	}

	@Override
	public boolean updateValueByBetId(String betId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserBetReturnInfoDao.BET_ID, betId);
		return userBetReturnInfoDao.updateValue(UserBetReturnInfo.class, valueMap, whereMap) > 0;
	}

	@Override
	public void deleteUserBetReturnInfoByLogId(String logId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { logId }, new String[] { "logId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		userBetReturnInfoDao.deleteUserBetReturnInfoByLogId(logId);
	}

	@Override
	public UserBetReturnInfo getUserBetReturnInfoByLogId(String logId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { logId }, new String[] { "logId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return userBetReturnInfoDao.getUserBetReturnInfoByLogId(logId);
	}

	@Override
	public boolean updateValueByLogId(String logId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserBetReturnInfoDao.LOG_ID, logId);
		return userBetReturnInfoDao.updateValue(UserBetReturnInfo.class, valueMap, whereMap) > 0;
	}

	public List<UserBetReturnInfo> getUserBetReturnInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return userBetReturnInfoDao.getList(UserBetReturnInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserBetReturnInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userBetReturnInfoDao.getListCount(UserBetReturnInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public List<UserBetReturnInfo> getUserBetReturnInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userBetReturnInfoDao.getList(UserBetReturnInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<UserBetReturnInfo> getUserBetReturnInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException
	{
		String whereClause = userBetReturnInfoDao.buildHqlTimeWhere(IUserBetReturnInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userBetReturnInfoDao.getList(UserBetReturnInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserBetReturnInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = userBetReturnInfoDao.buildHqlTimeWhere(IUserBetReturnInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userBetReturnInfoDao.getListCount(UserBetReturnInfo.class, whereClause, params, searchInfos);
	}
}
