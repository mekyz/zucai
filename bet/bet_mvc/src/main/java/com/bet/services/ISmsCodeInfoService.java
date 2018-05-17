package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.SmsCodeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface ISmsCodeInfoService
{
	public String genId();

	public SmsCodeInfo addSmsCodeInfo(SmsCodeInfo smsCodeInfo) throws HibernateJsonResultException;

	public SmsCodeInfo updateSmsCodeInfo(SmsCodeInfo smsCodeInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteSmsCodeInfo(SmsCodeInfo smsCodeInfo) throws HibernateJsonResultException;

	public void deleteSmsCodeInfoById(int id) throws HibernateJsonResultException;

	public SmsCodeInfo getSmsCodeInfoById(int id) throws HibernateJsonResultException;

	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<SmsCodeInfo> getSmsCodeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getSmsCodeInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	/**
	 * 获取短信验证码信息
	 * 
	 * @param number
	 *            手机号码
	 * @param type
	 *            验证码类型
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public SmsCodeInfo getSmsCodeInfoByNumber(String number, String type) throws HibernateJsonResultException;
}
