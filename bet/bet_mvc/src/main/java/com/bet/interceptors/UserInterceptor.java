package com.bet.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.bet.orms.UserInfo;
import com.bet.services.IConfigInfoService;
import com.bet.services.IUserInfoService;
import com.bet.utils.BetConstValues;
import com.bet.utils.SessionManage.SessionUser;
import com.lrcall.common.utils.StringTools;

public class UserInterceptor extends BaseInterceptor
{
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	public IConfigInfoService configInfoService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception
	{
		if (configInfoService.getBooleanValue(BetConstValues.CONFIG_ADMIN_MAINTAIN))
		{
			response.sendRedirect(request.getContextPath() + "/user/maintain");
			return false;
		}
		// 判断用户是否已登录
		UserInfo userInfo = (UserInfo) (request.getSession()).getAttribute(SessionUser.USER_INFO.getName());
		if (userInfo == null)
		{
			// 如果没登录，再检查是否携带了登录session
			try
			{
				String userId = request.getParameter("userId");
				String sessionId = request.getParameter("sessionId");
				userInfo = userInfoService.loginBySession(userId, sessionId);
				(request.getSession()).setAttribute(SessionUser.USER_INFO.getName(), userInfo);
				return true;
			}
			catch (Exception e)
			{
				// e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/user/login");
				String url = request.getRequestURL().toString();
				if (!url.contains("user/login"))
				{
					// String url = request.getRequestURI();
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
		else
		{
		}
		return true;
	}
}
