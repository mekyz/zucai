package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.ITeamLeaderProfitInfoDao;
import com.bet.orms.TeamLeaderProfitInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("teamLeaderProfitInfoDao")
public class ITeamLeaderProfitInfoDaoMySqlImpl extends IAbstractDaoImpl<TeamLeaderProfitInfo> implements ITeamLeaderProfitInfoDao
{
	@Override
	public ReturnInfo checkParams(TeamLeaderProfitInfo teamLeaderProfitInfo)
	{
		if (teamLeaderProfitInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "TeamLeaderProfitInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { teamLeaderProfitInfo.getUpdateDateLong() + "", teamLeaderProfitInfo.getAddDateLong() + "", teamLeaderProfitInfo.getUserId(), teamLeaderProfitInfo.getStatus() + "",
				teamLeaderProfitInfo.getPhaseId(), teamLeaderProfitInfo.getUserBetMoney() + "", teamLeaderProfitInfo.getProfitUserMoney() + "", teamLeaderProfitInfo.getProfitMoney() + "",
				teamLeaderProfitInfo.getFee() + "", teamLeaderProfitInfo.getTeamBetMoney() + "", teamLeaderProfitInfo.getSysStatus() + "", teamLeaderProfitInfo.getProfitType(),
				teamLeaderProfitInfo.getProfitStatus() + "", teamLeaderProfitInfo.getProfitId() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "userId不能为空！", "status不能为空！", "phaseId不能为空！", "userBetMoney不能为空！", "profitUserMoney不能为空！", "profitMoney不能为空！", "fee不能为空！",
				"teamBetMoney不能为空！", "sysStatus不能为空！", "profitType不能为空！", "profitStatus不能为空！", "profitId不能为空！" });
		return returnInfo;
	}

	@Override
	public TeamLeaderProfitInfo add(TeamLeaderProfitInfo teamLeaderProfitInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(teamLeaderProfitInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(teamLeaderProfitInfo);
	}

	@Override
	public TeamLeaderProfitInfo update(TeamLeaderProfitInfo teamLeaderProfitInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(teamLeaderProfitInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(teamLeaderProfitInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(TeamLeaderProfitInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteTeamLeaderProfitInfoByProfitId(String profitId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { profitId }, new String[] { "profitId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		TeamLeaderProfitInfo teamLeaderProfitInfo = getTeamLeaderProfitInfoByProfitId(profitId);
		if (teamLeaderProfitInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "TeamLeaderProfitInfo不存在！");
		}
		delete(teamLeaderProfitInfo);
	}

	@Override
	public TeamLeaderProfitInfo getTeamLeaderProfitInfoByProfitId(String profitId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { profitId }, new String[] { "profitId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", TeamLeaderProfitInfo.class.getSimpleName(), ITeamLeaderProfitInfoDao.PROFIT_ID, ITeamLeaderProfitInfoDao.PROFIT_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(ITeamLeaderProfitInfoDao.PROFIT_ID, profitId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByProfitId(String profitId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(ITeamLeaderProfitInfoDao.PROFIT_ID, profitId);
		return updateValue(TeamLeaderProfitInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */

	@Override
	public long getSum(String colName, String whereClause, Map<String, Object> params, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		if (params == null)
		{
			params = new HashMap<>();
		}
		String sql = String.format("select sum(%s) %s", StringTools.getTableColumnName(colName), buildSql(TeamLeaderProfitInfo.class, whereClause, null, searchInfos, params));
		return baseDao.getSqlRowSum(sql, params).longValue();
	}
}
