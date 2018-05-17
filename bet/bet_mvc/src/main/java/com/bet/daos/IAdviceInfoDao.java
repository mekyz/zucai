package com.bet.daos;

import java.util.Map;

import com.bet.orms.AdviceInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IAdviceInfoDao extends IAbstractDao<AdviceInfo>
{
	public static final String ID = "id";
	public static final String ADVICE_ID = "adviceId";
	public static final String PLATFORM = "platform";
	public static final String ADVICE_TYPE = "adviceType";
	public static final String USER_ID = "userId";
	public static final String NAME = "name";
	public static final String NUMBER = "number";
	public static final String EMAIL = "email";
	public static final String CONTENT = "content";
	public static final String READ_USER_ID = "readUserId";
	public static final String READ_STATUS = "readStatus";
	public static final String REPLY_USER_ID = "replyUserId";
	public static final String REPLY_STATUS = "replyStatus";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";

	public void deleteAdviceInfoByAdviceId(String adviceId) throws HibernateJsonResultException;

	public AdviceInfo getAdviceInfoByAdviceId(String adviceId) throws HibernateJsonResultException;

	public long updateValueByAdviceId(String adviceId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
