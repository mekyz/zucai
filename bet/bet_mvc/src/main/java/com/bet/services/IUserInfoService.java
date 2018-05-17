package com.bet.services;

import java.util.List;
import java.util.Map;

import com.bet.orms.UserInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IUserInfoService
{
	public String genId();

	public UserInfo addUserInfo(UserInfo userInfo) throws HibernateJsonResultException;

	public UserInfo updateUserInfo(UserInfo userInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deleteUserInfo(UserInfo userInfo) throws HibernateJsonResultException;

	public void deleteUserInfoByNumber(String number) throws HibernateJsonResultException;

	public UserInfo getUserInfoByNumber(String number) throws HibernateJsonResultException;

	public boolean updateValueByNumber(String number, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteUserInfoByUserId(String userId) throws HibernateJsonResultException;

	public UserInfo getUserInfoByUserId(String userId) throws HibernateJsonResultException;

	public boolean updateValueByUserId(String userId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<UserInfo> getUserInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getUserInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	public List<UserInfo> getUserInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException;

	public long getUserInfoListCount(List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong) throws HibernateJsonResultException;

	/**
	 * 用户注册
	 * 
	 * @param userInfo
	 *            用户信息
	 * @param code
	 *            手机验证码
	 * @return 用户信息
	 * @throws HibernateJsonResultException
	 *             注册失败的异常信息
	 */
	public UserInfo register(UserInfo userInfo, String code) throws HibernateJsonResultException;

	/**
	 * 用户登录
	 * 
	 * @param userId
	 *            登录账号
	 * @param password
	 *            登录密码
	 * @return 用户信息
	 * @throws HibernateJsonResultException
	 *             登录不成功的异常信息
	 */
	public UserInfo login(String userId, String password);

	/**
	 * 用户通过Session登录
	 * 
	 * @param userId
	 * @param sessionId
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public UserInfo loginBySession(String userId, String sessionId) throws HibernateJsonResultException;

	/**
	 * 修改密码
	 * 
	 * @param userId
	 *            账号
	 * @param password
	 *            旧密码
	 * @param newPassword
	 *            新密码
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public boolean updatePwd(String userId, String password, String newPassword) throws HibernateJsonResultException;

	/**
	 * 修改密码
	 * 
	 * @param userId
	 *            账号
	 * @param newPassword
	 *            新密码
	 * @param code
	 *            验证码
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public boolean updateResetPwd(String userId, String newPassword, String code) throws HibernateJsonResultException;

	/**
	 * 获取今日注册用户数
	 * 
	 * @param
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public long getTodayUserInfoListCount() throws HibernateJsonResultException;

	/**
	 * 更新积分
	 * 
	 * @param colName
	 *            积分字段
	 * @param userId
	 *            用户ID
	 * @param point
	 *            积分
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public boolean updatePoint(String colName, String userId, long point) throws HibernateJsonResultException;

	/**
	 * 积分转换
	 * 
	 * @param colName1
	 *            要转换的积分字段
	 * @param colName2
	 *            转换后的积分字段
	 * @param userId
	 *            用户ID
	 * @param point
	 *            积分
	 * @return
	 * @throws HibernateJsonResultException
	 */
	public boolean updatePointConvert(String colName1, String colName2, String userId, long point) throws HibernateJsonResultException;

	/**
	 * 获取子节点
	 * 
	 * @param userInfo
	 * @param depth
	 *            当前深度
	 * @param maxDepth
	 *            最大深度
	 * @return
	 */
	// public long getSubUserCount(UserInfo userInfo, int depth, int maxDepth);
	public List<UserInfo> getAllSubUserInfoList(String userId, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, Long startDateLong, Long endDateLong)
		throws HibernateJsonResultException;

	public long getAllSubUserCount(String userId, List<TableSearchInfo> searchInfos);

	/**
	 * 检查用户账户状态，如体验彩金有效期、是否可升级
	 * 
	 * @param userId
	 *            用户账号
	 * @return
	 */
	public ReturnInfo checkAndUpdateUserInfoJob(String userId);

	/**
	 * 检查用户上级
	 * 
	 * @param userId
	 *            用户账号
	 * @return
	 */
	public ReturnInfo checkAndUpdateUserParentInfoJob(String userId);
}
