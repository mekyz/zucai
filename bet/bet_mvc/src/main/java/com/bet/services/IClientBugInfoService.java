package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.ClientBugInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IClientBugInfoService
{
	public String genId();

	public ClientBugInfo addClientBugInfo(ClientBugInfo clientBugInfo) throws HibernateJsonResultException;

	public ClientBugInfo updateClientBugInfo(ClientBugInfo clientBugInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteClientBugInfo(ClientBugInfo clientBugInfo) throws HibernateJsonResultException;

	public void deleteClientBugInfoById(int id) throws HibernateJsonResultException;

	public ClientBugInfo getClientBugInfoById(int id) throws HibernateJsonResultException;

	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<ClientBugInfo> getClientBugInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getClientBugInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
