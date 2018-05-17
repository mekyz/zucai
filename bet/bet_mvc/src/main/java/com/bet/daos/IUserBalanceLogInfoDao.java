package com.bet.daos;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserBalanceLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.daos.IAbstractDao;

public interface IUserBalanceLogInfoDao extends IAbstractDao<UserBalanceLogInfo>
{
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String MONEY = "money";
	public static final String MONEY_UNIT = "moneyUnit";
	public static final String LOG_TYPE = "logType";
	public static final String SERVER_ID = "serverId";
	public static final String RESERVE_ID1 = "reserveId1";
	public static final String RESERVE_ID2 = "reserveId2";
	public static final String RESERVE_ID3 = "reserveId3";
	public static final String REMARK = "remark";
	public static final String ADD_DATE_LONG = "addDateLong";

	public void deleteUserBalanceLogInfoById(int id) throws HibernateJsonResultException;

	public UserBalanceLogInfo getUserBalanceLogInfoById(int id) throws HibernateJsonResultException;

	public long updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);

	/* 自定义方法 */
	/**
	 * 用户余额变动总金额
	 * 
	 * @param whereClause
	 *            查询条件
	 * @param params
	 *            查询参数
	 * @param searchInfos
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public long getUserLogTotalMoney(String whereClause, Map<String, Object> params, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
}
