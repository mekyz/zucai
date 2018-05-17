package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IAdviceTypeInfoDao;
import com.bet.orms.AdviceTypeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("adviceTypeInfoDao")
public class IAdviceTypeInfoDaoMySqlImpl extends IAbstractDaoImpl<AdviceTypeInfo> implements IAdviceTypeInfoDao
{
	@Override
	public ReturnInfo checkParams(AdviceTypeInfo adviceTypeInfo)
	{
		if (adviceTypeInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "AdviceTypeInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { adviceTypeInfo.getName(), adviceTypeInfo.getUpdateDateLong() + "", adviceTypeInfo.getAddDateLong() + "", adviceTypeInfo.getStatus() + "", adviceTypeInfo.getTypeId() },
			new String[] { "name不能为空！", "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "typeId不能为空！" });
		return returnInfo;
	}

	@Override
	public AdviceTypeInfo add(AdviceTypeInfo adviceTypeInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(adviceTypeInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(adviceTypeInfo);
	}

	@Override
	public AdviceTypeInfo update(AdviceTypeInfo adviceTypeInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(adviceTypeInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(adviceTypeInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(AdviceTypeInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteAdviceTypeInfoByTypeId(String typeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { typeId }, new String[] { "typeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		AdviceTypeInfo adviceTypeInfo = getAdviceTypeInfoByTypeId(typeId);
		if (adviceTypeInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "AdviceTypeInfo不存在！");
		}
		delete(adviceTypeInfo);
	}

	@Override
	public AdviceTypeInfo getAdviceTypeInfoByTypeId(String typeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { typeId }, new String[] { "typeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", AdviceTypeInfo.class.getSimpleName(), IAdviceTypeInfoDao.TYPE_ID, IAdviceTypeInfoDao.TYPE_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IAdviceTypeInfoDao.TYPE_ID, typeId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByTypeId(String typeId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IAdviceTypeInfoDao.TYPE_ID, typeId);
		return updateValue(AdviceTypeInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
