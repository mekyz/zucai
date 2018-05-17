package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IClientBugInfoDao;
import com.bet.orms.ClientBugInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("clientBugInfoDao")
public class IClientBugInfoDaoMySqlImpl extends IAbstractDaoImpl<ClientBugInfo> implements IClientBugInfoDao
{
	@Override
	public ReturnInfo checkParams(ClientBugInfo clientBugInfo)
	{
		if (clientBugInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "ClientBugInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { clientBugInfo.getContent(), clientBugInfo.getAddDateLong() + "", clientBugInfo.getStatus() + "", clientBugInfo.getDeviceName(), clientBugInfo.getVersionCode() + "",
				clientBugInfo.getSysVersion(), clientBugInfo.getPlatform() },
			new String[] { "content不能为空！", "addDateLong不能为空！", "status不能为空！", "deviceName不能为空！", "versionCode不能为空！", "sysVersion不能为空！", "platform不能为空！" });
		return returnInfo;
	}

	@Override
	public ClientBugInfo add(ClientBugInfo clientBugInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(clientBugInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(clientBugInfo);
	}

	@Override
	public ClientBugInfo update(ClientBugInfo clientBugInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(clientBugInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(clientBugInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(ClientBugInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteClientBugInfoById(int id) throws HibernateJsonResultException
	{
		ClientBugInfo clientBugInfo = getClientBugInfoById(id);
		if (clientBugInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "ClientBugInfo不存在！");
		}
		delete(clientBugInfo);
	}

	@Override
	public ClientBugInfo getClientBugInfoById(int id) throws HibernateJsonResultException
	{
		String hql = String.format("from %s where %s = :%s", ClientBugInfo.class.getSimpleName(), IClientBugInfoDao.ID, IClientBugInfoDao.ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IClientBugInfoDao.ID, id);
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
		whereMap.put(IClientBugInfoDao.ID, id);
		return updateValue(ClientBugInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
