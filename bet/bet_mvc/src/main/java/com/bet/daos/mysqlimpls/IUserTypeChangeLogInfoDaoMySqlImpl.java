package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IUserTypeChangeLogInfoDao;
import com.bet.orms.UserTypeChangeLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userTypeChangeLogInfoDao")
public class IUserTypeChangeLogInfoDaoMySqlImpl extends IAbstractDaoImpl<UserTypeChangeLogInfo> implements IUserTypeChangeLogInfoDao
{
	@Override
	public ReturnInfo checkParams(UserTypeChangeLogInfo userTypeChangeLogInfo)
	{
		if (userTypeChangeLogInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "UserTypeChangeLogInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userTypeChangeLogInfo.getAddDateLong() + "", userTypeChangeLogInfo.getUserId(), userTypeChangeLogInfo.getUserType() + "" },
			new String[] { "addDateLong不能为空！", "userId不能为空！", "userType不能为空！" });
		return returnInfo;
	}

	@Override
	public UserTypeChangeLogInfo add(UserTypeChangeLogInfo userTypeChangeLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userTypeChangeLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(userTypeChangeLogInfo);
	}

	@Override
	public UserTypeChangeLogInfo update(UserTypeChangeLogInfo userTypeChangeLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userTypeChangeLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(userTypeChangeLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(UserTypeChangeLogInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteUserTypeChangeLogInfoById(int id) throws HibernateJsonResultException
	{
		UserTypeChangeLogInfo userTypeChangeLogInfo = getUserTypeChangeLogInfoById(id);
		if (userTypeChangeLogInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserTypeChangeLogInfo不存在！");
		}
		delete(userTypeChangeLogInfo);
	}

	@Override
	public UserTypeChangeLogInfo getUserTypeChangeLogInfoById(int id) throws HibernateJsonResultException
	{
		String hql = String.format("from %s where %s = :%s", UserTypeChangeLogInfo.class.getSimpleName(), IUserTypeChangeLogInfoDao.ID, IUserTypeChangeLogInfoDao.ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserTypeChangeLogInfoDao.ID, id);
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
		whereMap.put(IUserTypeChangeLogInfoDao.ID, id);
		return updateValue(UserTypeChangeLogInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
