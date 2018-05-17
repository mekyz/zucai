package com.bet.daos;

import java.util.Map;

import com.bet.orms.AdviceReplyInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IAdviceReplyInfoDao extends IAbstractDao<AdviceReplyInfo>
{
	public static final String ID = "id";
	public static final String REPLY_ID = "replyId";
	public static final String ADVICE_ID = "adviceId";
	public static final String CONTENT = "content";
	public static final String PARENT_ID = "parentId";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";

	public void deleteAdviceReplyInfoByReplyId(String replyId) throws HibernateJsonResultException;

	public AdviceReplyInfo getAdviceReplyInfoByReplyId(String replyId) throws HibernateJsonResultException;

	public long updateValueByReplyId(String replyId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
