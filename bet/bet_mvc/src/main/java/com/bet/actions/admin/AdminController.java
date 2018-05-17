package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.AdminInfo;
import com.bet.services.IAdminInfoService;

@Controller("adminAdminController")
@RequestMapping("/admin")
public class AdminController extends BaseAdminController
{
	@Autowired
	private IAdminInfoService adminInfoService;

	/**
	 * 管理员登录页面<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("admin/login");
		mv.addObject("adminInfo", new AdminInfo());
		return mv;
	}

	/**
	 * 管理员退出登录<br>
	 * 退出后跳转到登录页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request)
	{
		setAdminSession(request, null);
		ModelAndView mv = new ModelAndView("redirect:/admin/login");
		// mv.addObject("adminInfo", new AdminInfo());
		return mv;
	}

	/**
	 * 添加管理员客服页面<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/adminAdd", method = RequestMethod.GET)
	public ModelAndView adminAdd(HttpServletRequest request)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/adminAdd");
		mv.addObject("adminInfo", new AdminInfo());
		return mv;
	}

	/**
	 * 修改管理员页面<br>
	 * 
	 * @param request
	 * @param userId
	 *            管理员ID
	 * @return
	 */
	@RequestMapping(value = "/adminEdit", method = RequestMethod.GET)
	public ModelAndView adminEdit(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/adminEdit");
		AdminInfo adminInfo = adminInfoService.getAdminInfoByUserId(userId);
		mv.addObject("adminInfo", adminInfo);
		return mv;
	}

	/**
	 * 管理管理员客服页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manageAdmins", method = RequestMethod.GET)
	public ModelAndView manageAdmins(HttpServletRequest request)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/manageAdmins");
		return mv;
	}

	/**
	 * 修改密码页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changePwd", method = RequestMethod.GET)
	public ModelAndView changePwd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("admin/changePwd");
		AdminInfo sessionAdminInfo = getAdminSession(request);
		AdminInfo adminInfo = new AdminInfo();
		adminInfo.setUserId(sessionAdminInfo.getUserId());
		mv.addObject("adminInfo", adminInfo);
		return mv;
	}
}
