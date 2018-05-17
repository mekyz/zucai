package com.bet.actions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.ClientInfo;
import com.bet.services.IClientInfoService;
import com.bet.services.IConfigInfoService;
import com.bet.utils.BetConstValues;
import com.lrcall.common.utils.StringTools;
import com.lrcall.common.utils.TimeTools;

@Controller("CommonController")
@RequestMapping("/")
public class CommonController extends BaseAction
{
	@Autowired
	public IConfigInfoService configInfoService;
	@Autowired
	private IClientInfoService clientInfoService;

	/**
	 * 任意页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/*")
	public ModelAndView common(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		String url = request.getRequestURL().toString();
		String params = request.getQueryString();
		if (!StringTools.isNull(params))
		{
			url = url + "?" + params;
		}
		log.info("common", TimeTools.getDateTimeString(System.currentTimeMillis()) + "请求的网址：" + url);
		return mv;
	}

	/**
	 * 二级目录任意页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/*/*")
	public ModelAndView common2(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		String url = request.getRequestURL().toString();
		String params = request.getQueryString();
		if (!StringTools.isNull(params))
		{
			url = url + "?" + params;
		}
		log.info("common2", TimeTools.getDateTimeString(System.currentTimeMillis()) + "请求的网址：" + url);
		return mv;
	}

	/**
	 * 下载页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/download")
	public ModelAndView download(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("download");
		ClientInfo androidClientInfo = clientInfoService.getLastAndroidClientInfo(configInfoService.getValue(BetConstValues.CONFIG_DEFAULT_AGENT));
		if (androidClientInfo == null)
		{
			androidClientInfo = new ClientInfo();
		}
		else
		{
			androidClientInfo.setContent(androidClientInfo.getContent().replace("\n", "<br>"));
		}
		ClientInfo iosClientInfo = clientInfoService.getLastIosClientInfo(configInfoService.getValue(BetConstValues.CONFIG_DEFAULT_AGENT));
		if (iosClientInfo == null)
		{
			iosClientInfo = new ClientInfo();
		}
		else
		{
			iosClientInfo.setContent(iosClientInfo.getContent().replace("\n", "<br>"));
		}
		mv.addObject("androidClientInfo", androidClientInfo);
		mv.addObject("iosClientInfo", iosClientInfo);
		mv.addObject("serverUrl", configInfoService.getServerUrl());
		return mv;
	}
}
