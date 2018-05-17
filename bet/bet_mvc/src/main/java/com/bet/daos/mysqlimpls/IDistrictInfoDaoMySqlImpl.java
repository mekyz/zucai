package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IDistrictInfoDao;
import com.bet.orms.DistrictInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("districtInfoDao")
public class IDistrictInfoDaoMySqlImpl extends IAbstractDaoImpl<DistrictInfo> implements IDistrictInfoDao
{
	@Override
	public ReturnInfo checkParams(DistrictInfo districtInfo)
	{
		if (districtInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "DistrictInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { districtInfo.getName(), districtInfo.getDistrictId() }, new String[] { "name不能为空！", "districtId不能为空！" });
		return returnInfo;
	}

	@Override
	public DistrictInfo add(DistrictInfo districtInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(districtInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(districtInfo);
	}

	@Override
	public DistrictInfo update(DistrictInfo districtInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(districtInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(districtInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(DistrictInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteDistrictInfoByDistrictId(String districtId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { districtId }, new String[] { "districtId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		DistrictInfo districtInfo = getDistrictInfoByDistrictId(districtId);
		if (districtInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "DistrictInfo不存在！");
		}
		delete(districtInfo);
	}

	@Override
	public DistrictInfo getDistrictInfoByDistrictId(String districtId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { districtId }, new String[] { "districtId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", DistrictInfo.class.getSimpleName(), IDistrictInfoDao.DISTRICT_ID, IDistrictInfoDao.DISTRICT_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IDistrictInfoDao.DISTRICT_ID, districtId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByDistrictId(String districtId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IDistrictInfoDao.DISTRICT_ID, districtId);
		return updateValue(DistrictInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
