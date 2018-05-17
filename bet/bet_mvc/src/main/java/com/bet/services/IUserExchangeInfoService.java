package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserExchangeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IUserExchangeInfoService
{
	public String genId();

	public UserExchangeInfo addUserExchangeInfo(UserExchangeInfo userExchangeInfo) throws HibernateJsonResultException;

	public UserExchangeInfo updateUserExchangeInfo(UserExchangeInfo userExchangeInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteUserExchangeInfo(UserExchangeInfo userExchangeInfo) throws HibernateJsonResultException;

	public void deleteUserExchangeInfoByExchangeId(String exchangeId) throws HibernateJsonResultException;

	public UserExchangeInfo getUserExchangeInfoByExchangeId(String exchangeId) throws HibernateJsonResultException;

	public boolean updateValueByExchangeId(String exchangeId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<UserExchangeInfo> getUserExchangeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getUserExchangeInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	public List<UserExchangeInfo> getUserExchangeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException;

	public long getUserExchangeInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;
}
