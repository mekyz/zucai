package com.bet.daos;

import java.util.Map;

import com.bet.orms.PayeeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IPayeeInfoDao extends IAbstractDao<PayeeInfo>
{
	public static final String ID = "id";
	public static final String PAYEE_ID = "payeeId";
	public static final String PAYEE_NAME = "payeeName";
	public static final String BANK_NAME = "bankName";
	public static final String BANK_CARD_ID = "bankCardId";
	public static final String PIC_URL = "picUrl";
	public static final String TYPE = "type";
	public static final String SORT_INDEX = "sortIndex";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deletePayeeInfoByPayeeId(String payeeId) throws HibernateJsonResultException;

	public PayeeInfo getPayeeInfoByPayeeId(String payeeId) throws HibernateJsonResultException;

	public long updateValueByPayeeId(String payeeId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
