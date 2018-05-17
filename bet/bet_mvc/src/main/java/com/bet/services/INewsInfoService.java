package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.NewsInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface INewsInfoService
{
	public String genId();

	public NewsInfo addNewsInfo(NewsInfo newsInfo) throws HibernateJsonResultException;

	public NewsInfo updateNewsInfo(NewsInfo newsInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteNewsInfo(NewsInfo newsInfo) throws HibernateJsonResultException;

	public void deleteNewsInfoByNewsId(String newsId) throws HibernateJsonResultException;

	public NewsInfo getNewsInfoByNewsId(String newsId) throws HibernateJsonResultException;

	public boolean updateValueByNewsId(String newsId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<NewsInfo> getNewsInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getNewsInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
