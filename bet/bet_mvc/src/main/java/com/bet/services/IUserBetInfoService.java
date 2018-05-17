package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserBetInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IUserBetInfoService
{
	public String genId();

	public UserBetInfo addUserBetInfo(UserBetInfo userBetInfo) throws HibernateJsonResultException;

	public UserBetInfo updateUserBetInfo(UserBetInfo userBetInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteUserBetInfo(UserBetInfo userBetInfo) throws HibernateJsonResultException;

	public void deleteUserBetInfoByBetId(String betId) throws HibernateJsonResultException;

	public UserBetInfo getUserBetInfoByBetId(String betId) throws HibernateJsonResultException;

	public boolean updateValueByBetId(String betId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<UserBetInfo> getUserBetInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getUserBetInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	/* 自定义方法 */
	public boolean updateProfitValueByBetId(String betId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public boolean updateTeamProfitValueByBetId(String betId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<UserBetInfo> getUserBetInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException;

	public long getUserBetInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;

	public long getSum(String colName, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;

	/**
	 * 取出指定级别的用户
	 * 
	 * @param start
	 * @param size
	 * @param orderInfos
	 * @param searchInfos
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public List<UserBetInfo> getUserListByUserType(byte userType, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getTeamUserTotalMoney(String userId, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
}
