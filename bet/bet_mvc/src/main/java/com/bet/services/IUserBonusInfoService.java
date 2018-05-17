package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserBonusInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IUserBonusInfoService
{
	public String genId();

	public UserBonusInfo addUserBonusInfo(UserBonusInfo userBonusInfo) throws HibernateJsonResultException;

	public UserBonusInfo updateUserBonusInfo(UserBonusInfo userBonusInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteUserBonusInfo(UserBonusInfo userBonusInfo) throws HibernateJsonResultException;

	public void deleteUserBonusInfoByBonusId(String bonusId) throws HibernateJsonResultException;

	public UserBonusInfo getUserBonusInfoByBonusId(String bonusId) throws HibernateJsonResultException;

	public boolean updateValueByBonusId(String bonusId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<UserBonusInfo> getUserBonusInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getUserBonusInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	/* 自定义方法 */
	public boolean updateStatusValueByBonusId(String bonusId, Map<String, Object> valueMap) throws HibernateJsonResultException;
}
