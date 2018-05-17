package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IDayUserStatisticsLogInfoDao;
import com.bet.orms.DayUserStatisticsLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("dayUserStatisticsLogInfoDao")
public class IDayUserStatisticsLogInfoDaoMySqlImpl extends IAbstractDaoImpl<DayUserStatisticsLogInfo> implements IDayUserStatisticsLogInfoDao
{
	@Override
	public ReturnInfo checkParams(DayUserStatisticsLogInfo dayUserStatisticsLogInfo)
	{
		if (dayUserStatisticsLogInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "DayUserStatisticsLogInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { dayUserStatisticsLogInfo.getUpdateDateLong() + "", dayUserStatisticsLogInfo.getAddDateLong() + "", dayUserStatisticsLogInfo.getUserId(),
				dayUserStatisticsLogInfo.getStatus() + "", dayUserStatisticsLogInfo.getBenefitBonusMoney() + "", dayUserStatisticsLogInfo.getSameLevel1BonusMoney() + "",
				dayUserStatisticsLogInfo.getSameLevel2BonusMoney() + "", dayUserStatisticsLogInfo.getShareBonusMoney() + "", dayUserStatisticsLogInfo.getAgentBonusMoney() + "",
				dayUserStatisticsLogInfo.getCountBonusMoney() + "", dayUserStatisticsLogInfo.getFinalBonusMoney() + "", dayUserStatisticsLogInfo.getHalfBonusMoney() + "",
				dayUserStatisticsLogInfo.getUserWithdrawMoney() + "", dayUserStatisticsLogInfo.getUserRechargeMoney() + "", dayUserStatisticsLogInfo.getDayId(),
				dayUserStatisticsLogInfo.getTeamMoney() + "", dayUserStatisticsLogInfo.getUserBetMoney() + "", dayUserStatisticsLogInfo.getLogId() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "userId不能为空！", "status不能为空！", "benefitBonusMoney不能为空！", "sameLevel1BonusMoney不能为空！", "sameLevel2BonusMoney不能为空！",
				"shareBonusMoney不能为空！", "agentBonusMoney不能为空！", "countBonusMoney不能为空！", "finalBonusMoney不能为空！", "halfBonusMoney不能为空！", "userWithdrawMoney不能为空！", "userRechargeMoney不能为空！", "dayId不能为空！",
				"teamMoney不能为空！", "userBetMoney不能为空！", "logId不能为空！" });
		return returnInfo;
	}

	@Override
	public DayUserStatisticsLogInfo add(DayUserStatisticsLogInfo dayUserStatisticsLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(dayUserStatisticsLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(dayUserStatisticsLogInfo);
	}

	@Override
	public DayUserStatisticsLogInfo update(DayUserStatisticsLogInfo dayUserStatisticsLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(dayUserStatisticsLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(dayUserStatisticsLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(DayUserStatisticsLogInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteDayUserStatisticsLogInfoByLogId(String logId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { logId }, new String[] { "logId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		DayUserStatisticsLogInfo dayUserStatisticsLogInfo = getDayUserStatisticsLogInfoByLogId(logId);
		if (dayUserStatisticsLogInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "DayUserStatisticsLogInfo不存在！");
		}
		delete(dayUserStatisticsLogInfo);
	}

	@Override
	public DayUserStatisticsLogInfo getDayUserStatisticsLogInfoByLogId(String logId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { logId }, new String[] { "logId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", DayUserStatisticsLogInfo.class.getSimpleName(), IDayUserStatisticsLogInfoDao.LOG_ID, IDayUserStatisticsLogInfoDao.LOG_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IDayUserStatisticsLogInfoDao.LOG_ID, logId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByLogId(String logId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IDayUserStatisticsLogInfoDao.LOG_ID, logId);
		return updateValue(DayUserStatisticsLogInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
