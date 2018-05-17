package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserBetInfoDao;
import com.bet.orms.UserBetInfo;
import com.bet.services.IUserBetInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userBetInfoService")
public class IUserBetInfoServiceImpl implements IUserBetInfoService
{
	@Autowired
	private IUserBetInfoDao userBetInfoDao;

	@Override
	public String genId()
	{
		return userBetInfoDao.genId();
	}

	@Override
	public UserBetInfo addUserBetInfo(UserBetInfo userBetInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userBetInfo.getAddDateLong() < 1)
		{
			userBetInfo.setAddDateLong(current);
		}
		if (userBetInfo.getUpdateDateLong() < 1)
		{
			userBetInfo.setUpdateDateLong(current);
		}
		return userBetInfoDao.add(userBetInfo);
	}

	@Override
	public UserBetInfo updateUserBetInfo(UserBetInfo userBetInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userBetInfo.getUpdateDateLong() < 1)
		{
			userBetInfo.setUpdateDateLong(current);
		}
		return userBetInfoDao.update(userBetInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return userBetInfoDao.updateValue(UserBetInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteUserBetInfo(UserBetInfo userBetInfo) throws HibernateJsonResultException
	{
		userBetInfoDao.delete(userBetInfo);
	}

	@Override
	public void deleteUserBetInfoByBetId(String betId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { betId }, new String[] { "betId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		userBetInfoDao.deleteUserBetInfoByBetId(betId);
	}

	@Override
	public UserBetInfo getUserBetInfoByBetId(String betId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { betId }, new String[] { "betId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return userBetInfoDao.getUserBetInfoByBetId(betId);
	}

	@Override
	public boolean updateValueByBetId(String betId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserBetInfoDao.BET_ID, betId);
		return userBetInfoDao.updateValue(UserBetInfo.class, valueMap, whereMap) > 0;
	}

	public List<UserBetInfo> getUserBetInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return userBetInfoDao.getList(UserBetInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserBetInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userBetInfoDao.getListCount(UserBetInfo.class, null, null, searchInfos);
	}

	/* 自定义方法 */
	@Override
	public boolean updateProfitValueByBetId(String betId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserBetInfoDao.BET_ID, betId);
		whereMap.put(IUserBetInfoDao.PROFIT_STATUS, StatusType.DISABLED.getStatus());
		return userBetInfoDao.updateValue(UserBetInfo.class, valueMap, whereMap) > 0;
	}

	@Override
	public boolean updateTeamProfitValueByBetId(String betId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserBetInfoDao.BET_ID, betId);
		whereMap.put(IUserBetInfoDao.TEAM_PROFIT_STATUS, StatusType.DISABLED.getStatus());
		return userBetInfoDao.updateValue(UserBetInfo.class, valueMap, whereMap) > 0;
	}

	@Override
	public List<UserBetInfo> getUserBetInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userBetInfoDao.getList(UserBetInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<UserBetInfo> getUserBetInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException
	{
		String whereClause = userBetInfoDao.buildHqlTimeWhere(IUserBetInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userBetInfoDao.getList(UserBetInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserBetInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = userBetInfoDao.buildHqlTimeWhere(IUserBetInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userBetInfoDao.getListCount(UserBetInfo.class, whereClause, params, searchInfos);
	}

	@Override
	public long getSum(String colName, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = userBetInfoDao.buildSqlTimeWhere(IUserBetInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userBetInfoDao.getSum(colName, whereClause, params, searchInfos);
	}

	@Override
	public List<UserBetInfo> getUserListByUserType(byte userType, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userBetInfoDao.getUserListByUserType(userType, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getTeamUserTotalMoney(String userId, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userBetInfoDao.getTeamUserTotalMoney(userId, null, null, searchInfos);
	}
}
