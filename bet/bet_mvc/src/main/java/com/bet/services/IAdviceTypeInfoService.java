package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.AdviceTypeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IAdviceTypeInfoService
{
	public String genId();

	public AdviceTypeInfo addAdviceTypeInfo(AdviceTypeInfo adviceTypeInfo) throws HibernateJsonResultException;

	public AdviceTypeInfo updateAdviceTypeInfo(AdviceTypeInfo adviceTypeInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteAdviceTypeInfo(AdviceTypeInfo adviceTypeInfo) throws HibernateJsonResultException;

	public void deleteAdviceTypeInfoByTypeId(String typeId) throws HibernateJsonResultException;

	public AdviceTypeInfo getAdviceTypeInfoByTypeId(String typeId) throws HibernateJsonResultException;

	public boolean updateValueByTypeId(String typeId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<AdviceTypeInfo> getAdviceTypeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getAdviceTypeInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
