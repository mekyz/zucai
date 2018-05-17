package com.lrcall.daos.mysqlimpls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.ConstValues;
import com.lrcall.common.utils.LogTools;
import com.lrcall.common.utils.TableOrderSortComparator;
import com.lrcall.common.utils.TimeTools;
import com.lrcall.daos.IAbstractDao;
import com.lrcall.daos.IBaseDao;
import com.lrcall.lrweb.common.enums.SqlOrderType;
import com.lrcall.lrweb.common.utils.StringTools;

/**
 * 服务基类
 * 
 * @author libit
 */
public abstract class IAbstractDaoImpl<T> implements IAbstractDao<T>
{
	@Autowired
	protected IBaseDao<T> baseDao;
	public static final LogTools log = LogTools.getInstance();
	private static long rd = 0;

	/**
	 * 产生ID
	 * 
	 * @return
	 */
	@Override
	public String genId()
	{
		if (rd == 9999)
		{
			rd = 0;
		}
		String id = String.format("%d%04d", TimeTools.getFromMillis(), rd++);
		return id;
	}

	/**
	 * 添加对象到数据库
	 * 
	 * @param t
	 * @return
	 */
	@Override
	public T add(T t)
	{
		baseDao.addObject(t);
		return t;
	}

	/**
	 * 更新对象到数据库
	 * 
	 * @param t
	 * @return
	 */
	@Override
	public T update(T t)
	{
		return baseDao.updateObject(t);
	}

	/**
	 * 删除对象
	 * 
	 * @param t
	 * @return
	 */
	@Override
	public void delete(T t)
	{
		baseDao.deleteObject(t);
	}

	@Override
	public long updateValue(Class<T> t, Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		Map<String, Object> params = new HashMap<>();
		String sql = String.format("update %s ", StringTools.getTableName(t));
		if (valueMap != null && valueMap.size() > 0)
		{
			String setValue = "";
			int i = 0;
			for (String key : valueMap.keySet())
			{
				if (i == 0)
				{
					setValue += String.format(" set %s = :%s ", StringTools.getTableColumnName(key), key);
				}
				else
				{
					setValue += String.format(" ,%s = :%s ", StringTools.getTableColumnName(key), key);
				}
				params.put(key, valueMap.get(key));
				i++;
			}
			sql += setValue;
		}
		if (whereMap != null && whereMap.size() > 0)
		{
			String whereValue = "";
			int i = 0;
			for (String key : whereMap.keySet())
			{
				if (i == 0)
				{
					whereValue += String.format(" where %s = :%s1 ", StringTools.getTableColumnName(key), key);
				}
				else
				{
					whereValue += String.format(" and %s = :%s1 ", StringTools.getTableColumnName(key), key);
				}
				params.put(key + "1", whereMap.get(key));
				i++;
			}
			sql += whereValue;
		}
		int result = baseDao.sqlUpdate(sql, params);
		LogTools.getInstance().info("updateValue", "updateValue sql:" + sql + ",result:" + result + ".");
		return result;
	}

	@Override
	public List<T> getList(Class<T> c, String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
	{
		if (params == null)
		{
			params = new HashMap<>();
		}
		String hql = buildHql(c, whereClause, orderInfos, searchInfos, params);
		// LogTools.getInstance().info("getList", "hql:" + hql + ".");
		List<T> list = baseDao.hqlQueryList(hql, params, start, size);
		if (list == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "列表为空！");
		}
		return list;
	}

	@Override
	public long getListCount(Class<T> c, String whereClause, Map<String, Object> params, List<TableSearchInfo> searchInfos)
	{
		if (params == null)
		{
			params = new HashMap<>();
		}
		String hql = buildHql(c, whereClause, null, searchInfos, params);
		long total = baseDao.getHqlRowCount("select count(*) " + hql.substring(hql.indexOf("from"), hql.length()), params);
		return total;
	}

