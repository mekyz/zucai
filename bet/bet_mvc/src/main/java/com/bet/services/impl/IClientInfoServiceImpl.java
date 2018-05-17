package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IClientInfoDao;
import com.bet.enums.PlatformType;
import com.bet.orms.ClientInfo;
import com.bet.services.IClientInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("clientInfoService")
public class IClientInfoServiceImpl implements IClientInfoService
{
	@Autowired
	private IClientInfoDao clientInfoDao;

	@Override
	public String genId()
	{
		return clientInfoDao.genId();
	}

	@Override
	public ClientInfo addClientInfo(ClientInfo clientInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (clientInfo.getAddDateLong() < 1)
		{
			clientInfo.setAddDateLong(current);
		}
		if (clientInfo.getUpdateDateLong() < 1)
		{
			clientInfo.setUpdateDateLong(current);
		}
		return clientInfoDao.add(clientInfo);
	}

	@Override
	public ClientInfo updateClientInfo(ClientInfo clientInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (clientInfo.getUpdateDateLong() < 1)
		{
			clientInfo.setUpdateDateLong(current);
		}
		return clientInfoDao.update(clientInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return clientInfoDao.updateValue(ClientInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteClientInfo(ClientInfo clientInfo) throws HibernateJsonResultException
	{
		clientInfoDao.delete(clientInfo);
	}

	@Override
	public void deleteClientInfoById(int id) throws HibernateJsonResultException
	{
		clientInfoDao.deleteClientInfoById(id);
	}

	@Override
	public ClientInfo getClientInfoById(int id) throws HibernateJsonResultException
	{
		return clientInfoDao.getClientInfoById(id);
	}

	@Override
	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IClientInfoDao.ID, id);
		return clientInfoDao.updateValue(ClientInfo.class, valueMap, whereMap) > 0;
	}

	public List<ClientInfo> getClientInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return clientInfoDao.getList(ClientInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<ClientInfo> getClientInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return clientInfoDao.getList(ClientInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getClientInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return clientInfoDao.getListCount(ClientInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public ClientInfo getLastAndroidClientInfo(String agentId) throws HibernateJsonResultException
	{
		String whereClause = String.format("where %s= :%s", IClientInfoDao.PLATFORM, IClientInfoDao.PLATFORM);
		Map<String, Object> params = new HashMap<>();
		params.put(IClientInfoDao.PLATFORM, PlatformType.ANDROID.getType());
		if (!StringTools.isNull(agentId))
		{
			whereClause = String.format("%s and %s = :%s ", whereClause, IClientInfoDao.AGENT_ID, IClientInfoDao.AGENT_ID);
			params.put(IClientInfoDao.AGENT_ID, agentId);
		}
		List<ClientInfo> list = clientInfoDao.getList(ClientInfo.class, whereClause, params, 0, 1, null, null);
		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}

	@Override
	public ClientInfo getLastIosClientInfo(String agentId) throws HibernateJsonResultException
	{
		String whereClause = String.format("where %s= :%s", IClientInfoDao.PLATFORM, IClientInfoDao.PLATFORM);
		Map<String, Object> params = new HashMap<>();
		params.put(IClientInfoDao.PLATFORM, PlatformType.IOS.getType());
		if (!StringTools.isNull(agentId))
		{
			whereClause = String.format("%s and %s = :%s ", whereClause, IClientInfoDao.AGENT_ID, IClientInfoDao.AGENT_ID);
			params.put(IClientInfoDao.AGENT_ID, agentId);
		}
		List<ClientInfo> list = clientInfoDao.getList(ClientInfo.class, whereClause, params, 0, 1, null, null);
		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}
}
