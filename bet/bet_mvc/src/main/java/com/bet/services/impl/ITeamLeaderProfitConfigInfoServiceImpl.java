package com.bet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.ITeamLeaderProfitConfigInfoDao;
import com.bet.orms.TeamLeaderProfitConfigInfo;
import com.bet.services.ITeamLeaderProfitConfigInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("teamLeaderProfitConfigInfoService")
public class ITeamLeaderProfitConfigInfoServiceImpl implements ITeamLeaderProfitConfigInfoService
{
	@Autowired
	private ITeamLeaderProfitConfigInfoDao teamLeaderProfitConfigInfoDao;

	@Override
	public String genId()
	{
		return teamLeaderProfitConfigInfoDao.genId();
	}

	@Override
	public TeamLeaderProfitConfigInfo addTeamLeaderProfitConfigInfo(TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (teamLeaderProfitConfigInfo.getAddDateLong() < 1)
		{
			teamLeaderProfitConfigInfo.setAddDateLong(current);
		}
		if (teamLeaderProfitConfigInfo.getUpdateDateLong() < 1)
		{
			teamLeaderProfitConfigInfo.setUpdateDateLong(current);
		}
		return teamLeaderProfitConfigInfoDao.add(teamLeaderProfitConfigInfo);
	}

	@Override
	public TeamLeaderProfitConfigInfo updateTeamLeaderProfitConfigInfo(TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (teamLeaderProfitConfigInfo.getUpdateDateLong() < 1)
		{
			teamLeaderProfitConfigInfo.setUpdateDateLong(current);
		}
		return teamLeaderProfitConfigInfoDao.update(teamLeaderProfitConfigInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return teamLeaderProfitConfigInfoDao.updateValue(TeamLeaderProfitConfigInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteTeamLeaderProfitConfigInfo(TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo) throws HibernateJsonResultException
	{
		teamLeaderProfitConfigInfoDao.delete(teamLeaderProfitConfigInfo);
	}

	@Override
	public void deleteTeamLeaderProfitConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		teamLeaderProfitConfigInfoDao.deleteTeamLeaderProfitConfigInfoByConfigId(configId);
	}

	@Override
	public TeamLeaderProfitConfigInfo getTeamLeaderProfitConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return teamLeaderProfitConfigInfoDao.getTeamLeaderProfitConfigInfoByConfigId(configId);
	}

	@Override
	public boolean updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ITeamLeaderProfitConfigInfoDao.CONFIG_ID, configId);
		return teamLeaderProfitConfigInfoDao.updateValue(TeamLeaderProfitConfigInfo.class, valueMap, whereMap) > 0;
	}

	public List<TeamLeaderProfitConfigInfo> getTeamLeaderProfitConfigInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos,
		List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return teamLeaderProfitConfigInfoDao.getList(TeamLeaderProfitConfigInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<TeamLeaderProfitConfigInfo> getTeamLeaderProfitConfigInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return teamLeaderProfitConfigInfoDao.getList(TeamLeaderProfitConfigInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getTeamLeaderProfitConfigInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return teamLeaderProfitConfigInfoDao.getListCount(TeamLeaderProfitConfigInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public TeamLeaderProfitConfigInfo getTeamLeaderProfitConfigInfoByUserType(byte userType) throws HibernateJsonResultException
	{
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(ITeamLeaderProfitConfigInfoDao.USER_TYPE, userType + "", true, false));
		searchInfos.add(new TableSearchInfo(ITeamLeaderProfitConfigInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
		List<TeamLeaderProfitConfigInfo> list = teamLeaderProfitConfigInfoDao.getList(TeamLeaderProfitConfigInfo.class, null, null, 0, 1, orderInfos, searchInfos);
		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}
}
