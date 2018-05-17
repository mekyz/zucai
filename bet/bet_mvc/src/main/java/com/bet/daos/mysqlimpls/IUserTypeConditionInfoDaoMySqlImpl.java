package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IUserTypeConditionInfoDao;
import com.bet.orms.UserTypeConditionInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userTypeConditionInfoDao")
public class IUserTypeConditionInfoDaoMySqlImpl extends IAbstractDaoImpl<UserTypeConditionInfo> implements IUserTypeConditionInfoDao
{
	@Override
	public ReturnInfo checkParams(UserTypeConditionInfo userTypeConditionInfo)
	{
		if (userTypeConditionInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "UserTypeConditionInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { userTypeConditionInfo.getUpdateDateLong() + "", userTypeConditionInfo.getAddDateLong() + "", userTypeConditionInfo.getStatus() + "", userTypeConditionInfo.getMoney() + "",
				userTypeConditionInfo.getTeamCount() + "", userTypeConditionInfo.getDirectCount() + "", userTypeConditionInfo.getUserType() + "" },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "money不能为空！", "teamCount不能为空！", "directCount不能为空！", "userType不能为空！" });
		return returnInfo;
	}

	@Override
	public UserTypeConditionInfo add(UserTypeConditionInfo userTypeConditionInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userTypeConditionInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(userTypeConditionInfo);
	}

	@Override
	public UserTypeConditionInfo update(UserTypeConditionInfo userTypeConditionInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userTypeConditionInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(userTypeConditionInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(UserTypeConditionInfo.class, valueMap, whereMap);
	}

	@Override
	public long updateValueByUserType(byte userType, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserTypeConditionInfoDao.USER_TYPE, userType);
		return updateValue(UserTypeConditionInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */

	@Override
	public void deleteUserTypeConditionInfoByUserType(byte userType) throws HibernateJsonResultException
	{
		UserTypeConditionInfo userTypeConditionInfo = getUserTypeConditionInfoByUserType(userType);
		if (userTypeConditionInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserTypeConditionInfo不存在！");
		}
		delete(userTypeConditionInfo);
	}

	@Override
	public UserTypeConditionInfo getUserTypeConditionInfoByUserType(byte userType) throws HibernateJsonResultException
	{
		String hql = String.format("from %s where %s = :%s", UserTypeConditionInfo.class.getSimpleName(), IUserTypeConditionInfoDao.USER_TYPE, IUserTypeConditionInfoDao.USER_TYPE);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserTypeConditionInfoDao.USER_TYPE, userType);
		return baseDao.hqlQuery(hql, params);
	}
}
