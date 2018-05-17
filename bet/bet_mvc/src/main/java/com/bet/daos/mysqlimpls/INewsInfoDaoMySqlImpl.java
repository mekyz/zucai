package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.INewsInfoDao;
import com.bet.orms.NewsInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("newsInfoDao")
public class INewsInfoDaoMySqlImpl extends IAbstractDaoImpl<NewsInfo> implements INewsInfoDao
{
	@Override
	public ReturnInfo checkParams(NewsInfo newsInfo)
	{
		if (newsInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "NewsInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { newsInfo.getContent(), newsInfo.getUpdateDateLong() + "", newsInfo.getAddDateLong() + "", newsInfo.getStatus() + "", newsInfo.getNewsId(), newsInfo.getShortContent(),
				newsInfo.getTitle(), newsInfo.getTopStatus() + "", newsInfo.getSortId(), newsInfo.getAuthor() },
			new String[] { "content不能为空！", "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "newsId不能为空！", "shortContent不能为空！", "title不能为空！", "topStatus不能为空！", "sortId不能为空！",
				"author不能为空！" });
		return returnInfo;
	}

	@Override
	public NewsInfo add(NewsInfo newsInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(newsInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(newsInfo);
	}

	@Override
	public NewsInfo update(NewsInfo newsInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(newsInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(newsInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(NewsInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteNewsInfoByNewsId(String newsId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { newsId }, new String[] { "newsId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		NewsInfo newsInfo = getNewsInfoByNewsId(newsId);
		if (newsInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "NewsInfo不存在！");
		}
		delete(newsInfo);
	}

	@Override
	public NewsInfo getNewsInfoByNewsId(String newsId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { newsId }, new String[] { "newsId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", NewsInfo.class.getSimpleName(), INewsInfoDao.NEWS_ID, INewsInfoDao.NEWS_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(INewsInfoDao.NEWS_ID, newsId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByNewsId(String newsId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(INewsInfoDao.NEWS_ID, newsId);
		return updateValue(NewsInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
