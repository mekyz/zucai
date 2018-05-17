package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IUserBonusInfoDao;
import com.bet.orms.UserBonusInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userBonusInfoDao")
public class IUserBonusInfoDaoMySqlImpl extends IAbstractDaoImpl<UserBonusInfo> implements IUserBonusInfoDao
{
	@Override
	public ReturnInfo checkParams(UserBonusInfo userBonusInfo)
	{
		if (userBonusInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "UserBonusInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { userBonusInfo.getUpdateDateLong() + "", userBonusInfo.getAddDateLong() + "", userBonusInfo.getUserId(), userBonusInfo.getStatus() + "",
				userBonusInfo.getBenefitBonusMoney() + "", userBonusInfo.getSameLevel1BonusMoney() + "", userBonusInfo.getSameLevel2BonusMoney() + "", userBonusInfo.getShareBonusMoney() + "",
				userBonusInfo.getAgentBonusMoney() + "", userBonusInfo.getCountBonusMoney() + "", userBonusInfo.getFinalBonusMoney() + "", userBonusInfo.getHalfBonusMoney() + "",
				userBonusInfo.getPhaseId(), userBonusInfo.getFinancialMoney() + "", userBonusInfo.getFee() + "", userBonusInfo.getBonusId(), userBonusInfo.getUserMoney() + "" },
			new String[] { "updateDateLong不能为空！", "addDateLong不能为空！", "userId不能为空！", "status不能为空！", "benefitBonusMoney不能为空！", "sameLevel1BonusMoney不能为空！", "sameLevel2BonusMoney不能为空！",
				"shareBonusMoney不能为空！", "agentBonusMoney不能为空！", "countBonusMoney不能为空！", "finalBonusMoney不能为空！", "halfBonusMoney不能为空！", "phaseId不能为空！", "financialMoney不能为空！", "fee不能为空！",
				"bonusId不能为空！", "userMoney不能为空！" });
		return returnInfo;
	}

	@Override
	public UserBonusInfo add(UserBonusInfo userBonusInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userBonusInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(userBonusInfo);
	}

	@Override
	public UserBonusInfo update(UserBonusInfo userBonusInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userBonusInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(userBonusInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(UserBonusInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteUserBonusInfoByBonusId(String bonusId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { bonusId }, new String[] { "bonusId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		UserBonusInfo userBonusInfo = getUserBonusInfoByBonusId(bonusId);
		if (userBonusInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserBonusInfo不存在！");
		}
		delete(userBonusInfo);
	}

	@Override
	public UserBonusInfo getUserBonusInfoByBonusId(String bonusId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { bonusId }, new String[] { "bonusId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", UserBonusInfo.class.getSimpleName(), IUserBonusInfoDao.BONUS_ID, IUserBonusInfoDao.BONUS_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserBonusInfoDao.BONUS_ID, bonusId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByBonusId(String bonusId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserBonusInfoDao.BONUS_ID, bonusId);
		return updateValue(UserBonusInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
