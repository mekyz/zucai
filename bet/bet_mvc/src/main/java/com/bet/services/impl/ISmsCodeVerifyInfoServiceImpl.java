package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.ISmsCodeVerifyInfoDao;
import com.bet.orms.SmsCodeVerifyInfo;
import com.bet.services.ISmsCodeVerifyInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

@Repository("smsCodeVerifyInfoService")
public class ISmsCodeVerifyInfoServiceImpl implements ISmsCodeVerifyInfoService
{
	@Autowired
	private ISmsCodeVerifyInfoDao smsCodeVerifyInfoDao;

	@Override
	public String genId()
	{
		return smsCodeVerifyInfoDao.genId();
	}

	@Override
	public SmsCodeVerifyInfo addSmsCodeVerifyInfo(SmsCodeVerifyInfo smsCodeVerifyInfo) throws HibernateJsonResultException
	{
		return smsCodeVerifyInfoDao.add(smsCodeVerifyInfo);
	}

	@Override
	public SmsCodeVerifyInfo updateSmsCodeVerifyInfo(SmsCodeVerifyInfo smsCodeVerifyInfo) throws HibernateJsonResultException
	{
		return smsCodeVerifyInfoDao.update(smsCodeVerifyInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return smsCodeVerifyInfoDao.updateValue(SmsCodeVerifyInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteSmsCodeVerifyInfo(SmsCodeVerifyInfo smsCodeVerifyInfo) throws HibernateJsonResultException
	{
		smsCodeVerifyInfoDao.delete(smsCodeVerifyInfo);
	}

	@Override
	public void deleteSmsCodeVerifyInfoById(int id) throws HibernateJsonResultException
	{
		smsCodeVerifyInfoDao.deleteSmsCodeVerifyInfoById(id);
	}

	@Override
	public SmsCodeVerifyInfo getSmsCodeVerifyInfoById(int id) throws HibernateJsonResultException
	{
		return smsCodeVerifyInfoDao.getSmsCodeVerifyInfoById(id);
	}

	@Override
	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ISmsCodeVerifyInfoDao.ID, id);
		return smsCodeVerifyInfoDao.updateValue(SmsCodeVerifyInfo.class, valueMap, whereMap) > 0;
	}

	public List<SmsCodeVerifyInfo> getSmsCodeVerifyInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return smsCodeVerifyInfoDao.getList(SmsCodeVerifyInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<SmsCodeVerifyInfo> getSmsCodeVerifyInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return smsCodeVerifyInfoDao.getList(SmsCodeVerifyInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getSmsCodeVerifyInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return smsCodeVerifyInfoDao.getListCount(SmsCodeVerifyInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
