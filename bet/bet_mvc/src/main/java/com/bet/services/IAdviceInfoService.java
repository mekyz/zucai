package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.AdviceInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IAdviceInfoService
{
	public String genId();

	public AdviceInfo addAdviceInfo(AdviceInfo adviceInfo) throws HibernateJsonResultException;

	public AdviceInfo updateAdviceInfo(AdviceInfo adviceInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteAdviceInfo(AdviceInfo adviceInfo) throws HibernateJsonResultException;

	public void deleteAdviceInfoByAdviceId(String adviceId) throws HibernateJsonResultException;

	public AdviceInfo getAdviceInfoByAdviceId(String adviceId) throws HibernateJsonResultException;

	public boolean updateValueByAdviceId(String adviceId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<AdviceInfo> getAdviceInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getAdviceInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