	/**
	 * 组建HQL语句
	 * 
	 * @param c
	 * @param whereClause
	 * @param orderInfos
	 * @param searchInfos
	 * @return
	 */
	private String buildHql(Class<T> c, String whereClause, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Map<String, Object> params)
	{
		// if (params == null)
		// {
		// params = new HashMap<>();
		// }
		String hql = String.format("from %s ", c.getSimpleName());
		String where = "where";
		if (!StringTools.isNull(whereClause))
		{
			hql += String.format("%s ", whereClause);
			where = "and";
		}
		boolean isLeft = false;
		if (searchInfos != null && searchInfos.size() > 0)
		{
			int i = 0;
			for (TableSearchInfo searchInfo : searchInfos)
			{
				if (i == 0)
				{
					hql += String.format("%s (", where);
				}
				if (ConstValues.LEFT_BRACKET.equals(searchInfo.getSearchColumnName()))
				{
					if (i == 0)
					{
						hql += String.format(" %s ", ConstValues.LEFT_BRACKET);
					}
					else
					{
						hql += String.format(" %s %s ", where, ConstValues.LEFT_BRACKET);
					}
					isLeft = true;
				}
				else if (ConstValues.RIGHT_BRACKET.equals(searchInfo.getSearchColumnName()))
				{
					hql += String.format(" %s ", ConstValues.RIGHT_BRACKET);
				}
				else
				{
					if (!isLeft)
					{
						if (i != 0)
						{
							hql += String.format("%s ", where);
						}
					}
					else
					{
						isLeft = false;
					}
					if (searchInfo.isbFullMatch())
					{
						// hql += String.format("%s (%s = :%s) ", where, searchInfo.getSearchColumnName(), "eq_" + searchInfo.getSearchColumnName());
						// params.put("eq_" + searchInfo.getSearchColumnName(), searchInfo.getSearchValue());
						hql += String.format("(%s = '%s') ", searchInfo.getSearchColumnName(), searchInfo.getSearchValue());
					}
					else
					{
						// hql += String.format("(%s like :%s) ", where, searchInfo.getSearchColumnName(), "like_" + searchInfo.getSearchColumnName());
						// params.put("like_" + searchInfo.getSearchColumnName(), "%" + searchInfo.getSearchValue() + "%");
						hql += String.format("(%s like '%s') ", searchInfo.getSearchColumnName(), "%" + searchInfo.getSearchValue() + "%");
					}
					if (searchInfo.isbOr())
					{
						where = "or";
					}
					else
					{
						where = "and";
					}
				}
				i++;
			}
			hql += ")";
		}
		if (orderInfos != null && orderInfos.size() > 0)
		{
			orderInfos.sort(new TableOrderSortComparator());
			String orderBy = " order by";
			int i = 0;
			for (TableOrderInfo orderInfo : orderInfos)
			{
				if (i == 0)
				{
					hql += String.format("%s %s %s", orderBy, orderInfo.getOrderColumnName(), isDesc(orderInfo.getOrderDir()));
				}
				else
				{
					hql += String.format(",%s %s", orderInfo.getOrderColumnName(), isDesc(orderInfo.getOrderDir()));
				}
				i++;
			}
		}
		return hql;
	}

