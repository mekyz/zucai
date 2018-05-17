package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IMatchProfitInfoDao;
import com.bet.orms.MatchProfitInfo;
import com.bet.services.IMatchProfitInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("matchProfitInfoService")
public class IMatchProfitInfoServiceImpl implements IMatchProfitInfoService
{
	@Autowired
	private IMatchProfitInfoDao matchProfitInfoDao;

	@Override
	public String genId()
	{
		return matchProfitInfoDao.genId();
	}

	@Override
	public MatchProfitInfo addMatchProfitInfo(MatchProfitInfo matchProfitInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (matchProfitInfo.getAddDateLong() < 1)
		{
			matchProfitInfo.setAddDateLong(current);
		}
		if (matchProfitInfo.getUpdateDateLong() < 1)
		{
			matchProfitInfo.setUpdateDateLong(current);
		}
		return matchProfitInfoDao.add(matchProfitInfo);
	}

	@Override
	public MatchProfitInfo updateMatchProfitInfo(MatchProfitInfo matchProfitInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (matchProfitInfo.getUpdateDateLong() < 1)
		{
			matchProfitInfo.setUpdateDateLong(current);
		}
		return matchProfitInfoDao.update(matchProfitInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return matchProfitInfoDao.updateValue(MatchProfitInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteMatchProfitInfo(MatchProfitInfo matchProfitInfo) throws HibernateJsonResultException
	{
		matchProfitInfoDao.delete(matchProfitInfo);
	}

	@Override
	public void deleteMatchProfitInfoByProfitId(String profitId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { profitId }, new String[] { "profitId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		matchProfitInfoDao.deleteMatchProfitInfoByProfitId(profitId);
	}

	@Override
	public MatchProfitInfo getMatchProfitInfoByProfitId(String profitId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { profitId }, new String[] { "profitId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return matchProfitInfoDao.getMatchProfitInfoByProfitId(profitId);
	}

	@Override
	public boolean updateValueByProfitId(String profitId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMatchProfitInfoDao.PROFIT_ID, profitId);
		return matchProfitInfoDao.updateValue(MatchProfitInfo.class, valueMap, whereMap) > 0;
	}

	public List<MatchProfitInfo> getMatchProfitInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return matchProfitInfoDao.getList(MatchProfitInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<MatchProfitInfo> getMatchProfitInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return matchProfitInfoDao.getList(MatchProfitInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getMatchProfitInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return matchProfitInfoDao.getListCount(MatchProfitInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public boolean updateSaledAmount(String profitId, long amount) throws HibernateJsonResultException
	{
		return matchProfitInfoDao.updateSaledAmount(profitId, amount);
	}

	@Override
	public boolean checkAndUpdateSaledAmount(String profitId) throws HibernateJsonResultException
	{
		long amount = 0;
		return matchProfitInfoDao.updateSaledAmount(profitId, amount);
	}
}
