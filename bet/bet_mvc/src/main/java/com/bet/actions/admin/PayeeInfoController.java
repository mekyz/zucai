package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.PayeeType;
import com.bet.orms.PayeeInfo;
import com.bet.services.IPayeeInfoService;

@Controller("adminPayeeInfoController")
@RequestMapping(value = "/admin")
public class PayeeInfoController extends BaseAdminController
{
	@Autowired
	private IPayeeInfoService payeeInfoService;

	@RequestMapping(value = "/managePayeeInfos", method = RequestMethod.GET)
	public ModelAndView managePayeeInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("managePayeeInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/payeeInfoAdd", method = RequestMethod.GET)
	public ModelAndView payeeInfoAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("payeeInfoAdd"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("payeeInfo", new PayeeInfo());
		mv.addObject("payeeTypeMap", PayeeType.getMap());
		return mv;
	}

	@RequestMapping(value = "/payeeInfoEdit", method = RequestMethod.GET)
	public ModelAndView payeeInfoEdit(HttpServletRequest request, @RequestParam(value = "payeeId", required = true) String payeeId)
	{
		ModelAndView mv = new ModelAndView(getViewName("payeeInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		PayeeInfo payeeInfo = payeeInfoService.getPayeeInfoByPayeeId(payeeId);
		if (payeeInfo == null)
		{
			throw new RuntimeException("收款信息ID不存在！");
		}
		mv.addObject("payeeInfo", payeeInfo);
		mv.addObject("payeeTypeMap", PayeeType.getMap());
		return mv;
	}
}