	/**
	 * 组建SQL语句
	 * 
	 * @param c
	 * @param whereClause
	 * @param orderInfos
	 * @param searchInfos
	 * @return
	 */
	public String buildSql(Class<T> c, String whereClause, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Map<String, Object> params)
	{
		// if (params == null)
		// {
		// params = new HashMap<>();
		// }
		String sql = String.format("from %s ", StringTools.getTableName(c));
		String where = "where";
		if (!StringTools.isNull(whereClause))
		{
			sql += String.format("%s ", whereClause);
			where = "and";
		}
		boolean isLeft = false;
		if (searchInfos != null && searchInfos.size() > 0)
		{
			int i = 0;
			for (TableSearchInfo searchInfo : searchInfos)
			{
				if (i == 0)
				{
					sql += String.format("%s (", where);
				}
				if (ConstValues.LEFT_BRACKET.equals(searchInfo.getSearchColumnName()))
				{
					if (i == 0)
					{
						sql += String.format(" %s ", ConstValues.LEFT_BRACKET);
					}
					else
					{
						sql += String.format(" %s %s ", where, ConstValues.LEFT_BRACKET);
					}
					isLeft = true;
				}
				else if (ConstValues.RIGHT_BRACKET.equals(searchInfo.getSearchColumnName()))
				{
					sql += String.format(" %s ", ConstValues.RIGHT_BRACKET);
				}
				else
				{
					if (!isLeft)
					{
						if (i != 0)
						{
							sql += String.format("%s ", where);
						}
					}
					else
					{
						isLeft = false;
					}
					if (searchInfo.isbFullMatch())
					{
						// hql += String.format("%s (%s = :%s) ", where, searchInfo.getSearchColumnName(), "eq_" + searchInfo.getSearchColumnName());
						// params.put("eq_" + searchInfo.getSearchColumnName(), searchInfo.getSearchValue());
						sql += String.format("(%s = '%s') ", StringTools.getTableColumnName(searchInfo.getSearchColumnName()), searchInfo.getSearchValue());
					}
					else
					{
						// sql += String.format("(%s like :%s) ", where, StringTools.getTableColumnName(searchInfo.getSearchColumnName()), "like_" + searchInfo.getSearchColumnName());
						// params.put("like_" + searchInfo.getSearchColumnName(), "%" + searchInfo.getSearchValue() + "%");
						sql += String.format("(%s like '%s') ", StringTools.getTableColumnName(searchInfo.getSearchColumnName()), "%" + searchInfo.getSearchValue() + "%");
					}
					if (searchInfo.isbOr())
					{
						where = "or";
					}
					else
					{
						where = "and";
					}
				}
				i++;
			}
			sql += ")";
		}
		if (orderInfos != null && orderInfos.size() > 0)
		{
			orderInfos.sort(new TableOrderSortComparator());
			String orderBy = " order by";
			int i = 0;
			for (TableOrderInfo orderInfo : orderInfos)
			{
				if (i == 0)
				{
					sql += String.format("%s %s %s", orderBy, StringTools.getTableColumnName(orderInfo.getOrderColumnName()), isDesc(orderInfo.getOrderDir()));
				}
				else
				{
					sql += String.format(",%s %s", StringTools.getTableColumnName(orderInfo.getOrderColumnName()), isDesc(orderInfo.getOrderDir()));
				}
				i++;
			}
		}
		return sql;
	}

	private String isDesc(String orderDir)
	{
		return (!StringTools.isNull(orderDir) && orderDir.equalsIgnoreCase(SqlOrderType.DESC.getType())) ? SqlOrderType.DESC.getType() : SqlOrderType.ASC.getType();
	}

	@Override
	public String buildSqlTimeWhere(String dateColName, Long startDateLong, Long endDateLong)
	{
		String whereClause = "";
		if (startDateLong != null)
		{
			if (endDateLong != null)
			{
				whereClause = String.format("where (%s >= %d and %s <= %d)", StringTools.getTableColumnName(dateColName), startDateLong, StringTools.getTableColumnName(dateColName), endDateLong);
			}
			else
			{
				whereClause = String.format("where (%s >= %d)", StringTools.getTableColumnName(dateColName), startDateLong);
			}
		}
		else
		{
			if (endDateLong != null)
			{
				whereClause = String.format("where (%s <= %d)", StringTools.getTableColumnName(dateColName), endDateLong);
			}
		}
		return whereClause;
	}

	@Override
	public String buildHqlTimeWhere(String dateColName, Long startDateLong, Long endDateLong)
	{
		String whereClause = "";
		if (startDateLong != null)
		{
			if (endDateLong != null)
			{
				whereClause = String.format("where (%s >= %d and %s <= %d)", dateColName, startDateLong, dateColName, endDateLong);
			}
			else
			{
				whereClause = String.format("where (%s >= %d)", dateColName, startDateLong);
			}
		}
		else
		{
			if (endDateLong != null)
			{
				whereClause = String.format("where (%s <= %d)", dateColName, endDateLong);
			}
		}
		return whereClause;
	}
}
