package com.bet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserTypeConditionInfoDao;
import com.bet.orms.UserTypeConditionInfo;
import com.bet.services.IUserTypeConditionInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.enums.SqlOrderType;

@Repository("userTypeConditionInfoService")
public class IUserTypeConditionInfoServiceImpl implements IUserTypeConditionInfoService
{
	@Autowired
	private IUserTypeConditionInfoDao userTypeConditionInfoDao;

	@Override
	public String genId()
	{
		return userTypeConditionInfoDao.genId();
	}

	@Override
	public UserTypeConditionInfo addUserTypeConditionInfo(UserTypeConditionInfo userTypeConditionInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userTypeConditionInfo.getAddDateLong() < 1)
		{
			userTypeConditionInfo.setAddDateLong(current);
		}
		if (userTypeConditionInfo.getUpdateDateLong() < 1)
		{
			userTypeConditionInfo.setUpdateDateLong(current);
		}
		return userTypeConditionInfoDao.add(userTypeConditionInfo);
	}

	@Override
	public UserTypeConditionInfo updateUserTypeConditionInfo(UserTypeConditionInfo userTypeConditionInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userTypeConditionInfo.getUpdateDateLong() < 1)
		{
			userTypeConditionInfo.setUpdateDateLong(current);
		}
		return userTypeConditionInfoDao.update(userTypeConditionInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return userTypeConditionInfoDao.updateValue(UserTypeConditionInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteUserTypeConditionInfo(UserTypeConditionInfo userTypeConditionInfo) throws HibernateJsonResultException
	{
		userTypeConditionInfoDao.delete(userTypeConditionInfo);
	}

	@Override
	public boolean updateValueByUserType(byte userType, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserTypeConditionInfoDao.USER_TYPE, userType);
		return userTypeConditionInfoDao.updateValue(UserTypeConditionInfo.class, valueMap, whereMap) > 0;
	}

	public List<UserTypeConditionInfo> getUserTypeConditionInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos,
		List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userTypeConditionInfoDao.getList(UserTypeConditionInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<UserTypeConditionInfo> getUserTypeConditionInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userTypeConditionInfoDao.getList(UserTypeConditionInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserTypeConditionInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userTypeConditionInfoDao.getListCount(UserTypeConditionInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public void deleteUserTypeConditionInfoByUserType(byte userType) throws HibernateJsonResultException
	{
		userTypeConditionInfoDao.deleteUserTypeConditionInfoByUserType(userType);
	}

	@Override
	public UserTypeConditionInfo getUserTypeConditionInfoByUserType(byte userType) throws HibernateJsonResultException
	{
		return userTypeConditionInfoDao.getUserTypeConditionInfoByUserType(userType);
	}

	@Override
	public UserTypeConditionInfo getMaxUserTypeConditionInfo(long directCount, long teamCount, long money, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		orderInfos.add(new TableOrderInfo(IUserTypeConditionInfoDao.USER_TYPE, SqlOrderType.DESC.getType()));
		String whereClause = String.format("where %s <= %d and %s <= %d and %s <= %d and %s = :%s", IUserTypeConditionInfoDao.DIRECT_COUNT, directCount, IUserTypeConditionInfoDao.TEAM_COUNT,
			teamCount, IUserTypeConditionInfoDao.MONEY, money, IUserTypeConditionInfoDao.STATUS, IUserTypeConditionInfoDao.STATUS);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserTypeConditionInfoDao.STATUS, StatusType.ENABLED.getStatus());
		List<UserTypeConditionInfo> list = userTypeConditionInfoDao.getList(UserTypeConditionInfo.class, whereClause, params, 0, 1, orderInfos, searchInfos);
		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}
}
