package com.bet.actions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.lrcall.common.utils.LogTools;

@ControllerAdvice
public class ExceptionAction
{
	private static final LogTools log = LogTools.getInstance();

	@ExceptionHandler
	public ModelAndView exceptionHandler(HttpServletRequest request, Exception e)
	{
		e.printStackTrace();
		String url = request.getRequestURI();
		// 寻找是否是ajax请求
		// int index = url.lastIndexOf("/");
		// String path = url.substring(index);
		// if (!StringTools.isNull(path))
		// {
		// if (path.startsWith("/ajax"))// ajax请求
		// {
		// }
		// }
		ModelAndView mv = new ModelAndView("/error");
		mv.addObject("exception", e);
		log.error("ExceptionAction", "请求路径：" + url + ",错误信息：" + e.getMessage());
		if (e.getCause() != null)
		{
			log.error("ExceptionAction", "请求路径：" + url + ",错误信息：" + e.getCause().getMessage());
		}
		return mv;
	}
}
