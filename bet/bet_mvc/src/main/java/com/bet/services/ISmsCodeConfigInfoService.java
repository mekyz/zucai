package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.SmsCodeConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface ISmsCodeConfigInfoService
{
	public String genId();

	public SmsCodeConfigInfo addSmsCodeConfigInfo(SmsCodeConfigInfo smsCodeConfigInfo) throws HibernateJsonResultException;

	public SmsCodeConfigInfo updateSmsCodeConfigInfo(SmsCodeConfigInfo smsCodeConfigInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteSmsCodeConfigInfo(SmsCodeConfigInfo smsCodeConfigInfo) throws HibernateJsonResultException;

	public void deleteSmsCodeConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public SmsCodeConfigInfo getSmsCodeConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public boolean updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<SmsCodeConfigInfo> getSmsCodeConfigInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getSmsCodeConfigInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	public SmsCodeConfigInfo getSmsCodeConfigInfoByType(String type) throws HibernateJsonResultException;
}
