package com.bet.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bet.orms.AdminInfo;
import com.bet.utils.SessionManage.SessionAdmin;
import com.lrcall.common.utils.StringTools;

public class AdminInterceptor extends BaseInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception
	{
		// 判断管理员是否已登录
		AdminInfo adminInfo = (AdminInfo) (request.getSession()).getAttribute(SessionAdmin.ADMIN_INFO.getName());
		if (adminInfo == null)
		{
			response.sendRedirect(request.getContextPath() + "/admin/login");
			String url = request.getRequestURL().toString();
			if (!url.contains("admin/login"))
			{
				String params = request.getQueryString();
				if (!StringTools.isNull(params))
				{
					url = url + "?" + params;
				}
				(request.getSession()).setAttribute(SessionAdmin.ADMIN_PRE_URL.getName(), url);
			}
			else
			{
				(request.getSession()).setAttribute(SessionAdmin.ADMIN_PRE_URL.getName(), "");
			}
			return false;
		}
		else
		{
			(request.getSession()).setAttribute(SessionAdmin.ADMIN_PRE_URL.getName(), "");
		}
		return true;
	}
}
