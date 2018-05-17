package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.TeamProfitInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface ITeamProfitInfoService
{
	public String genId();

	public TeamProfitInfo addTeamProfitInfo(TeamProfitInfo teamProfitInfo) throws HibernateJsonResultException;

	public TeamProfitInfo updateTeamProfitInfo(TeamProfitInfo teamProfitInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteTeamProfitInfo(TeamProfitInfo teamProfitInfo) throws HibernateJsonResultException;

	public void deleteTeamProfitInfoByProfitId(String profitId) throws HibernateJsonResultException;

	public TeamProfitInfo getTeamProfitInfoByProfitId(String profitId) throws HibernateJsonResultException;

	public boolean updateValueByProfitId(String profitId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<TeamProfitInfo> getTeamProfitInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getTeamProfitInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	public List<TeamProfitInfo> getTeamProfitInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException;

	public long getTeamProfitInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;

	public long getSum(String colName, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;
}
