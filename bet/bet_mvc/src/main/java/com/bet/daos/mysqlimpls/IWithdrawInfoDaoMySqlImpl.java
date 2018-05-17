package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IWithdrawInfoDao;
import com.bet.orms.WithdrawInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("withdrawInfoDao")
public class IWithdrawInfoDaoMySqlImpl extends IAbstractDaoImpl<WithdrawInfo> implements IWithdrawInfoDao
{
	@Override
	public ReturnInfo checkParams(WithdrawInfo withdrawInfo)
	{
		if (withdrawInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "WithdrawInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { withdrawInfo.getUpdateDateLong() + "", withdrawInfo.getAddDateLong() + "", withdrawInfo.getUserId(), withdrawInfo.getStatus() + "", withdrawInfo.getMoney() + "",
				withdrawInfo.getMoneyUnit() + "", withdrawInfo.getWithdrawId(), withdrawInfo.getFee() + "", withdrawInfo.getUserMoney() + "", withdrawInfo.getBankCardId(), withdrawInfo.getPayeeName(),
				withdrawInfo.getBankName() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "userId不能为空！", "status不能为空！", "money不能为空！", "moneyUnit不能为空！", "withdrawId不能为空！", "fee不能为空！", "userMoney不能为空！", "bankCardId不能为空！",
				"payeeName不能为空！", "bankName不能为空！" });
		return returnInfo;
	}

	@Override
	public WithdrawInfo add(WithdrawInfo withdrawInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(withdrawInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(withdrawInfo);
	}

	@Override
	public WithdrawInfo update(WithdrawInfo withdrawInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(withdrawInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(withdrawInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(WithdrawInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteWithdrawInfoByWithdrawId(String withdrawId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { withdrawId }, new String[] { "withdrawId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		WithdrawInfo withdrawInfo = getWithdrawInfoByWithdrawId(withdrawId);
		if (withdrawInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "WithdrawInfo不存在！");
		}
		delete(withdrawInfo);
	}

	@Override
	public WithdrawInfo getWithdrawInfoByWithdrawId(String withdrawId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { withdrawId }, new String[] { "withdrawId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", WithdrawInfo.class.getSimpleName(), IWithdrawInfoDao.WITHDRAW_ID, IWithdrawInfoDao.WITHDRAW_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IWithdrawInfoDao.WITHDRAW_ID, withdrawId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByWithdrawId(String withdrawId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IWithdrawInfoDao.WITHDRAW_ID, withdrawId);
		return updateValue(WithdrawInfo.class, valueMap, whereMap);
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
		String sql = String.format("select sum(%s) %s", StringTools.getTableColumnName(colName), buildSql(WithdrawInfo.class, whereClause, null, searchInfos, params));
		return baseDao.getSqlRowSum(sql, params).longValue();
	}
}
