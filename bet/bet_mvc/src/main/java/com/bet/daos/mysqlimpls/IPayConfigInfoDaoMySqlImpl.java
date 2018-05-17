package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IPayConfigInfoDao;
import com.bet.orms.PayConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("payConfigInfoDao")
public class IPayConfigInfoDaoMySqlImpl extends IAbstractDaoImpl<PayConfigInfo> implements IPayConfigInfoDao
{
	@Override
	public ReturnInfo checkParams(PayConfigInfo payConfigInfo)
	{
		if (payConfigInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "PayConfigInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { payConfigInfo.getUpdateDateLong() + "", payConfigInfo.getAddDateLong() + "", payConfigInfo.getStatus() + "", payConfigInfo.getSortIndex() + "", payConfigInfo.getPlatform(),
				payConfigInfo.getConfigId(), payConfigInfo.getMchId(), payConfigInfo.getPayKey() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "sortIndex不能为空！", "platform不能为空！", "configId不能为空！", "mchId不能为空！", "payKey不能为空！" });
		return returnInfo;
	}

	@Override
	public PayConfigInfo add(PayConfigInfo payConfigInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(payConfigInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(payConfigInfo);
	}

	@Override
	public PayConfigInfo update(PayConfigInfo payConfigInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(payConfigInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(payConfigInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(PayConfigInfo.class, valueMap, whereMap);
	}

	@Override
	public void deletePayConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		PayConfigInfo payConfigInfo = getPayConfigInfoByConfigId(configId);
		if (payConfigInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "PayConfigInfo不存在！");
		}
		delete(payConfigInfo);
	}

	@Override
	public PayConfigInfo getPayConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", PayConfigInfo.class.getSimpleName(), IPayConfigInfoDao.CONFIG_ID, IPayConfigInfoDao.CONFIG_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IPayConfigInfoDao.CONFIG_ID, configId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IPayConfigInfoDao.CONFIG_ID, configId);
		return updateValue(PayConfigInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
