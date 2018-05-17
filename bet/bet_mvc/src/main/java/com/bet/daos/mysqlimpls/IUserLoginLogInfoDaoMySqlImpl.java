package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IUserLoginLogInfoDao;
import com.bet.orms.UserLoginLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userLoginLogInfoDao")
public class IUserLoginLogInfoDaoMySqlImpl extends IAbstractDaoImpl<UserLoginLogInfo> implements IUserLoginLogInfoDao
{
	@Override
	public ReturnInfo checkParams(UserLoginLogInfo userLoginLogInfo)
	{
		if (userLoginLogInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "UserLoginLogInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userLoginLogInfo.getAddDateLong() + "", userLoginLogInfo.getStatus() + "" }, new String[] { "addDateLong不能为空！", "status不能为空！" });
		return returnInfo;
	}

	@Override
	public UserLoginLogInfo add(UserLoginLogInfo userLoginLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userLoginLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(userLoginLogInfo);
	}

	@Override
	public UserLoginLogInfo update(UserLoginLogInfo userLoginLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userLoginLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(userLoginLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(UserLoginLogInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteUserLoginLogInfoById(int id) throws HibernateJsonResultException
	{
		UserLoginLogInfo userLoginLogInfo = getUserLoginLogInfoById(id);
		if (userLoginLogInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserLoginLogInfo不存在！");
		}
		delete(userLoginLogInfo);
	}

	@Override
	public UserLoginLogInfo getUserLoginLogInfoById(int id) throws HibernateJsonResultException
	{
		String hql = String.format("from %s where %s = :%s", UserLoginLogInfo.class.getSimpleName(), IUserLoginLogInfoDao.ID, IUserLoginLogInfoDao.ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserLoginLogInfoDao.ID, id);
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
		whereMap.put(IUserLoginLogInfoDao.ID, id);
		return updateValue(UserLoginLogInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
