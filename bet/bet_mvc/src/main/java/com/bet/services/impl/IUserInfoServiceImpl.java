package com.bet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserBalanceLogInfoDao;
import com.bet.daos.IUserInfoDao;
import com.bet.daos.IUserRechargeInfoDao;
import com.bet.daos.IUserTypeChangeLogInfoDao;
import com.bet.enums.ApplyStatus;
import com.bet.enums.MoneyUnit;
import com.bet.enums.UserBalanceLogType;
import com.bet.models.ParentUser;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.orms.UserInfo;
import com.bet.orms.UserTypeChangeLogInfo;
import com.bet.orms.UserTypeConditionInfo;
import com.bet.services.IUserInfoService;
import com.bet.services.IUserTypeConditionInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.GsonTools;
import com.lrcall.common.utils.LogTools;
import com.lrcall.common.utils.TimeTools;
import com.lrcall.common.utils.crypto.UserCryptoTools;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userInfoService")
public class IUserInfoServiceImpl implements IUserInfoService
{
	@Autowired
	private IUserInfoDao userInfoDao;

	@Override
	public String genId()
	{
		return userInfoDao.genId();
	}

	@Override
	public UserInfo addUserInfo(UserInfo userInfo) throws HibernateJsonResultException
	{
		return userInfoDao.add(userInfo);
	}

	@Override
	public UserInfo updateUserInfo(UserInfo userInfo) throws HibernateJsonResultException
	{
		return userInfoDao.update(userInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return userInfoDao.updateValue(UserInfo.class, valueMap, valueMap);
	}

	@Override
	public void deleteUserInfo(UserInfo userInfo) throws HibernateJsonResultException
	{
		userInfoDao.delete(userInfo);
	}

	@Override
	public void deleteUserInfoByNumber(String number) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { number }, new String[] { "number不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		userInfoDao.deleteUserInfoByNumber(number);
	}

	@Override
	public UserInfo getUserInfoByNumber(String number) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { number }, new String[] { "number不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return userInfoDao.getUserInfoByNumber(number);
	}

	@Override
	public boolean updateValueByNumber(String number, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserInfoDao.NUMBER, number);
		return userInfoDao.updateValue(UserInfo.class, valueMap, whereMap) > 0;
	}

	@Override
	public void deleteUserInfoByUserId(String userId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId }, new String[] { "userId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		userInfoDao.deleteUserInfoByUserId(userId);
	}

	@Override
	public UserInfo getUserInfoByUserId(String userId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId }, new String[] { "userId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return userInfoDao.getUserInfoByUserId(userId);
	}

