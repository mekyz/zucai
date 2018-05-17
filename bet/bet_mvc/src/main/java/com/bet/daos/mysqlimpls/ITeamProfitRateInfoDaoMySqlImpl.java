package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.ITeamProfitRateInfoDao;
import com.bet.orms.TeamProfitRateInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("teamProfitRateInfoDao")
public class ITeamProfitRateInfoDaoMySqlImpl extends IAbstractDaoImpl<TeamProfitRateInfo> implements ITeamProfitRateInfoDao
{
	@Override
	public ReturnInfo checkParams(TeamProfitRateInfo teamProfitRateInfo)
	{
		if (teamProfitRateInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "TeamProfitRateInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { teamProfitRateInfo.getName(), teamProfitRateInfo.getUpdateDateLong() + "", teamProfitRateInfo.getAddDateLong() + "", teamProfitRateInfo.getStatus() + "",
				teamProfitRateInfo.getType6() + "", teamProfitRateInfo.getType7() + "", teamProfitRateInfo.getType5() + "", teamProfitRateInfo.getType2() + "", teamProfitRateInfo.getType1() + "",
				teamProfitRateInfo.getType3() + "", teamProfitRateInfo.getRateId(), teamProfitRateInfo.getType4() + "", teamProfitRateInfo.getSameLevel() + "" },
			new String[] { "name不能为空！", "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "type6不能为空！", "type7不能为空！", "type5不能为空！", "type2不能为空！", "type1不能为空！", "type3不能为空！", "rateId不能为空！",
				"type4不能为空！", "sameLevel不能为空！" });
		return returnInfo;
	}

	@Override
	public TeamProfitRateInfo add(TeamProfitRateInfo teamProfitRateInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(teamProfitRateInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(teamProfitRateInfo);
	}

	@Override
	public TeamProfitRateInfo update(TeamProfitRateInfo teamProfitRateInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(teamProfitRateInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(teamProfitRateInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(TeamProfitRateInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteTeamProfitRateInfoByRateId(String rateId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { rateId }, new String[] { "rateId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		TeamProfitRateInfo teamProfitRateInfo = getTeamProfitRateInfoByRateId(rateId);
		if (teamProfitRateInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "TeamProfitRateInfo不存在！");
		}
		delete(teamProfitRateInfo);
	}

	@Override
	public TeamProfitRateInfo getTeamProfitRateInfoByRateId(String rateId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { rateId }, new String[] { "rateId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", TeamProfitRateInfo.class.getSimpleName(), ITeamProfitRateInfoDao.RATE_ID, ITeamProfitRateInfoDao.RATE_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(ITeamProfitRateInfoDao.RATE_ID, rateId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByRateId(String rateId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ITeamProfitRateInfoDao.RATE_ID, rateId);
		return updateValue(TeamProfitRateInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
