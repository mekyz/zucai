package com.lrcall.common.utils.crypto;

import com.lrcall.common.utils.RandomTools;
import com.lrcall.common.utils.RandomTools.SecurityCodeLevel;

/**
 * 用户使用的加密类
 * 
 * @author libit
 */
public final class UserCryptoTools
{
	/**
	 * 为用户随机生成一个密码
	 * 
	 * @return
	 */
	public static String genRandomPwd()
	{
		String result = RandomTools.getSecurityCode(6, SecurityCodeLevel.Simple, true);
		return result;
	}

	/**
	 * 计算存储在数据库中的加密字符串，先将密码取MD5，再和账号连接后取SHA1，这样即使用户设置的密码一样，存储在数据库中的密码也不一样。
	 * 
	 * @param userId
	 *            账号
	 * @param pwd
	 *            密码
	 * @return 加密后的字符串
	 */
	public static String getCryptoPwd(String userId, String pwd)
	{
		String md5 = userId + CryptoTools.md5(pwd);
		String result = CryptoTools.md5(md5.substring(14, 24) + md5.substring(0, 9)).toUpperCase();
		return result;
	}

	/**
	 * 计算存储在数据库中的加密字符串，先将密码取MD5，再和账号连接后取SHA1，这样即使用户设置的密码一样，存储在数据库中的密码也不一样。
	 * 
	 * @param userId
	 *            账号
	 * @param pwd
	 *            密码
	 * @return 加密后的字符串
	 */
	public static String getCryptoPwd2(String userId, String pwd)
	{
		String md5 = userId + CryptoTools.md5(pwd);
		String result = CryptoTools.md5(md5.substring(11, 24) + md5.substring(0, 9)).toUpperCase();
		return result;
	}

	/**
	 * 计算session id
	 * 
	 * @param userId
	 * @param pwd
	 * @return
	 */
	public static String genSessionId(String userId, String pwd)
	{
		long current = System.currentTimeMillis();
		String md5 = userId + CryptoTools.md5(pwd);
		String result = CryptoTools.sha1(md5.substring(10, 24) + md5.substring(0, 12) + current).toUpperCase();
		return result;
	}

	public static void main(String[] args)
	{
		System.out.println("tgm8898:" + getCryptoPwd2("tgm8898", "123456"));
	}
}
