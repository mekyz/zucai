package com.bet.daos;

import java.util.Map;

import com.bet.orms.NewsInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface INewsInfoDao extends IAbstractDao<NewsInfo>
{
	public static final String ID = "id";
	public static final String NEWS_ID = "newsId";
	public static final String SORT_ID = "sortId";
	public static final String TITLE = "title";
	public static final String AUTHOR = "author";
	public static final String SHORT_CONTENT = "shortContent";
	public static final String CONTENT = "content";
	public static final String TOP_STATUS = "topStatus";
	public static final String STATUS = "status";
	public static final String VALIDE_DATE_LONG = "valideDateLong";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteNewsInfoByNewsId(String newsId) throws HibernateJsonResultException;

	public NewsInfo getNewsInfoByNewsId(String newsId) throws HibernateJsonResultException;

	public long updateValueByNewsId(String newsId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
