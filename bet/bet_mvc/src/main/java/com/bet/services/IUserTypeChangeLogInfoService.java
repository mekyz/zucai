package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserTypeChangeLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IUserTypeChangeLogInfoService
{
	public String genId();

	public UserTypeChangeLogInfo addUserTypeChangeLogInfo(UserTypeChangeLogInfo userTypeChangeLogInfo) throws HibernateJsonResultException;

	public UserTypeChangeLogInfo updateUserTypeChangeLogInfo(UserTypeChangeLogInfo userTypeChangeLogInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteUserTypeChangeLogInfo(UserTypeChangeLogInfo userTypeChangeLogInfo) throws HibernateJsonResultException;

	public void deleteUserTypeChangeLogInfoById(int id) throws HibernateJsonResultException;

	public UserTypeChangeLogInfo getUserTypeChangeLogInfoById(int id) throws HibernateJsonResultException;

	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<UserTypeChangeLogInfo> getUserTypeChangeLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getUserTypeChangeLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
