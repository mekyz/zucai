package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.UserBetReturnInfo;
import com.bet.services.IUserBetReturnInfoService;

@Controller("adminUserBetReturnInfoController")
@RequestMapping(value = "/admin")
public class UserBetReturnInfoController extends BaseAdminController
{
	@Autowired
	private IUserBetReturnInfoService userBetReturnInfoService;

	@RequestMapping(value = "/manageUserBetReturnInfos", method = RequestMethod.GET)
	public ModelAndView manageUserBetReturnInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageUserBetReturnInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/userBetReturnInfoEdit", method = RequestMethod.GET)
	public ModelAndView userBetReturnInfoEdit(HttpServletRequest request, @RequestParam(value = "logId", required = true) String logId)
	{
		ModelAndView mv = new ModelAndView(getViewName("userBetReturnInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		UserBetReturnInfo userBetReturnInfo = userBetReturnInfoService.getUserBetReturnInfoByLogId(logId);
		if (userBetReturnInfo == null)
		{
			throw new RuntimeException("用户撤回下注信息ID不存在！");
		}
		mv.addObject("userBetReturnInfo", userBetReturnInfo);
		return mv;
	}
}
