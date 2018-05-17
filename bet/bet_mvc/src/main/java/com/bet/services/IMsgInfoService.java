package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.MsgInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IMsgInfoService
{
	public String genId();

	public MsgInfo addMsgInfo(MsgInfo msgInfo) throws HibernateJsonResultException;

	public MsgInfo updateMsgInfo(MsgInfo msgInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteMsgInfo(MsgInfo msgInfo) throws HibernateJsonResultException;

	public void deleteMsgInfoByMsgId(String msgId) throws HibernateJsonResultException;

	public MsgInfo getMsgInfoByMsgId(String msgId) throws HibernateJsonResultException;

	public boolean updateValueByMsgId(String msgId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<MsgInfo> getMsgInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getMsgInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
