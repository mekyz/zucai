package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.UserTypeConditionInfo;
import com.bet.services.IUserTypeConditionInfoService;

@Controller("adminUserTypeConditionInfoController")
@RequestMapping(value = "/admin")
public class UserTypeConditionInfoController extends BaseAdminController
{
	@Autowired
	private IUserTypeConditionInfoService userTypeConditionInfoService;

	@RequestMapping(value = "/manageUserTypeConditionInfos", method = RequestMethod.GET)
	public ModelAndView manageUserTypeConditionInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageUserTypeConditionInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	// @RequestMapping(value = "/userTypeConditionInfoAdd", method = RequestMethod.GET)
	// public ModelAndView userTypeConditionInfoAdd(HttpServletRequest request)
	// {
	// ModelAndView mv = new ModelAndView(getViewName("userTypeConditionInfoAdd"));
	// AdminInfo sessionAdminInfo = getAdminSession(request);
	// mv.addObject("userTypeConditionInfo", new UserTypeConditionInfo());
	// return mv;
	// }
	@RequestMapping(value = "/userTypeConditionInfoEdit", method = RequestMethod.GET)
	public ModelAndView userTypeConditionInfoEdit(HttpServletRequest request, @RequestParam(value = "userType", required = true) byte userType)
	{
		ModelAndView mv = new ModelAndView(getViewName("userTypeConditionInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		UserTypeConditionInfo userTypeConditionInfo = userTypeConditionInfoService.getUserTypeConditionInfoByUserType(userType);
		if (userTypeConditionInfo == null)
		{
			throw new RuntimeException("用户类型满足的条件信息ID不存在！");
		}
		mv.addObject("userTypeConditionInfo", userTypeConditionInfo);
		return mv;
	}
}
