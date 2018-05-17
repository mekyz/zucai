package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.MatchStatisticsLogInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IMatchStatisticsLogInfoService
{
	public String genId();

	public MatchStatisticsLogInfo addMatchStatisticsLogInfo(MatchStatisticsLogInfo matchStatisticsLogInfo) throws HibernateJsonResultException;

	public MatchStatisticsLogInfo updateMatchStatisticsLogInfo(MatchStatisticsLogInfo matchStatisticsLogInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteMatchStatisticsLogInfo(MatchStatisticsLogInfo matchStatisticsLogInfo) throws HibernateJsonResultException;

	public void deleteMatchStatisticsLogInfoByPhaseId(String phaseId) throws HibernateJsonResultException;

	public MatchStatisticsLogInfo getMatchStatisticsLogInfoByPhaseId(String phaseId) throws HibernateJsonResultException;

	public boolean updateValueByPhaseId(String phaseId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<MatchStatisticsLogInfo> getMatchStatisticsLogInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getMatchStatisticsLogInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
