package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IMatchInfoDao;
import com.bet.orms.MatchInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("matchInfoDao")
public class IMatchInfoDaoMySqlImpl extends IAbstractDaoImpl<MatchInfo> implements IMatchInfoDao
{
	@Override
	public ReturnInfo checkParams(MatchInfo matchInfo)
	{
		if (matchInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "MatchInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { matchInfo.getUpdateDateLong() + "", matchInfo.getAddDateLong() + "", matchInfo.getStatus() + "", matchInfo.getSortIndex() + "", matchInfo.getLeaderProfitStatus() + "",
				matchInfo.getHalfProfitStatus() + "", matchInfo.getFinalResultStatus() + "", matchInfo.getHalfResultStatus() + "", matchInfo.getFinalProfitStatus() + "", matchInfo.getMatchDate() + "",
				matchInfo.getAwayTeam(), matchInfo.getMatchName(), matchInfo.getPhaseId(), matchInfo.getTimeEndsale() + "", matchInfo.getHomeTeamId(), matchInfo.getAwayTeamId(),
				matchInfo.getHomeTeam() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "sortIndex不能为空！", "leaderProfitStatus不能为空！", "halfProfitStatus不能为空！", "finalResultStatus不能为空！",
				"halfResultStatus不能为空！", "finalProfitStatus不能为空！", "matchDate不能为空！", "awayTeam不能为空！", "matchName不能为空！", "phaseId不能为空！", "timeEndsale不能为空！", "homeTeamId不能为空！", "awayTeamId不能为空！",
				"homeTeam不能为空！" });
		return returnInfo;
	}

	@Override
	public MatchInfo add(MatchInfo matchInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(matchInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(matchInfo);
	}

	@Override
	public MatchInfo update(MatchInfo matchInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(matchInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(matchInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(MatchInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteMatchInfoByPhaseId(String phaseId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { phaseId }, new String[] { "phaseId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		MatchInfo matchInfo = getMatchInfoByPhaseId(phaseId);
		if (matchInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "MatchInfo不存在！");
		}
		delete(matchInfo);
	}

	@Override
	public MatchInfo getMatchInfoByPhaseId(String phaseId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { phaseId }, new String[] { "phaseId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", MatchInfo.class.getSimpleName(), IMatchInfoDao.PHASE_ID, IMatchInfoDao.PHASE_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IMatchInfoDao.PHASE_ID, phaseId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByPhaseId(String phaseId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMatchInfoDao.PHASE_ID, phaseId);
		return updateValue(MatchInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteMatchInfoByOutId(String outId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { outId }, new String[] { "outId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		MatchInfo matchInfo = getMatchInfoByOutId(outId);
		if (matchInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "MatchInfo不存在！");
		}
		delete(matchInfo);
	}

	@Override
	public MatchInfo getMatchInfoByOutId(String outId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { outId }, new String[] { "outId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", MatchInfo.class.getSimpleName(), IMatchInfoDao.OUT_ID, IMatchInfoDao.OUT_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IMatchInfoDao.OUT_ID, outId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByOutId(String outId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMatchInfoDao.OUT_ID, outId);
		return updateValue(MatchInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
