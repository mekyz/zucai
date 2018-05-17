package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.AdminPayLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IAdminPayLogInfoService
{
	public String genId();

	public AdminPayLogInfo addAdminPayLogInfo(AdminPayLogInfo adminPayLogInfo) throws HibernateJsonResultException;

	public AdminPayLogInfo updateAdminPayLogInfo(AdminPayLogInfo adminPayLogInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteAdminPayLogInfo(AdminPayLogInfo adminPayLogInfo) throws HibernateJsonResultException;

	public void deleteAdminPayLogInfoById(int id) throws HibernateJsonResultException;

	public AdminPayLogInfo getAdminPayLogInfoById(int id) throws HibernateJsonResultException;

	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<AdminPayLogInfo> getAdminPayLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getAdminPayLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
