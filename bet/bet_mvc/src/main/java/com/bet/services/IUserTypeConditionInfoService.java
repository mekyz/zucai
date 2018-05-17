package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserTypeConditionInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IUserTypeConditionInfoService
{
	public String genId();

	public UserTypeConditionInfo addUserTypeConditionInfo(UserTypeConditionInfo userTypeConditionInfo) throws HibernateJsonResultException;

	public UserTypeConditionInfo updateUserTypeConditionInfo(UserTypeConditionInfo userTypeConditionInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteUserTypeConditionInfo(UserTypeConditionInfo userTypeConditionInfo) throws HibernateJsonResultException;

	public void deleteUserTypeConditionInfoByUserType(byte userType) throws HibernateJsonResultException;

	public UserTypeConditionInfo getUserTypeConditionInfoByUserType(byte userType) throws HibernateJsonResultException;

	public boolean updateValueByUserType(byte userType, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<UserTypeConditionInfo> getUserTypeConditionInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getUserTypeConditionInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	/**
	 * 找出满足条件的最大级别
	 * 
	 * @param directCount
	 * @param teamCount
	 * @param searchInfos
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public UserTypeConditionInfo getMaxUserTypeConditionInfo(long directCount, long teamCount, long money, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
}
