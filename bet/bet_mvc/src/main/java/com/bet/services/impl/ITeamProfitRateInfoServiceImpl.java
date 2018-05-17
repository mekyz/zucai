package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.ITeamProfitRateInfoDao;
import com.bet.orms.TeamProfitRateInfo;
import com.bet.services.ITeamProfitRateInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("teamProfitRateInfoService")
public class ITeamProfitRateInfoServiceImpl implements ITeamProfitRateInfoService
{
	@Autowired
	private ITeamProfitRateInfoDao teamProfitRateInfoDao;

	@Override
	public String genId()
	{
		return teamProfitRateInfoDao.genId();
	}

	@Override
	public TeamProfitRateInfo addTeamProfitRateInfo(TeamProfitRateInfo teamProfitRateInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (teamProfitRateInfo.getAddDateLong() < 1)
		{
			teamProfitRateInfo.setAddDateLong(current);
		}
		if (teamProfitRateInfo.getUpdateDateLong() < 1)
		{
			teamProfitRateInfo.setUpdateDateLong(current);
		}
		return teamProfitRateInfoDao.add(teamProfitRateInfo);
	}

	@Override
	public TeamProfitRateInfo updateTeamProfitRateInfo(TeamProfitRateInfo teamProfitRateInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (teamProfitRateInfo.getUpdateDateLong() < 1)
		{
			teamProfitRateInfo.setUpdateDateLong(current);
		}
		return teamProfitRateInfoDao.update(teamProfitRateInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return teamProfitRateInfoDao.updateValue(TeamProfitRateInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteTeamProfitRateInfo(TeamProfitRateInfo teamProfitRateInfo) throws HibernateJsonResultException
	{
		teamProfitRateInfoDao.delete(teamProfitRateInfo);
	}

	@Override
	public void deleteTeamProfitRateInfoByRateId(String rateId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { rateId }, new String[] { "rateId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		teamProfitRateInfoDao.deleteTeamProfitRateInfoByRateId(rateId);
	}

	@Override
	public TeamProfitRateInfo getTeamProfitRateInfoByRateId(String rateId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { rateId }, new String[] { "rateId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return teamProfitRateInfoDao.getTeamProfitRateInfoByRateId(rateId);
	}

	@Override
	public boolean updateValueByRateId(String rateId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ITeamProfitRateInfoDao.RATE_ID, rateId);
		return teamProfitRateInfoDao.updateValue(TeamProfitRateInfo.class, valueMap, whereMap) > 0;
	}

	public List<TeamProfitRateInfo> getTeamProfitRateInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return teamProfitRateInfoDao.getList(TeamProfitRateInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<TeamProfitRateInfo> getTeamProfitRateInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return teamProfitRateInfoDao.getList(TeamProfitRateInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getTeamProfitRateInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return teamProfitRateInfoDao.getListCount(TeamProfitRateInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
