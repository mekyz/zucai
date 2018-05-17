package com.bet.daos;

import java.util.Map;

import com.bet.orms.MinlePayLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IMinlePayLogInfoDao extends IAbstractDao<MinlePayLogInfo>
{
	public static final String ID = "id";
	public static final String LOG_ID = "logId";
	public static final String MCH_ID = "mchId";
	public static final String REQ_SIGN = "reqSign";
	public static final String TYPE = "type";
	public static final String NOTIFY_URL = "notifyUrl";
	public static final String BACK_URL = "backUrl";
	public static final String CARD_TYPE = "cardType";
	public static final String OUT_TRADE_NO = "outTradeNo";
	public static final String TOTAL_FEE = "totalFee";
	public static final String BODY = "body";
	public static final String ERROR_CODE = "errorCode";
	public static final String ERROR_MSG = "errorMsg";
	public static final String RESP_SIGN = "respSign";
	public static final String OUT_TRADE_NO1 = "outTradeNo1";
	public static final String ORDER_ID = "orderId";
	public static final String TOTAL_FEE1 = "totalFee1";
	public static final String PAY_URL = "payUrl";
	public static final String QR_CODE = "qrCode";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteMinlePayLogInfoByLogId(String logId) throws HibernateJsonResultException;

	public MinlePayLogInfo getMinlePayLogInfoByLogId(String logId) throws HibernateJsonResultException;

	public long updateValueByLogId(String logId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
