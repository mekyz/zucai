package com.bet.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bet.orms.UserInfo;
import com.bet.utils.SessionManage.SessionUser;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.utils.StringTools;

public class UserActiveInterceptor extends BaseInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception
	{
		// 判断用户是否已登录
		UserInfo userInfo = (UserInfo) (request.getSession()).getAttribute(SessionUser.USER_INFO.getName());
		if (userInfo == null)
		{
			// 如果没验证二级密码
			// e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/user/login");
			String url = request.getRequestURL().toString();
			if (!url.contains("user/login"))
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
		else
		{
			if (userInfo.getActiveStatus() != StatusType.ENABLED.getStatus())
			{
				// 如果没验证二级密码
				// e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/user/updatemembermsg");
				String url = request.getRequestURL().toString();
				if (!url.contains("user/login") && !url.contains("user/updatemembermsg"))
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
		}
		return true;
	}
}
