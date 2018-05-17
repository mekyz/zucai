package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IAdviceTypeInfoDao;
import com.bet.orms.AdviceTypeInfo;
import com.bet.services.IAdviceTypeInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("adviceTypeInfoService")
public class IAdviceTypeInfoServiceImpl implements IAdviceTypeInfoService
{
	@Autowired
	private IAdviceTypeInfoDao adviceTypeInfoDao;

	@Override
	public String genId()
	{
		return adviceTypeInfoDao.genId();
	}

	@Override
	public AdviceTypeInfo addAdviceTypeInfo(AdviceTypeInfo adviceTypeInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (adviceTypeInfo.getAddDateLong() < 1)
		{
			adviceTypeInfo.setAddDateLong(current);
		}
		if (adviceTypeInfo.getUpdateDateLong() < 1)
		{
			adviceTypeInfo.setUpdateDateLong(current);
		}
		return adviceTypeInfoDao.add(adviceTypeInfo);
	}

	@Override
	public AdviceTypeInfo updateAdviceTypeInfo(AdviceTypeInfo adviceTypeInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (adviceTypeInfo.getUpdateDateLong() < 1)
		{
			adviceTypeInfo.setUpdateDateLong(current);
		}
		return adviceTypeInfoDao.update(adviceTypeInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return adviceTypeInfoDao.updateValue(AdviceTypeInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteAdviceTypeInfo(AdviceTypeInfo adviceTypeInfo) throws HibernateJsonResultException
	{
		adviceTypeInfoDao.delete(adviceTypeInfo);
	}

	@Override
	public void deleteAdviceTypeInfoByTypeId(String typeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { typeId }, new String[] { "typeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		adviceTypeInfoDao.deleteAdviceTypeInfoByTypeId(typeId);
	}

	@Override
	public AdviceTypeInfo getAdviceTypeInfoByTypeId(String typeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { typeId }, new String[] { "typeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return adviceTypeInfoDao.getAdviceTypeInfoByTypeId(typeId);
	}

	@Override
	public boolean updateValueByTypeId(String typeId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IAdviceTypeInfoDao.TYPE_ID, typeId);
		return adviceTypeInfoDao.updateValue(AdviceTypeInfo.class, valueMap, whereMap) > 0;
	}

	public List<AdviceTypeInfo> getAdviceTypeInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return adviceTypeInfoDao.getList(AdviceTypeInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<AdviceTypeInfo> getAdviceTypeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return adviceTypeInfoDao.getList(AdviceTypeInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getAdviceTypeInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return adviceTypeInfoDao.getListCount(AdviceTypeInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
