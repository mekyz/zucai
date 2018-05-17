package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.ITeamLeaderProfitConfigInfoDao;
import com.bet.orms.TeamLeaderProfitConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("teamLeaderProfitConfigInfoDao")
public class ITeamLeaderProfitConfigInfoDaoMySqlImpl extends IAbstractDaoImpl<TeamLeaderProfitConfigInfo> implements ITeamLeaderProfitConfigInfoDao
{
	@Override
	public ReturnInfo checkParams(TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo)
	{
		if (teamLeaderProfitConfigInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "TeamLeaderProfitConfigInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { teamLeaderProfitConfigInfo.getName(), teamLeaderProfitConfigInfo.getUpdateDateLong() + "", teamLeaderProfitConfigInfo.getAddDateLong() + "",
				teamLeaderProfitConfigInfo.getStatus() + "", teamLeaderProfitConfigInfo.getConfigId(), teamLeaderProfitConfigInfo.getUserBetMoney() + "",
				teamLeaderProfitConfigInfo.getProfitMaxMoney() + "", teamLeaderProfitConfigInfo.getFee() + "", teamLeaderProfitConfigInfo.getTeamBetMoney() + "",
				teamLeaderProfitConfigInfo.getUserType() + "", teamLeaderProfitConfigInfo.getProfitPercent() + "" },
			new String[] { "name不能为空！", "updateDateLong不能为空！", "addDateLong不能为空！", "status不能为空！", "configId不能为空！", "userBetMoney不能为空！", "profitMaxMoney不能为空！", "fee不能为空！", "teamBetMoney不能为空！",
				"userType不能为空！", "profitPercent不能为空！" });
		return returnInfo;
	}

	@Override
	public TeamLeaderProfitConfigInfo add(TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(teamLeaderProfitConfigInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(teamLeaderProfitConfigInfo);
	}

	@Override
	public TeamLeaderProfitConfigInfo update(TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(teamLeaderProfitConfigInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(teamLeaderProfitConfigInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(TeamLeaderProfitConfigInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteTeamLeaderProfitConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo = getTeamLeaderProfitConfigInfoByConfigId(configId);
		if (teamLeaderProfitConfigInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "TeamLeaderProfitConfigInfo不存在！");
		}
		delete(teamLeaderProfitConfigInfo);
	}

	@Override
	public TeamLeaderProfitConfigInfo getTeamLeaderProfitConfigInfoByConfigId(String configId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { configId }, new String[] { "configId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", TeamLeaderProfitConfigInfo.class.getSimpleName(), ITeamLeaderProfitConfigInfoDao.CONFIG_ID, ITeamLeaderProfitConfigInfoDao.CONFIG_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(ITeamLeaderProfitConfigInfoDao.CONFIG_ID, configId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ITeamLeaderProfitConfigInfoDao.CONFIG_ID, configId);
		return updateValue(TeamLeaderProfitConfigInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
