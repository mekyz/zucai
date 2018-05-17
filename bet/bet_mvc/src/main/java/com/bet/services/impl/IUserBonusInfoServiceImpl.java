package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserBonusInfoDao;
import com.bet.orms.UserBonusInfo;
import com.bet.services.IUserBonusInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userBonusInfoService")
public class IUserBonusInfoServiceImpl implements IUserBonusInfoService
{
	@Autowired
	private IUserBonusInfoDao userBonusInfoDao;

	@Override
	public String genId()
	{
		return userBonusInfoDao.genId();
	}

	@Override
	public UserBonusInfo addUserBonusInfo(UserBonusInfo userBonusInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userBonusInfo.getAddDateLong() < 1)
		{
			userBonusInfo.setAddDateLong(current);
		}
		if (userBonusInfo.getUpdateDateLong() < 1)
		{
			userBonusInfo.setUpdateDateLong(current);
		}
		return userBonusInfoDao.add(userBonusInfo);
	}

	@Override
	public UserBonusInfo updateUserBonusInfo(UserBonusInfo userBonusInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (userBonusInfo.getUpdateDateLong() < 1)
		{
			userBonusInfo.setUpdateDateLong(current);
		}
		return userBonusInfoDao.update(userBonusInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return userBonusInfoDao.updateValue(UserBonusInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteUserBonusInfo(UserBonusInfo userBonusInfo) throws HibernateJsonResultException
	{
		userBonusInfoDao.delete(userBonusInfo);
	}

	@Override
	public void deleteUserBonusInfoByBonusId(String bonusId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { bonusId }, new String[] { "bonusId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		userBonusInfoDao.deleteUserBonusInfoByBonusId(bonusId);
	}

	@Override
	public UserBonusInfo getUserBonusInfoByBonusId(String bonusId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { bonusId }, new String[] { "bonusId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return userBonusInfoDao.getUserBonusInfoByBonusId(bonusId);
	}

	@Override
	public boolean updateValueByBonusId(String bonusId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserBonusInfoDao.BONUS_ID, bonusId);
		return userBonusInfoDao.updateValue(UserBonusInfo.class, valueMap, whereMap) > 0;
	}

	public List<UserBonusInfo> getUserBonusInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return userBonusInfoDao.getList(UserBonusInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<UserBonusInfo> getUserBonusInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userBonusInfoDao.getList(UserBonusInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserBonusInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userBonusInfoDao.getListCount(UserBonusInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public boolean updateStatusValueByBonusId(String bonusId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserBonusInfoDao.BONUS_ID, bonusId);
		whereMap.put(IUserBonusInfoDao.STATUS, StatusType.DISABLED.getStatus());
		return userBonusInfoDao.updateValue(UserBonusInfo.class, valueMap, whereMap) > 0;
	}
}
