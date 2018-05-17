package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.SmsCodeType;
import com.bet.orms.SmsCodeConfigInfo;
import com.bet.services.ISmsCodeConfigInfoService;

@Controller("adminSmsCodeConfigController")
@RequestMapping(value = "/admin")
public class SmsCodeConfigController extends BaseAdminController
{
	@Autowired
	private ISmsCodeConfigInfoService smsCodeConfigInfoService;

	/**
	 * 短信验证码配置管理页面<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manageSmsCodeConfigs", method = RequestMethod.GET)
	public ModelAndView manageSmsCodeConfigs(HttpServletRequest request)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/manageSmsCodeConfigs");
		return mv;
	}

	/**
	 * 添加短信验证码页面<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/smsCodeConfigAdd", method = RequestMethod.GET)
	public ModelAndView smsCodeConfigAdd(HttpServletRequest request)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/smsCodeConfigAdd");
		mv.addObject("smsCodeConfigInfo", new SmsCodeConfigInfo());
		mv.addObject("smsCodeTypeMap", SmsCodeType.getMap());
		return mv;
	}

	/**
	 * 修改短信验证码页面<br>
	 * 
	 * @param request
	 * @param configId
	 *            配置ID
	 * @return
	 */
	@RequestMapping(value = "/smsCodeConfigEdit", method = RequestMethod.GET)
	public ModelAndView smsCodeConfigEdit(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/smsCodeConfigEdit");
		SmsCodeConfigInfo smsCodeConfigInfo = smsCodeConfigInfoService.getSmsCodeConfigInfoByConfigId(configId);
		if (smsCodeConfigInfo == null)
		{
			throw new RuntimeException("验证码配置ID不存在！");
		}
		mv.addObject("smsCodeConfigInfo", smsCodeConfigInfo);
		mv.addObject("smsCodeTypeMap", SmsCodeType.getMap());
		return mv;
	}
}
