package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IMatchProfitInfoDao;
import com.bet.orms.MatchProfitInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("matchProfitInfoDao")
public class IMatchProfitInfoDaoMySqlImpl extends IAbstractDaoImpl<MatchProfitInfo> implements IMatchProfitInfoDao
{
	@Override
	public ReturnInfo checkParams(MatchProfitInfo matchProfitInfo)
	{
		if (matchProfitInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "MatchProfitInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { matchProfitInfo.getUpdateDateLong() + "", matchProfitInfo.getAddDateLong() + "", matchProfitInfo.getStatus() + "", matchProfitInfo.getPhaseId(),
				matchProfitInfo.getScore1() + "", matchProfitInfo.getScore2() + "", matchProfitInfo.getProfitId(), matchProfitInfo.getProfitPercent() + "", matchProfitInfo.getSaledAmount() + "",
				matchProfitInfo.getAmount() + "", matchProfitInfo.getMatchType() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "phaseId不能为空！", "score1不能为空！", "score2不能为空！", "profitId不能为空！", "profitPercent不能为空！", "saledAmount不能为空！",
				"amount不能为空！", "matchType不能为空！" });
		return returnInfo;
	}

	@Override
	public MatchProfitInfo add(MatchProfitInfo matchProfitInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(matchProfitInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(matchProfitInfo);
	}

	@Override
	public MatchProfitInfo update(MatchProfitInfo matchProfitInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(matchProfitInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(matchProfitInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(MatchProfitInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteMatchProfitInfoByProfitId(String profitId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { profitId }, new String[] { "profitId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		MatchProfitInfo matchProfitInfo = getMatchProfitInfoByProfitId(profitId);
		if (matchProfitInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "MatchProfitInfo不存在！");
		}
		delete(matchProfitInfo);
	}

	@Override
	public MatchProfitInfo getMatchProfitInfoByProfitId(String profitId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { profitId }, new String[] { "profitId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", MatchProfitInfo.class.getSimpleName(), IMatchProfitInfoDao.PROFIT_ID, IMatchProfitInfoDao.PROFIT_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IMatchProfitInfoDao.PROFIT_ID, profitId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByProfitId(String profitId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMatchProfitInfoDao.PROFIT_ID, profitId);
		return updateValue(MatchProfitInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */

	@Override
	public boolean updateSaledAmount(String profitId, long amount) throws HibernateJsonResultException
	{
		String sql = String.format("update %s set %s = %s + %d where %s = :%s and %s <= %s - %d", StringTools.getTableName(MatchProfitInfo.class),
			StringTools.getTableColumnName(IMatchProfitInfoDao.SALED_AMOUNT), StringTools.getTableColumnName(IMatchProfitInfoDao.SALED_AMOUNT), amount,
			StringTools.getTableColumnName(IMatchProfitInfoDao.PROFIT_ID), IMatchProfitInfoDao.PROFIT_ID, StringTools.getTableColumnName(IMatchProfitInfoDao.SALED_AMOUNT),
			StringTools.getTableColumnName(IMatchProfitInfoDao.AMOUNT), amount);
		Map<String, Object> params = new HashMap<>();
		params.put(IMatchProfitInfoDao.PROFIT_ID, profitId);
		int result = baseDao.sqlUpdate(sql, params);
		return result > 0;
	}
}
