package com.lrcall.common.utils;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;

/**
 * 字符串辅助类
 * 
 * @author libit
 * @date 2015-12-27
 */
public class StringTools
{
	public static boolean isIDCards(String str)
	{
		String regExp1 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
		String regExp2 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
		Pattern p;
		if (!StringTools.isNull(str) && str.length() == 15)
		{
			p = Pattern.compile(regExp1);
		}
		else
		{
			p = Pattern.compile(regExp2);
		}
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isEmail(String str)
	{
		String regExp = "^[a-zA-Z0-9]+([._\\-]*[a-zA-Z0-9])*@([a-zA-Z0-9]+[-a-zA-Z0-9]*[a-zA-Z0-9]+.){1,63}[a-zA-Z0-9]+$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isAbc(String str)
	{
		String regExp = "^[a-z0-9]+$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isChinaPhoneNumber(String str)
	{
		String regExp = "^(\\+86)?((1\\d{10})|((0\\d{2,3})?\\d{8}))$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isChinaMobilePhoneNumber(String str)
	{
		String regExp = "^(\\+86)?1\\d{10}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isChinaMobilePhoneNumberAddZero(String str)
	{
		String regExp = "^(\\+86)?01\\d{10}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isChinaTelePhoneNumberWithAreaCode(String str)
	{
		String regExp = "^(\\+86)?0\\d{10,11}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isChinaTelePhoneNumberWithoutAreaCode(String str)
	{
		String regExp = "\\d{8}";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static String Remove86InChinaPhoneNumber(String str)
	{
		if (str.startsWith("+86"))
		{
			return str.substring(3);
		}
		return str;
	}

	public static String ConvertToChinaPhoneNumber(String str, boolean hasZero, String areaCode)
	{
		if (isChinaPhoneNumber(str))
		{
			String number = Remove86InChinaPhoneNumber(str);
			if (hasZero && isChinaMobilePhoneNumber(number))
			{
				number = "0" + number;
			}
			if (areaCode != null && !number.startsWith("0") && number.length() == 8)
			{
				number = areaCode + number;
			}
			return number;
		}
		return str;
	}

	public static boolean isNumber(String str, int min, int max)
	{
		String regExp = "\\d{" + min + "," + max + "}";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isUnKnownPhoneNumber(String str)
	{
		String regExp = "-\\d*";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isFloat(String str)
	{
		String regExp = "\\d+(.\\d*)?";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static String convertToCallPhoneNumber(String originalCallNumber)
	{
		String num = "";
		originalCallNumber = Remove86InChinaPhoneNumber(originalCallNumber);
		// num = PhoneNumberUtils.convertKeypadLettersToDigits(caller);
		for (int i = 0; i < originalCallNumber.length(); i++)
		{
			if (originalCallNumber.charAt(i) >= '0' && originalCallNumber.charAt(i) <= '9')
			{
				num += originalCallNumber.charAt(i);
			}
		}
		return num;
	}

	public static boolean isNull(String str)
	{
		if (str == null || str.length() < 1)
		{
			return true;
		}
		return false;
	}

	public static Timestamp getCurrentDateTime()
	{
		Date now = new Date();
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return Timestamp.valueOf(from.format(now));
	}

	public static Timestamp getDateTimeString(long time)
	{
		Date tm = new Date(time);
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return Timestamp.valueOf(from.format(tm));
	}

	public static long getTime(String tmp)
	{
		System.out.println("getTime调用" + tmp);
		// if (!tmp.startsWith("20"))
		// {
		// tmp = "20" + tmp;
		// }
		// if (tmp.endsWith(".000"))
		// {
		// tmp = tmp.replace(".000", "");
		// }
		// SimpleDateFormat from = new SimpleDateFormat("yy-MM-d H:mm:ss.SSS");
		// System.out.println("调用" + Timestamp.valueOf(from.format(tmp)).toString());
		Timestamp t = Timestamp.valueOf(tmp);
		return t.getTime();
		// return System.currentTimeMillis();
	}

	/**
	 * 获取当前时间，格式为yyyyMMddHHmmss
	 *
	 * @param tm
	 *            时间long类型
	 * @return
	 */
	public static String getTimeNum(long tm)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = format.format(new Date(tm));
		return date;
	}

	/**
	 * 获取当前时间，格式为yyyyMMddHHmmss
	 *
	 * @return
	 */
	public static String getCurrentTimeNum()
	{
		return getTimeNum(System.currentTimeMillis());
	}

	public static String getValue(String str, String start, String end)
	{
		String result = str;
		if (StringTools.isNull(str))
		{
			return result;
		}
		int index = -1;
		if (StringTools.isNull(start))
		{
			result = str;
		}
		else
		{
			index = str.indexOf(start);
			if (index > -1)
			{
				result = str.substring(index + start.length());
			}
		}
		if (!StringTools.isNull(end))
		{
			index = result.indexOf(end);
			if (index > -1)
			{
				result = result.substring(0, index);
			}
		}
		// result = trimPound(result);
		return result;
	}

	public static String getValue(String str, String start, String end, boolean isNull)
	{
		String result = "";
		if (!isNull)
		{
			result = str;
		}
		if (StringTools.isNull(str))
		{
			return result;
		}
		int index = -1;
		if (StringTools.isNull(start))
		{
			result = str;
		}
		else
		{
			index = str.indexOf(start);
			if (index > -1)
			{
				result = str.substring(index + start.length());
			}
		}
		if (!StringTools.isNull(end))
		{
			index = result.indexOf(end);
			if (index > -1)
			{
				result = result.substring(0, index);
			}
		}
		return result;
	}

	public static char[] charSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	/**
	 * 将10进制转化为62进制
	 * 
	 * @param number
	 * @param length
	 *            转化成的62进制长度，不足length长度的话高位补0，否则不改变什么
	 * @return
	 */
	public static String _10_to_62(long number, int length)
	{
		Long rest = number;
		Stack<Character> stack = new Stack<Character>();
		StringBuilder result = new StringBuilder(0);
		while (rest != 0)
		{
			stack.add(charSet[new Long((rest - (rest / 62) * 62)).intValue()]);
			rest = rest / 62;
		}
		for (; !stack.isEmpty();)
		{
			result.append(stack.pop());
		}
		int result_length = result.length();
		StringBuilder temp0 = new StringBuilder();
		for (int i = 0; i < length - result_length; i++)
		{
			temp0.append('0');
		}
		return temp0.toString() + result.toString();
	}

	/**
	 * 将62进制转换成10进制数
	 * 
	 * @param ident62
	 * @return
	 */
	public static String convertBase62ToDecimal(String ident62)
	{
		long decimal = 0;
		for (int i = 0; i < ident62.length(); i++)
		{
			int k = 0;
			char c = ident62.charAt(i);
			if (c >= 'A' && c <= 'Z')
			{
				k = c - 'A' + 36;
			}
			else if (c >= 'a' && c <= 'z')
			{
				k = c - 'a' + 10;
			}
			else if (c >= '0' && c <= '9')
			{
				k = c - '0';
			}
			decimal += k * Math.pow(62, ident62.length() - i - 1);
			System.out.println("num:" + k);
		}
		return String.format("%d", decimal);
	}

	public static void logResult(String path, String content, boolean isAppend)
	{
		FileWriter writer = null;
		try
		{
			writer = new FileWriter(path, isAppend);
			writer.write(content);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	// 转码，用于输出stream类型
	public static byte[] getUtf8Bytes(String str)
	{
		try
		{
			return str.getBytes("utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将字符串大写替换为带下划线的组合
	 * 
	 * @param name
	 * @return
	 */
	public static String getConstantName(String name)
	{
		String result = "";
		if (StringTools.isNull(name))
		{
			return "";
		}
		int len = name.length();
		for (int i = 0; i < len; i++)
		{
			char ch = name.charAt(i);
			if (i > 0 && ch >= 'A' && ch <= 'Z')
			{
				result += "_" + String.valueOf(ch).toUpperCase();
			}
			else
			{
				result += String.valueOf(ch).toUpperCase();
			}
		}
		return result;
	}

	/**
	 * 转换成类的实例名称
	 * 
	 * @param className
	 * @return
	 */
	public static String getClassInstanceName(String className)
	{
		String result = "";
		if (StringTools.isNull(className))
		{
			return "";
		}
		int len = className.length();
		for (int i = 0; i < len; i++)
		{
			char ch = className.charAt(i);
			if (i == 0)
			{
				result += String.valueOf(ch).toLowerCase();
			}
			else
			{
				if (ch == '_')
				{
					if (i + 1 < len)
					{
						i = i + 1;
						result += String.valueOf(className.charAt(i)).toUpperCase();
					}
					else
					{
						break;
					}
				}
				else
				{
					result += ch;
				}
			}
		}
		return result;
	}

	/**
	 * 转换成类名称
	 * 
	 * @param className
	 * @return
	 */
	public static String getClassName(String name)
	{
		String result = "";
		if (StringTools.isNull(name))
		{
			return "";
		}
		int len = name.length();
		for (int i = 0; i < len; i++)
		{
			char ch = name.charAt(i);
			if (i == 0)
			{
				result += String.valueOf(ch).toUpperCase();
			}
			else
			{
				if (ch == '_')
				{
					if (i + 1 < len)
					{
						i = i + 1;
						result += String.valueOf(name.charAt(i)).toUpperCase();
					}
					else
					{
						break;
					}
				}
				else
				{
					result += ch;
				}
			}
		}
		return result;
	}

	/**
	 * 读文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static final String getFileContent(String fileName)
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			StringBuffer bs = new StringBuffer();
			String line = "";
			while (true)
			{
				line = br.readLine();
				if (line == null)
				{
					break;
				}
				bs.append(line + "\n");
			}
			br.close();
			br = null;
			String content = bs.toString();
			return content;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 写文件
	 * 
	 * @param fileName
	 * @param content
	 * @return
	 */
	public static final boolean writeFileConent(String fileName, String content, boolean overWritten)
	{
		BufferedWriter bw = null;
		try
		{
			File file = new File(fileName);
			if (file.getParentFile() != null && !file.getParentFile().exists())
			{
				file.getParentFile().mkdirs();
			}
			if (!file.exists())
			{
				file.createNewFile();
			}
			if (file.exists() && overWritten)
			{
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
				bw.write(content);
				bw.close();
				bw = null;
			}
			return true;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (bw != null)
			{
				try
				{
					bw.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 获取文件夹下的所有文件名
	 * 
	 * @param dirName
	 * @return
	 */
	public static List<String> getFileNameList(String dirName)
	{
		File file = new File(dirName);
		if (!file.isDirectory())
		{
			return null;
		}
		List<String> files = new ArrayList<String>();
		String[] strs = file.list();
		for (String str : strs)
		{
			files.add(str);
		}
		return files;
	}

	/**
	 * 检查参数是否为空
	 * 
	 * @param params
	 *            参数
	 * @param tips
	 *            提示字符串
	 * @return
	 */
	public static ReturnInfo checkParams(String[] params, String[] tips)
	{
		int len1 = params.length;
		int len2 = tips.length;
		if (len1 == len2 && len1 > 0)
		{
			for (int i = 0; i < len1; i++)
			{
				if (StringTools.isNull(params[i]))
				{
					return new ReturnInfo(ErrorInfo.PARAM_ERROR, tips[i]);
				}
			}
		}
		return null;
	}

	/**
	 * 将表的列名字改为小写加下划线的组合
	 * 
	 * @param columnName
	 *            列名
	 * @return
	 */
	public static String getTableColumnName(String columnName)
	{
		String result = "";
		if (StringTools.isNull(columnName))
		{
			return "";
		}
		int len = columnName.length();
		for (int i = 0; i < len; i++)
		{
			char ch = columnName.charAt(i);
			if (ch >= 'A' && ch <= 'Z')
			{
				result += "_" + String.valueOf(ch).toLowerCase();
			}
			else
			{
				result += ch;
			}
		}
		return result;
	}

	/**
	 * 将页面传过来的表的列名字改为小写加下划线的组合
	 * 
	 * @param columnName
	 *            列名
	 * @return
	 */
	public static String getOrderColumnName(String columnName)
	{
		if (StringTools.isNull(columnName))
		{
			return "";
		}
		String[] names = columnName.split("\\.");
		String result = "";
		int len = names.length;
		for (int i = 0; i < len; i++)
		{
			if (i == 0)
			{
				result += names[i].substring(0, 1).toUpperCase() + names[i].substring(1);
			}
			else
			{
				result += "." + getTableColumnName(names[i]);
			}
		}
		return result;
	}

	/**
	 * 把中文转成Unicode码
	 * 
	 * @param str
	 * @return
	 */
	public static String chinaToUnicode(String str)
	{
		String result = "";
		for (int i = 0; i < str.length(); i++)
		{
			int chr1 = str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941)
			{
				result += "\\u" + Integer.toHexString(chr1);
			}
			else
			{
				result += str.charAt(i);
			}
		}
		return result;
	}

	/**
	 * 获取字符串的字节长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static int getStringByteLength(String value)
	{
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++)
		{
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese))
			{
				/* 中文字符长度为2 */
				valueLength += 2;
			}
			else
			{
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 过滤掉超过3个字节的UTF8字符
	 * 
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException
	{
		byte[] bytes = text.getBytes("utf-8");
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		int i = 0;
		while (i < bytes.length)
		{
			short b = bytes[i];
			if (b > 0)
			{
				buffer.put(bytes[i++]);
				continue;
			}
			b += 256; // 去掉符号位
			if (((b >> 5) ^ 0x6) == 0)
			{
				buffer.put(bytes, i, 2);
				i += 2;
			}
			else if (((b >> 4) ^ 0xE) == 0)
			{
				buffer.put(bytes, i, 3);
				i += 3;
			}
			else if (((b >> 3) ^ 0x1E) == 0)
			{
				i += 4;
			}
			else if (((b >> 2) ^ 0x3E) == 0)
			{
				i += 5;
			}
			else if (((b >> 1) ^ 0x7E) == 0)
			{
				i += 6;
			}
			else
			{
				buffer.put(bytes[i++]);
			}
		}
		buffer.flip();
		// return new String(buffer.array(), "utf-8");
		return new String(buffer.array(), 0, buffer.limit(), "utf-8");
	}

	public static String parseWxNickname(String nickName)
	{
		if (isNull(nickName))
		{
			return "";
		}
		try
		{
			// nickName = new String(nickName.getBytes(), "gb2312");
			Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Matcher emojiMatcher = emoji.matcher(nickName);
			if (emojiMatcher.find())
			{
				nickName = emojiMatcher.replaceAll("");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			// int index = nickName.indexOf("\\x");
			// while (index > 0)
			// {
			// nickName = nickName.substring(0, index) + nickName.substring(index + 4);
			// index = nickName.indexOf("\\x");
			// }
			nickName = StringTools.filterOffUtf8Mb4(nickName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return nickName;
	}

	public static void main(String[] args)
	{
		String path = "/Users/libit/Downloads/logs/debug.log.2017-03-27.log";
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			// long index = 0;
			while (true)
			{
				String line = br.readLine();
				if (line == null)
				{
					break;
				}
				// index++;
				// if (index > 10000)
				// {
				// break;
				// }
				// System.out.println(line + "\n");
				// System.out.println(index + "\n");
				if (line.contains("ajaxGetWxPrePay"))
				{
					System.out.println(line + "\n");
				}
			}
			br.close();
			br = null;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	// 创建颜色
	public static Color getRandColor(int fc, int bc)
	{
		Random random = new Random();
		if (fc > 255) fc = 255;
		if (bc > 255) bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public static final char[] LETTERS = "0123456789ABCDEF".toCharArray();

	/**
	 * 字节转16进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static String toHexString(byte[] bytes)
	{
		char[] values = new char[bytes.length * 2];
		int i = 0;
		for (byte b : bytes)
		{
			values[i++] = LETTERS[((b & 0xF0) >>> 4)];
			values[i++] = LETTERS[b & 0xF];
		}
		return String.valueOf(values);
	}

	/**
	 * 正则匹配
	 *
	 * @param p
	 * @param str
	 * @return
	 */
	public static String match(String p, String str)
	{
		Pattern pattern = Pattern.compile(p);
		Matcher m = pattern.matcher(str);
		if (m.find())
		{
			return m.group(1);
		}
		return null;
	}

	/**
	 * 取>=d的最小整数
	 * 
	 * @param str
	 * @return
	 */
	public static int getNavInt(double d)
	{
		int i = (int) Math.round(d);
		if (i < d)
		{
			i = i + 1;
		}
		return i;
	}

	/**
	 * 将数据库的以分计算的价格转换成元
	 *
	 * @param pricce
	 *            以分为单位的价格
	 * @return
	 */
	public static String getPrice(long pricce)
	{
		return String.format("%.2f", ((double) pricce) / 100);
	}
}
