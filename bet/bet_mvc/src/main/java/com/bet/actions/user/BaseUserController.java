package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.bet.actions.BaseAction;
import com.bet.orms.UserInfo;
import com.bet.services.IConfigInfoService;
import com.bet.utils.SessionManage.SessionUser;

public class BaseUserController extends BaseAction
{
	@Autowired
	public IConfigInfoService configInfoService;

	public static String getViewName(String name)
	{
		return String.format("user/%s", name);
	}

	public static String getRedirectViewName(String name)
	{
		return String.format("redirect:/user/%s", name);
	}

	public static String getViewName(HttpServletRequest request, String name)
	{
		return String.format("user/%s%s", name, isMobile(request) ? "_m" : "");
	}

	public static String getRedirectViewName(HttpServletRequest request, String name)
	{
		return String.format("redirect:/user/%s%s", name, isMobile(request) ? "_m" : "");
	}

	/**
	 * 设置Session
	 * 
	 * @param request
	 * @param userInfo
	 *            用户信息
	 */
	protected void setUserSession(HttpServletRequest request, UserInfo userInfo)
	{
		(request.getSession()).setAttribute(SessionUser.USER_INFO.getName(), userInfo);
	}

	/**
	 * 获取Session
	 * 
	 * @param request
	 * @return
	 */
	protected UserInfo getUserSession(HttpServletRequest request)
	{
		return (UserInfo) (request.getSession()).getAttribute(SessionUser.USER_INFO.getName());
	}

	/**
	 * 设置Session
	 * 
	 * @param request
	 * @param prePage
	 *            登录前网页
	 */
	protected void setPrePageSession(HttpServletRequest request, String prePage)
	{
		(request.getSession()).setAttribute(SessionUser.USER_PRE_URL.getName(), prePage);
	}

	/**
	 * 获取Session
	 * 
	 * @param request
	 * @return
	 */
	protected String getPrePageSession(HttpServletRequest request)
	{
		return (String) (request.getSession()).getAttribute(SessionUser.USER_PRE_URL.getName());
	}

	/**
	 * 设置Session
	 * 
	 * @param request
	 * @param code
	 *            验证码
	 */
	protected void setCodeSession(HttpServletRequest request, String code)
	{
		(request.getSession()).setAttribute(SessionUser.RANDOM_CODE.getName(), code);
	}

	/**
	 * 获取Session
	 * 
	 * @param request
	 * @return
	 */
	protected String getCodeSession(HttpServletRequest request)
	{
		return (String) (request.getSession()).getAttribute(SessionUser.RANDOM_CODE.getName());
	}

	/**
	 * 设置Session
	 * 
	 * @param request
	 * @param code
	 *            验证码
	 */
	protected void setAuth2Session(HttpServletRequest request, boolean auth2)
	{
		(request.getSession()).setAttribute(SessionUser.PASSWORD2_AUTH.getName(), auth2);
	}

	/**
	 * 获取Session
	 * 
	 * @param request
	 * @return
	 */
	protected Boolean getAuth2Session(HttpServletRequest request)
	{
		return (Boolean) (request.getSession()).getAttribute(SessionUser.PASSWORD2_AUTH.getName());
	}
}
