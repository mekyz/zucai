package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IClientBugInfoDao;
import com.bet.orms.ClientBugInfo;
import com.bet.services.IClientBugInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

@Repository("clientBugInfoService")
public class IClientBugInfoServiceImpl implements IClientBugInfoService
{
	@Autowired
	private IClientBugInfoDao clientBugInfoDao;

	@Override
	public String genId()
	{
		return clientBugInfoDao.genId();
	}

	@Override
	public ClientBugInfo addClientBugInfo(ClientBugInfo clientBugInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (clientBugInfo.getAddDateLong() < 1)
		{
			clientBugInfo.setAddDateLong(current);
		}
		return clientBugInfoDao.add(clientBugInfo);
	}

	@Override
	public ClientBugInfo updateClientBugInfo(ClientBugInfo clientBugInfo) throws HibernateJsonResultException
	{
		return clientBugInfoDao.update(clientBugInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return clientBugInfoDao.updateValue(ClientBugInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteClientBugInfo(ClientBugInfo clientBugInfo) throws HibernateJsonResultException
	{
		clientBugInfoDao.delete(clientBugInfo);
	}

	@Override
	public void deleteClientBugInfoById(int id) throws HibernateJsonResultException
	{
		clientBugInfoDao.deleteClientBugInfoById(id);
	}

	@Override
	public ClientBugInfo getClientBugInfoById(int id) throws HibernateJsonResultException
	{
		return clientBugInfoDao.getClientBugInfoById(id);
	}

	@Override
	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IClientBugInfoDao.ID, id);
		return clientBugInfoDao.updateValue(ClientBugInfo.class, valueMap, whereMap) > 0;
	}

	public List<ClientBugInfo> getClientBugInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return clientBugInfoDao.getList(ClientBugInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<ClientBugInfo> getClientBugInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return clientBugInfoDao.getList(ClientBugInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getClientBugInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return clientBugInfoDao.getListCount(ClientBugInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
