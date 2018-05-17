package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IMsgInfoDao;
import com.bet.orms.MsgInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("msgInfoDao")
public class IMsgInfoDaoMySqlImpl extends IAbstractDaoImpl<MsgInfo> implements IMsgInfoDao
{
	@Override
	public ReturnInfo checkParams(MsgInfo msgInfo)
	{
		if (msgInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "MsgInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { msgInfo.getUpdateDateLong() + "", msgInfo.getAddDateLong() + "", msgInfo.getUserId(), msgInfo.getStatus() + "", msgInfo.getShortContent(), msgInfo.getTitle(),
				msgInfo.getSortId(), msgInfo.getMsgId() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "userId不能为空！", "status不能为空！", "shortContent不能为空！", "title不能为空！", "sortId不能为空！", "msgId不能为空！" });
		return returnInfo;
	}

	@Override
	public MsgInfo add(MsgInfo msgInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(msgInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(msgInfo);
	}

	@Override
	public MsgInfo update(MsgInfo msgInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(msgInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(msgInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(MsgInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteMsgInfoByMsgId(String msgId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { msgId }, new String[] { "msgId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		MsgInfo msgInfo = getMsgInfoByMsgId(msgId);
		if (msgInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "MsgInfo不存在！");
		}
		delete(msgInfo);
	}

	@Override
	public MsgInfo getMsgInfoByMsgId(String msgId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { msgId }, new String[] { "msgId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", MsgInfo.class.getSimpleName(), IMsgInfoDao.MSG_ID, IMsgInfoDao.MSG_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IMsgInfoDao.MSG_ID, msgId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByMsgId(String msgId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMsgInfoDao.MSG_ID, msgId);
		return updateValue(MsgInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
