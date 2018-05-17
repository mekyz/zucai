package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.ApplyStatus;
import com.bet.orms.UserRechargeInfo;
import com.bet.services.IUserRechargeInfoService;

@Controller("adminUserRechargeInfoController")
@RequestMapping(value = "/admin")
public class UserRechargeInfoController extends BaseAdminController
{
	@Autowired
	private IUserRechargeInfoService userRechargeInfoService;

	@RequestMapping(value = "/manageUserRechargeInfos", method = RequestMethod.GET)
	public ModelAndView manageUserRechargeInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageUserRechargeInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("applyStatusList", ApplyStatus.values());
		return mv;
	}

	@RequestMapping(value = "/userRechargeInfoEdit", method = RequestMethod.GET)
	public ModelAndView userRechargeInfoEdit(HttpServletRequest request, @RequestParam(value = "rechargeId", required = true) String rechargeId)
	{
		ModelAndView mv = new ModelAndView(getViewName("userRechargeInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		UserRechargeInfo userRechargeInfo = userRechargeInfoService.getUserRechargeInfoByRechargeId(rechargeId);
		if (userRechargeInfo == null)
		{
			throw new RuntimeException("用户充值信息ID不存在！");
		}
		mv.addObject("userRechargeInfo", userRechargeInfo);
		mv.addObject("applyStatusMap", ApplyStatus.getMap());
		return mv;
	}
}
