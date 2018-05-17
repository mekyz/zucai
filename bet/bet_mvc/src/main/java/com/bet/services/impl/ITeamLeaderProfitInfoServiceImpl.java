package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.ITeamLeaderProfitInfoDao;
import com.bet.orms.TeamLeaderProfitInfo;
import com.bet.services.ITeamLeaderProfitInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("teamLeaderProfitInfoService")
public class ITeamLeaderProfitInfoServiceImpl implements ITeamLeaderProfitInfoService
{
	@Autowired
	private ITeamLeaderProfitInfoDao teamLeaderProfitInfoDao;

	@Override
	public String genId()
	{
		return teamLeaderProfitInfoDao.genId();
	}

	@Override
	public TeamLeaderProfitInfo addTeamLeaderProfitInfo(TeamLeaderProfitInfo teamLeaderProfitInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (teamLeaderProfitInfo.getAddDateLong() < 1)
		{
			teamLeaderProfitInfo.setAddDateLong(current);
		}
		if (teamLeaderProfitInfo.getUpdateDateLong() < 1)
		{
			teamLeaderProfitInfo.setUpdateDateLong(current);
		}
		return teamLeaderProfitInfoDao.add(teamLeaderProfitInfo);
	}

	@Override
	public TeamLeaderProfitInfo updateTeamLeaderProfitInfo(TeamLeaderProfitInfo teamLeaderProfitInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (teamLeaderProfitInfo.getUpdateDateLong() < 1)
		{
			teamLeaderProfitInfo.setUpdateDateLong(current);
		}
		return teamLeaderProfitInfoDao.update(teamLeaderProfitInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return teamLeaderProfitInfoDao.updateValue(TeamLeaderProfitInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteTeamLeaderProfitInfo(TeamLeaderProfitInfo teamLeaderProfitInfo) throws HibernateJsonResultException
	{
		teamLeaderProfitInfoDao.delete(teamLeaderProfitInfo);
	}

	@Override
	public void deleteTeamLeaderProfitInfoByProfitId(String profitId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { profitId }, new String[] { "profitId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		teamLeaderProfitInfoDao.deleteTeamLeaderProfitInfoByProfitId(profitId);
	}

	@Override
	public TeamLeaderProfitInfo getTeamLeaderProfitInfoByProfitId(String profitId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { profitId }, new String[] { "profitId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return teamLeaderProfitInfoDao.getTeamLeaderProfitInfoByProfitId(profitId);
	}

	@Override
	public boolean updateValueByProfitId(String profitId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ITeamLeaderProfitInfoDao.PROFIT_ID, profitId);
		return teamLeaderProfitInfoDao.updateValue(TeamLeaderProfitInfo.class, valueMap, whereMap) > 0;
	}

	public List<TeamLeaderProfitInfo> getTeamLeaderProfitInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos,
		List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return teamLeaderProfitInfoDao.getList(TeamLeaderProfitInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<TeamLeaderProfitInfo> getTeamLeaderProfitInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return teamLeaderProfitInfoDao.getList(TeamLeaderProfitInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getTeamLeaderProfitInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return teamLeaderProfitInfoDao.getListCount(TeamLeaderProfitInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public long getSum(String colName, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = teamLeaderProfitInfoDao.buildSqlTimeWhere(ITeamLeaderProfitInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return teamLeaderProfitInfoDao.getSum(colName, whereClause, params, searchInfos);
	}
}
