package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IMatchStatisticsLogInfoDao;
import com.bet.orms.MatchStatisticsLogInfo;
import com.bet.services.IMatchStatisticsLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("matchStatisticsLogInfoService")
public class IMatchStatisticsLogInfoServiceImpl implements IMatchStatisticsLogInfoService
{
	@Autowired
	private IMatchStatisticsLogInfoDao matchStatisticsLogInfoDao;

	@Override
	public String genId()
	{
		return matchStatisticsLogInfoDao.genId();
	}

	@Override
	public MatchStatisticsLogInfo addMatchStatisticsLogInfo(MatchStatisticsLogInfo matchStatisticsLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (matchStatisticsLogInfo.getAddDateLong() < 1)
		{
			matchStatisticsLogInfo.setAddDateLong(current);
		}
		if (matchStatisticsLogInfo.getUpdateDateLong() < 1)
		{
			matchStatisticsLogInfo.setUpdateDateLong(current);
		}
		return matchStatisticsLogInfoDao.add(matchStatisticsLogInfo);
	}

	@Override
	public MatchStatisticsLogInfo updateMatchStatisticsLogInfo(MatchStatisticsLogInfo matchStatisticsLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (matchStatisticsLogInfo.getUpdateDateLong() < 1)
		{
			matchStatisticsLogInfo.setUpdateDateLong(current);
		}
		return matchStatisticsLogInfoDao.update(matchStatisticsLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return matchStatisticsLogInfoDao.updateValue(MatchStatisticsLogInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteMatchStatisticsLogInfo(MatchStatisticsLogInfo matchStatisticsLogInfo) throws HibernateJsonResultException
	{
		matchStatisticsLogInfoDao.delete(matchStatisticsLogInfo);
	}

	@Override
	public void deleteMatchStatisticsLogInfoByPhaseId(String phaseId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { phaseId }, new String[] { "phaseId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		matchStatisticsLogInfoDao.deleteMatchStatisticsLogInfoByPhaseId(phaseId);
	}

	@Override
	public MatchStatisticsLogInfo getMatchStatisticsLogInfoByPhaseId(String phaseId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { phaseId }, new String[] { "phaseId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return matchStatisticsLogInfoDao.getMatchStatisticsLogInfoByPhaseId(phaseId);
	}

	@Override
	public boolean updateValueByPhaseId(String phaseId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMatchStatisticsLogInfoDao.PHASE_ID, phaseId);
		return matchStatisticsLogInfoDao.updateValue(MatchStatisticsLogInfo.class, valueMap, whereMap) > 0;
	}

	public List<MatchStatisticsLogInfo> getMatchStatisticsLogInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos,
		List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return matchStatisticsLogInfoDao.getList(MatchStatisticsLogInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<MatchStatisticsLogInfo> getMatchStatisticsLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return matchStatisticsLogInfoDao.getList(MatchStatisticsLogInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getMatchStatisticsLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return matchStatisticsLogInfoDao.getListCount(MatchStatisticsLogInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
