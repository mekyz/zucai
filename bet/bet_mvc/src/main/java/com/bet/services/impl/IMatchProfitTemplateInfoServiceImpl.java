package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IMatchProfitTemplateInfoDao;
import com.bet.orms.MatchProfitTemplateInfo;
import com.bet.services.IMatchProfitTemplateInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("matchProfitTemplateInfoService")
public class IMatchProfitTemplateInfoServiceImpl implements IMatchProfitTemplateInfoService
{
	@Autowired
	private IMatchProfitTemplateInfoDao matchProfitTemplateInfoDao;

	@Override
	public String genId()
	{
		return matchProfitTemplateInfoDao.genId();
	}

	@Override
	public MatchProfitTemplateInfo addMatchProfitTemplateInfo(MatchProfitTemplateInfo matchProfitTemplateInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (matchProfitTemplateInfo.getAddDateLong() < 1)
		{
			matchProfitTemplateInfo.setAddDateLong(current);
		}
		if (matchProfitTemplateInfo.getUpdateDateLong() < 1)
		{
			matchProfitTemplateInfo.setUpdateDateLong(current);
		}
		return matchProfitTemplateInfoDao.add(matchProfitTemplateInfo);
	}

	@Override
	public MatchProfitTemplateInfo updateMatchProfitTemplateInfo(MatchProfitTemplateInfo matchProfitTemplateInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (matchProfitTemplateInfo.getUpdateDateLong() < 1)
		{
			matchProfitTemplateInfo.setUpdateDateLong(current);
		}
		return matchProfitTemplateInfoDao.update(matchProfitTemplateInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return matchProfitTemplateInfoDao.updateValue(MatchProfitTemplateInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteMatchProfitTemplateInfo(MatchProfitTemplateInfo matchProfitTemplateInfo) throws HibernateJsonResultException
	{
		matchProfitTemplateInfoDao.delete(matchProfitTemplateInfo);
	}

	@Override
	public void deleteMatchProfitTemplateInfoByTemplateId(String templateId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { templateId }, new String[] { "templateId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		matchProfitTemplateInfoDao.deleteMatchProfitTemplateInfoByTemplateId(templateId);
	}

	@Override
	public MatchProfitTemplateInfo getMatchProfitTemplateInfoByTemplateId(String templateId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { templateId }, new String[] { "templateId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return matchProfitTemplateInfoDao.getMatchProfitTemplateInfoByTemplateId(templateId);
	}

	@Override
	public boolean updateValueByTemplateId(String templateId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMatchProfitTemplateInfoDao.TEMPLATE_ID, templateId);
		return matchProfitTemplateInfoDao.updateValue(MatchProfitTemplateInfo.class, valueMap, whereMap) > 0;
	}

	public List<MatchProfitTemplateInfo> getMatchProfitTemplateInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos,
		List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return matchProfitTemplateInfoDao.getList(MatchProfitTemplateInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<MatchProfitTemplateInfo> getMatchProfitTemplateInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return matchProfitTemplateInfoDao.getList(MatchProfitTemplateInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getMatchProfitTemplateInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return matchProfitTemplateInfoDao.getListCount(MatchProfitTemplateInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
