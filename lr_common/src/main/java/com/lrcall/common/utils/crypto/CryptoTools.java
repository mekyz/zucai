package com.lrcall.common.utils.crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.lrcall.common.utils.HttpTools;
import com.lrcall.common.utils.LogTools;
import com.lrcall.common.utils.StringTools;

/**
 * 加密算法
 * 
 * @author libit
 */
public final class CryptoTools
{
	/**
	 * 哈希算法
	 * 
	 * @param digest
	 * @param src
	 * @return
	 */
	private static String hash(MessageDigest digest, String src)
	{
		try
		{
			return StringTools.toHexString(digest.digest(src.getBytes(HttpTools.UTF8)));
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算字符串MD5值
	 * 
	 * @param str
	 *            待计算的字符串
	 * @return MD5值
	 */
	public static String md5(String str)
	{
		try
		{
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// messageDigest.update(str.getBytes(HttpTools.UTF8));
			return hash(messageDigest, str).toUpperCase();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算字符串SHA1值
	 * 
	 * @param str
	 * @return
	 */
	public static String sha1(String str)
	{
		try
		{
			return hash(MessageDigest.getInstance("SHA1"), str).toUpperCase();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算字符串SHA256值
	 * 
	 * @param str
	 * @return
	 */
	public static String sha256(String str)
	{
		try
		{
			return hash(MessageDigest.getInstance("SHA-256"), str).toUpperCase();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 微信签名算法
	 * 
	 * @param map
	 *            参数键值对
	 * @param wxKey
	 *            商户配置的密钥
	 * @return
	 */
	public static String getWxPaySignData(Map<String, String> map, String wxKey)
	{
		LogTools log = LogTools.getInstance();
		String result = "";
		List<String> list = new ArrayList<String>();
		for (String key : map.keySet())
		{
			list.add(key);
		}
		Collections.sort(list);
		StringBuilder builder = new StringBuilder();
		for (String key : list)
		{
			builder.append(key + "=" + map.get(key) + "&");
		}
		builder.append("key=" + wxKey);
		String str = builder.toString();
		result = md5(str).toUpperCase();
		log.debug("getWxPaySignData", "builder:" + str + ",result:" + result);
		return result;
	}

	/**
	 * 按照字典顺序排序
	 * 
	 * @param map
	 * @return
	 */
	public static String getSignData(Map<String, String> map)
	{
		LogTools log = LogTools.getInstance();
		String result = "";
		List<String> list = new ArrayList<String>();
		for (String key : map.keySet())
		{
			list.add(key);
		}
		Collections.sort(list);
		StringBuilder builder = new StringBuilder();
		for (String key : list)
		{
			builder.append("&" + key + "=" + map.get(key));
		}
		result = builder.toString();
		log.info("getSignData", "builder:" + result);
		return result;
	}

	public static String ascii2native(String ascii)
	{
		int n = ascii.length() / 6;
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0, j = 2; i < n; i++, j += 6)
		{
			String code = ascii.substring(j, j + 4);
			char ch = (char) Integer.parseInt(code, 16);
			sb.append(ch);
		}
		return sb.toString();
	}
	// public static void main(String[] args)
	// {
	// // String str =
	// //
	// "act_name=商家活动&client_ip=127.0.0.1&mch_billno=1244450902201703135313200148&mch_id=1244450902&nonce_str=PLFXCILYIPJKDUSSGCIRDWSNGBPZTQBJ&re_openid=ocMD6s0pujk0uxbr0uD27L6lU9Jw&scene_id=PRODUCT_2&send_name="+ConstValues.PROJECT_NAME+"&total_amount=100&total_num=1&wishing=恭喜发财，大吉大利！&wxappid=wx21214e713ec57f32&key=NJOKFEMLNSWBCVOKSPWYSAZGBAEWWHUX";
	// String str = "测试页面";
	// System.out.println("str:" + str + ",md5:" + md5(str));
	// }
}
