package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IMinlePayNotifyLogInfoDao;
import com.bet.orms.MinlePayNotifyLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("minlePayNotifyLogInfoDao")
public class IMinlePayNotifyLogInfoDaoMySqlImpl extends IAbstractDaoImpl<MinlePayNotifyLogInfo> implements IMinlePayNotifyLogInfoDao
{
	@Override
	public ReturnInfo checkParams(MinlePayNotifyLogInfo minlePayNotifyLogInfo)
	{
		if (minlePayNotifyLogInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "MinlePayNotifyLogInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { minlePayNotifyLogInfo.getUpdateDateLong() + "", minlePayNotifyLogInfo.getAddDateLong() + "", minlePayNotifyLogInfo.getStatus() + "", minlePayNotifyLogInfo.getNotifyId() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "notifyId不能为空！" });
		return returnInfo;
	}

	@Override
	public MinlePayNotifyLogInfo add(MinlePayNotifyLogInfo minlePayNotifyLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(minlePayNotifyLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(minlePayNotifyLogInfo);
	}

	@Override
	public MinlePayNotifyLogInfo update(MinlePayNotifyLogInfo minlePayNotifyLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(minlePayNotifyLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(minlePayNotifyLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(MinlePayNotifyLogInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteMinlePayNotifyLogInfoByNotifyId(String notifyId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { notifyId }, new String[] { "notifyId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		MinlePayNotifyLogInfo minlePayNotifyLogInfo = getMinlePayNotifyLogInfoByNotifyId(notifyId);
		if (minlePayNotifyLogInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "MinlePayNotifyLogInfo不存在！");
		}
		delete(minlePayNotifyLogInfo);
	}

	@Override
	public MinlePayNotifyLogInfo getMinlePayNotifyLogInfoByNotifyId(String notifyId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { notifyId }, new String[] { "notifyId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", MinlePayNotifyLogInfo.class.getSimpleName(), IMinlePayNotifyLogInfoDao.NOTIFY_ID, IMinlePayNotifyLogInfoDao.NOTIFY_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IMinlePayNotifyLogInfoDao.NOTIFY_ID, notifyId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByNotifyId(String notifyId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMinlePayNotifyLogInfoDao.NOTIFY_ID, notifyId);
		return updateValue(MinlePayNotifyLogInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
