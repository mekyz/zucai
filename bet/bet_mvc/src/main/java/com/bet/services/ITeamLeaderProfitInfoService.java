package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.TeamLeaderProfitInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface ITeamLeaderProfitInfoService
{
	public String genId();

	public TeamLeaderProfitInfo addTeamLeaderProfitInfo(TeamLeaderProfitInfo teamLeaderProfitInfo) throws HibernateJsonResultException;

	public TeamLeaderProfitInfo updateTeamLeaderProfitInfo(TeamLeaderProfitInfo teamLeaderProfitInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteTeamLeaderProfitInfo(TeamLeaderProfitInfo teamLeaderProfitInfo) throws HibernateJsonResultException;

	public void deleteTeamLeaderProfitInfoByProfitId(String profitId) throws HibernateJsonResultException;

	public TeamLeaderProfitInfo getTeamLeaderProfitInfoByProfitId(String profitId) throws HibernateJsonResultException;

	public boolean updateValueByProfitId(String profitId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<TeamLeaderProfitInfo> getTeamLeaderProfitInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getTeamLeaderProfitInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	public long getSum(String colName, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;
}
