package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.MatchProfitTemplateInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IMatchProfitTemplateInfoService
{
	public String genId();

	public MatchProfitTemplateInfo addMatchProfitTemplateInfo(MatchProfitTemplateInfo matchProfitTemplateInfo) throws HibernateJsonResultException;

	public MatchProfitTemplateInfo updateMatchProfitTemplateInfo(MatchProfitTemplateInfo matchProfitTemplateInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteMatchProfitTemplateInfo(MatchProfitTemplateInfo matchProfitTemplateInfo) throws HibernateJsonResultException;

	public void deleteMatchProfitTemplateInfoByTemplateId(String templateId) throws HibernateJsonResultException;

	public MatchProfitTemplateInfo getMatchProfitTemplateInfoByTemplateId(String templateId) throws HibernateJsonResultException;

	public boolean updateValueByTemplateId(String templateId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<MatchProfitTemplateInfo> getMatchProfitTemplateInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getMatchProfitTemplateInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */
}
