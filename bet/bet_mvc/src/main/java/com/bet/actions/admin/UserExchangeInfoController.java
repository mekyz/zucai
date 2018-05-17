package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.UserExchangeInfo;
import com.bet.services.IUserExchangeInfoService;

@Controller("adminUserExchangeInfoController")
@RequestMapping(value = "/admin")
public class UserExchangeInfoController extends BaseAdminController
{
	@Autowired
	private IUserExchangeInfoService userExchangeInfoService;

	@RequestMapping(value = "/manageUserExchangeInfos", method = RequestMethod.GET)
	public ModelAndView manageUserExchangeInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageUserExchangeInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/userExchangeInfoEdit", method = RequestMethod.GET)
	public ModelAndView userExchangeInfoEdit(HttpServletRequest request, @RequestParam(value = "exchangeId", required = true) String exchangeId)
	{
		ModelAndView mv = new ModelAndView(getViewName("userExchangeInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		UserExchangeInfo userExchangeInfo = userExchangeInfoService.getUserExchangeInfoByExchangeId(exchangeId);
		if (userExchangeInfo == null)
		{
			throw new RuntimeException("用户转账信息ID不存在！");
		}
		mv.addObject("userExchangeInfo", userExchangeInfo);
		return mv;
	}
}
