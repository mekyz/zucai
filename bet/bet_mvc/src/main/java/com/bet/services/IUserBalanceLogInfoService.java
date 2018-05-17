package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserBalanceLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IUserBalanceLogInfoService
{
	public String genId();

	public UserBalanceLogInfo addUserBalanceLogInfo(UserBalanceLogInfo userBalanceLogInfo) throws HibernateJsonResultException;

	public UserBalanceLogInfo updateUserBalanceLogInfo(UserBalanceLogInfo userBalanceLogInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteUserBalanceLogInfo(UserBalanceLogInfo userBalanceLogInfo) throws HibernateJsonResultException;

	public void deleteUserBalanceLogInfoById(int id) throws HibernateJsonResultException;

	public UserBalanceLogInfo getUserBalanceLogInfoById(int id) throws HibernateJsonResultException;

	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<UserBalanceLogInfo> getUserBalanceLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getUserBalanceLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	public List<UserBalanceLogInfo> getUserBalanceLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException;

	public long getUserBalanceLogInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;

	/**
	 * 用户余额变动总金额
	 * 
	 * @param searchInfos
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public long getUserLogTotalMoney(byte moneyUnit, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;
}
