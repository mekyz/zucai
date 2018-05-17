package com.bet.daos;

import java.util.Map;

import com.bet.orms.UserInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IUserInfoDao extends IAbstractDao<UserInfo>
{
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String PASSWORD = "password";
	public static final String PASSWORD2 = "password2";
	public static final String SESSION_ID = "sessionId";
	public static final String USER_TYPE = "userType";
	public static final String PARENT_ID = "parentId";
	public static final String PARENT_SIGN = "parentSign";
	public static final String REFERRER_ID = "referrerId";
	public static final String REFERRER_DATE_LONG = "referrerDateLong";
	public static final String PARENTS_ID = "parentsId";
	public static final String NUMBER = "number";
	public static final String NAME = "name";
	public static final String NICKNAME = "nickname";
	public static final String SEX = "sex";
	public static final String PIC_URL = "picUrl";
	public static final String BIRTHDAY = "birthday";
	public static final String COUNTRY = "country";
	public static final String PROVINCE = "province";
	public static final String CITY = "city";
	public static final String ADDRESS = "address";
	public static final String IDENTITY_CARD = "identityCard";
	public static final String PAYEE_NAME = "payeeName";
	public static final String BANK_NAME = "bankName";
	public static final String BANK_CARD_ID = "bankCardId";
	public static final String QR_URL = "qrUrl";
	public static final String POINT = "point";
	public static final String FREEZE_POINT = "freezePoint";
	public static final String REWARD_POINT = "rewardPoint";
	public static final String GIVE_POINT = "givePoint";
	public static final String GIVE_VALIDATE_DATE_LONG = "giveValidateDateLong";
	public static final String NUMBER_VERIFY_STATUS = "numberVerifyStatus";
	public static final String AUTH_STATUS = "authStatus";
	public static final String ACTIVE_STATUS = "activeStatus";
	public static final String SYS_STATUS = "sysStatus";
	public static final String STATUS = "status";
	public static final String REMARK = "remark";
	public static final String REG_DATE_LONG = "regDateLong";
	public static final String ACTIVE_DATE_LONG = "activeDateLong";

	public void deleteUserInfoByNumber(String number) throws HibernateJsonResultException;

	public UserInfo getUserInfoByNumber(String number) throws HibernateJsonResultException;

	public long updateValueByNumber(String number, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteUserInfoByUserId(String userId) throws HibernateJsonResultException;

	public UserInfo getUserInfoByUserId(String userId) throws HibernateJsonResultException;

	public long updateValueByUserId(String userId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */

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
}
