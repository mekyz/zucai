package com.bet.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lrcall.common.utils.LogTools;

public class BaseInterceptor implements HandlerInterceptor
{
	protected static final LogTools log = LogTools.getInstance();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception
	{
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView) throws Exception
	{
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handle, Exception ex) throws Exception
	{
	}
}
