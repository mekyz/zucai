package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IAdminPayLogInfoDao;
import com.bet.orms.AdminPayLogInfo;
import com.bet.services.IAdminPayLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

@Repository("adminPayLogInfoService")
public class IAdminPayLogInfoServiceImpl implements IAdminPayLogInfoService
{
	@Autowired
	private IAdminPayLogInfoDao adminPayLogInfoDao;

	@Override
	public String genId()
	{
		return adminPayLogInfoDao.genId();
	}

	@Override
	public AdminPayLogInfo addAdminPayLogInfo(AdminPayLogInfo adminPayLogInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (adminPayLogInfo.getAddDateLong() < 1)
		{
			adminPayLogInfo.setAddDateLong(current);
		}
		return adminPayLogInfoDao.add(adminPayLogInfo);
	}

	@Override
	public AdminPayLogInfo updateAdminPayLogInfo(AdminPayLogInfo adminPayLogInfo) throws HibernateJsonResultException
	{
		return adminPayLogInfoDao.update(adminPayLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return adminPayLogInfoDao.updateValue(AdminPayLogInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteAdminPayLogInfo(AdminPayLogInfo adminPayLogInfo) throws HibernateJsonResultException
	{
		adminPayLogInfoDao.delete(adminPayLogInfo);
	}

	@Override
	public void deleteAdminPayLogInfoById(int id) throws HibernateJsonResultException
	{
		adminPayLogInfoDao.deleteAdminPayLogInfoById(id);
	}

	@Override
	public AdminPayLogInfo getAdminPayLogInfoById(int id) throws HibernateJsonResultException
	{
		return adminPayLogInfoDao.getAdminPayLogInfoById(id);
	}

	@Override
	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IAdminPayLogInfoDao.ID, id);
		return adminPayLogInfoDao.updateValue(AdminPayLogInfo.class, valueMap, whereMap) > 0;
	}

	public List<AdminPayLogInfo> getAdminPayLogInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return adminPayLogInfoDao.getList(AdminPayLogInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<AdminPayLogInfo> getAdminPayLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return adminPayLogInfoDao.getList(AdminPayLogInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getAdminPayLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return adminPayLogInfoDao.getListCount(AdminPayLogInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
