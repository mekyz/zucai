package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.INewsSortInfoDao;
import com.bet.orms.NewsSortInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("newsSortInfoDao")
public class INewsSortInfoDaoMySqlImpl extends IAbstractDaoImpl<NewsSortInfo> implements INewsSortInfoDao
{
	@Override
	public ReturnInfo checkParams(NewsSortInfo newsSortInfo)
	{
		if (newsSortInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "NewsSortInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { newsSortInfo.getName(), newsSortInfo.getStatus() + "", newsSortInfo.getSortId(), newsSortInfo.getLevelId() + "" },
			new String[] { "name不能为空！", "status不能为空！", "sortId不能为空！", "levelId不能为空！" });
		return returnInfo;
	}

	@Override
	public NewsSortInfo add(NewsSortInfo newsSortInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(newsSortInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(newsSortInfo);
	}

	@Override
	public NewsSortInfo update(NewsSortInfo newsSortInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(newsSortInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(newsSortInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(NewsSortInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteNewsSortInfoBySortId(String sortId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { sortId }, new String[] { "sortId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		NewsSortInfo newsSortInfo = getNewsSortInfoBySortId(sortId);
		if (newsSortInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "NewsSortInfo不存在！");
		}
		delete(newsSortInfo);
	}

	@Override
	public NewsSortInfo getNewsSortInfoBySortId(String sortId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { sortId }, new String[] { "sortId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", NewsSortInfo.class.getSimpleName(), INewsSortInfoDao.SORT_ID, INewsSortInfoDao.SORT_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(INewsSortInfoDao.SORT_ID, sortId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueBySortId(String sortId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(INewsSortInfoDao.SORT_ID, sortId);
		return updateValue(NewsSortInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteNewsSortInfoByName(String name) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { name }, new String[] { "name不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		NewsSortInfo newsSortInfo = getNewsSortInfoByName(name);
		if (newsSortInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "NewsSortInfo不存在！");
		}
		delete(newsSortInfo);
	}

	@Override
	public NewsSortInfo getNewsSortInfoByName(String name) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { name }, new String[] { "name不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", NewsSortInfo.class.getSimpleName(), INewsSortInfoDao.NAME, INewsSortInfoDao.NAME);
		Map<String, Object> params = new HashMap<>();
		params.put(INewsSortInfoDao.NAME, name);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByName(String name, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(INewsSortInfoDao.NAME, name);
		return updateValue(NewsSortInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
