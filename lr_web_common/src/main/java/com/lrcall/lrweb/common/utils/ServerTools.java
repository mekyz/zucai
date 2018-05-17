package com.lrcall.lrweb.common.utils;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContext;

public class ServerTools
{
	/**
	 * 获取服务器项目的真实根路径
	 * 
	 * @param request
	 *            HTTP请求
	 * @return
	 */
	public static String getServerRealRootPath(HttpServletRequest request)
	{
		String path = request.getSession().getServletContext().getRealPath("/");
		if (StringTools.isNull(path))
		{
			path = "/";
		}
		if (!path.endsWith("/"))
		{
			path += "/";
		}
		// System.out.println("getServerRealRootPath:" + path);
		return path;
		// return getServerRealRootPath();
	}

	/**
	 * 通过web.xml设置的变量值yyy.webapp获取项目根路径，仅适用于SpringMVC
	 * 
	 * @return
	 */
	public static String getServerRealRootPath()
	{
		String root = RequestContext.class.getResource("/").getFile();
		try
		{
			return new File(root).getParentFile().getParentFile().getCanonicalPath() + "/";
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
