package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.TeamInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface ITeamInfoService
{
	public String genId();

	public TeamInfo addTeamInfo(TeamInfo teamInfo) throws HibernateJsonResultException;

	public TeamInfo updateTeamInfo(TeamInfo teamInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteTeamInfo(TeamInfo teamInfo) throws HibernateJsonResultException;

	public void deleteTeamInfoByTeamId(String teamId) throws HibernateJsonResultException;

	public TeamInfo getTeamInfoByTeamId(String teamId) throws HibernateJsonResultException;

	public boolean updateValueByTeamId(String teamId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<TeamInfo> getTeamInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getTeamInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
