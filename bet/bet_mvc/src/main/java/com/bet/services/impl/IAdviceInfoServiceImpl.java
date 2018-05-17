package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IAdviceInfoDao;
import com.bet.orms.AdviceInfo;
import com.bet.services.IAdviceInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("adviceInfoService")
public class IAdviceInfoServiceImpl implements IAdviceInfoService
{
	@Autowired
	private IAdviceInfoDao adviceInfoDao;

	@Override
	public String genId()
	{
		return adviceInfoDao.genId();
	}

	@Override
	public AdviceInfo addAdviceInfo(AdviceInfo adviceInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (adviceInfo.getAddDateLong() < 1)
		{
			adviceInfo.setAddDateLong(current);
		}
		return adviceInfoDao.add(adviceInfo);
	}

	@Override
	public AdviceInfo updateAdviceInfo(AdviceInfo adviceInfo) throws HibernateJsonResultException
	{
		return adviceInfoDao.update(adviceInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return adviceInfoDao.updateValue(AdviceInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteAdviceInfo(AdviceInfo adviceInfo) throws HibernateJsonResultException
	{
		adviceInfoDao.delete(adviceInfo);
	}

	@Override
	public void deleteAdviceInfoByAdviceId(String adviceId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { adviceId }, new String[] { "adviceId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		adviceInfoDao.deleteAdviceInfoByAdviceId(adviceId);
	}

	@Override
	public AdviceInfo getAdviceInfoByAdviceId(String adviceId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { adviceId }, new String[] { "adviceId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return adviceInfoDao.getAdviceInfoByAdviceId(adviceId);
	}

	@Override
	public boolean updateValueByAdviceId(String adviceId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IAdviceInfoDao.ADVICE_ID, adviceId);
		return adviceInfoDao.updateValue(AdviceInfo.class, valueMap, whereMap) > 0;
	}

	public List<AdviceInfo> getAdviceInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return adviceInfoDao.getList(AdviceInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<AdviceInfo> getAdviceInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return adviceInfoDao.getList(AdviceInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getAdviceInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return adviceInfoDao.getListCount(AdviceInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
