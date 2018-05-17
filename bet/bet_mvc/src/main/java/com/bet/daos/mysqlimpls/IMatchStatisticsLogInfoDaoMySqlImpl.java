package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IMatchStatisticsLogInfoDao;
import com.bet.orms.MatchStatisticsLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("matchStatisticsLogInfoDao")
public class IMatchStatisticsLogInfoDaoMySqlImpl extends IAbstractDaoImpl<MatchStatisticsLogInfo> implements IMatchStatisticsLogInfoDao
{
	@Override
	public ReturnInfo checkParams(MatchStatisticsLogInfo matchStatisticsLogInfo)
	{
		if (matchStatisticsLogInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "MatchStatisticsLogInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { matchStatisticsLogInfo.getUpdateDateLong() + "", matchStatisticsLogInfo.getAddDateLong() + "", matchStatisticsLogInfo.getStatus() + "",
				matchStatisticsLogInfo.getBenefitBonusMoney() + "", matchStatisticsLogInfo.getSameLevel1BonusMoney() + "", matchStatisticsLogInfo.getSameLevel2BonusMoney() + "",
				matchStatisticsLogInfo.getShareBonusMoney() + "", matchStatisticsLogInfo.getAgentBonusMoney() + "", matchStatisticsLogInfo.getCountBonusMoney() + "",
				matchStatisticsLogInfo.getFinalBonusMoney() + "", matchStatisticsLogInfo.getHalfBonusMoney() + "", matchStatisticsLogInfo.getPhaseId(), matchStatisticsLogInfo.getFinalWinMoney() + "",
				matchStatisticsLogInfo.getCountBetMoney() + "", matchStatisticsLogInfo.getCountWinMoney() + "", matchStatisticsLogInfo.getHalfBetMoney() + "",
				matchStatisticsLogInfo.getTeamMoney() + "", matchStatisticsLogInfo.getUserBetMoney() + "", matchStatisticsLogInfo.getFinalBetMoney() + "",
				matchStatisticsLogInfo.getHalfWinMoney() + "", matchStatisticsLogInfo.getCompanyMoney() + "" },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "benefitBonusMoney不能为空！", "sameLevel1BonusMoney不能为空！", "sameLevel2BonusMoney不能为空！", "shareBonusMoney不能为空！",
				"agentBonusMoney不能为空！", "countBonusMoney不能为空！", "finalBonusMoney不能为空！", "halfBonusMoney不能为空！", "phaseId不能为空！", "finalWinMoney不能为空！", "countBetMoney不能为空！", "countWinMoney不能为空！",
				"halfBetMoney不能为空！", "teamMoney不能为空！", "userBetMoney不能为空！", "finalBetMoney不能为空！", "halfWinMoney不能为空！", "companyMoney不能为空！" });
		return returnInfo;
	}

	@Override
	public MatchStatisticsLogInfo add(MatchStatisticsLogInfo matchStatisticsLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(matchStatisticsLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(matchStatisticsLogInfo);
	}

	@Override
	public MatchStatisticsLogInfo update(MatchStatisticsLogInfo matchStatisticsLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(matchStatisticsLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(matchStatisticsLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(MatchStatisticsLogInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteMatchStatisticsLogInfoByPhaseId(String phaseId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { phaseId }, new String[] { "phaseId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		MatchStatisticsLogInfo matchStatisticsLogInfo = getMatchStatisticsLogInfoByPhaseId(phaseId);
		if (matchStatisticsLogInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "MatchStatisticsLogInfo不存在！");
		}
		delete(matchStatisticsLogInfo);
	}

	@Override
	public MatchStatisticsLogInfo getMatchStatisticsLogInfoByPhaseId(String phaseId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { phaseId }, new String[] { "phaseId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", MatchStatisticsLogInfo.class.getSimpleName(), IMatchStatisticsLogInfoDao.PHASE_ID, IMatchStatisticsLogInfoDao.PHASE_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IMatchStatisticsLogInfoDao.PHASE_ID, phaseId);
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
		whereMap.put(IMatchStatisticsLogInfoDao.PHASE_ID, phaseId);
		return updateValue(MatchStatisticsLogInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
