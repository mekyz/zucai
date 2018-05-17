package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.PayPlatform;
import com.bet.orms.PayConfigInfo;
import com.bet.services.IPayConfigInfoService;

@Controller("adminPayConfigInfoController")
@RequestMapping(value = "/admin")
public class PayConfigInfoController extends BaseAdminController
{
	@Autowired
	private IPayConfigInfoService payConfigInfoService;

	@RequestMapping(value = "/managePayConfigInfos", method = RequestMethod.GET)
	public ModelAndView managePayConfigInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("managePayConfigInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/payConfigInfoAdd", method = RequestMethod.GET)
	public ModelAndView payConfigInfoAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("payConfigInfoAdd"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("payConfigInfo", new PayConfigInfo());
		mv.addObject("payPlatformMap", PayPlatform.getMap());
		return mv;
	}

	@RequestMapping(value = "/payConfigInfoEdit", method = RequestMethod.GET)
	public ModelAndView payConfigInfoEdit(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId)
	{
		ModelAndView mv = new ModelAndView(getViewName("payConfigInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		PayConfigInfo payConfigInfo = payConfigInfoService.getPayConfigInfoByConfigId(configId);
		if (payConfigInfo == null)
		{
			throw new RuntimeException("支付配置信息ID不存在！");
		}
		mv.addObject("payConfigInfo", payConfigInfo);
		mv.addObject("payPlatformMap", PayPlatform.getMap());
		return mv;
	}
}
