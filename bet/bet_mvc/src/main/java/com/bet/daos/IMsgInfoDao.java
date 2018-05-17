package com.bet.daos;

import java.util.Map;

import com.bet.orms.MsgInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IMsgInfoDao extends IAbstractDao<MsgInfo>
{
	public static final String ID = "id";
	public static final String MSG_ID = "msgId";
	public static final String SORT_ID = "sortId";
	public static final String TITLE = "title";
	public static final String USER_ID = "userId";
	public static final String SHORT_CONTENT = "shortContent";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteMsgInfoByMsgId(String msgId) throws HibernateJsonResultException;

	public MsgInfo getMsgInfoByMsgId(String msgId) throws HibernateJsonResultException;

	public long updateValueByMsgId(String msgId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
