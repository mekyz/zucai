package com.bet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.ISmsCodeInfoDao;
import com.bet.orms.SmsCodeInfo;
import com.bet.services.ISmsCodeInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.enums.SqlOrderType;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("smsCodeInfoService")
public class ISmsCodeInfoServiceImpl implements ISmsCodeInfoService
{
	@Autowired
	private ISmsCodeInfoDao smsCodeInfoDao;

	@Override
	public String genId()
	{
		return smsCodeInfoDao.genId();
	}

	@Override
	public SmsCodeInfo addSmsCodeInfo(SmsCodeInfo smsCodeInfo) throws HibernateJsonResultException
	{
		long current = System.currentTimeMillis();
		if (smsCodeInfo.getAddDateLong() < 1)
		{
			smsCodeInfo.setAddDateLong(current);
		}
		return smsCodeInfoDao.add(smsCodeInfo);
	}

	@Override
	public SmsCodeInfo updateSmsCodeInfo(SmsCodeInfo smsCodeInfo) throws HibernateJsonResultException
	{
		return smsCodeInfoDao.update(smsCodeInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return smsCodeInfoDao.updateValue(SmsCodeInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteSmsCodeInfo(SmsCodeInfo smsCodeInfo) throws HibernateJsonResultException
	{
		smsCodeInfoDao.delete(smsCodeInfo);
	}

	@Override
	public void deleteSmsCodeInfoById(int id) throws HibernateJsonResultException
	{
		smsCodeInfoDao.deleteSmsCodeInfoById(id);
	}

	@Override
	public SmsCodeInfo getSmsCodeInfoById(int id) throws HibernateJsonResultException
	{
		return smsCodeInfoDao.getSmsCodeInfoById(id);
	}

	@Override
	public boolean updateValueById(int id, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ISmsCodeInfoDao.ID, id);
		return smsCodeInfoDao.updateValue(SmsCodeInfo.class, valueMap, whereMap) > 0;
	}

	public List<SmsCodeInfo> getSmsCodeInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return smsCodeInfoDao.getList(SmsCodeInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<SmsCodeInfo> getSmsCodeInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return smsCodeInfoDao.getList(SmsCodeInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getSmsCodeInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return smsCodeInfoDao.getListCount(SmsCodeInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public SmsCodeInfo getSmsCodeInfoByNumber(String number, String type) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { number, type }, new String[] { "手机号码不能为空！", "验证码类型不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String whereClause = String.format("where %s = :%s and %s = :%s", ISmsCodeInfoDao.NUMBER, ISmsCodeInfoDao.NUMBER, ISmsCodeInfoDao.VERIFY_TYPE, ISmsCodeInfoDao.VERIFY_TYPE);
		Map<String, Object> params = new HashMap<>();
		params.put(ISmsCodeInfoDao.NUMBER, number);
		params.put(ISmsCodeInfoDao.VERIFY_TYPE, type);
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		orderInfos.add(new TableOrderInfo(ISmsCodeInfoDao.ADD_DATE_LONG, SqlOrderType.DESC.getType()));
		List<SmsCodeInfo> list = smsCodeInfoDao.getList(SmsCodeInfo.class, whereClause, params, 0, 1, orderInfos, null);
		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}
}
