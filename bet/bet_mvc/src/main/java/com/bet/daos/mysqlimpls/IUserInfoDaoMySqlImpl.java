package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IUserInfoDao;
import com.bet.orms.UserInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.utils.crypto.UserCryptoTools;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("userInfoDao")
public class IUserInfoDaoMySqlImpl extends IAbstractDaoImpl<UserInfo> implements IUserInfoDao
{
	@Override
	public ReturnInfo checkParams(UserInfo userInfo)
	{
		if (userInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "UserInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { userInfo.getPassword(), userInfo.getUserId(), userInfo.getStatus() + "", userInfo.getNumberVerifyStatus() + "", userInfo.getSysStatus() + "", userInfo.getUserType() + "",
				userInfo.getPassword2(), userInfo.getRegDateLong() + "", userInfo.getRewardPoint() + "", userInfo.getGivePoint() + "", userInfo.getFreezePoint() + "", userInfo.getPoint() + "",
				userInfo.getActiveStatus() + "", userInfo.getAuthStatus() + "" },
			new String[] { "password不能为空！", "userId不能为空！", "status不能为空！", "numberVerifyStatus不能为空！", "sysStatus不能为空！", "userType不能为空！", "password2不能为空！", "regDateLong不能为空！", "rewardPoint不能为空！",
				"givePoint不能为空！", "freezePoint不能为空！", "point不能为空！", "activeStatus不能为空！", "authStatus不能为空！" });
		return returnInfo;
	}

	@Override
	public UserInfo update(UserInfo userInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(userInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(userInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(UserInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteUserInfoByNumber(String number) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { number }, new String[] { "number不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		UserInfo userInfo = getUserInfoByNumber(number);
		if (userInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserInfo不存在！");
		}
		delete(userInfo);
	}

	@Override
	public UserInfo getUserInfoByNumber(String number) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { number }, new String[] { "number不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", UserInfo.class.getSimpleName(), IUserInfoDao.NUMBER, IUserInfoDao.NUMBER);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserInfoDao.NUMBER, number);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByNumber(String number, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserInfoDao.NUMBER, number);
		return updateValue(UserInfo.class, valueMap, whereMap);
	}

	@Override
	public void deleteUserInfoByUserId(String userId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId }, new String[] { "userId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		UserInfo userInfo = getUserInfoByUserId(userId);
		if (userInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "UserInfo不存在！");
		}
		delete(userInfo);
	}

	@Override
	public UserInfo getUserInfoByUserId(String userId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { userId }, new String[] { "userId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", UserInfo.class.getSimpleName(), IUserInfoDao.USER_ID, IUserInfoDao.USER_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserInfoDao.USER_ID, userId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByUserId(String userId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IUserInfoDao.USER_ID, userId);
		return updateValue(UserInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */

	@Override
	public UserInfo add(UserInfo userInfo) throws HibernateJsonResultException
	{
		// long current = System.currentTimeMillis();
		ReturnInfo returnInfo = checkParams(userInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		if (!StringTools.isAbc(userInfo.getUserId()))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "账号必须是英文或数字的组合！");
		}
		if (userInfo.getUserId().length() < 6 || userInfo.getUserId().length() > 16)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "账号长度不对，请输入6-16位账号！");
		}
		if (userInfo.getPassword().length() < 6 || userInfo.getPassword().length() > 16)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "登录密码长度不对，请输入6-16位密码！");
		}
		if (userInfo.getPassword2().length() < 6 || userInfo.getPassword2().length() > 16)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "二级密码长度不对，请输入6-16位密码！");
		}
		if (getUserInfoByUserId(userInfo.getUserId()) != null)
		{
			throw new HibernateJsonResultException(ErrorInfo.EXIST_ERROR, "账号已注册！");
		}
		// if (!StringTools.isNull(userInfo.getReferrerId()))
		// {
		// if (getUserInfoByUserId(userInfo.getReferrerId()) == null)
		// {
		// throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "推荐人账号不存在！");
		// }
		// userInfo.setReferrerDateLong(current);
		// }
		userInfo.setPassword(UserCryptoTools.getCryptoPwd(userInfo.getUserId(), userInfo.getPassword()));
		userInfo.setPassword2(UserCryptoTools.getCryptoPwd2(userInfo.getUserId(), userInfo.getPassword2()));
		return super.add(userInfo);
	}

	@Override
	public boolean updatePoint(String colName, String userId, long point) throws HibernateJsonResultException
	{
		String sql = String.format("update %s set %s = %s + %d where %s = :%s and %s >= -%d", StringTools.getTableName(UserInfo.class), StringTools.getTableColumnName(colName),
			StringTools.getTableColumnName(colName), point, StringTools.getTableColumnName(IUserInfoDao.USER_ID), IUserInfoDao.USER_ID, StringTools.getTableColumnName(colName), point);
		// String sql = String.format("update %s set %s = %s + %d where %s = :%s ", StringTools.getTableName(UserInfo.class), StringTools.getTableColumnName(colName),
		// StringTools.getTableColumnName(colName), point, StringTools.getTableColumnName(IUserInfoDao.USER_ID), IUserInfoDao.USER_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserInfoDao.USER_ID, userId);
		int result = baseDao.sqlUpdate(sql, params);
		return result > 0;
	}

	@Override
	public boolean updatePointConvert(String colName1, String colName2, String userId, long point) throws HibernateJsonResultException
	{
		String sql = String.format("update %s set %s = %s - %d, %s = %s + %d where %s = :%s and %s >= %d and %s >= -%d", StringTools.getTableName(UserInfo.class),
			StringTools.getTableColumnName(colName1), StringTools.getTableColumnName(colName1), point, StringTools.getTableColumnName(colName2), StringTools.getTableColumnName(colName2), point,
			StringTools.getTableColumnName(IUserInfoDao.USER_ID), IUserInfoDao.USER_ID, StringTools.getTableColumnName(colName1), point, StringTools.getTableColumnName(colName2), point);
		// String sql = String.format("update %s set %s = %s - %d, %s = %s + %d where %s = :%s ", StringTools.getTableName(UserInfo.class), StringTools.getTableColumnName(colName1),
		// StringTools.getTableColumnName(colName1), point, StringTools.getTableColumnName(colName2), StringTools.getTableColumnName(colName2), point,
		// StringTools.getTableColumnName(IUserInfoDao.USER_ID), IUserInfoDao.USER_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IUserInfoDao.USER_ID, userId);
		int result = baseDao.sqlUpdate(sql, params);
		return result > 0;
	}
}
