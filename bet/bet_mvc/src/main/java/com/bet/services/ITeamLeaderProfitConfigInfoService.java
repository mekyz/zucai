package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.TeamLeaderProfitConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface ITeamLeaderProfitConfigInfoService
{
	public String genId();

	public TeamLeaderProfitConfigInfo addTeamLeaderProfitConfigInfo(TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo) throws HibernateJsonResultException;

	public TeamLeaderProfitConfigInfo updateTeamLeaderProfitConfigInfo(TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteTeamLeaderProfitConfigInfo(TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo) throws HibernateJsonResultException;

	public void deleteTeamLeaderProfitConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public TeamLeaderProfitConfigInfo getTeamLeaderProfitConfigInfoByConfigId(String configId) throws HibernateJsonResultException;

	public boolean updateValueByConfigId(String configId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<TeamLeaderProfitConfigInfo> getTeamLeaderProfitConfigInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException;

	public long getTeamLeaderProfitConfigInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	public TeamLeaderProfitConfigInfo getTeamLeaderProfitConfigInfoByUserType(byte userType) throws HibernateJsonResultException;
}
