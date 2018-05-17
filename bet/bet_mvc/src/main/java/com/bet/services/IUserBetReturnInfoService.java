package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserBetReturnInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IUserBetReturnInfoService
{
	public String genId();

	public UserBetReturnInfo addUserBetReturnInfo(UserBetReturnInfo userBetReturnInfo) throws HibernateJsonResultException;

	public UserBetReturnInfo updateUserBetReturnInfo(UserBetReturnInfo userBetReturnInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteUserBetReturnInfo(UserBetReturnInfo userBetReturnInfo) throws HibernateJsonResultException;

	public void deleteUserBetReturnInfoByBetId(String betId) throws HibernateJsonResultException;

	public UserBetReturnInfo getUserBetReturnInfoByBetId(String betId) throws HibernateJsonResultException;

	public boolean updateValueByBetId(String betId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteUserBetReturnInfoByLogId(String logId) throws HibernateJsonResultException;

	public UserBetReturnInfo getUserBetReturnInfoByLogId(String logId) throws HibernateJsonResultException;

	public boolean updateValueByLogId(String logId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<UserBetReturnInfo> getUserBetReturnInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getUserBetReturnInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	public List<UserBetReturnInfo> getUserBetReturnInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException;

	public long getUserBetReturnInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;
}
