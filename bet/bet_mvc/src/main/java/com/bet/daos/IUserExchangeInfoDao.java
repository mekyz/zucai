package com.bet.daos;

import java.util.Map;

import com.bet.orms.UserExchangeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IUserExchangeInfoDao extends IAbstractDao<UserExchangeInfo>
{
	public static final String ID = "id";
	public static final String EXCHANGE_ID = "exchangeId";
	public static final String USER_ID = "userId";
	public static final String RECEIVE_USER_ID = "receiveUserId";
	public static final String MONEY = "money";
	public static final String USER_MONEY = "userMoney";
	public static final String FEE = "fee";
	public static final String PAY_REMARK = "payRemark";
	public static final String STATUS = "status";
	public static final String PAY_DATE_LONG = "payDateLong";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteUserExchangeInfoByExchangeId(String exchangeId) throws HibernateJsonResultException;

	public UserExchangeInfo getUserExchangeInfoByExchangeId(String exchangeId) throws HibernateJsonResultException;

	public long updateValueByExchangeId(String exchangeId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
