package com.bet.daos;

import java.util.Map;

import com.bet.orms.SmsCodeConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface ISmsCodeConfigInfoDao extends IAbstractDao<SmsCodeConfigInfo>
{
	public static final String ID = "id";
	public static final String CONFIG_ID = "configId";
	public static final String NAME = "name";
	public static final String PLATFORM = "platform";
	public static final String URL = "url";
	public static final String TOPIC = "topic";
	public static final String APP_KEY = "appKey";
	public static final String APP_SECRET = "appSecret";
	public static final String SMS_TYPE = "smsType";
	public static final String SIGN_NAME = "signName";
	public static final String SMS_PARAMS = "smsParams";
	public static final String SMS_TEMPLATE_CODE = "smsTemplateCode";
	public static final String MNS_ENDPOINT = "mnsEndpoint";
	public static final String SORT_INDEX = "sortIndex";
	public static final String VALIDATE_SECONDS = "validateSeconds";
	public static final String STATUS = "status";
	public static final String REMARK = "remark";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteSmsCodeConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public SmsCodeConfigInfo getSmsCodeConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public long updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
