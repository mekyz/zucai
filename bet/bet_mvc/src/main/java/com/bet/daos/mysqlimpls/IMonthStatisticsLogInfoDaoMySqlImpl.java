package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IMonthStatisticsLogInfoDao;
import com.bet.orms.MonthStatisticsLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("monthStatisticsLogInfoDao")
public class IMonthStatisticsLogInfoDaoMySqlImpl extends IAbstractDaoImpl<MonthStatisticsLogInfo> implements IMonthStatisticsLogInfoDao
{
	@Override
	public ReturnInfo checkParams(MonthStatisticsLogInfo monthStatisticsLogInfo)
	{
		if (monthStatisticsLogInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "MonthStatisticsLogInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { monthStatisticsLogInfo.getUpdateDateLong() + "", monthStatisticsLogInfo.getAddDateLong() + "", monthStatisticsLogInfo.getStatus() + "",
				monthStatisticsLogInfo.getBenefitBonusMoney() + "", monthStatisticsLogInfo.getSameLevel1BonusMoney() + "", monthStatisticsLogInfo.getSameLevel2BonusMoney() + "",
				monthStatisticsLogInfo.getShareBonusMoney() + "", monthStatisticsLogInfo.getAgentBonusMoney() + "", monthStatisticsLogInfo.getCountBonusMoney() + "",
				monthStatisticsLogInfo.getFinalBonusMoney() + "", monthStatisticsLogInfo.getHalfBonusMoney() + "", monthStatisticsLogInfo.getUserWithdrawMoney() + "",
				monthStatisticsLogInfo.getUserRechargeMoney() + "", monthStatisticsLogInfo.getDayId(), monthStatisticsLogInfo.getFinalWinMoney() + "", monthStatisticsLogInfo.getCountBetMoney() + "",
				monthStatisticsLogInfo.getCountWinMoney() + "", monthStatisticsLogInfo.getHalfBetMoney() + "", monthStatisticsLogInfo.getTeamMoney() + "",
				monthStatisticsLogInfo.getUserBetMoney() + "", monthStatisticsLogInfo.getFinalBetMoney() + "", monthStatisticsLogInfo.getHalfWinMoney() + "",
				monthStatisticsLogInfo.getCompanyMoney() + "" },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "benefitBonusMoney不能为空！", "sameLevel1BonusMoney不能为空！", "sameLevel2BonusMoney不能为空！", "shareBonusMoney不能为空！",
				"agentBonusMoney不能为空！", "countBonusMoney不能为空！", "finalBonusMoney不能为空！", "halfBonusMoney不能为空！", "userWithdrawMoney不能为空！", "userRechargeMoney不能为空！", "dayId不能为空！", "finalWinMoney不能为空！",
				"countBetMoney不能为空！", "countWinMoney不能为空！", "halfBetMoney不能为空！", "teamMoney不能为空！", "userBetMoney不能为空！", "finalBetMoney不能为空！", "halfWinMoney不能为空！", "companyMoney不能为空！" });
		return returnInfo;
	}

	@Override
	public MonthStatisticsLogInfo add(MonthStatisticsLogInfo monthStatisticsLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(monthStatisticsLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(monthStatisticsLogInfo);
	}

	@Override
	public MonthStatisticsLogInfo update(MonthStatisticsLogInfo monthStatisticsLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(monthStatisticsLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(monthStatisticsLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(MonthStatisticsLogInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteMonthStatisticsLogInfoByDayId(String dayId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { dayId }, new String[] { "dayId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		MonthStatisticsLogInfo monthStatisticsLogInfo = getMonthStatisticsLogInfoByDayId(dayId);
		if (monthStatisticsLogInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "MonthStatisticsLogInfo不存在！");
		}
		delete(monthStatisticsLogInfo);
	}

	@Override
	public MonthStatisticsLogInfo getMonthStatisticsLogInfoByDayId(String dayId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { dayId }, new String[] { "dayId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", MonthStatisticsLogInfo.class.getSimpleName(), IMonthStatisticsLogInfoDao.DAY_ID, IMonthStatisticsLogInfoDao.DAY_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IMonthStatisticsLogInfoDao.DAY_ID, dayId);
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
		whereMap.put(IMonthStatisticsLogInfoDao.DAY_ID, dayId);
		return updateValue(MonthStatisticsLogInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
