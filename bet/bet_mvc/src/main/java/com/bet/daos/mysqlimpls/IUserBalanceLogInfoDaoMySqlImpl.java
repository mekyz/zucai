package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IUserBalanceLogInfoDao;
import com.bet.orms.UserBalanceLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userBalanceLogInfoDao")
public class IUserBalanceLogInfoDaoMySqlImpl extends IAbstractDaoImpl<UserBalanceLogInfo> implements IUserBalanceLogInfoDao
{
	@Override
	public ReturnInfo checkParams(UserBalanceLogInfo userBalanceLogInfo)
	{
		if (userBalanceLogInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "UserBalanceLogInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { userBalanceLogInfo.getAddDateLong() + "", userBalanceLogInfo.getUserId(), userBalanceLogInfo.getMoney() + "", userBalanceLogInfo.getMoneyUnit() + "" },
			new String[] { "addDateLong不能为空！", "userId不能为空！", "money不能为空！", "moneyUnit不能为空！" });
		return returnInfo;
	}

	@Override
	public UserBalanceLogInfo add(UserBalanceLogInfo userBalanceLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userBalanceLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(userBalanceLogInfo);
	}

	@Override
	public UserBalanceLogInfo update(UserBalanceLogInfo userBalanceLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userBalanceLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(userBalanceLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(UserBalanceLogInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteUserBalanceLogInfoById(int id) throws HibernateJsonResultException
	{
		UserBalanceLogInfo userBalanceLogInfo = getUserBalanceLogInfoById(id);
		if (userBalanceLogInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserBalanceLogInfo不存在！");
		}
		delete(userBalanceLogInfo);
	}

	@Override
	public UserBalanceLogInfo getUserBalanceLogInfoById(int id) throws HibernateJsonResultException
	{
		String hql = String.format("from %s where %s = :%s", UserBalanceLogInfo.class.getSimpleName(), IUserBalanceLogInfoDao.ID, IUserBalanceLogInfoDao.ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserBalanceLogInfoDao.ID, id);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserBalanceLogInfoDao.ID, id);
		return updateValue(UserBalanceLogInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}

	/* 自定义方法 */
	@Override
	public long getUserLogTotalMoney(String whereClause, Map<String, Object> params, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		if (params == null)
		{
			params = new HashMap<>();
		}
		String sql = String.format("select sum(%s) %s", StringTools.getTableColumnName(IUserBalanceLogInfoDao.MONEY), buildSql(UserBalanceLogInfo.class, whereClause, null, searchInfos, params));
		return baseDao.getSqlRowSum(sql, params).longValue();
	}
}
