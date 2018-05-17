package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserLoginLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IUserLoginLogInfoService
{
	public String genId();

	public UserLoginLogInfo addUserLoginLogInfo(UserLoginLogInfo userLoginLogInfo) throws HibernateJsonResultException;

	public UserLoginLogInfo updateUserLoginLogInfo(UserLoginLogInfo userLoginLogInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteUserLoginLogInfo(UserLoginLogInfo userLoginLogInfo) throws HibernateJsonResultException;

	public void deleteUserLoginLogInfoById(int id) throws HibernateJsonResultException;

	public UserLoginLogInfo getUserLoginLogInfoById(int id) throws HibernateJsonResultException;

	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<UserLoginLogInfo> getUserLoginLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getUserLoginLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
