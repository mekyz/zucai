package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.bet.actions.BaseAction;
import com.bet.orms.AdminInfo;
import com.bet.services.IConfigInfoService;
import com.bet.utils.SessionManage.SessionAdmin;

public class BaseAdminController extends BaseAction
{
	@Autowired
	public IConfigInfoService configInfoService;

	public static String getViewName(String name)
	{
		return String.format("admin/%s", name);
	}

	public static String getRedirectViewName(String name)
	{
		return String.format("redirect:/admin/%s", name);
	}

	/**
	 * 设置Session
	 * 
	 * @param request
	 * @param adminInfo
	 */
	protected void setAdminSession(HttpServletRequest request, AdminInfo adminInfo)
	{
		(request.getSession()).setAttribute(SessionAdmin.ADMIN_INFO.getName(), adminInfo);
	}

	/**
	 * 获取Session
	 * 
	 * @param request
	 * @return
	 */
	protected AdminInfo getAdminSession(HttpServletRequest request)
	{
		return (AdminInfo) (request.getSession()).getAttribute(SessionAdmin.ADMIN_INFO.getName());
	}

	/**
	 * 获取Session
	 * 
	 * @param request
	 * @return
	 */
	protected String getCodeSession(HttpServletRequest request)
	{
		return (String) (request.getSession()).getAttribute(SessionAdmin.ADMIN_RANDOM_CODE.getName());
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
		(request.getSession()).setAttribute(SessionAdmin.ADMIN_RANDOM_CODE.getName(), code);
	}
}
