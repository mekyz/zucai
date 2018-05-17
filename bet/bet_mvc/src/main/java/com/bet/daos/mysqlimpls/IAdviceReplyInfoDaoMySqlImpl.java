package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IAdviceReplyInfoDao;
import com.bet.orms.AdviceReplyInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("adviceReplyInfoDao")
public class IAdviceReplyInfoDaoMySqlImpl extends IAbstractDaoImpl<AdviceReplyInfo> implements IAdviceReplyInfoDao
{
	@Override
	public ReturnInfo checkParams(AdviceReplyInfo adviceReplyInfo)
	{
		if (adviceReplyInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "AdviceReplyInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { adviceReplyInfo.getContent(), adviceReplyInfo.getAddDateLong() + "", adviceReplyInfo.getStatus() + "", adviceReplyInfo.getAdviceId(), adviceReplyInfo.getReplyId() },
			new String[] { "content不能为空！", "addDateLong不能为空！", "status不能为空！", "adviceId不能为空！", "replyId不能为空！" });
		return returnInfo;
	}

	@Override
	public AdviceReplyInfo add(AdviceReplyInfo adviceReplyInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(adviceReplyInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(adviceReplyInfo);
	}

	@Override
	public AdviceReplyInfo update(AdviceReplyInfo adviceReplyInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(adviceReplyInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(adviceReplyInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(AdviceReplyInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteAdviceReplyInfoByReplyId(String replyId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { replyId }, new String[] { "replyId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		AdviceReplyInfo adviceReplyInfo = getAdviceReplyInfoByReplyId(replyId);
		if (adviceReplyInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "AdviceReplyInfo不存在！");
		}
		delete(adviceReplyInfo);
	}

	@Override
	public AdviceReplyInfo getAdviceReplyInfoByReplyId(String replyId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { replyId }, new String[] { "replyId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", AdviceReplyInfo.class.getSimpleName(), IAdviceReplyInfoDao.REPLY_ID, IAdviceReplyInfoDao.REPLY_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IAdviceReplyInfoDao.REPLY_ID, replyId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByReplyId(String replyId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IAdviceReplyInfoDao.REPLY_ID, replyId);
		return updateValue(AdviceReplyInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
