package com.lrcall.daos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Service接口，提供基本的操作
 * 
 * @author libit
 */
public interface IBaseDao<T>
{
	public Object sqlQuery(String sql, Map<String, Object> params);

	/**
	 * 执行更新操作
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @return int
	 */
	public int sqlUpdate(String sql, Map<String, Object> params);

	/**
	 * 增加对象
	 * 
	 * @param obj
	 *            对象
	 * @return Object
	 */
	public Serializable addObject(T obj);

	/**
	 * 更新对象
	 * 
	 * @param obj
	 *            对象
	 * @return Object
	 */
	public T updateObject(T obj);

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            对象
	 * @return
	 */
	public void deleteObject(T obj);

	/**
	 * 查询
	 * 
	 * @param entityType
	 *            查询结果要转换成的类对象
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @return Object 注意：表的别名与实体类一一对应
	 */
	public T sqlQuery(Class<T> entityType, String sql, Map<String, Object> params);

	/**
	 * 列表查询
	 * 
	 * @param entityType
	 *            查询结果要转换成的类对象
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @param start
	 *            起始位置
	 * @param size
	 *            取得的个数
	 * @return List 注意：表的别名与实体类一一对应
	 */
	public List<T> sqlQueryList(Class<T> entityType, String sql, Map<String, Object> params, int start, int size);

	/**
	 * HQL查询对象
	 * 
	 * @param hql
	 *            SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @return T
	 */
	public T hqlQuery(String hql, Map<String, Object> params);

	/**
	 * HQL查询列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            HQL语句的参数
	 * @param start
	 *            起始位置
	 * @param size
	 *            取得的个数
	 * @return List
	 */
	public List<T> hqlQueryList(String hql, Map<String, Object> params, int start, int size);

	/**
	 * 获取查询数据的总数（返回记录条数）
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @return long
	 */
	public long getSqlRowCount(String sql, Map<String, Object> params);

	/**
	 * 获取查询数据的总和（对列求和）
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @return BigDecimal
	 */
	public BigDecimal getSqlRowSum(String sql, Map<String, Object> params);

	/**
	 * 获取查询数据的总数（返回记录条数）
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            HQL语句的参数
	 * @return long
	 */
	public long getHqlRowCount(String hql, Map<String, Object> params);

	/**
	 * 获取查询数据的总和（对列求和）
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            HQL语句的参数
	 * @return long
	 */
	public long getHqlRowSum(String hql, Map<String, Object> params);
}
