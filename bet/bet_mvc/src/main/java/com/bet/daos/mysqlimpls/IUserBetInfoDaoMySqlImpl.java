package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IUserBetInfoDao;
import com.bet.daos.IUserInfoDao;
import com.bet.orms.UserBetInfo;
import com.bet.orms.UserInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.LogTools;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userBetInfoDao")
public class IUserBetInfoDaoMySqlImpl extends IAbstractDaoImpl<UserBetInfo> implements IUserBetInfoDao
{
	@Override
	public ReturnInfo checkParams(UserBetInfo userBetInfo)
	{
		if (userBetInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "UserBetInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { userBetInfo.getUpdateDateLong() + "", userBetInfo.getAddDateLong() + "", userBetInfo.getUserId(), userBetInfo.getStatus() + "", userBetInfo.getMoney() + "",
				userBetInfo.getMoneyUnit() + "", userBetInfo.getTeamProfitStatus() + "", userBetInfo.getBetId(), userBetInfo.getSysStatus() + "", userBetInfo.getProfitId() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "userId不能为空！", "status不能为空！", "money不能为空！", "moneyUnit不能为空！", "teamProfitStatus不能为空！", "betId不能为空！", "sysStatus不能为空！",
				"profitId不能为空！" });
		return returnInfo;
	}

	@Override
	public UserBetInfo add(UserBetInfo userBetInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userBetInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(userBetInfo);
	}

	@Override
	public UserBetInfo update(UserBetInfo userBetInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userBetInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(userBetInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(UserBetInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteUserBetInfoByBetId(String betId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { betId }, new String[] { "betId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		UserBetInfo userBetInfo = getUserBetInfoByBetId(betId);
		if (userBetInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserBetInfo不存在！");
		}
		delete(userBetInfo);
	}

	@Override
	public UserBetInfo getUserBetInfoByBetId(String betId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { betId }, new String[] { "betId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", UserBetInfo.class.getSimpleName(), IUserBetInfoDao.BET_ID, IUserBetInfoDao.BET_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserBetInfoDao.BET_ID, betId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByBetId(String betId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserBetInfoDao.BET_ID, betId);
		return updateValue(UserBetInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */

	@Override
	public long getSum(String colName, String whereClause, Map<String, Object> params, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		if (params == null)
		{
			params = new HashMap<>();
		}
		String sql = String.format("select sum(%s) %s", StringTools.getTableColumnName(colName), buildSql(UserBetInfo.class, whereClause, null, searchInfos, params));
		return baseDao.getSqlRowSum(sql, params).longValue();
	}

	@Override
	public List<UserBetInfo> getUserListByUserType(byte userType, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		String sql = String.format("select * from %s as a where exists (select * from %s as b where a.%s = b.%s and b.%s = :%s)", StringTools.getTableName(UserBetInfo.class),
			StringTools.getTableName(UserInfo.class), StringTools.getTableColumnName(IUserBetInfoDao.USER_ID), StringTools.getTableColumnName(IUserBetInfoDao.USER_ID),
			StringTools.getTableColumnName(IUserInfoDao.USER_TYPE), StringTools.getTableColumnName(IUserInfoDao.USER_TYPE));
		Map<String, Object> params = new HashMap<>();
		params.put(StringTools.getTableColumnName(IUserInfoDao.USER_TYPE), userType);
		return baseDao.sqlQueryList(UserBetInfo.class, sql, params, start, size);
	}

	@Override
	public long getTeamUserTotalMoney(String userId, String whereClause, Map<String, Object> params, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		String whe = buildSql(UserBetInfo.class, whereClause, null, searchInfos, params);
		String sql = String.format("select sum(%s) %s %s %s in (select %s from %s as b where b.%s like '%s')", StringTools.getTableColumnName(IUserBetInfoDao.MONEY), whe,
			whe.indexOf("where") < 0 ? "where" : "and", StringTools.getTableColumnName(IUserBetInfoDao.USER_ID), StringTools.getTableColumnName(IUserInfoDao.USER_ID),
			StringTools.getTableName(UserInfo.class), StringTools.getTableColumnName(IUserInfoDao.PARENTS_ID), "%\"userId\":\"" + userId + "\"%");
		LogTools.getInstance().info("getTeamUserTotalMoney", "sql:" + sql);
		return baseDao.getSqlRowSum(sql, params).longValue();
	}
}
