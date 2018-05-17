package com.bet.daos;

import java.util.Map;

import com.bet.orms.AdviceTypeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IAdviceTypeInfoDao extends IAbstractDao<AdviceTypeInfo>
{
	public static final String ID = "id";
	public static final String TYPE_ID = "typeId";
	public static final String NAME = "name";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteAdviceTypeInfoByTypeId(String typeId) throws HibernateJsonResultException;

	public AdviceTypeInfo getAdviceTypeInfoByTypeId(String typeId) throws HibernateJsonResultException;

	public long updateValueByTypeId(String typeId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
