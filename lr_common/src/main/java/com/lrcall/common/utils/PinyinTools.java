/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.lrcall.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by libit on 15/8/11.
 */
public class PinyinTools
{
	/**
	 * 汉字转拼音
	 *
	 * @param cnStr
	 *            待转换的汉字
	 * @return 拼音的字符串（全部大写）
	 */
	public static String Chinese2Pinyin(String cnStr)
	{
		HanyuPinyinOutputFormat PINYIN_FORMAT = new HanyuPinyinOutputFormat();
		PINYIN_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		PINYIN_FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cnStr.length(); i++)
		{
			char c = cnStr.charAt(i);
			if (c <= 255)
			{
				sb.append(c);
			}
			else
			{
				String pinyin = null;
				try
				{
					String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, PINYIN_FORMAT);
					pinyin = pinyinArray[0];
				}
				catch (BadHanyuPinyinOutputFormatCombination e)
				{
				}
				catch (NullPointerException e)
				{
				}
				if (pinyin != null)
				{
					sb.append(pinyin);
				}
			}
		}
		// Log.i("汉字转拼音", cnStr + "->" + sb.toString());
		return sb.toString();
	}

	public static String getCharacterPinYin(char c)
	{
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		String[] pinyin = null;
		try
		{
			pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
		}
		catch (BadHanyuPinyinOutputFormatCombination e)
		{
			e.printStackTrace();
		}
		if (pinyin == null)
		{
			return null;
		}
		return pinyin[0];
	}
}
