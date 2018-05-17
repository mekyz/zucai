package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.INewsInfoDao;
import com.bet.orms.NewsInfo;
import com.bet.services.INewsInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("newsInfoService")
public class INewsInfoServiceImpl implements INewsInfoService
{
	@Autowired
	private INewsInfoDao newsInfoDao;

	@Override
	public String genId()
	{
		return newsInfoDao.genId();
	}

	@Override
	public NewsInfo addNewsInfo(NewsInfo newsInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (newsInfo.getAddDateLong() < 1)
		{
			newsInfo.setAddDateLong(current);
		}
		if (newsInfo.getUpdateDateLong() < 1)
		{
			newsInfo.setUpdateDateLong(current);
		}
		return newsInfoDao.add(newsInfo);
	}

	@Override
	public NewsInfo updateNewsInfo(NewsInfo newsInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (newsInfo.getUpdateDateLong() < 1)
		{
			newsInfo.setUpdateDateLong(current);
		}
		return newsInfoDao.update(newsInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return newsInfoDao.updateValue(NewsInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteNewsInfo(NewsInfo newsInfo) throws HibernateJsonResultException
	{
		newsInfoDao.delete(newsInfo);
	}

	@Override
	public void deleteNewsInfoByNewsId(String newsId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { newsId }, new String[] { "newsId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		newsInfoDao.deleteNewsInfoByNewsId(newsId);
	}

	@Override
	public NewsInfo getNewsInfoByNewsId(String newsId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { newsId }, new String[] { "newsId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return newsInfoDao.getNewsInfoByNewsId(newsId);
	}

	@Override
	public boolean updateValueByNewsId(String newsId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(INewsInfoDao.NEWS_ID, newsId);
		return newsInfoDao.updateValue(NewsInfo.class, valueMap, whereMap) > 0;
	}

	public List<NewsInfo> getNewsInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return newsInfoDao.getList(NewsInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<NewsInfo> getNewsInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return newsInfoDao.getList(NewsInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getNewsInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return newsInfoDao.getListCount(NewsInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
