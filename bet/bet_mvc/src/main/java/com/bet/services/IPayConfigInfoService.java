package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.PayConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IPayConfigInfoService
{
	public String genId();

	public PayConfigInfo addPayConfigInfo(PayConfigInfo payConfigInfo) throws HibernateJsonResultException;

	public PayConfigInfo updatePayConfigInfo(PayConfigInfo payConfigInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deletePayConfigInfo(PayConfigInfo payConfigInfo) throws HibernateJsonResultException;

	public void deletePayConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public PayConfigInfo getPayConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public boolean updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<PayConfigInfo> getPayConfigInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getPayConfigInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	public PayConfigInfo getMinlePayConfigInfo() throws HibernateJsonResultException;
}
