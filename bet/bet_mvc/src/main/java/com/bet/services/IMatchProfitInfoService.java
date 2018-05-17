package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.MatchProfitInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IMatchProfitInfoService
{
	public String genId();

	public MatchProfitInfo addMatchProfitInfo(MatchProfitInfo matchProfitInfo) throws HibernateJsonResultException;

	public MatchProfitInfo updateMatchProfitInfo(MatchProfitInfo matchProfitInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteMatchProfitInfo(MatchProfitInfo matchProfitInfo) throws HibernateJsonResultException;

	public void deleteMatchProfitInfoByProfitId(String profitId) throws HibernateJsonResultException;

	public MatchProfitInfo getMatchProfitInfoByProfitId(String profitId) throws HibernateJsonResultException;

	public boolean updateValueByProfitId(String profitId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<MatchProfitInfo> getMatchProfitInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getMatchProfitInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	/**
	 * 更新已售数量
	 * 
	 * @param profitId
	 *            用户ID
	 * @param point
	 *            积分
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public boolean updateSaledAmount(String profitId, long amount) throws HibernateJsonResultException;

	public boolean checkAndUpdateSaledAmount(String profitId) throws HibernateJsonResultException;
}
