package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IAdminInfoDao;
import com.bet.orms.AdminInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("adminInfoDao")
public class IAdminInfoDaoMySqlImpl extends IAbstractDaoImpl<AdminInfo> implements IAdminInfoDao
{
	@Override
	public ReturnInfo checkParams(AdminInfo adminInfo)
	{
		if (adminInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "AdminInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { adminInfo.getUpdateDateLong() + "", adminInfo.getAddDateLong() + "", adminInfo.getUserLevel() + "", adminInfo.getPassword(),
			adminInfo.getUserId(), adminInfo.getStatus() + "" }, new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "userLevel不能为空！", "password不能为空！", "userId不能为空！", "status不能为空！" });
		return returnInfo;
	}

	@Override
	public AdminInfo add(AdminInfo adminInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(adminInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(adminInfo);
	}

	@Override
	public AdminInfo update(AdminInfo adminInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(adminInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(adminInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(AdminInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteAdminInfoByUserId(String userId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId }, new String[] { "userId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		AdminInfo adminInfo = getAdminInfoByUserId(userId);
		if (adminInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "AdminInfo不存在！");
		}
		delete(adminInfo);
	}

	@Override
	public AdminInfo getAdminInfoByUserId(String userId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId }, new String[] { "userId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", AdminInfo.class.getSimpleName(), IAdminInfoDao.USER_ID, IAdminInfoDao.USER_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IAdminInfoDao.USER_ID, userId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByUserId(String userId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IAdminInfoDao.USER_ID, userId);
		return updateValue(AdminInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
