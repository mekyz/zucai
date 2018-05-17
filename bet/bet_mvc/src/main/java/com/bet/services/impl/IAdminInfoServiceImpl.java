package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IAdminInfoDao;
import com.bet.orms.AdminInfo;
import com.bet.services.IAdminInfoService;
import com.bet.utils.AdminCryptoTools;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("adminInfoService")
public class IAdminInfoServiceImpl implements IAdminInfoService
{
	@Autowired
	private IAdminInfoDao adminInfoDao;

	@Override
	public String genId()
	{
		return adminInfoDao.genId();
	}

	@Override
	public AdminInfo addAdminInfo(AdminInfo adminInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (adminInfo.getAddDateLong() < 1)
		{
			adminInfo.setAddDateLong(current);
		}
		if (adminInfo.getUpdateDateLong() < 1)
		{
			adminInfo.setUpdateDateLong(current);
		}
		return adminInfoDao.add(adminInfo);
	}

	@Override
	public AdminInfo updateAdminInfo(AdminInfo adminInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (adminInfo.getUpdateDateLong() < 1)
		{
			adminInfo.setUpdateDateLong(current);
		}
		return adminInfoDao.update(adminInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return adminInfoDao.updateValue(AdminInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteAdminInfo(AdminInfo adminInfo) throws HibernateJsonResultException
	{
		adminInfoDao.delete(adminInfo);
	}

	@Override
	public void deleteAdminInfoByUserId(String userId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId }, new String[] { "userId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		adminInfoDao.deleteAdminInfoByUserId(userId);
	}

	@Override
	public AdminInfo getAdminInfoByUserId(String userId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId }, new String[] { "userId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return adminInfoDao.getAdminInfoByUserId(userId);
	}

	@Override
	public boolean updateValueByUserId(String userId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IAdminInfoDao.USER_ID, userId);
		return adminInfoDao.updateValue(AdminInfo.class, valueMap, whereMap) > 0;
	}

	public List<AdminInfo> getAdminInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return adminInfoDao.getList(AdminInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<AdminInfo> getAdminInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return adminInfoDao.getList(AdminInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getAdminInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return adminInfoDao.getListCount(AdminInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public AdminInfo login(String userId, String password) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId, password }, new String[] { "账号不能为空！", "密码不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		AdminInfo adminInfo = adminInfoDao.getAdminInfoByUserId(userId);
		if (adminInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "账号不存在！");
		}
		if (!adminInfo.getPassword().equalsIgnoreCase(AdminCryptoTools.getCryptoPwd(userId, password)))
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "账号或密码错误！");
		}
		return adminInfo;
	}
}
