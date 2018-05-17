package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.UserBetReturnInfo;
import com.bet.orms.UserInfo;
import com.bet.services.IUserBetReturnInfoService;
import com.bet.utils.ParseModel;

@Controller("userUserBetReturnInfoController")
@RequestMapping(value = "/user")
public class UserBetReturnInfoController extends BaseUserController
{
	@Autowired
	private IUserBetReturnInfoService userBetReturnInfoService;

	@RequestMapping(value = "/userBetReturnList", method = RequestMethod.GET)
	public ModelAndView userBetReturnList(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("userBetReturnList"));
		// UserInfo sessionUserInfo = getUserSession(request);
		return mv;
	}

	@RequestMapping(value = "/userBetReturnList_m", method = RequestMethod.GET)
	public ModelAndView userBetReturnList_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("userBetReturnList_m"));
		// UserInfo sessionUserInfo = getUserSession(request);
		return mv;
	}

	@RequestMapping(value = "/userBetReturnInfo", method = RequestMethod.GET)
	public ModelAndView userBetReturnInfo(HttpServletRequest request, @RequestParam(value = "logId", required = true) String logId)
	{
		ModelAndView mv = new ModelAndView(getViewName("userBetReturnInfo"));
		UserInfo sessionUserInfo = getUserSession(request);
		UserBetReturnInfo userBetReturnInfo = userBetReturnInfoService.getUserBetReturnInfoByLogId(logId);
		if (sessionUserInfo.getUserId().equals(userBetReturnInfo.getUserId()))
		{
			mv.addObject("userBetReturnInfo", ParseModel.parseUserBetReturnInfo(userBetReturnInfo));
		}
		return mv;
	}
}
