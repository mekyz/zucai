package com.bet.daos;

import java.util.Map;

import com.bet.orms.PayConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IPayConfigInfoDao extends IAbstractDao<PayConfigInfo>
{
	public static final String ID = "id";
	public static final String CONFIG_ID = "configId";
	public static final String PLATFORM = "platform";
	public static final String MCH_ID = "mchId";
	public static final String PAY_KEY = "payKey";
	public static final String SORT_INDEX = "sortIndex";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deletePayConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public PayConfigInfo getPayConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public long updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
