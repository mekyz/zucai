package com.lrcall.lrweb.common.utils;

import javax.persistence.Table;

/**
 * 字符串辅助类
 * 
 * @author libit
 * @date 2015-12-27
 */
public class StringTools extends com.lrcall.common.utils.StringTools
{
	/**
	 * 获取Hibernate的注解表名
	 * 
	 * @param c
	 *            class
	 * @return
	 */
	public static String getTableName(Class<?> c)
	{
		Table a = c.getAnnotation(Table.class);
		String name = a.name();
		return name;
	}
}
