package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.MatchInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IMatchInfoService
{
	public String genId();

	public MatchInfo addMatchInfo(MatchInfo matchInfo) throws HibernateJsonResultException;

	public MatchInfo updateMatchInfo(MatchInfo matchInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteMatchInfo(MatchInfo matchInfo) throws HibernateJsonResultException;

	public void deleteMatchInfoByPhaseId(String phaseId) throws HibernateJsonResultException;

	public MatchInfo getMatchInfoByPhaseId(String phaseId) throws HibernateJsonResultException;

	public boolean updateValueByPhaseId(String phaseId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteMatchInfoByOutId(String outId) throws HibernateJsonResultException;

	public MatchInfo getMatchInfoByOutId(String outId) throws HibernateJsonResultException;

	public boolean updateValueByOutId(String outId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<MatchInfo> getMatchInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getMatchInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	/* 自定义方法 */
	public boolean updateProfitValueByPhaseId(String phaseId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<MatchInfo> getMatchInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException;

	public long getMatchInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;
}
