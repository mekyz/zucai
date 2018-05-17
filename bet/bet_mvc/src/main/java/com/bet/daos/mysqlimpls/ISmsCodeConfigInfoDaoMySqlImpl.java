package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.ISmsCodeConfigInfoDao;
import com.bet.orms.SmsCodeConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("smsCodeConfigInfoDao")
public class ISmsCodeConfigInfoDaoMySqlImpl extends IAbstractDaoImpl<SmsCodeConfigInfo> implements ISmsCodeConfigInfoDao
{
	@Override
	public ReturnInfo checkParams(SmsCodeConfigInfo smsCodeConfigInfo)
	{
		if (smsCodeConfigInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "SmsCodeConfigInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { smsCodeConfigInfo.getName(), smsCodeConfigInfo.getUpdateDateLong() + "", smsCodeConfigInfo.getAddDateLong() + "", smsCodeConfigInfo.getStatus() + "",
				smsCodeConfigInfo.getSortIndex() + "", smsCodeConfigInfo.getPlatform(), smsCodeConfigInfo.getConfigId(), smsCodeConfigInfo.getValidateSeconds() + "", smsCodeConfigInfo.getAppSecret(),
				smsCodeConfigInfo.getTopic(), smsCodeConfigInfo.getAppKey() },
			new String[] { "name不能为空！", "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "sortIndex不能为空！", "platform不能为空！", "configId不能为空！", "validateSeconds不能为空！", "appSecret不能为空！",
				"topic不能为空！", "appKey不能为空！" });
		return returnInfo;
	}

	@Override
	public SmsCodeConfigInfo add(SmsCodeConfigInfo smsCodeConfigInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(smsCodeConfigInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(smsCodeConfigInfo);
	}

	@Override
	public SmsCodeConfigInfo update(SmsCodeConfigInfo smsCodeConfigInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(smsCodeConfigInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(smsCodeConfigInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(SmsCodeConfigInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteSmsCodeConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		SmsCodeConfigInfo smsCodeConfigInfo = getSmsCodeConfigInfoByConfigId(configId);
		if (smsCodeConfigInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "SmsCodeConfigInfo不存在！");
		}
		delete(smsCodeConfigInfo);
	}

	@Override
	public SmsCodeConfigInfo getSmsCodeConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", SmsCodeConfigInfo.class.getSimpleName(), ISmsCodeConfigInfoDao.CONFIG_ID, ISmsCodeConfigInfoDao.CONFIG_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(ISmsCodeConfigInfoDao.CONFIG_ID, configId);
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
		whereMap.put(ISmsCodeConfigInfoDao.CONFIG_ID, configId);
		return updateValue(SmsCodeConfigInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