	@Override
	public boolean updateValueByUserId(String userId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserInfoDao.USER_ID, userId);
		return userInfoDao.updateValue(UserInfo.class, valueMap, whereMap) > 0;
	}

	public List<UserInfo> getUserInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return userInfoDao.getList(UserInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userInfoDao.getListCount(UserInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Autowired
	private IUserBalanceLogInfoDao userBalanceLogInfoDao;
	@Autowired
	private IUserTypeConditionInfoService userTypeConditionInfoService;
	@Autowired
	private IUserTypeChangeLogInfoDao userTypeChangeLogInfoDao;
	@Autowired
	private IUserRechargeInfoDao userRechargeInfoDao;

	@Override
	public List<UserInfo> getUserInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return userInfoDao.getList(UserInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<UserInfo> getUserInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException
	{
		String whereClause = userInfoDao.buildHqlTimeWhere(IUserInfoDao.REG_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userInfoDao.getList(UserInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getUserInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException
	{
		String whereClause = userInfoDao.buildHqlTimeWhere(IUserInfoDao.REG_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (1 = 1)", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "));
		Map<String, Object> params = new HashMap<>();
		return userInfoDao.getListCount(UserInfo.class, whereClause, params, searchInfos);
	}

	@Override
	public UserInfo register(UserInfo userInfo, String code) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userInfo.getUserId(), userInfo.getPassword() }, new String[] { "账号不能为空！", "密码不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String sessionId = UserCryptoTools.genSessionId(userInfo.getUserId(), userInfo.getPassword());
		userInfo.setSessionId(sessionId);
		return userInfoDao.add(userInfo);
	}

	@Override
	public UserInfo login(String userId, String password)
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId, password }, new String[] { "账号不能为空！", "密码不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		UserInfo userInfo = userInfoDao.getUserInfoByUserId(userId);
		if (userInfo == null || !userInfo.getPassword().equalsIgnoreCase(UserCryptoTools.getCryptoPwd(userId, password)))
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "账号或密码错误！");
		}
		String sessionId = UserCryptoTools.genSessionId(userId, password);
		Map<String, Object> valueMap = new HashMap<>();
		valueMap.put(IUserInfoDao.SESSION_ID, sessionId);
		if (userInfoDao.updateValueByUserId(userId, valueMap) < 1)
		{
			LogTools.getInstance().error("login", "更新用户sessionId失败");
		}
		userInfo.setSessionId(sessionId);
		// userInfo = userInfoDao.getUserInfoByUserId(userId);
		return userInfo;
	}

	@Override
	public UserInfo loginBySession(String userId, String sessionId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId, sessionId }, new String[] { "账号不能为空！", "session不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		UserInfo userInfo = userInfoDao.getUserInfoByUserId(userId);
		if (userInfo == null || !sessionId.equals(userInfo.getSessionId()))
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "登录已过期！");
		}
		return userInfo;
	}

	@Override
	public boolean updatePwd(String userId, String password, String newPassword) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId, password, newPassword }, new String[] { "账号不能为空！", "旧密码不能为空！", "新密码不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		UserInfo userInfo = userInfoDao.getUserInfoByUserId(userId);
		if (userInfo == null || !userInfo.getPassword().equalsIgnoreCase(UserCryptoTools.getCryptoPwd(userId, password)))
		{
			throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "账号或密码错误！");
		}
		Map<String, Object> valueMap = new HashMap<>();
		valueMap.put(IUserInfoDao.PASSWORD, UserCryptoTools.getCryptoPwd(userId, newPassword));
		valueMap.put(IUserInfoDao.SESSION_ID, UserCryptoTools.genSessionId(userId, newPassword));
		return updateValueByUserId(userId, valueMap);
	}

	@Override
	public boolean updateResetPwd(String userId, String newPassword, String code) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId, newPassword, code }, new String[] { "账号不能为空！", "新密码不能为空！", "验证码不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> valueMap = new HashMap<>();
		valueMap.put(IUserInfoDao.PASSWORD, UserCryptoTools.getCryptoPwd(userId, newPassword));
		valueMap.put(IUserInfoDao.SESSION_ID, UserCryptoTools.genSessionId(userId, newPassword));
		return updateValueByUserId(userId, valueMap);
	}

	@Override
	public long getTodayUserInfoListCount() throws HibernateJsonResultException
	{
		String whereClause = String.format("where (%s between %d and %d)", IUserInfoDao.REG_DATE_LONG, TimeTools.getTodayBeginDateTimeLong(), TimeTools.getTodayEndDateTimeLong());
		Map<String, Object> params = new HashMap<>();
		return userInfoDao.getListCount(UserInfo.class, whereClause, params, null);
	}

	@Override
	public boolean updatePoint(String colName, String userId, long point) throws HibernateJsonResultException
	{
		return userInfoDao.updatePoint(colName, userId, point);
	}

	@Override
	public boolean updatePointConvert(String colName1, String colName2, String userId, long point) throws HibernateJsonResultException
	{
		return userInfoDao.updatePointConvert(colName1, colName2, userId, point);
	}

	// @Override
	// public long getSubUserCount(UserInfo userInfo, int depth, int maxDepth)
	// {
	// long count = 0;
	// List<TableOrderInfo> orderInfos = new ArrayList<>();
	// List<TableSearchInfo> searchInfos = new ArrayList<>();
	// searchInfos.add(new TableSearchInfo(IUserInfoDao.REFERRER_ID, userInfo.getUserId(), true, false));
	// List<UserInfo> list = userInfoDao.getList(UserInfo.class, null, null, 0, -1, orderInfos, searchInfos);
	// if (list != null && list.size() > 0)
	// {
	// count += list.size();
	// depth++;
	// if (maxDepth > 0 && depth >= maxDepth)
	// {
	// return count;
	// }
	// for (UserInfo userInfo1 : list)
	// {
	// count += getSubUserCount(userInfo1, depth, maxDepth);
	// }
	// }
	// return count;
	// }
	@Override
	public List<UserInfo> getAllSubUserInfoList(String userId, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException
	{
		String whereClause = userInfoDao.buildHqlTimeWhere(IUserInfoDao.REG_DATE_LONG, startDateLong, endDateLong);
		whereClause = String.format("%s (%s like '%s')", (StringTools.isNull(whereClause) ? "where " : whereClause + " and "), IUserInfoDao.PARENTS_ID, "%\"userId\":\"" + userId + "\"%");
		Map<String, Object> params = new HashMap<>();
		return userInfoDao.getList(UserInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getAllSubUserCount(String userId, List<TableSearchInfo> searchInfos)
	{
		String where = String.format("where (%s like '%s') ", IUserInfoDao.PARENTS_ID, "%\"userId\":\"" + userId + "\"%");
		Map<String, Object> params = new HashMap<>();
		return userInfoDao.getListCount(UserInfo.class, where, params, searchInfos);
	}

	@Override
	public ReturnInfo checkAndUpdateUserInfoJob(String userId)
	{
		UserInfo userInfo = userInfoDao.getUserInfoByUserId(userId);
		long oldGivePoint = userInfo.getGivePoint();
		Long oldValidateDateLong = userInfo.getGiveValidateDateLong();
		long current = System.currentTimeMillis();
		if ((oldGivePoint > 0 && (oldValidateDateLong == null || current > oldValidateDateLong)) || (oldValidateDateLong != null && current > oldValidateDateLong))// 已过有效期
		{
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserInfoDao.GIVE_POINT, 0);
			valueMap.put(IUserInfoDao.GIVE_VALIDATE_DATE_LONG, null);
			if (!updateValueByUserId(userId, valueMap))
			{
				return new ReturnInfo(ErrorInfo.HIBERNATE_ERROR, "有效期已到，更新用户" + userId + "体验彩金失败。");
			}
			// 添加变化记录
			if (oldGivePoint > 0)
			{
				userBalanceLogInfoDao.add(new UserBalanceLogInfo(userId, -oldGivePoint, MoneyUnit.GIVE_POINT.getType(), UserBalanceLogType.GIVE_POINT.getType(), null, null, null, null,
					String.format("体验彩金已到有效期！"), current));
			}
			return new ReturnInfo(ErrorInfo.SUCCESS, "已过有效期。");
		}
		// 检查用户的级别
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IUserInfoDao.REFERRER_ID, userId, true, false));
		long directCount = getUserInfoListCount(searchInfos);
		long teamCount = getAllSubUserCount(userId, null);
		searchInfos.clear();
		searchInfos.add(new TableSearchInfo(IUserRechargeInfoDao.USER_ID, userId, true, false));
		searchInfos.add(new TableSearchInfo(IUserRechargeInfoDao.STATUS, ApplyStatus.VERIFY_SUCCESS.getStatus() + "", true, false));
		long rechargeMoney = userRechargeInfoDao.getSum(IUserRechargeInfoDao.MONEY, null, null, searchInfos, null, null);
		UserTypeConditionInfo userTypeConditionInfo = userTypeConditionInfoService.getMaxUserTypeConditionInfo(directCount, teamCount, rechargeMoney, null);
		if (userTypeConditionInfo != null && userTypeConditionInfo.getUserType() > userInfo.getUserType())
		{
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserInfoDao.USER_TYPE, userTypeConditionInfo.getUserType());
			if (!updateValueByUserId(userId, valueMap))
			{
				return new ReturnInfo(ErrorInfo.HIBERNATE_ERROR, "更新用户" + userId + "级别到" + userTypeConditionInfo.getUserType() + "失败。");
			}
			// 添加变化记录
			userTypeChangeLogInfoDao.add(new UserTypeChangeLogInfo(userId, null, userTypeConditionInfo.getUserType(), current));
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, "");
	}

	@Override
	public ReturnInfo checkAndUpdateUserParentInfoJob(String userId)
	{
		UserInfo userInfo = userInfoDao.getUserInfoByUserId(userId);
		// if (!StringTools.isNull(userInfo.getParentsId()))
		// {
		// return new ReturnInfo(ErrorInfo.SUCCESS, "用户父节点已存在！");
		// }
		if (userInfo != null && !StringTools.isNull(userInfo.getReferrerId()))
		{
			List<ParentUser> parents = new ArrayList<>();
			UserInfo parentUserInfo = userInfoDao.getUserInfoByUserId(userInfo.getReferrerId());
			if (parentUserInfo == null)
			{
				parentUserInfo = userInfoDao.getUserInfoByNumber(userInfo.getReferrerId());
			}
			int index = 0;
			while (parentUserInfo != null)
			{
				index++;
				parents.add(new ParentUser(index, parentUserInfo.getUserId()));
				if (StringTools.isNull(parentUserInfo.getReferrerId()))
				{
					break;
				}
				UserInfo parentUserInfo1 = userInfoDao.getUserInfoByUserId(parentUserInfo.getReferrerId());
				if (parentUserInfo1 == null)
				{
					parentUserInfo1 = userInfoDao.getUserInfoByNumber(parentUserInfo.getReferrerId());
					if (parentUserInfo1 != null)
					{
						Map<String, Object> valueMap = new HashMap<>();
						valueMap.put(IUserInfoDao.REFERRER_ID, parentUserInfo1.getUserId());
						if (!updateValueByUserId(parentUserInfo.getUserId(), valueMap))
						{
							return new ReturnInfo(ErrorInfo.HIBERNATE_ERROR, "更新用户" + userId + "父节点失败。");
						}
					}
				}
				parentUserInfo = parentUserInfo1;
			}
			Map<String, Object> valueMap = new HashMap<>();
			String p = GsonTools.toJson(parents);
			LogTools.getInstance().info("checkAndUpdateUserParentInfoJob", p);
			valueMap.put(IUserInfoDao.PARENTS_ID, p);
			if (!updateValueByUserId(userId, valueMap))
			{
				return new ReturnInfo(ErrorInfo.HIBERNATE_ERROR, "更新用户" + userId + "父节点失败。");
			}
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, "");
	}
}
