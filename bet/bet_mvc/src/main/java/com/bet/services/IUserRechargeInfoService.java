package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserRechargeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IUserRechargeInfoService
{
	public String genId();

	public UserRechargeInfo addUserRechargeInfo(UserRechargeInfo userRechargeInfo) throws HibernateJsonResultException;

	public UserRechargeInfo updateUserRechargeInfo(UserRechargeInfo userRechargeInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteUserRechargeInfo(UserRechargeInfo userRechargeInfo) throws HibernateJsonResultException;

	public void deleteUserRechargeInfoByRechargeId(String rechargeId) throws HibernateJsonResultException;

	public UserRechargeInfo getUserRechargeInfoByRechargeId(String rechargeId) throws HibernateJsonResultException;

	public boolean updateValueByRechargeId(String rechargeId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<UserRechargeInfo> getUserRechargeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getUserRechargeInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	public List<UserRechargeInfo> getUserRechargeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException;

	public long getUserRechargeInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;

	public long getSum(String colName, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;

	public boolean updateStatusValueByRechargeId(String rechargeId, byte status, Map<String, Object> valueMap) throws HibernateJsonResultException;
}
