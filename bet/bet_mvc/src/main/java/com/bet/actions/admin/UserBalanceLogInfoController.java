package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.MoneyUnit;
import com.bet.enums.UserBalanceLogType;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.services.IUserBalanceLogInfoService;

@Controller("adminUserBalanceLogInfoController")
@RequestMapping(value = "/admin")
public class UserBalanceLogInfoController extends BaseAdminController
{
	@Autowired
	private IUserBalanceLogInfoService userBalanceLogInfoService;

	@RequestMapping(value = "/manageUserBalanceLogInfos", method = RequestMethod.GET)
	public ModelAndView manageUserBalanceLogInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageUserBalanceLogInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("userBalanceLogTypeList", UserBalanceLogType.values());
		mv.addObject("moneyUnitList", MoneyUnit.values());
		return mv;
	}

	@RequestMapping(value = "/userBalanceLogInfoAdd", method = RequestMethod.GET)
	public ModelAndView userBalanceLogInfoAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("userBalanceLogInfoAdd"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("userBalanceLogInfo", new UserBalanceLogInfo());
		return mv;
	}

	@RequestMapping(value = "/userBalanceLogInfoEdit", method = RequestMethod.GET)
	public ModelAndView userBalanceLogInfoEdit(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
	{
		ModelAndView mv = new ModelAndView(getViewName("userBalanceLogInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		UserBalanceLogInfo userBalanceLogInfo = userBalanceLogInfoService.getUserBalanceLogInfoById(id);
		if (userBalanceLogInfo == null)
		{
			throw new RuntimeException("用户余额记录信息ID不存在！");
		}
		mv.addObject("userBalanceLogInfo", userBalanceLogInfo);
		return mv;
	}
}
