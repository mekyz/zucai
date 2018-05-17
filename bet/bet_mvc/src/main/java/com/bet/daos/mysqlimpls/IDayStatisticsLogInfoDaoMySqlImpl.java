package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IDayStatisticsLogInfoDao;
import com.bet.orms.DayStatisticsLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("dayStatisticsLogInfoDao")
public class IDayStatisticsLogInfoDaoMySqlImpl extends IAbstractDaoImpl<DayStatisticsLogInfo> implements IDayStatisticsLogInfoDao
{
	@Override
	public ReturnInfo checkParams(DayStatisticsLogInfo dayStatisticsLogInfo)
	{
		if (dayStatisticsLogInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "DayStatisticsLogInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { dayStatisticsLogInfo.getUpdateDateLong() + "", dayStatisticsLogInfo.getAddDateLong() + "", dayStatisticsLogInfo.getStatus() + "",
				dayStatisticsLogInfo.getBenefitBonusMoney() + "", dayStatisticsLogInfo.getSameLevel1BonusMoney() + "", dayStatisticsLogInfo.getSameLevel2BonusMoney() + "",
				dayStatisticsLogInfo.getShareBonusMoney() + "", dayStatisticsLogInfo.getAgentBonusMoney() + "", dayStatisticsLogInfo.getCountBonusMoney() + "",
				dayStatisticsLogInfo.getFinalBonusMoney() + "", dayStatisticsLogInfo.getHalfBonusMoney() + "", dayStatisticsLogInfo.getUserWithdrawMoney() + "",
				dayStatisticsLogInfo.getUserRechargeMoney() + "", dayStatisticsLogInfo.getDayId(), dayStatisticsLogInfo.getFinalWinMoney() + "", dayStatisticsLogInfo.getCountBetMoney() + "",
				dayStatisticsLogInfo.getCountWinMoney() + "", dayStatisticsLogInfo.getHalfBetMoney() + "", dayStatisticsLogInfo.getTeamMoney() + "", dayStatisticsLogInfo.getUserBetMoney() + "",
				dayStatisticsLogInfo.getFinalBetMoney() + "", dayStatisticsLogInfo.getHalfWinMoney() + "", dayStatisticsLogInfo.getCompanyMoney() + "" },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "benefitBonusMoney不能为空！", "sameLevel1BonusMoney不能为空！", "sameLevel2BonusMoney不能为空！", "shareBonusMoney不能为空！",
				"agentBonusMoney不能为空！", "countBonusMoney不能为空！", "finalBonusMoney不能为空！", "halfBonusMoney不能为空！", "userWithdrawMoney不能为空！", "userRechargeMoney不能为空！", "dayId不能为空！", "finalWinMoney不能为空！",
				"countBetMoney不能为空！", "countWinMoney不能为空！", "halfBetMoney不能为空！", "teamMoney不能为空！", "userBetMoney不能为空！", "finalBetMoney不能为空！", "halfWinMoney不能为空！", "companyMoney不能为空！" });
		return returnInfo;
	}

	@Override
	public DayStatisticsLogInfo add(DayStatisticsLogInfo dayStatisticsLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(dayStatisticsLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(dayStatisticsLogInfo);
	}

	@Override
	public DayStatisticsLogInfo update(DayStatisticsLogInfo dayStatisticsLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(dayStatisticsLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(dayStatisticsLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(DayStatisticsLogInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteDayStatisticsLogInfoByDayId(String dayId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { dayId }, new String[] { "dayId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		DayStatisticsLogInfo dayStatisticsLogInfo = getDayStatisticsLogInfoByDayId(dayId);
		if (dayStatisticsLogInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "DayStatisticsLogInfo不存在！");
		}
		delete(dayStatisticsLogInfo);
	}

	@Override
	public DayStatisticsLogInfo getDayStatisticsLogInfoByDayId(String dayId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { dayId }, new String[] { "dayId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", DayStatisticsLogInfo.class.getSimpleName(), IDayStatisticsLogInfoDao.DAY_ID, IDayStatisticsLogInfoDao.DAY_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IDayStatisticsLogInfoDao.DAY_ID, dayId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByDayId(String dayId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IDayStatisticsLogInfoDao.DAY_ID, dayId);
		return updateValue(DayStatisticsLogInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
