package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IClientInfoDao;
import com.bet.orms.ClientInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("clientInfoDao")
public class IClientInfoDaoMySqlImpl extends IAbstractDaoImpl<ClientInfo> implements IClientInfoDao
{
	@Override
	public ReturnInfo checkParams(ClientInfo clientInfo)
	{
		if (clientInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "ClientInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { clientInfo.getContent(), clientInfo.getUpdateDateLong() + "", clientInfo.getAddDateLong() + "", clientInfo.getStatus() + "", clientInfo.getVersionCode() + "",
				clientInfo.getUrl(), clientInfo.getPlatform(), clientInfo.getVersionName() },
			new String[] { "content不能为空！", "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "versionCode不能为空！", "url不能为空！", "platform不能为空！", "versionName不能为空！" });
		return returnInfo;
	}

	@Override
	public ClientInfo add(ClientInfo clientInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(clientInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(clientInfo);
	}

	@Override
	public ClientInfo update(ClientInfo clientInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(clientInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(clientInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(ClientInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteClientInfoById(int id) throws HibernateJsonResultException
	{
		ClientInfo clientInfo = getClientInfoById(id);
		if (clientInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "ClientInfo不存在！");
		}
		delete(clientInfo);
	}

	@Override
	public ClientInfo getClientInfoById(int id) throws HibernateJsonResultException
	{
		String hql = String.format("from %s where %s = :%s", ClientInfo.class.getSimpleName(), IClientInfoDao.ID, IClientInfoDao.ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IClientInfoDao.ID, id);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IClientInfoDao.ID, id);
		return updateValue(ClientInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
