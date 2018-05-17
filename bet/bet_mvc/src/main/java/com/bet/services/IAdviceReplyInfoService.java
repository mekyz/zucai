package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.AdviceReplyInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IAdviceReplyInfoService
{
	public String genId();

	public AdviceReplyInfo addAdviceReplyInfo(AdviceReplyInfo adviceReplyInfo) throws HibernateJsonResultException;

	public AdviceReplyInfo updateAdviceReplyInfo(AdviceReplyInfo adviceReplyInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteAdviceReplyInfo(AdviceReplyInfo adviceReplyInfo) throws HibernateJsonResultException;

	public void deleteAdviceReplyInfoByReplyId(String replyId) throws HibernateJsonResultException;

	public AdviceReplyInfo getAdviceReplyInfoByReplyId(String replyId) throws HibernateJsonResultException;

	public boolean updateValueByReplyId(String replyId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<AdviceReplyInfo> getAdviceReplyInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getAdviceReplyInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
