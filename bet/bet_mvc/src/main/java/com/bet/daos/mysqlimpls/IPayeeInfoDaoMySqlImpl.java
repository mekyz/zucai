package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IPayeeInfoDao;
import com.bet.orms.PayeeInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("payeeInfoDao")
public class IPayeeInfoDaoMySqlImpl extends IAbstractDaoImpl<PayeeInfo> implements IPayeeInfoDao
{
	@Override
	public ReturnInfo checkParams(PayeeInfo payeeInfo)
	{
		if (payeeInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "PayeeInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { payeeInfo.getUpdateDateLong() + "", payeeInfo.getAddDateLong() + "", payeeInfo.getStatus() + "", payeeInfo.getSortIndex() + "", payeeInfo.getBankCardId(),
				payeeInfo.getPayeeName(), payeeInfo.getPayeeId(), payeeInfo.getBankName() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "sortIndex不能为空！", "bankCardId不能为空！", "payeeName不能为空！", "payeeId不能为空！", "bankName不能为空！" });
		return returnInfo;
	}

	@Override
	public PayeeInfo add(PayeeInfo payeeInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(payeeInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(payeeInfo);
	}

	@Override
	public PayeeInfo update(PayeeInfo payeeInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(payeeInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(payeeInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(PayeeInfo.class, valueMap, whereMap);
	}

	@Override
	public void deletePayeeInfoByPayeeId(String payeeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { payeeId }, new String[] { "payeeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		PayeeInfo payeeInfo = getPayeeInfoByPayeeId(payeeId);
		if (payeeInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "PayeeInfo不存在！");
		}
		delete(payeeInfo);
	}

	@Override
	public PayeeInfo getPayeeInfoByPayeeId(String payeeId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { payeeId }, new String[] { "payeeId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", PayeeInfo.class.getSimpleName(), IPayeeInfoDao.PAYEE_ID, IPayeeInfoDao.PAYEE_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IPayeeInfoDao.PAYEE_ID, payeeId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByPayeeId(String payeeId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IPayeeInfoDao.PAYEE_ID, payeeId);
		return updateValue(PayeeInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
