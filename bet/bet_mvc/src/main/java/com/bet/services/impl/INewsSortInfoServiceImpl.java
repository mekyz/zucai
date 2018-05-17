package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.INewsSortInfoDao;
import com.bet.orms.NewsSortInfo;
import com.bet.services.INewsSortInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("newsSortInfoService")
public class INewsSortInfoServiceImpl implements INewsSortInfoService
{
	@Autowired
	private INewsSortInfoDao newsSortInfoDao;

	@Override
	public String genId()
	{
		return newsSortInfoDao.genId();
	}

	@Override
	public NewsSortInfo addNewsSortInfo(NewsSortInfo newsSortInfo) throws HibernateJsonResultException
	{
		return newsSortInfoDao.add(newsSortInfo);
	}

	@Override
	public NewsSortInfo updateNewsSortInfo(NewsSortInfo newsSortInfo) throws HibernateJsonResultException
	{
		return newsSortInfoDao.update(newsSortInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return newsSortInfoDao.updateValue(NewsSortInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteNewsSortInfo(NewsSortInfo newsSortInfo) throws HibernateJsonResultException
	{
		newsSortInfoDao.delete(newsSortInfo);
	}

	@Override
	public void deleteNewsSortInfoBySortId(String sortId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { sortId }, new String[] { "sortId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		newsSortInfoDao.deleteNewsSortInfoBySortId(sortId);
	}

	@Override
	public NewsSortInfo getNewsSortInfoBySortId(String sortId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { sortId }, new String[] { "sortId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return newsSortInfoDao.getNewsSortInfoBySortId(sortId);
	}

	@Override
	public boolean updateValueBySortId(String sortId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(INewsSortInfoDao.SORT_ID, sortId);
		return newsSortInfoDao.updateValue(NewsSortInfo.class, valueMap, whereMap) > 0;
	}

	@Override
	public void deleteNewsSortInfoByName(String name) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { name }, new String[] { "name不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		newsSortInfoDao.deleteNewsSortInfoByName(name);
	}

	@Override
	public NewsSortInfo getNewsSortInfoByName(String name) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { name }, new String[] { "name不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return newsSortInfoDao.getNewsSortInfoByName(name);
	}

	@Override
	public boolean updateValueByName(String name, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(INewsSortInfoDao.NAME, name);
		return newsSortInfoDao.updateValue(NewsSortInfo.class, valueMap, whereMap) > 0;
	}

	public List<NewsSortInfo> getNewsSortInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return newsSortInfoDao.getList(NewsSortInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<NewsSortInfo> getNewsSortInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return newsSortInfoDao.getList(NewsSortInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getNewsSortInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return newsSortInfoDao.getListCount(NewsSortInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
