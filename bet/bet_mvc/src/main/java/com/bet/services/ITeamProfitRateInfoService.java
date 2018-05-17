package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.TeamProfitRateInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface ITeamProfitRateInfoService
{
	public String genId();

	public TeamProfitRateInfo addTeamProfitRateInfo(TeamProfitRateInfo teamProfitRateInfo) throws HibernateJsonResultException;

	public TeamProfitRateInfo updateTeamProfitRateInfo(TeamProfitRateInfo teamProfitRateInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteTeamProfitRateInfo(TeamProfitRateInfo teamProfitRateInfo) throws HibernateJsonResultException;

	public void deleteTeamProfitRateInfoByRateId(String rateId) throws HibernateJsonResultException;

	public TeamProfitRateInfo getTeamProfitRateInfoByRateId(String rateId) throws HibernateJsonResultException;

	public boolean updateValueByRateId(String rateId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<TeamProfitRateInfo> getTeamProfitRateInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getTeamProfitRateInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
