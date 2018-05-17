package com.bet.daos;

import java.util.Map;

import com.bet.orms.MinlePayNotifyLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IMinlePayNotifyLogInfoDao extends IAbstractDao<MinlePayNotifyLogInfo>
{
	public static final String ID = "id";
	public static final String NOTIFY_ID = "notifyId";
	public static final String ERROR_CODE = "errorCode";
	public static final String ERROR_MSG = "errorMsg";
	public static final String SIGN = "sign";
	public static final String PAY_STATUS = "payStatus";
	public static final String OUT_TRADE_NO = "outTradeNo";
	public static final String ORDER_ID = "orderId";
	public static final String TOTAL_FEE = "totalFee";
	public static final String BODY = "body";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteMinlePayNotifyLogInfoByNotifyId(String notifyId) throws HibernateJsonResultException;

	public MinlePayNotifyLogInfo getMinlePayNotifyLogInfoByNotifyId(String notifyId) throws HibernateJsonResultException;

	public long updateValueByNotifyId(String notifyId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
