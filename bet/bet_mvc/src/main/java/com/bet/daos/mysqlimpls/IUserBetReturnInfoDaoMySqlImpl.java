package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IUserBetReturnInfoDao;
import com.bet.orms.UserBetReturnInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userBetReturnInfoDao")
public class IUserBetReturnInfoDaoMySqlImpl extends IAbstractDaoImpl<UserBetReturnInfo> implements IUserBetReturnInfoDao
{
	@Override
	public ReturnInfo checkParams(UserBetReturnInfo userBetReturnInfo)
	{
		if (userBetReturnInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "UserBetReturnInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { userBetReturnInfo.getUpdateDateLong() + "", userBetReturnInfo.getAddDateLong() + "", userBetReturnInfo.getUserId(), userBetReturnInfo.getStatus() + "",
				userBetReturnInfo.getMoney() + "", userBetReturnInfo.getMoneyUnit() + "", userBetReturnInfo.getLogId(), userBetReturnInfo.getBetId(), userBetReturnInfo.getProfitId() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "userId不能为空！", "status不能为空！", "money不能为空！", "moneyUnit不能为空！", "logId不能为空！", "betId不能为空！", "profitId不能为空！" });
		return returnInfo;
	}

	@Override
	public UserBetReturnInfo add(UserBetReturnInfo userBetReturnInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userBetReturnInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(userBetReturnInfo);
	}

	@Override
	public UserBetReturnInfo update(UserBetReturnInfo userBetReturnInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userBetReturnInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(userBetReturnInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(UserBetReturnInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteUserBetReturnInfoByBetId(String betId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { betId }, new String[] { "betId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		UserBetReturnInfo userBetReturnInfo = getUserBetReturnInfoByBetId(betId);
		if (userBetReturnInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserBetReturnInfo不存在！");
		}
		delete(userBetReturnInfo);
	}

	@Override
	public UserBetReturnInfo getUserBetReturnInfoByBetId(String betId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { betId }, new String[] { "betId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", UserBetReturnInfo.class.getSimpleName(), IUserBetReturnInfoDao.BET_ID, IUserBetReturnInfoDao.BET_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserBetReturnInfoDao.BET_ID, betId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByBetId(String betId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserBetReturnInfoDao.BET_ID, betId);
		return updateValue(UserBetReturnInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteUserBetReturnInfoByLogId(String logId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { logId }, new String[] { "logId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		UserBetReturnInfo userBetReturnInfo = getUserBetReturnInfoByLogId(logId);
		if (userBetReturnInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserBetReturnInfo不存在！");
		}
		delete(userBetReturnInfo);
	}

	@Override
	public UserBetReturnInfo getUserBetReturnInfoByLogId(String logId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { logId }, new String[] { "logId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", UserBetReturnInfo.class.getSimpleName(), IUserBetReturnInfoDao.LOG_ID, IUserBetReturnInfoDao.LOG_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserBetReturnInfoDao.LOG_ID, logId);
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
		whereMap.put(IUserBetReturnInfoDao.LOG_ID, logId);
		return updateValue(UserBetReturnInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
