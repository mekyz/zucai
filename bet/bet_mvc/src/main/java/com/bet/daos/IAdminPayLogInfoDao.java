package com.bet.daos;

import java.util.Map;

import com.bet.orms.AdminPayLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IAdminPayLogInfoDao extends IAbstractDao<AdminPayLogInfo>
{
	public static final String ID = "id";
	public static final String ADMIN_ID = "adminId";
	public static final String PAY_TYPE = "payType";
	public static final String PAY_ID = "payId";
	public static final String MONEY = "money";
	public static final String MONEY_UNIT = "moneyUnit";
	public static final String ADD_DATE_LONG = "addDateLong";

	public void deleteAdminPayLogInfoById(int id) throws HibernateJsonResultException;

	public AdminPayLogInfo getAdminPayLogInfoById(int id) throws HibernateJsonResultException;

	public long updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
