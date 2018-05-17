package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IMsgInfoDao;
import com.bet.orms.MsgInfo;
import com.bet.services.IMsgInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("msgInfoService")
public class IMsgInfoServiceImpl implements IMsgInfoService
{
	@Autowired
	private IMsgInfoDao msgInfoDao;

	@Override
	public String genId()
	{
		return msgInfoDao.genId();
	}

	@Override
	public MsgInfo addMsgInfo(MsgInfo msgInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (msgInfo.getAddDateLong() < 1)
		{
			msgInfo.setAddDateLong(current);
		}
		if (msgInfo.getUpdateDateLong() < 1)
		{
			msgInfo.setUpdateDateLong(current);
		}
		return msgInfoDao.add(msgInfo);
	}

	@Override
	public MsgInfo updateMsgInfo(MsgInfo msgInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (msgInfo.getUpdateDateLong() < 1)
		{
			msgInfo.setUpdateDateLong(current);
		}
		return msgInfoDao.update(msgInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return msgInfoDao.updateValue(MsgInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteMsgInfo(MsgInfo msgInfo) throws HibernateJsonResultException
	{
		msgInfoDao.delete(msgInfo);
	}

	@Override
	public void deleteMsgInfoByMsgId(String msgId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { msgId }, new String[] { "msgId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		msgInfoDao.deleteMsgInfoByMsgId(msgId);
	}

	@Override
	public MsgInfo getMsgInfoByMsgId(String msgId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { msgId }, new String[] { "msgId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return msgInfoDao.getMsgInfoByMsgId(msgId);
	}

	@Override
	public boolean updateValueByMsgId(String msgId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMsgInfoDao.MSG_ID, msgId);
		return msgInfoDao.updateValue(MsgInfo.class, valueMap, whereMap) > 0;
	}

	public List<MsgInfo> getMsgInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return msgInfoDao.getList(MsgInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<MsgInfo> getMsgInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return msgInfoDao.getList(MsgInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getMsgInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return msgInfoDao.getListCount(MsgInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
