package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IUserRechargeInfoDao;
import com.bet.orms.UserRechargeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userRechargeInfoDao")
public class IUserRechargeInfoDaoMySqlImpl extends IAbstractDaoImpl<UserRechargeInfo> implements IUserRechargeInfoDao
{
	@Override
	public ReturnInfo checkParams(UserRechargeInfo userRechargeInfo)
	{
		if (userRechargeInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "UserRechargeInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { userRechargeInfo.getUpdateDateLong() + "", userRechargeInfo.getAddDateLong() + "", userRechargeInfo.getUserId(), userRechargeInfo.getStatus() + "",
				userRechargeInfo.getMoney() + "", userRechargeInfo.getMoneyUnit() + "", userRechargeInfo.getPayeeBankCardId(), userRechargeInfo.getRechargeId(), userRechargeInfo.getPayName(),
				userRechargeInfo.getPayBankName(), userRechargeInfo.getPayeeBankName(), userRechargeInfo.getPayBankCardId(), userRechargeInfo.getPayPicUrl(), userRechargeInfo.getFee() + "",
				userRechargeInfo.getUserMoney() + "", userRechargeInfo.getPayeeName() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "userId不能为空！", "status不能为空！", "money不能为空！", "moneyUnit不能为空！", "payeeBankCardId不能为空！", "rechargeId不能为空！", "payName不能为空！",
				"payBankName不能为空！", "payeeBankName不能为空！", "payBankCardId不能为空！", "payPicUrl不能为空！", "fee不能为空！", "userMoney不能为空！", "payeeName不能为空！" });
		return returnInfo;
	}

	@Override
	public UserRechargeInfo add(UserRechargeInfo userRechargeInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userRechargeInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(userRechargeInfo);
	}

	@Override
	public UserRechargeInfo update(UserRechargeInfo userRechargeInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userRechargeInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(userRechargeInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(UserRechargeInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteUserRechargeInfoByRechargeId(String rechargeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { rechargeId }, new String[] { "rechargeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		UserRechargeInfo userRechargeInfo = getUserRechargeInfoByRechargeId(rechargeId);
		if (userRechargeInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserRechargeInfo不存在！");
		}
		delete(userRechargeInfo);
	}

	@Override
	public UserRechargeInfo getUserRechargeInfoByRechargeId(String rechargeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { rechargeId }, new String[] { "rechargeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", UserRechargeInfo.class.getSimpleName(), IUserRechargeInfoDao.RECHARGE_ID, IUserRechargeInfoDao.RECHARGE_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserRechargeInfoDao.RECHARGE_ID, rechargeId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByRechargeId(String rechargeId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserRechargeInfoDao.RECHARGE_ID, rechargeId);
		return updateValue(UserRechargeInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */

	@Override
	public long getSum(String colName, String whereClause, Map<String, Object> params, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		if (params == null)
		{
			params = new HashMap<>();
		}
		String sql = String.format("select sum(%s) %s", StringTools.getTableColumnName(colName), buildSql(UserRechargeInfo.class, whereClause, null, searchInfos, params));
		return baseDao.getSqlRowSum(sql, params).longValue();
	}
}
