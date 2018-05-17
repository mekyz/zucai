package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.ClientInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IClientInfoService
{
	public String genId();

	public ClientInfo addClientInfo(ClientInfo clientInfo) throws HibernateJsonResultException;

	public ClientInfo updateClientInfo(ClientInfo clientInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteClientInfo(ClientInfo clientInfo) throws HibernateJsonResultException;

	public void deleteClientInfoById(int id) throws HibernateJsonResultException;

	public ClientInfo getClientInfoById(int id) throws HibernateJsonResultException;

	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<ClientInfo> getClientInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getClientInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	public ClientInfo getLastAndroidClientInfo(String agentId) throws HibernateJsonResultException;

	public ClientInfo getLastIosClientInfo(String agentId) throws HibernateJsonResultException;
}
