package com.bet.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IDistrictInfoDao;
import com.bet.orms.DistrictInfo;
import com.bet.services.IDistrictInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("districtInfoService")
public class IDistrictInfoServiceImpl implements IDistrictInfoService
{
	@Autowired
	private IDistrictInfoDao districtInfoDao;

	@Override
	public String genId()
	{
		return districtInfoDao.genId();
	}

	@Override
	public DistrictInfo addDistrictInfo(DistrictInfo districtInfo) throws HibernateJsonResultException
	{
		return districtInfoDao.add(districtInfo);
	}

	@Override
	public DistrictInfo updateDistrictInfo(DistrictInfo districtInfo) throws HibernateJsonResultException
	{
		return districtInfoDao.update(districtInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return districtInfoDao.updateValue(DistrictInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteDistrictInfo(DistrictInfo districtInfo) throws HibernateJsonResultException
	{
		districtInfoDao.delete(districtInfo);
	}

	@Override
	public void deleteDistrictInfoByDistrictId(String districtId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { districtId }, new String[] { "districtId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		districtInfoDao.deleteDistrictInfoByDistrictId(districtId);
	}

	@Override
	public DistrictInfo getDistrictInfoByDistrictId(String districtId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { districtId }, new String[] { "districtId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return districtInfoDao.getDistrictInfoByDistrictId(districtId);
	}

	@Override
	public boolean updateValueByDistrictId(String districtId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IDistrictInfoDao.DISTRICT_ID, districtId);
		return districtInfoDao.updateValue(DistrictInfo.class, valueMap, whereMap) > 0;
	}

	public List<DistrictInfo> getDistrictInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return districtInfoDao.getList(DistrictInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<DistrictInfo> getDistrictInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return districtInfoDao.getList(DistrictInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getDistrictInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return districtInfoDao.getListCount(DistrictInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */
}
