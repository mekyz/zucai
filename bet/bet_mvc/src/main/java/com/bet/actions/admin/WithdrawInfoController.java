package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.ApplyStatus;
import com.bet.orms.WithdrawInfo;
import com.bet.services.IWithdrawInfoService;

@Controller("adminWithdrawInfoController")
@RequestMapping(value = "/admin")
public class WithdrawInfoController extends BaseAdminController
{
	@Autowired
	private IWithdrawInfoService withdrawInfoService;

	@RequestMapping(value = "/manageWithdrawInfos", method = RequestMethod.GET)
	public ModelAndView manageWithdrawInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageWithdrawInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("applyStatusList", ApplyStatus.values());
		return mv;
	}

	@RequestMapping(value = "/withdrawInfoEdit", method = RequestMethod.GET)
	public ModelAndView withdrawInfoEdit(HttpServletRequest request, @RequestParam(value = "withdrawId", required = true) String withdrawId)
	{
		ModelAndView mv = new ModelAndView(getViewName("withdrawInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		WithdrawInfo withdrawInfo = withdrawInfoService.getWithdrawInfoByWithdrawId(withdrawId);
		if (withdrawInfo == null)
		{
			throw new RuntimeException("用户提现信息ID不存在！");
		}
		mv.addObject("withdrawInfo", withdrawInfo);
		mv.addObject("applyStatusMap", ApplyStatus.getMap());
		return mv;
	}
}
