package com.lrcall.daos;

import java.util.List;
import java.util.Map;

import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

/**
 * O 服务基类
 * 
 * @author libit
 */
public interface IAbstractDao<T>
{
	/**
	 * 产生ID
	 * 
	 * @return
	 */
	public String genId();

	/**
	 * 检查对象的参数值
	 * 
	 * @param t
	 * @return
	 */
	public ReturnInfo checkParams(T t);

	/**
	 * 添加对象到数据库
	 * 
	 * @param t
	 * @return
	 */
	public T add(T t);

	/**
	 * 更新对象到数据库
	 * 
	 * @param t
	 * @return
	 */
	public T update(T t);

	/**
	 * 删除对象
	 * 
	 * @param t
	 * @return
	 */
	public void delete(T t);

	/**
	 * 更新值
	 * 
	 * @param t
	 * @param valueMap
	 * @param whereMap
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public long updateValue(Class<T> t, Map<String, Object> valueMap, Map<String, Object> whereMap);

	/**
	 * 获取列表
	 * 
	 * @param c
	 * @param whereClause
	 * @param params
	 * @param start
	 * @param size
	 * @param orderInfos
	 * @param searchInfos
	 * @return
	 */
	public List<T> getList(Class<T> c, String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos);

	/**
	 * 获取列表总数
	 * 
	 * @param c
	 * @param whereClause
	 * @param params
	 * @param orderInfos
	 * @param searchInfos
	 * @return
	 */
	public long getListCount(Class<T> c, String whereClause, Map<String, Object> params, List<TableSearchInfo> searchInfos);

	/**
	 * 建立时间sql语句
	 * 
	 * @param dateColName
	 * @param startDateLong
	 * @param endDateLong
	 * @return
	 */
	public String buildSqlTimeWhere(String dateColName, Long startDateLong, Long endDateLong);

	/**
	 * 建立时间hql语句
	 * 
	 * @param dateColName
	 * @param startDateLong
	 * @param endDateLong
	 * @return
	 */
	public String buildHqlTimeWhere(String dateColName, Long startDateLong, Long endDateLong);
}
