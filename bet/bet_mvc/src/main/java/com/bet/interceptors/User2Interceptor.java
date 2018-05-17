package com.bet.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bet.utils.SessionManage.SessionUser;
import com.lrcall.common.utils.StringTools;

public class User2Interceptor extends BaseInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception
	{
		// 判断用户是否已登录
		Boolean password2Auth = (Boolean) (request.getSession()).getAttribute(SessionUser.PASSWORD2_AUTH.getName());
		if (password2Auth == null || !password2Auth)
		{
			// 如果没验证二级密码
			// e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/user/auth2");
			String url = request.getRequestURL().toString();
			if (!url.contains("user/login") && !url.contains("user/auth2"))
			{
				String params = request.getQueryString();
				if (!StringTools.isNull(params))
				{
					url = url + "?" + params;
				}
				(request.getSession()).setAttribute(SessionUser.USER_PRE_URL.getName(), url);
			}
			else
			{
				(request.getSession()).setAttribute(SessionUser.USER_PRE_URL.getName(), "");
			}
			return false;
		}
		return true;
	}
}
