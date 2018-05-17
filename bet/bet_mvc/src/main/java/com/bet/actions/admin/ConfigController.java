package com.bet.actions.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.ConfigInfo;

@Controller("adminConfigController")
@RequestMapping("/admin")
public class ConfigController extends BaseAdminController
{
	/**
	 * 参数设置页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/manageConfig", method = RequestMethod.GET)
	public ModelAndView manageConfig(HttpServletRequest request)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/manageConfig");
		List<ConfigInfo> configInfoList = configInfoService.getConfigInfoList(0, -1, null, null);
		mv.addObject("configInfoList", configInfoList);
		Map<String, String> bList = new HashMap<>();
		bList.put("true", "是");
		bList.put("false", "否");
		mv.addObject("bList", bList);
		mv.addObject("configInfo", new ConfigInfo());
		return mv;
	}
}
