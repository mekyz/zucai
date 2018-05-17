package com.bet.daos;

import java.util.Map;

import com.bet.orms.MatchProfitInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IMatchProfitInfoDao extends IAbstractDao<MatchProfitInfo>
{
	public static final String ID = "id";
	public static final String PROFIT_ID = "profitId";
	public static final String PHASE_ID = "phaseId";
	public static final String MATCH_TYPE = "matchType";
	public static final String SCORE1 = "score1";
	public static final String SCORE2 = "score2";
	public static final String PROFIT_PERCENT = "profitPercent";
	public static final String AMOUNT = "amount";
	public static final String SALED_AMOUNT = "saledAmount";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteMatchProfitInfoByProfitId(String profitId) throws HibernateJsonResultException;

	public MatchProfitInfo getMatchProfitInfoByProfitId(String profitId) throws HibernateJsonResultException;

	public long updateValueByProfitId(String profitId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
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
}
