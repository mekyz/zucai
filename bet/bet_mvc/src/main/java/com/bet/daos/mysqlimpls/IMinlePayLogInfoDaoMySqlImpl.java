package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IMinlePayLogInfoDao;
import com.bet.orms.MinlePayLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("minlePayLogInfoDao")
public class IMinlePayLogInfoDaoMySqlImpl extends IAbstractDaoImpl<MinlePayLogInfo> implements IMinlePayLogInfoDao
{
	@Override
	public ReturnInfo checkParams(MinlePayLogInfo minlePayLogInfo)
	{
		if (minlePayLogInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "MinlePayLogInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { minlePayLogInfo.getType(), minlePayLogInfo.getUpdateDateLong() + "", minlePayLogInfo.getAddDateLong() + "", minlePayLogInfo.getStatus() + "", minlePayLogInfo.getLogId(),
				minlePayLogInfo.getMchId(), minlePayLogInfo.getReqSign(), minlePayLogInfo.getNotifyUrl(), minlePayLogInfo.getTotalFee(), minlePayLogInfo.getOutTradeNo() },
			new String[] { "type不能为空！", "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "logId不能为空！", "mchId不能为空！", "reqSign不能为空！", "notifyUrl不能为空！", "totalFee不能为空！", "outTradeNo不能为空！" });
		return returnInfo;
	}

	@Override
	public MinlePayLogInfo add(MinlePayLogInfo minlePayLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(minlePayLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(minlePayLogInfo);
	}

	@Override
	public MinlePayLogInfo update(MinlePayLogInfo minlePayLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(minlePayLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(minlePayLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(MinlePayLogInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteMinlePayLogInfoByLogId(String logId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { logId }, new String[] { "logId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		MinlePayLogInfo minlePayLogInfo = getMinlePayLogInfoByLogId(logId);
		if (minlePayLogInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "MinlePayLogInfo不存在！");
		}
		delete(minlePayLogInfo);
	}

	@Override
	public MinlePayLogInfo getMinlePayLogInfoByLogId(String logId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { logId }, new String[] { "logId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", MinlePayLogInfo.class.getSimpleName(), IMinlePayLogInfoDao.LOG_ID, IMinlePayLogInfoDao.LOG_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IMinlePayLogInfoDao.LOG_ID, logId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByLogId(String logId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMinlePayLogInfoDao.LOG_ID, logId);
		return updateValue(MinlePayLogInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
