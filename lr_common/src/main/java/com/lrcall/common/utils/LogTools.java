package com.lrcall.common.utils;

import org.apache.log4j.Logger;

public class LogTools
{
	private static Logger logger = Logger.getLogger(LogTools.class);
	private static LogTools instance = null;

	public static LogTools getInstance()
	{
		if (instance == null)
		{
			instance = new LogTools();
		}
		return instance;
	}

	private LogTools()
	{
	}

	public void debug(String tag, String msg)
	{
		long current = System.currentTimeMillis();
		System.out.println(String.format("%s 调试标签:%s,信息:%s.", TimeTools.getDateTimeString(current), tag, msg));
		logger.debug(msg);
	}

	public void info(String tag, String msg)
	{
		long current = System.currentTimeMillis();
		System.out.println(String.format("%s 信息标签:%s,信息:%s.", TimeTools.getDateTimeString(current), tag, msg));
		logger.info(msg);
	}

	public void error(String tag, String msg)
	{
		long current = System.currentTimeMillis();
		System.out.println(String.format("%s 错误标签:%s,信息:%s.", TimeTools.getDateTimeString(current), tag, msg));
		logger.error(msg);
	}
}
