package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.ITeamProfitInfoDao;
import com.bet.orms.TeamProfitInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("teamProfitInfoDao")
public class ITeamProfitInfoDaoMySqlImpl extends IAbstractDaoImpl<TeamProfitInfo> implements ITeamProfitInfoDao
{
	@Override
	public ReturnInfo checkParams(TeamProfitInfo teamProfitInfo)
	{
		if (teamProfitInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "TeamProfitInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { teamProfitInfo.getUpdateDateLong() + "", teamProfitInfo.getAddDateLong() + "", teamProfitInfo.getUserId(), teamProfitInfo.getStatus() + "", teamProfitInfo.getPhaseId(),
				teamProfitInfo.getProfitUserMoney() + "", teamProfitInfo.getBetUserId(), teamProfitInfo.getProfitMoney() + "", teamProfitInfo.getBetId(), teamProfitInfo.getFee() + "",
				teamProfitInfo.getSysStatus() + "", teamProfitInfo.getProfitType() + "", teamProfitInfo.getProfitStatus() + "", teamProfitInfo.getWinMoney() + "", teamProfitInfo.getProfitId() },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "userId不能为空！", "status不能为空！", "phaseId不能为空！", "profitUserMoney不能为空！", "betUserId不能为空！", "profitMoney不能为空！", "betId不能为空！",
				"fee不能为空！", "sysStatus不能为空！", "profitType不能为空！", "profitStatus不能为空！", "winMoney不能为空！", "profitId不能为空！" });
		return returnInfo;
	}

	@Override
	public TeamProfitInfo add(TeamProfitInfo teamProfitInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(teamProfitInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(teamProfitInfo);
	}

	@Override
	public TeamProfitInfo update(TeamProfitInfo teamProfitInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(teamProfitInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(teamProfitInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(TeamProfitInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteTeamProfitInfoByProfitId(String profitId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { profitId }, new String[] { "profitId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		TeamProfitInfo teamProfitInfo = getTeamProfitInfoByProfitId(profitId);
		if (teamProfitInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "TeamProfitInfo不存在！");
		}
		delete(teamProfitInfo);
	}

	@Override
	public TeamProfitInfo getTeamProfitInfoByProfitId(String profitId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { profitId }, new String[] { "profitId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", TeamProfitInfo.class.getSimpleName(), ITeamProfitInfoDao.PROFIT_ID, ITeamProfitInfoDao.PROFIT_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(ITeamProfitInfoDao.PROFIT_ID, profitId);
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
		whereMap.put(ITeamProfitInfoDao.PROFIT_ID, profitId);
		return updateValue(TeamProfitInfo.class, valueMap, whereMap);
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
		String sql = String.format("select sum(%s) %s", StringTools.getTableColumnName(colName), buildSql(TeamProfitInfo.class, whereClause, null, searchInfos, params));
		return baseDao.getSqlRowSum(sql, params).longValue();
	}
}
