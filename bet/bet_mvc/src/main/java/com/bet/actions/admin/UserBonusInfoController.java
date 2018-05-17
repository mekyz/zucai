package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.UserBonusInfo;
import com.bet.services.IUserBonusInfoService;

@Controller("adminUserBonusInfoController")
@RequestMapping(value = "/admin")
public class UserBonusInfoController extends BaseAdminController
{
	@Autowired
	private IUserBonusInfoService userBonusInfoService;

	@RequestMapping(value = "/manageUserBonusInfos", method = RequestMethod.GET)
	public ModelAndView manageUserBonusInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageUserBonusInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/userBonusInfoEdit", method = RequestMethod.GET)
	public ModelAndView userBonusInfoEdit(HttpServletRequest request, @RequestParam(value = "bonusId", required = true) String bonusId)
	{
		ModelAndView mv = new ModelAndView(getViewName("userBonusInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		UserBonusInfo userBonusInfo = userBonusInfoService.getUserBonusInfoByBonusId(bonusId);
		if (userBonusInfo == null)
		{
			throw new RuntimeException("用户奖金信息ID不存在！");
		}
		mv.addObject("userBonusInfo", userBonusInfo);
		return mv;
	}
}
