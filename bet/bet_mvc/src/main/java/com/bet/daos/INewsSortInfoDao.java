package com.bet.daos;

import java.util.Map;

import com.bet.orms.NewsSortInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface INewsSortInfoDao extends IAbstractDao<NewsSortInfo>
{
	public static final String ID = "id";
	public static final String SORT_ID = "sortId";
	public static final String NAME = "name";
	public static final String PARENT_ID = "parentId";
	public static final String LEVEL_ID = "levelId";
	public static final String STATUS = "status";

	public void deleteNewsSortInfoBySortId(String sortId) throws HibernateJsonResultException;

	public NewsSortInfo getNewsSortInfoBySortId(String sortId) throws HibernateJsonResultException;

	public long updateValueBySortId(String sortId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteNewsSortInfoByName(String name) throws HibernateJsonResultException;

	public NewsSortInfo getNewsSortInfoByName(String name) throws HibernateJsonResultException;

	public long updateValueByName(String name, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
