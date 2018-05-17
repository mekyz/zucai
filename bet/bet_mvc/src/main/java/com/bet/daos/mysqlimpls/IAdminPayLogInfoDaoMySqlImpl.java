package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IAdminPayLogInfoDao;
import com.bet.orms.AdminPayLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("adminPayLogInfoDao")
public class IAdminPayLogInfoDaoMySqlImpl extends IAbstractDaoImpl<AdminPayLogInfo> implements IAdminPayLogInfoDao
{
	@Override
	public ReturnInfo checkParams(AdminPayLogInfo adminPayLogInfo)
	{
		if (adminPayLogInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "AdminPayLogInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { adminPayLogInfo.getAddDateLong() + "", adminPayLogInfo.getMoney() + "", adminPayLogInfo.getPayType(),
			adminPayLogInfo.getAdminId(), adminPayLogInfo.getPayId(), adminPayLogInfo.getMoneyUnit() + "" },
			new String[] { "addDateLong不能为空！", "money不能为空！", "payType不能为空！", "adminId不能为空！", "payId不能为空！", "moneyUnit不能为空！" });
		return returnInfo;
	}

	@Override
	public AdminPayLogInfo add(AdminPayLogInfo adminPayLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(adminPayLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(adminPayLogInfo);
	}

	@Override
	public AdminPayLogInfo update(AdminPayLogInfo adminPayLogInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(adminPayLogInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(adminPayLogInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(AdminPayLogInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteAdminPayLogInfoById(int id) throws HibernateJsonResultException
	{
		AdminPayLogInfo adminPayLogInfo = getAdminPayLogInfoById(id);
		if (adminPayLogInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "AdminPayLogInfo不存在！");
		}
		delete(adminPayLogInfo);
	}

	@Override
	public AdminPayLogInfo getAdminPayLogInfoById(int id) throws HibernateJsonResultException
	{
		String hql = String.format("from %s where %s = :%s", AdminPayLogInfo.class.getSimpleName(), IAdminPayLogInfoDao.ID, IAdminPayLogInfoDao.ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IAdminPayLogInfoDao.ID, id);
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
		whereMap.put(IAdminPayLogInfoDao.ID, id);
		return updateValue(AdminPayLogInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
