package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.ISmsCodeInfoDao;
import com.bet.orms.SmsCodeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("smsCodeInfoDao")
public class ISmsCodeInfoDaoMySqlImpl extends IAbstractDaoImpl<SmsCodeInfo> implements ISmsCodeInfoDao
{
	@Override
	public ReturnInfo checkParams(SmsCodeInfo smsCodeInfo)
	{
		if (smsCodeInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "SmsCodeInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { smsCodeInfo.getNumber(), smsCodeInfo.getAddDateLong() + "", smsCodeInfo.getStatus() + "", smsCodeInfo.getCode(),
			smsCodeInfo.getVerifyType(), smsCodeInfo.getValidateTimeLong() + "" },
			new String[] { "number不能为空！", "addDateLong不能为空！", "status不能为空！", "code不能为空！", "verifyType不能为空！", "validateTimeLong不能为空！" });
		return returnInfo;
	}

	@Override
	public SmsCodeInfo add(SmsCodeInfo smsCodeInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(smsCodeInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(smsCodeInfo);
	}

	@Override
	public SmsCodeInfo update(SmsCodeInfo smsCodeInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(smsCodeInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(smsCodeInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(SmsCodeInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteSmsCodeInfoById(int id) throws HibernateJsonResultException
	{
		SmsCodeInfo smsCodeInfo = getSmsCodeInfoById(id);
		if (smsCodeInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "SmsCodeInfo不存在！");
		}
		delete(smsCodeInfo);
	}

	@Override
	public SmsCodeInfo getSmsCodeInfoById(int id) throws HibernateJsonResultException
	{
		String hql = String.format("from %s where %s = :%s", SmsCodeInfo.class.getSimpleName(), ISmsCodeInfoDao.ID, ISmsCodeInfoDao.ID);
		Map<String, Object> params = new HashMap<>();
		params.put(ISmsCodeInfoDao.ID, id);
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
		whereMap.put(ISmsCodeInfoDao.ID, id);
		return updateValue(SmsCodeInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
