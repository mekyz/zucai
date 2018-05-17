package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IAdviceReplyInfoDao;
import com.bet.orms.AdviceReplyInfo;
import com.bet.services.IAdviceReplyInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("adviceReplyInfoService")
public class IAdviceReplyInfoServiceImpl implements IAdviceReplyInfoService
{
	@Autowired
	private IAdviceReplyInfoDao adviceReplyInfoDao;

	@Override
	public String genId()
	{
		return adviceReplyInfoDao.genId();
	}

	@Override
	public AdviceReplyInfo addAdviceReplyInfo(AdviceReplyInfo adviceReplyInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (adviceReplyInfo.getAddDateLong() < 1)
		{
			adviceReplyInfo.setAddDateLong(current);
		}
		return adviceReplyInfoDao.add(adviceReplyInfo);
	}

	@Override
	public AdviceReplyInfo updateAdviceReplyInfo(AdviceReplyInfo adviceReplyInfo) throws HibernateJsonResultException
	{
		return adviceReplyInfoDao.update(adviceReplyInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return adviceReplyInfoDao.updateValue(AdviceReplyInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteAdviceReplyInfo(AdviceReplyInfo adviceReplyInfo) throws HibernateJsonResultException
	{
		adviceReplyInfoDao.delete(adviceReplyInfo);
	}

	@Override
	public void deleteAdviceReplyInfoByReplyId(String replyId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { replyId }, new String[] { "replyId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		adviceReplyInfoDao.deleteAdviceReplyInfoByReplyId(replyId);
	}

	@Override
	public AdviceReplyInfo getAdviceReplyInfoByReplyId(String replyId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { replyId }, new String[] { "replyId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return adviceReplyInfoDao.getAdviceReplyInfoByReplyId(replyId);
	}

	@Override
	public boolean updateValueByReplyId(String replyId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IAdviceReplyInfoDao.REPLY_ID, replyId);
		return adviceReplyInfoDao.updateValue(AdviceReplyInfo.class, valueMap, whereMap) > 0;
	}

	public List<AdviceReplyInfo> getAdviceReplyInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return adviceReplyInfoDao.getList(AdviceReplyInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<AdviceReplyInfo> getAdviceReplyInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return adviceReplyInfoDao.getList(AdviceReplyInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getAdviceReplyInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return adviceReplyInfoDao.getListCount(AdviceReplyInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
