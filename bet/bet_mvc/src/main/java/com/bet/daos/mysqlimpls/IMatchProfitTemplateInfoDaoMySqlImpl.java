package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IMatchProfitTemplateInfoDao;
import com.bet.orms.MatchProfitTemplateInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("matchProfitTemplateInfoDao")
public class IMatchProfitTemplateInfoDaoMySqlImpl extends IAbstractDaoImpl<MatchProfitTemplateInfo> implements IMatchProfitTemplateInfoDao
{
	@Override
	public ReturnInfo checkParams(MatchProfitTemplateInfo matchProfitTemplateInfo)
	{
		if (matchProfitTemplateInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "MatchProfitTemplateInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { matchProfitTemplateInfo.getUpdateDateLong() + "", matchProfitTemplateInfo.getAddDateLong() + "", matchProfitTemplateInfo.getStatus() + "",
				matchProfitTemplateInfo.getScore1() + "", matchProfitTemplateInfo.getScore2() + "", matchProfitTemplateInfo.getTemplateId(), matchProfitTemplateInfo.getProfitPercent() + "",
				matchProfitTemplateInfo.getAmount() + "", matchProfitTemplateInfo.getMatchType() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "score1不能为空！", "score2不能为空！", "templateId不能为空！", "profitPercent不能为空！", "amount不能为空！", "matchType不能为空！" });
		return returnInfo;
	}

	@Override
	public MatchProfitTemplateInfo add(MatchProfitTemplateInfo matchProfitTemplateInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(matchProfitTemplateInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(matchProfitTemplateInfo);
	}

	@Override
	public MatchProfitTemplateInfo update(MatchProfitTemplateInfo matchProfitTemplateInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(matchProfitTemplateInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(matchProfitTemplateInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(MatchProfitTemplateInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteMatchProfitTemplateInfoByTemplateId(String templateId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { templateId }, new String[] { "templateId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		MatchProfitTemplateInfo matchProfitTemplateInfo = getMatchProfitTemplateInfoByTemplateId(templateId);
		if (matchProfitTemplateInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "MatchProfitTemplateInfo不存在！");
		}
		delete(matchProfitTemplateInfo);
	}

	@Override
	public MatchProfitTemplateInfo getMatchProfitTemplateInfoByTemplateId(String templateId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { templateId }, new String[] { "templateId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", MatchProfitTemplateInfo.class.getSimpleName(), IMatchProfitTemplateInfoDao.TEMPLATE_ID, IMatchProfitTemplateInfoDao.TEMPLATE_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IMatchProfitTemplateInfoDao.TEMPLATE_ID, templateId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByTemplateId(String templateId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IMatchProfitTemplateInfoDao.TEMPLATE_ID, templateId);
		return updateValue(MatchProfitTemplateInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
