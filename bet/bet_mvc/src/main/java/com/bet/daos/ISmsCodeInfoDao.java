package com.bet.daos;

import java.util.Map;

import com.bet.orms.SmsCodeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface ISmsCodeInfoDao extends IAbstractDao<SmsCodeInfo>
{
	public static final String ID = "id";
	public static final String NUMBER = "number";
	public static final String VERIFY_TYPE = "verifyType";
	public static final String CODE = "code";
	public static final String START_DATE_LONG = "startDateLong";
	public static final String VALIDATE_TIME_LONG = "validateTimeLong";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";

	public void deleteSmsCodeInfoById(int id) throws HibernateJsonResultException;

	public SmsCodeInfo getSmsCodeInfoById(int id) throws HibernateJsonResultException;

	public long updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
