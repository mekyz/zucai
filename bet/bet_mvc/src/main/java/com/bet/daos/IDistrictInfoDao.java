package com.bet.daos;

import java.util.Map;

import com.bet.orms.DistrictInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IDistrictInfoDao extends IAbstractDao<DistrictInfo>
{
	public static final String ID = "id";
	public static final String DISTRICT_ID = "districtId";
	public static final String NAME = "name";
	public static final String CODE = "code";
	public static final String PROVINCE_ID = "provinceId";
	public static final String CITY_ID = "cityId";

	public void deleteDistrictInfoByDistrictId(String districtId) throws HibernateJsonResultException;

	public DistrictInfo getDistrictInfoByDistrictId(String districtId) throws HibernateJsonResultException;

	public long updateValueByDistrictId(String districtId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
