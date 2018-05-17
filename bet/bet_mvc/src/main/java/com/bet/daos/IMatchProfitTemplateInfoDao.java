package com.bet.daos;

import java.util.Map;

import com.bet.orms.MatchProfitTemplateInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IMatchProfitTemplateInfoDao extends IAbstractDao<MatchProfitTemplateInfo>
{
	public static final String ID = "id";
	public static final String TEMPLATE_ID = "templateId";
	public static final String MATCH_TYPE = "matchType";
	public static final String SCORE1 = "score1";
	public static final String SCORE2 = "score2";
	public static final String PROFIT_PERCENT = "profitPercent";
	public static final String AMOUNT = "amount";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteMatchProfitTemplateInfoByTemplateId(String templateId) throws HibernateJsonResultException;

	public MatchProfitTemplateInfo getMatchProfitTemplateInfoByTemplateId(String templateId) throws HibernateJsonResultException;

	public long updateValueByTemplateId(String templateId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
