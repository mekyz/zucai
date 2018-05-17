package com.bet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserNewsStatusInfoDao;
import com.bet.orms.UserNewsStatusInfo;
import com.bet.services.IUserNewsStatusInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.enums.SqlOrderType;

@Repository("userNewsStatusInfoService")
public class IUserNewsStatusInfoServiceImpl implements IUserNewsStatusInfoService
{
	@Autowired
	private IUserNewsStatusInfoDao userNewsStatusInfoDao;

	@Override
	public String genId()
	{
		return userNewsStatusInfoDao.genId();
	}

	@Override
	public UserNewsStatusInfo addUserNewsStatusInfo(UserNewsStatusInfo userNewsStatusInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userNewsStatusInfo.getAddDateLong() < 1)
		{
			userNewsStatusInfo.setAddDateLong(current);
		}
		return userNewsStatusInfoDao.add(userNewsStatusInfo);
	}

	@Override
	public UserNewsStatusInfo updateUserNewsStatusInfo(UserNewsStatusInfo userNewsStatusInfo) throws HibernateJsonResultException
	{
		return userNewsStatusInfoDao.update(userNewsStatusInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return userNewsStatusInfoDao.updateValue(UserNewsStatusInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteUserNewsStatusInfo(UserNewsStatusInfo userNewsStatusInfo) throws HibernateJsonResultException
	{
		userNewsStatusInfoDao.delete(userNewsStatusInfo);
	}

	@Override
	public void deleteUserNewsStatusInfoById(int id) throws HibernateJsonResultException
	{
		userNewsStatusInfoDao.deleteUserNewsStatusInfoById(id);
	}

	@Override
	public UserNewsStatusInfo getUserNewsStatusInfoById(int id) throws HibernateJsonResultException
	{
		return userNewsStatusInfoDao.getUserNewsStatusInfoById(id);
	}

	@Override
	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserNewsStatusInfoDao.ID, id);
		return userNewsStatusInfoDao.updateValue(UserNewsStatusInfo.class, valueMap, whereMap) > 0;
	}

	public List<UserNewsStatusInfo> getUserNewsStatusInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return userNewsStatusInfoDao.getList(UserNewsStatusInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<UserNewsStatusInfo> getUserNewsStatusInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userNewsStatusInfoDao.getList(UserNewsStatusInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserNewsStatusInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userNewsStatusInfoDao.getListCount(UserNewsStatusInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public UserNewsStatusInfo getUserNewsStatusInfo(String userId, String newsId) throws HibernateJsonResultException
	{
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		orderInfos.add(new TableOrderInfo(IUserNewsStatusInfoDao.ADD_DATE_LONG, SqlOrderType.DESC.getType()));
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IUserNewsStatusInfoDao.USER_ID, userId, true, false));
		searchInfos.add(new TableSearchInfo(IUserNewsStatusInfoDao.NEWS_ID, newsId, true, false));
		List<UserNewsStatusInfo> list = userNewsStatusInfoDao.getList(UserNewsStatusInfo.class, null, null, 0, 1, orderInfos, searchInfos);
		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}
}
