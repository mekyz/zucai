package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.AdminInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IAdminInfoService
{
	public String genId();

	public AdminInfo addAdminInfo(AdminInfo adminInfo) throws HibernateJsonResultException;

	public AdminInfo updateAdminInfo(AdminInfo adminInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteAdminInfo(AdminInfo adminInfo) throws HibernateJsonResultException;

	public void deleteAdminInfoByUserId(String userId) throws HibernateJsonResultException;

	public AdminInfo getAdminInfoByUserId(String userId) throws HibernateJsonResultException;

	public boolean updateValueByUserId(String userId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<AdminInfo> getAdminInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getAdminInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	/**
	 * 管理员登录
	 * 
	 * @param userId
	 *            账号
	 * @param password
	 *            密码
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public AdminInfo login(String userId, String password) throws HibernateJsonResultException;
}
