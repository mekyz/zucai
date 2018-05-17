package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserLoginLogInfoDao;
import com.bet.orms.UserLoginLogInfo;
import com.bet.services.IUserLoginLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

@Repository("userLoginLogInfoService")
public class IUserLoginLogInfoServiceImpl implements IUserLoginLogInfoService
{
	@Autowired
	private IUserLoginLogInfoDao userLoginLogInfoDao;

	@Override
	public String genId()
	{
		return userLoginLogInfoDao.genId();
	}

	@Override
	public UserLoginLogInfo addUserLoginLogInfo(UserLoginLogInfo userLoginLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userLoginLogInfo.getAddDateLong() < 1)
		{
			userLoginLogInfo.setAddDateLong(current);
		}
		return userLoginLogInfoDao.add(userLoginLogInfo);
	}

	@Override
	public UserLoginLogInfo updateUserLoginLogInfo(UserLoginLogInfo userLoginLogInfo) throws HibernateJsonResultException
	{
		return userLoginLogInfoDao.update(userLoginLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return userLoginLogInfoDao.updateValue(UserLoginLogInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteUserLoginLogInfo(UserLoginLogInfo userLoginLogInfo) throws HibernateJsonResultException
	{
		userLoginLogInfoDao.delete(userLoginLogInfo);
	}

	@Override
	public void deleteUserLoginLogInfoById(int id) throws HibernateJsonResultException
	{
		userLoginLogInfoDao.deleteUserLoginLogInfoById(id);
	}

	@Override
	public UserLoginLogInfo getUserLoginLogInfoById(int id) throws HibernateJsonResultException
	{
		return userLoginLogInfoDao.getUserLoginLogInfoById(id);
	}

	@Override
	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserLoginLogInfoDao.ID, id);
		return userLoginLogInfoDao.updateValue(UserLoginLogInfo.class, valueMap, whereMap) > 0;
	}

	public List<UserLoginLogInfo> getUserLoginLogInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return userLoginLogInfoDao.getList(UserLoginLogInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<UserLoginLogInfo> getUserLoginLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userLoginLogInfoDao.getList(UserLoginLogInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserLoginLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userLoginLogInfoDao.getListCount(UserLoginLogInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
