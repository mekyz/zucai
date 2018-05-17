package com.bet.daos;

import java.util.Map;

import com.bet.orms.ProvinceInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IProvinceInfoDao extends IAbstractDao<ProvinceInfo>
{
	public static final String ID = "id";
	public static final String PROVINCE_ID = "provinceId";
	public static final String NAME = "name";
	public static final String CODE = "code";

	public void deleteProvinceInfoByName(String name) throws HibernateJsonResultException;

	public ProvinceInfo getProvinceInfoByName(String name) throws HibernateJsonResultException;

	public long updateValueByName(String name, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteProvinceInfoByProvinceId(String provinceId) throws HibernateJsonResultException;

	public ProvinceInfo getProvinceInfoByProvinceId(String provinceId) throws HibernateJsonResultException;

	public long updateValueByProvinceId(String provinceId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
