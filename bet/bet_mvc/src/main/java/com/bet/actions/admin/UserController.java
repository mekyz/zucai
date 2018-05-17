package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.UserType;
import com.bet.orms.UserInfo;
import com.bet.services.IUserInfoService;
import com.bet.utils.SessionManage.SessionUser;

@Controller("adminUserController")
@RequestMapping("/admin")
public class UserController extends BaseAdminController
{
	@Autowired
	private IUserInfoService userInfoService;

	/**
	 * 用户管理页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manageUsers", method = RequestMethod.GET)
	public ModelAndView manageUsers(HttpServletRequest request)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView(getViewName("manageUsers"));
		mv.addObject("userTypeList", UserType.values());
		return mv;
	}

	/**
	 * 用户信息页面
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @return
	 */
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public ModelAndView userInfo(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView(getViewName("userInfo"));
		UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
		mv.addObject("userInfo", userInfo);
		return mv;
	}

	/**
	 * 跳转到客户端用户页面
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @return
	 */
	@RequestMapping(value = "/goUserInfo", method = RequestMethod.GET)
	public ModelAndView goUserInfo(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("redirect:/user/index");
		UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
		setSession(request, SessionUser.USER_INFO.getName(), userInfo);
		return mv;
	}
}
