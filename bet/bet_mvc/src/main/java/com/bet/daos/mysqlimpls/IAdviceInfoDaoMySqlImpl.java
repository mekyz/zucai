package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IAdviceInfoDao;
import com.bet.orms.AdviceInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("adviceInfoDao")
public class IAdviceInfoDaoMySqlImpl extends IAbstractDaoImpl<AdviceInfo> implements IAdviceInfoDao
{
	@Override
	public ReturnInfo checkParams(AdviceInfo adviceInfo)
	{
		if (adviceInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "AdviceInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { adviceInfo.getContent(), adviceInfo.getAddDateLong() + "", adviceInfo.getStatus() + "", adviceInfo.getPlatform(), adviceInfo.getReplyStatus() + "",
				adviceInfo.getReadStatus() + "", adviceInfo.getAdviceId() },
			new String[] { "content不能为空！", "addDateLong不能为空！", "status不能为空！", "platform不能为空！", "replyStatus不能为空！", "readStatus不能为空！", "adviceId不能为空！" });
		return returnInfo;
	}

	@Override
	public AdviceInfo add(AdviceInfo adviceInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(adviceInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(adviceInfo);
	}

	@Override
	public AdviceInfo update(AdviceInfo adviceInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(adviceInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(adviceInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(AdviceInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteAdviceInfoByAdviceId(String adviceId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { adviceId }, new String[] { "adviceId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		AdviceInfo adviceInfo = getAdviceInfoByAdviceId(adviceId);
		if (adviceInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "AdviceInfo不存在！");
		}
		delete(adviceInfo);
	}

	@Override
	public AdviceInfo getAdviceInfoByAdviceId(String adviceId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { adviceId }, new String[] { "adviceId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", AdviceInfo.class.getSimpleName(), IAdviceInfoDao.ADVICE_ID, IAdviceInfoDao.ADVICE_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IAdviceInfoDao.ADVICE_ID, adviceId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByAdviceId(String adviceId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IAdviceInfoDao.ADVICE_ID, adviceId);
		return updateValue(AdviceInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
