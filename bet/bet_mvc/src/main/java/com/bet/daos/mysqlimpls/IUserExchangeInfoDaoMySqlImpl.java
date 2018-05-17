package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IUserExchangeInfoDao;
import com.bet.orms.UserExchangeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userExchangeInfoDao")
public class IUserExchangeInfoDaoMySqlImpl extends IAbstractDaoImpl<UserExchangeInfo> implements IUserExchangeInfoDao
{
	@Override
	public ReturnInfo checkParams(UserExchangeInfo userExchangeInfo)
	{
		if (userExchangeInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "UserExchangeInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { userExchangeInfo.getUpdateDateLong() + "", userExchangeInfo.getAddDateLong() + "", userExchangeInfo.getUserId(), userExchangeInfo.getStatus() + "",
				userExchangeInfo.getMoney() + "", userExchangeInfo.getFee() + "", userExchangeInfo.getUserMoney() + "", userExchangeInfo.getReceiveUserId(), userExchangeInfo.getExchangeId() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "userId不能为空！", "status不能为空！", "money不能为空！", "fee不能为空！", "userMoney不能为空！", "receiveUserId不能为空！", "exchangeId不能为空！" });
		return returnInfo;
	}

	@Override
	public UserExchangeInfo add(UserExchangeInfo userExchangeInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userExchangeInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(userExchangeInfo);
	}

	@Override
	public UserExchangeInfo update(UserExchangeInfo userExchangeInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userExchangeInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(userExchangeInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(UserExchangeInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteUserExchangeInfoByExchangeId(String exchangeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { exchangeId }, new String[] { "exchangeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		UserExchangeInfo userExchangeInfo = getUserExchangeInfoByExchangeId(exchangeId);
		if (userExchangeInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserExchangeInfo不存在！");
		}
		delete(userExchangeInfo);
	}

	@Override
	public UserExchangeInfo getUserExchangeInfoByExchangeId(String exchangeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { exchangeId }, new String[] { "exchangeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", UserExchangeInfo.class.getSimpleName(), IUserExchangeInfoDao.EXCHANGE_ID, IUserExchangeInfoDao.EXCHANGE_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserExchangeInfoDao.EXCHANGE_ID, exchangeId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByExchangeId(String exchangeId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserExchangeInfoDao.EXCHANGE_ID, exchangeId);
		return updateValue(UserExchangeInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
