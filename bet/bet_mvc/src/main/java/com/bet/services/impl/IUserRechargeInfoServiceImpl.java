package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserRechargeInfoDao;
import com.bet.orms.UserRechargeInfo;
import com.bet.services.IUserRechargeInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userRechargeInfoService")
public class IUserRechargeInfoServiceImpl implements IUserRechargeInfoService
{
	@Autowired
	private IUserRechargeInfoDao userRechargeInfoDao;

	@Override
	public String genId()
	{
		return userRechargeInfoDao.genId();
	}

	@Override
	public UserRechargeInfo addUserRechargeInfo(UserRechargeInfo userRechargeInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userRechargeInfo.getAddDateLong() < 1)
		{
			userRechargeInfo.setAddDateLong(current);
		}
		if (userRechargeInfo.getUpdateDateLong() < 1)
		{
			userRechargeInfo.setUpdateDateLong(current);
		}
		return userRechargeInfoDao.add(userRechargeInfo);
	}

	@Override
	public UserRechargeInfo updateUserRechargeInfo(UserRechargeInfo userRechargeInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userRechargeInfo.getUpdateDateLong() < 1)
		{
			userRechargeInfo.setUpdateDateLong(current);
		}
		return userRechargeInfoDao.update(userRechargeInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return userRechargeInfoDao.updateValue(UserRechargeInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteUserRechargeInfo(UserRechargeInfo userRechargeInfo) throws HibernateJsonResultException
	{
		userRechargeInfoDao.delete(userRechargeInfo);
	}

	@Override
	public void deleteUserRechargeInfoByRechargeId(String rechargeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { rechargeId }, new String[] { "rechargeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		userRechargeInfoDao.deleteUserRechargeInfoByRechargeId(rechargeId);
	}

	@Override
	public UserRechargeInfo getUserRechargeInfoByRechargeId(String rechargeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { rechargeId }, new String[] { "rechargeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return userRechargeInfoDao.getUserRechargeInfoByRechargeId(rechargeId);
	}

	@Override
	public boolean updateValueByRechargeId(String rechargeId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserRechargeInfoDao.RECHARGE_ID, rechargeId);
		return userRechargeInfoDao.updateValue(UserRechargeInfo.class, valueMap, whereMap) > 0;
	}

	public List<UserRechargeInfo> getUserRechargeInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return userRechargeInfoDao.getList(UserRechargeInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserRechargeInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userRechargeInfoDao.getListCount(UserRechargeInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public List<UserRechargeInfo> getUserRechargeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userRechargeInfoDao.getList(UserRechargeInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<UserRechargeInfo> getUserRechargeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException
	{
		String whereClause = userRechargeInfoDao.buildHqlTimeWhere(IUserRechargeInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userRechargeInfoDao.getList(UserRechargeInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserRechargeInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = userRechargeInfoDao.buildHqlTimeWhere(IUserRechargeInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userRechargeInfoDao.getListCount(UserRechargeInfo.class, whereClause, params, searchInfos);
	}

	@Override
	public long getSum(String colName, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = userRechargeInfoDao.buildSqlTimeWhere(IUserRechargeInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userRechargeInfoDao.getSum(colName, whereClause, params, searchInfos, startDateLong, endDateLong);
	}

	@Override
	public boolean updateStatusValueByRechargeId(String rechargeId, byte status, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserRechargeInfoDao.RECHARGE_ID, rechargeId);
		whereMap.put(IUserRechargeInfoDao.STATUS, status);
		return userRechargeInfoDao.updateValue(UserRechargeInfo.class, valueMap, whereMap) > 0;
	}
}
