package com.lrcall.common.utils.crypto;

/**
 * 管理员使用的加密类
 * 
 * @author libit
 */
public class AdminCryptoTools
{
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
		String result = CryptoTools.sha1(userId + CryptoTools.md5(pwd).substring(0, 13)).substring(0, 32).toUpperCase();
		return result;
	}
	// public static void main(String[] args)
	// {
	// System.out.println("admin:" + getCryptoPwd("admin", "123456"));
	// }
}
