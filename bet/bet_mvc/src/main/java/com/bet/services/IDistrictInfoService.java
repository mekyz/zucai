package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.DistrictInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IDistrictInfoService
{
	public String genId();

	public DistrictInfo addDistrictInfo(DistrictInfo districtInfo) throws HibernateJsonResultException;

	public DistrictInfo updateDistrictInfo(DistrictInfo districtInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteDistrictInfo(DistrictInfo districtInfo) throws HibernateJsonResultException;

	public void deleteDistrictInfoByDistrictId(String districtId) throws HibernateJsonResultException;

	public DistrictInfo getDistrictInfoByDistrictId(String districtId) throws HibernateJsonResultException;

	public boolean updateValueByDistrictId(String districtId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<DistrictInfo> getDistrictInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getDistrictInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
