package com.lrcall.daos.mysqlimpls;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.utils.LogTools;
import com.lrcall.common.utils.StringTools;
import com.lrcall.daos.IBaseDao;

/**
 * 服务基类： 返回值定义为json数据格式，对象为ReturnInfo，详细参见类说明
 * 
 * @author libit
 */
@Repository("baseDao")
public class IBaseDaoImpl<T> implements IBaseDao<T>
{
	// private static final Logger logger = Logger.getLogger(IBaseDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession()
	{
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	/**
	 * sql查询
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @return long
	 */
	@Override
	public Object sqlQuery(String sql, Map<String, Object> params)
	{
		if (StringTools.isNull(sql))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "SQL语句为空！");
		}
		try
		{
			SQLQuery query = getCurrentSession().createSQLQuery(sql);
			if (params != null)
			{
				for (String name : params.keySet())
				{
					query.setParameter(name, params.get(name));
				}
			}
			return query.uniqueResult();
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("sqlQuery", msg);
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "查询记录出错：" + msg);
		}
	}

	/**
	 * 执行更新操作
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @return ReturnInfo
	 */
	@Override
	public int sqlUpdate(String sql, Map<String, Object> params)
	{
		if (StringTools.isNull(sql))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "SQL语句为空！");
		}
		try
		{
			SQLQuery query = getCurrentSession().createSQLQuery(sql);
			if (params != null)
			{
				for (String name : params.keySet())
				{
					query.setParameter(name, params.get(name));
				}
			}
			return query.executeUpdate();
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("sqlUpdate", msg);
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "执行SQL语句出错：" + msg);
		}
	}

	/**
	 * 增加对象
	 * 
	 * @param obj
	 *            对象
	 * @return ReturnInfo
	 */
	@Override
	public Serializable addObject(T obj)
	{
		if (obj == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "对象为空！");
		}
		try
		{
			return getCurrentSession().save(obj);
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("addObject", msg);
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "保存对象出错：" + msg);
		}
	}

	/**
	 * 更新对象
	 * 
	 * @param obj
	 *            对象
	 * @return ReturnInfo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T updateObject(T obj)
	{
		if (obj == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "对象为空！");
		}
		try
		{
			// 更新前先clear一下才能正确的更新，否则报2个不同对象的错误
			// session.clear();
			// session.update(obj);
			return (T) getCurrentSession().merge(obj);
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("updateObject", msg);
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "更新对象出错：" + msg);
		}
	}

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            对象
	 * @return ReturnInfo
	 */
	@Override
	public void deleteObject(T obj)
	{
		if (obj == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "对象为空！");
		}
		try
		{
			// // 更新前先clear一下才能正确的更新，否则报2个不同对象的错误
			// session.clear();
			Object o = getCurrentSession().merge(obj);
			getCurrentSession().delete(o);
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("deleteObject", msg);
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "删除对象出错：" + msg);
		}
	}

	/**
	 * 查询
	 * 
	 * @param entityType
	 *            查询结果要转换成的类对象
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @return ReturnInfo 注意：表的别名与实体类一一对应
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T sqlQuery(Class<T> entityType, String sql, Map<String, Object> params)
	{
		if (StringTools.isNull(sql))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "SQL语句为空！");
		}
		try
		{
			SQLQuery query = getCurrentSession().createSQLQuery(sql);
			if (entityType != null)
			{
				query.addEntity(entityType);
			}
			if (params != null)
			{
				for (String name : params.keySet())
				{
					query.setParameter(name, params.get(name));
				}
			}
			query.setMaxResults(1);
			Object obj = query.uniqueResult();
			return (T) obj;
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("sqlQuery", msg);
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "查询对象出错：" + msg);
		}
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public List<T> sqlQueryList(Class<T> entityType, String sql, Map<String, Object> params, int start, int size)
	{
		if (StringTools.isNull(sql))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "SQL语句为空！");
		}
		try
		{
			SQLQuery query = getCurrentSession().createSQLQuery(sql);
			if (entityType != null)
			{
				query.addEntity(entityType);
			}
			if (params != null)
			{
				for (String name : params.keySet())
				{
					query.setParameter(name, params.get(name));
				}
			}
			if (start >= 0)
			{
				query.setFirstResult(start);
				if (size > 0)
				{
					query.setMaxResults(size);
				}
			}
			return query.list();
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("sqlQueryList", msg);
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "查询对象出错：" + msg);
		}
	}

	/**
	 * HQL查询对象
	 * 
	 * @param entityType
	 *            查询结果要转换成的类对象
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T hqlQuery(String hql, Map<String, Object> params)
	{
		if (StringTools.isNull(hql))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "HQL语句为空！");
		}
		try
		{
			Query query = getCurrentSession().createQuery(hql).setCacheable(true);
			if (params != null)
			{
				for (String name : params.keySet())
				{
					query.setParameter(name, params.get(name));
				}
			}
			query.setMaxResults(1);
			T obj = (T) query.uniqueResult();
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("hqlQuery", msg);
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "查询对象出错：" + msg);
		}
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public List<T> hqlQueryList(String hql, Map<String, Object> params, int start, int size)
	{
		if (StringTools.isNull(hql))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "HQL语句为空！");
		}
		try
		{
			Query query = getCurrentSession().createQuery(hql);
			if (params != null)
			{
				for (String name : params.keySet())
				{
					query.setParameter(name, params.get(name));
				}
			}
			if (start >= 0)
			{
				query.setFirstResult(start);
				if (size > 0)
				{
					query.setMaxResults(size);
				}
			}
			return query.list();
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("hqlQueryList", msg);
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "查询对象出错：" + msg);
		}
	}

	/**
	 * 获取查询数据的总数（返回记录条数）
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @return long
	 */
	@Override
	public long getSqlRowCount(String sql, Map<String, Object> params)
	{
		if (StringTools.isNull(sql))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "SQL语句为空！");
		}
		try
		{
			Query query = getCurrentSession().createSQLQuery(sql);
			if (params != null)
			{
				for (String name : params.keySet())
				{
					query.setParameter(name, params.get(name));
				}
			}
			query.setMaxResults(1);
			long count = ((BigInteger) query.uniqueResult()).longValue();
			return count;
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("getSqlRowCount", msg);
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "查询记录数出错：" + msg);
		}
	}

	/**
	 * 获取查询数据的总和（对列求和）
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @return BigDecimal
	 */
	@Override
	public BigDecimal getSqlRowSum(String sql, Map<String, Object> params)
	{
		if (StringTools.isNull(sql))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "SQL语句为空！");
		}
		try
		{
			Query query = getCurrentSession().createSQLQuery(sql);
			if (params != null)
			{
				for (String name : params.keySet())
				{
					query.setParameter(name, params.get(name));
				}
			}
			query.setMaxResults(1);
			Object obj = query.uniqueResult();
			if (obj == null)
			{
				return new BigDecimal(0);
			}
			// LogTools.getInstance().info("getSqlRowSum", "sql:" + sql + ",obj:" + obj.toString());
			try
			{
				BigDecimal count = (BigDecimal) obj;
				return count;
			}
			catch (Exception e)
			{
				try
				{
					Double count = (Double) obj;
					return new BigDecimal(count);
				}
				catch (Exception e2)
				{
					BigInteger count = (BigInteger) obj;
					return new BigDecimal(count);
				}
			}
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("getSqlRowSum", msg);
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "求和出错：" + msg);
		}
	}

	/**
	 * 获取查询数据的总数（返回记录条数）
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            HQL语句的参数
	 * @return long
	 */
	@Override
	public long getHqlRowCount(String hql, Map<String, Object> params)
	{
		if (StringTools.isNull(hql))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "HQL语句为空！");
		}
		try
		{
			Query query = getCurrentSession().createQuery(hql);
			if (params != null)
			{
				for (String name : params.keySet())
				{
					query.setParameter(name, params.get(name));
				}
			}
			query.setMaxResults(1);
			long count = ((Long) query.uniqueResult()).longValue();
			return count;
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("getHqlRowCount", msg);
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "查询记录数出错：" + msg);
		}
	}

	/**
	 * 获取查询数据的总和（对列求和）
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            HQL语句的参数
	 * @return long
	 */
	@Override
	public long getHqlRowSum(String hql, Map<String, Object> params)
	{
		if (StringTools.isNull(hql))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "HQL语句为空！");
		}
		try
		{
			Query query = getCurrentSession().createQuery(hql);
			if (params != null)
			{
				for (String name : params.keySet())
				{
					query.setParameter(name, params.get(name));
				}
			}
			query.setMaxResults(1);
			long count = ((BigDecimal) query.uniqueResult()).longValue();
			return count;
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (e.getCause() != null)
			{
				msg += ":" + e.getCause().getMessage();
			}
			LogTools.getInstance().error("getHqlRowSum", msg);
			throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "求和出错：" + msg);
		}
	}
}
