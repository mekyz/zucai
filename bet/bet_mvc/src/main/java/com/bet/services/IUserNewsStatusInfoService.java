package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserNewsStatusInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IUserNewsStatusInfoService
{
	public String genId();

	public UserNewsStatusInfo addUserNewsStatusInfo(UserNewsStatusInfo userNewsStatusInfo) throws HibernateJsonResultException;

	public UserNewsStatusInfo updateUserNewsStatusInfo(UserNewsStatusInfo userNewsStatusInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteUserNewsStatusInfo(UserNewsStatusInfo userNewsStatusInfo) throws HibernateJsonResultException;

	public void deleteUserNewsStatusInfoById(int id) throws HibernateJsonResultException;

	public UserNewsStatusInfo getUserNewsStatusInfoById(int id) throws HibernateJsonResultException;

	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<UserNewsStatusInfo> getUserNewsStatusInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getUserNewsStatusInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	public UserNewsStatusInfo getUserNewsStatusInfo(String userId, String newsId) throws HibernateJsonResultException;
}
