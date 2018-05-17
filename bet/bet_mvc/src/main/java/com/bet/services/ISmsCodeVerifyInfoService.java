package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.SmsCodeVerifyInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface ISmsCodeVerifyInfoService
{
	public String genId();

	public SmsCodeVerifyInfo addSmsCodeVerifyInfo(SmsCodeVerifyInfo smsCodeVerifyInfo) throws HibernateJsonResultException;

	public SmsCodeVerifyInfo updateSmsCodeVerifyInfo(SmsCodeVerifyInfo smsCodeVerifyInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteSmsCodeVerifyInfo(SmsCodeVerifyInfo smsCodeVerifyInfo) throws HibernateJsonResultException;

	public void deleteSmsCodeVerifyInfoById(int id) throws HibernateJsonResultException;

	public SmsCodeVerifyInfo getSmsCodeVerifyInfoById(int id) throws HibernateJsonResultException;

	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<SmsCodeVerifyInfo> getSmsCodeVerifyInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getSmsCodeVerifyInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
