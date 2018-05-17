package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.ProvinceInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IProvinceInfoService
{
	public String genId();

	public ProvinceInfo addProvinceInfo(ProvinceInfo provinceInfo) throws HibernateJsonResultException;

	public ProvinceInfo updateProvinceInfo(ProvinceInfo provinceInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteProvinceInfo(ProvinceInfo provinceInfo) throws HibernateJsonResultException;

	public void deleteProvinceInfoByName(String name) throws HibernateJsonResultException;

	public ProvinceInfo getProvinceInfoByName(String name) throws HibernateJsonResultException;

	public boolean updateValueByName(String name, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteProvinceInfoByProvinceId(String provinceId) throws HibernateJsonResultException;

	public ProvinceInfo getProvinceInfoByProvinceId(String provinceId) throws HibernateJsonResultException;

	public boolean updateValueByProvinceId(String provinceId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<ProvinceInfo> getProvinceInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getProvinceInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
