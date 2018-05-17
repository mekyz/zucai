package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.ITeamProfitInfoDao;
import com.bet.orms.TeamProfitInfo;
import com.bet.services.ITeamProfitInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("teamProfitInfoService")
public class ITeamProfitInfoServiceImpl implements ITeamProfitInfoService
{
	@Autowired
	private ITeamProfitInfoDao teamProfitInfoDao;

	@Override
	public String genId()
	{
		return teamProfitInfoDao.genId();
	}

	@Override
	public TeamProfitInfo addTeamProfitInfo(TeamProfitInfo teamProfitInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (teamProfitInfo.getAddDateLong() < 1)
		{
			teamProfitInfo.setAddDateLong(current);
		}
		if (teamProfitInfo.getUpdateDateLong() < 1)
		{
			teamProfitInfo.setUpdateDateLong(current);
		}
		return teamProfitInfoDao.add(teamProfitInfo);
	}

	@Override
	public TeamProfitInfo updateTeamProfitInfo(TeamProfitInfo teamProfitInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (teamProfitInfo.getUpdateDateLong() < 1)
		{
			teamProfitInfo.setUpdateDateLong(current);
		}
		return teamProfitInfoDao.update(teamProfitInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return teamProfitInfoDao.updateValue(TeamProfitInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteTeamProfitInfo(TeamProfitInfo teamProfitInfo) throws HibernateJsonResultException
	{
		teamProfitInfoDao.delete(teamProfitInfo);
	}

	@Override
	public void deleteTeamProfitInfoByProfitId(String profitId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { profitId }, new String[] { "profitId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		teamProfitInfoDao.deleteTeamProfitInfoByProfitId(profitId);
	}

	@Override
	public TeamProfitInfo getTeamProfitInfoByProfitId(String profitId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { profitId }, new String[] { "profitId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return teamProfitInfoDao.getTeamProfitInfoByProfitId(profitId);
	}

	@Override
	public boolean updateValueByProfitId(String profitId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ITeamProfitInfoDao.PROFIT_ID, profitId);
		return teamProfitInfoDao.updateValue(TeamProfitInfo.class, valueMap, whereMap) > 0;
	}

	public List<TeamProfitInfo> getTeamProfitInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return teamProfitInfoDao.getList(TeamProfitInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getTeamProfitInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return teamProfitInfoDao.getListCount(TeamProfitInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public List<TeamProfitInfo> getTeamProfitInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return teamProfitInfoDao.getList(TeamProfitInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<TeamProfitInfo> getTeamProfitInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException
	{
		String whereClause = teamProfitInfoDao.buildHqlTimeWhere(ITeamProfitInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return teamProfitInfoDao.getList(TeamProfitInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getTeamProfitInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = teamProfitInfoDao.buildHqlTimeWhere(ITeamProfitInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return teamProfitInfoDao.getListCount(TeamProfitInfo.class, whereClause, params, searchInfos);
	}

	@Override
	public long getSum(String colName, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = teamProfitInfoDao.buildSqlTimeWhere(ITeamProfitInfoDao.ADD_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return teamProfitInfoDao.getSum(colName, whereClause, params, searchInfos);
	}
}
