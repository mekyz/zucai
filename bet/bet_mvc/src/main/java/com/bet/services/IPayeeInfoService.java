package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.PayeeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IPayeeInfoService
{
	public String genId();

	public PayeeInfo addPayeeInfo(PayeeInfo payeeInfo) throws HibernateJsonResultException;

	public PayeeInfo updatePayeeInfo(PayeeInfo payeeInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deletePayeeInfo(PayeeInfo payeeInfo) throws HibernateJsonResultException;

	public void deletePayeeInfoByPayeeId(String payeeId) throws HibernateJsonResultException;

	public PayeeInfo getPayeeInfoByPayeeId(String payeeId) throws HibernateJsonResultException;

	public boolean updateValueByPayeeId(String payeeId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<PayeeInfo> getPayeeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getPayeeInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	/**
	 * 根据类型来获取收款信息
	 * 
	 * @param payeeType
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public PayeeInfo getPayeeInfoByPayeeType(String payeeType) throws HibernateJsonResultException;
}
