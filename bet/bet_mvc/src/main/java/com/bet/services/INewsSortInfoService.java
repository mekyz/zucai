package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.NewsSortInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface INewsSortInfoService
{
	public String genId();

	public NewsSortInfo addNewsSortInfo(NewsSortInfo newsSortInfo) throws HibernateJsonResultException;

	public NewsSortInfo updateNewsSortInfo(NewsSortInfo newsSortInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteNewsSortInfo(NewsSortInfo newsSortInfo) throws HibernateJsonResultException;

	public void deleteNewsSortInfoBySortId(String sortId) throws HibernateJsonResultException;

	public NewsSortInfo getNewsSortInfoBySortId(String sortId) throws HibernateJsonResultException;

	public boolean updateValueBySortId(String sortId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteNewsSortInfoByName(String name) throws HibernateJsonResultException;

	public NewsSortInfo getNewsSortInfoByName(String name) throws HibernateJsonResultException;

	public boolean updateValueByName(String name, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<NewsSortInfo> getNewsSortInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getNewsSortInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
