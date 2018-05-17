package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.ITeamInfoDao;
import com.bet.orms.TeamInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("teamInfoDao")
public class ITeamInfoDaoMySqlImpl extends IAbstractDaoImpl<TeamInfo> implements ITeamInfoDao
{
	@Override
	public ReturnInfo checkParams(TeamInfo teamInfo)
	{
		if (teamInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "TeamInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { teamInfo.getName(), teamInfo.getUpdateDateLong() + "", teamInfo.getAddDateLong() + "", teamInfo.getStatus() + "", teamInfo.getTeamId() },
			new String[] { "name不能为空！", "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "teamId不能为空！" });
		return returnInfo;
	}

	@Override
	public TeamInfo add(TeamInfo teamInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(teamInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(teamInfo);
	}

	@Override
	public TeamInfo update(TeamInfo teamInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(teamInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(teamInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(TeamInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteTeamInfoByTeamId(String teamId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { teamId }, new String[] { "teamId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		TeamInfo teamInfo = getTeamInfoByTeamId(teamId);
		if (teamInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "TeamInfo不存在！");
		}
		delete(teamInfo);
	}

	@Override
	public TeamInfo getTeamInfoByTeamId(String teamId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { teamId }, new String[] { "teamId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", TeamInfo.class.getSimpleName(), ITeamInfoDao.TEAM_ID, ITeamInfoDao.TEAM_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(ITeamInfoDao.TEAM_ID, teamId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByTeamId(String teamId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ITeamInfoDao.TEAM_ID, teamId);
		return updateValue(TeamInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
