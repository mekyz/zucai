package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.ITeamInfoDao;
import com.bet.orms.TeamInfo;
import com.bet.services.ITeamInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("teamInfoService")
public class ITeamInfoServiceImpl implements ITeamInfoService
{
	@Autowired
	private ITeamInfoDao teamInfoDao;

	@Override
	public String genId()
	{
		return teamInfoDao.genId();
	}

	@Override
	public TeamInfo addTeamInfo(TeamInfo teamInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (teamInfo.getAddDateLong() < 1)
		{
			teamInfo.setAddDateLong(current);
		}
		if (teamInfo.getUpdateDateLong() < 1)
		{
			teamInfo.setUpdateDateLong(current);
		}
		return teamInfoDao.add(teamInfo);
	}

	@Override
	public TeamInfo updateTeamInfo(TeamInfo teamInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (teamInfo.getUpdateDateLong() < 1)
		{
			teamInfo.setUpdateDateLong(current);
		}
		return teamInfoDao.update(teamInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return teamInfoDao.updateValue(TeamInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteTeamInfo(TeamInfo teamInfo) throws HibernateJsonResultException
	{
		teamInfoDao.delete(teamInfo);
	}

	@Override
	public void deleteTeamInfoByTeamId(String teamId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { teamId }, new String[] { "teamId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		teamInfoDao.deleteTeamInfoByTeamId(teamId);
	}

	@Override
	public TeamInfo getTeamInfoByTeamId(String teamId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { teamId }, new String[] { "teamId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return teamInfoDao.getTeamInfoByTeamId(teamId);
	}

	@Override
	public boolean updateValueByTeamId(String teamId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ITeamInfoDao.TEAM_ID, teamId);
		return teamInfoDao.updateValue(TeamInfo.class, valueMap, whereMap) > 0;
	}

	public List<TeamInfo> getTeamInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return teamInfoDao.getList(TeamInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<TeamInfo> getTeamInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return teamInfoDao.getList(TeamInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getTeamInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return teamInfoDao.getListCount(TeamInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
