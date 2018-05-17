package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserTypeChangeLogInfoDao;
import com.bet.orms.UserTypeChangeLogInfo;
import com.bet.services.IUserTypeChangeLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

@Repository("userTypeChangeLogInfoService")
public class IUserTypeChangeLogInfoServiceImpl implements IUserTypeChangeLogInfoService
{
	@Autowired
	private IUserTypeChangeLogInfoDao userTypeChangeLogInfoDao;

	@Override
	public String genId()
	{
		return userTypeChangeLogInfoDao.genId();
	}

	@Override
	public UserTypeChangeLogInfo addUserTypeChangeLogInfo(UserTypeChangeLogInfo userTypeChangeLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userTypeChangeLogInfo.getAddDateLong() < 1)
		{
			userTypeChangeLogInfo.setAddDateLong(current);
		}
		return userTypeChangeLogInfoDao.add(userTypeChangeLogInfo);
	}

	@Override
	public UserTypeChangeLogInfo updateUserTypeChangeLogInfo(UserTypeChangeLogInfo userTypeChangeLogInfo) throws HibernateJsonResultException
	{
		return userTypeChangeLogInfoDao.update(userTypeChangeLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return userTypeChangeLogInfoDao.updateValue(UserTypeChangeLogInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteUserTypeChangeLogInfo(UserTypeChangeLogInfo userTypeChangeLogInfo) throws HibernateJsonResultException
	{
		userTypeChangeLogInfoDao.delete(userTypeChangeLogInfo);
	}

	@Override
	public void deleteUserTypeChangeLogInfoById(int id) throws HibernateJsonResultException
	{
		userTypeChangeLogInfoDao.deleteUserTypeChangeLogInfoById(id);
	}

	@Override
	public UserTypeChangeLogInfo getUserTypeChangeLogInfoById(int id) throws HibernateJsonResultException
	{
		return userTypeChangeLogInfoDao.getUserTypeChangeLogInfoById(id);
	}

	@Override
	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserTypeChangeLogInfoDao.ID, id);
		return userTypeChangeLogInfoDao.updateValue(UserTypeChangeLogInfo.class, valueMap, whereMap) > 0;
	}

	public List<UserTypeChangeLogInfo> getUserTypeChangeLogInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos,
		List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userTypeChangeLogInfoDao.getList(UserTypeChangeLogInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<UserTypeChangeLogInfo> getUserTypeChangeLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userTypeChangeLogInfoDao.getList(UserTypeChangeLogInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserTypeChangeLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userTypeChangeLogInfoDao.getListCount(UserTypeChangeLogInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
