package com.bet.daos;

import java.util.Map;

import com.bet.orms.SmsCodeVerifyInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface ISmsCodeVerifyInfoDao extends IAbstractDao<SmsCodeVerifyInfo>
{
	public static final String ID = "id";
	public static final String NUMBER = "number";
	public static final String VERIFY_TYPE = "verifyType";
	public static final String IP = "ip";
	public static final String CODE = "code";
	public static final String VERIFY_DATE_LONG = "verifyDateLong";
	public static final String STATUS = "status";

	public void deleteSmsCodeVerifyInfoById(int id) throws HibernateJsonResultException;

	public SmsCodeVerifyInfo getSmsCodeVerifyInfoById(int id) throws HibernateJsonResultException;

	public long updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
