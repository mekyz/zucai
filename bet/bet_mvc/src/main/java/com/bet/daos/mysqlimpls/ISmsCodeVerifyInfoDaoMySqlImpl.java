package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.ISmsCodeVerifyInfoDao;
import com.bet.orms.SmsCodeVerifyInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("smsCodeVerifyInfoDao")
public class ISmsCodeVerifyInfoDaoMySqlImpl extends IAbstractDaoImpl<SmsCodeVerifyInfo> implements ISmsCodeVerifyInfoDao
{
	@Override
	public ReturnInfo checkParams(SmsCodeVerifyInfo smsCodeVerifyInfo)
	{
		if (smsCodeVerifyInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "SmsCodeVerifyInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { smsCodeVerifyInfo.getNumber(), smsCodeVerifyInfo.getStatus() + "", smsCodeVerifyInfo.getIp(), smsCodeVerifyInfo.getCode(),
			smsCodeVerifyInfo.getVerifyType(), smsCodeVerifyInfo.getVerifyDateLong() + "" },
			new String[] { "number不能为空！", "status不能为空！", "ip不能为空！", "code不能为空！", "verifyType不能为空！", "verifyDateLong不能为空！" });
		return returnInfo;
	}

	@Override
	public SmsCodeVerifyInfo add(SmsCodeVerifyInfo smsCodeVerifyInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(smsCodeVerifyInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(smsCodeVerifyInfo);
	}

	@Override
	public SmsCodeVerifyInfo update(SmsCodeVerifyInfo smsCodeVerifyInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(smsCodeVerifyInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(smsCodeVerifyInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(SmsCodeVerifyInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteSmsCodeVerifyInfoById(int id) throws HibernateJsonResultException
	{
		SmsCodeVerifyInfo smsCodeVerifyInfo = getSmsCodeVerifyInfoById(id);
		if (smsCodeVerifyInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "SmsCodeVerifyInfo不存在！");
		}
		delete(smsCodeVerifyInfo);
	}

	@Override
	public SmsCodeVerifyInfo getSmsCodeVerifyInfoById(int id) throws HibernateJsonResultException
	{
		String hql = String.format("from %s where %s = :%s", SmsCodeVerifyInfo.class.getSimpleName(), ISmsCodeVerifyInfoDao.ID, ISmsCodeVerifyInfoDao.ID);
		Map<String, Object> params = new HashMap<>();
		params.put(ISmsCodeVerifyInfoDao.ID, id);
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
		whereMap.put(ISmsCodeVerifyInfoDao.ID, id);
		return updateValue(SmsCodeVerifyInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
