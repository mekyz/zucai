package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.WithdrawInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IWithdrawInfoService
{
	public String genId();

	public WithdrawInfo addWithdrawInfo(WithdrawInfo withdrawInfo) throws HibernateJsonResultException;

	public WithdrawInfo updateWithdrawInfo(WithdrawInfo withdrawInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteWithdrawInfo(WithdrawInfo withdrawInfo) throws HibernateJsonResultException;

	public void deleteWithdrawInfoByWithdrawId(String withdrawId) throws HibernateJsonResultException;

	public WithdrawInfo getWithdrawInfoByWithdrawId(String withdrawId) throws HibernateJsonResultException;

	public boolean updateValueByWithdrawId(String withdrawId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<WithdrawInfo> getWithdrawInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getWithdrawInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	/* 自定义方法 */
	public boolean updateStatusValueByWithdrawId(String withdrawId, byte status, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<WithdrawInfo> getWithdrawInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException;

	public long getWithdrawInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;

	public long getSum(String colName, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;
}
