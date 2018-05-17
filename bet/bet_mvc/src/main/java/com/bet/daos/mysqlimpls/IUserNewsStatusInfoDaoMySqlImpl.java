package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IUserNewsStatusInfoDao;
import com.bet.orms.UserNewsStatusInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userNewsStatusInfoDao")
public class IUserNewsStatusInfoDaoMySqlImpl extends IAbstractDaoImpl<UserNewsStatusInfo> implements IUserNewsStatusInfoDao
{
	@Override
	public ReturnInfo checkParams(UserNewsStatusInfo userNewsStatusInfo)
	{
		if (userNewsStatusInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "UserNewsStatusInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { userNewsStatusInfo.getAddDateLong() + "", userNewsStatusInfo.getUserId(), userNewsStatusInfo.getStatus() + "", userNewsStatusInfo.getNewsId() },
			new String[] { "addDateLong不能为空！", "userId不能为空！", "status不能为空！", "newsId不能为空！" });
		return returnInfo;
	}

	@Override
	public UserNewsStatusInfo add(UserNewsStatusInfo userNewsStatusInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userNewsStatusInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(userNewsStatusInfo);
	}

	@Override
	public UserNewsStatusInfo update(UserNewsStatusInfo userNewsStatusInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userNewsStatusInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(userNewsStatusInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(UserNewsStatusInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteUserNewsStatusInfoById(int id) throws HibernateJsonResultException
	{
		UserNewsStatusInfo userNewsStatusInfo = getUserNewsStatusInfoById(id);
		if (userNewsStatusInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserNewsStatusInfo不存在！");
		}
		delete(userNewsStatusInfo);
	}

	@Override
	public UserNewsStatusInfo getUserNewsStatusInfoById(int id) throws HibernateJsonResultException
	{
		String hql = String.format("from %s where %s = :%s", UserNewsStatusInfo.class.getSimpleName(), IUserNewsStatusInfoDao.ID, IUserNewsStatusInfoDao.ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserNewsStatusInfoDao.ID, id);
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
		whereMap.put(IUserNewsStatusInfoDao.ID, id);
		return updateValue(UserNewsStatusInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
