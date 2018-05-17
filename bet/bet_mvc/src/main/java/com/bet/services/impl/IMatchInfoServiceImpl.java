package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IMatchInfoDao;
import com.bet.orms.MatchInfo;
import com.bet.services.IMatchInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("matchInfoService")
public class IMatchInfoServiceImpl implements IMatchInfoService
{
	@Autowired
	private IMatchInfoDao matchInfoDao;

	@Override
	public String genId()
	{
		return matchInfoDao.genId();
	}

	@Override
	public MatchInfo addMatchInfo(MatchInfo matchInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (matchInfo.getAddDateLong() < 1)
		{
			matchInfo.setAddDateLong(current);
		}
		if (matchInfo.getUpdateDateLong() < 1)
		{
			matchInfo.setUpdateDateLong(current);
		}
		return matchInfoDao.add(matchInfo);
	}

	@Override
	public MatchInfo updateMatchInfo(MatchInfo matchInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (matchInfo.getUpdateDateLong() < 1)
		{
			matchInfo.setUpdateDateLong(current);
		}
		return matchInfoDao.update(matchInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return matchInfoDao.updateValue(MatchInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteMatchInfo(MatchInfo matchInfo) throws HibernateJsonResultException
	{
		matchInfoDao.delete(matchInfo);
	}

	@Override
	public void deleteMatchInfoByPhaseId(String phaseId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { phaseId }, new String[] { "phaseId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		matchInfoDao.deleteMatchInfoByPhaseId(phaseId);
	}

	@Override
	public MatchInfo getMatchInfoByPhaseId(String phaseId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { phaseId }, new String[] { "phaseId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return matchInfoDao.getMatchInfoByPhaseId(phaseId);
	}

	@Override
	public boolean updateValueByPhaseId(String phaseId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMatchInfoDao.PHASE_ID, phaseId);
		return matchInfoDao.updateValue(MatchInfo.class, valueMap, whereMap) > 0;
	}

	@Override
	public void deleteMatchInfoByOutId(String outId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { outId }, new String[] { "outId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		matchInfoDao.deleteMatchInfoByOutId(outId);
	}

	@Override
	public MatchInfo getMatchInfoByOutId(String outId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { outId }, new String[] { "outId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return matchInfoDao.getMatchInfoByOutId(outId);
	}

	@Override
	public boolean updateValueByOutId(String outId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMatchInfoDao.OUT_ID, outId);
		return matchInfoDao.updateValue(MatchInfo.class, valueMap, whereMap) > 0;
	}

	public List<MatchInfo> getMatchInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return matchInfoDao.getList(MatchInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getMatchInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return matchInfoDao.getListCount(MatchInfo.class, null, null, searchInfos);
	}

	/* 自定义方法 */
	@Override
	public boolean updateProfitValueByPhaseId(String phaseId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMatchInfoDao.PHASE_ID, phaseId);
		whereMap.put(IMatchInfoDao.FINAL_PROFIT_STATUS, StatusType.DISABLED.getStatus());
		return matchInfoDao.updateValue(MatchInfo.class, valueMap, whereMap) > 0;
	}

	@Override
	public List<MatchInfo> getMatchInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return matchInfoDao.getList(MatchInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<MatchInfo> getMatchInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException
	{
		String whereClause = matchInfoDao.buildHqlTimeWhere(IMatchInfoDao.MATCH_DATE, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return matchInfoDao.getList(MatchInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getMatchInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = matchInfoDao.buildHqlTimeWhere(IMatchInfoDao.MATCH_DATE, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return matchInfoDao.getListCount(MatchInfo.class, whereClause, params, searchInfos);
	}
}
