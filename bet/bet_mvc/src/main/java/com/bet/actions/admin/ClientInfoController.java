package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.ClientInfo;
import com.bet.services.IClientInfoService;

@Controller("adminClientInfoController")
@RequestMapping(value = "/admin")
public class ClientInfoController extends BaseAdminController
{
	@Autowired
	private IClientInfoService clientInfoService;

	@RequestMapping(value = "/manageClients", method = RequestMethod.GET)
	public ModelAndView manageClients(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("admin/manageClients");
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/clientInfoAdd", method = RequestMethod.GET)
	public ModelAndView clientInfoAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("admin/clientInfoAdd");
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("clientInfo", new ClientInfo());
		mv.addObject("serverUrl", configInfoService.getServerUrl());
		return mv;
	}

	@RequestMapping(value = "/clientInfoEdit", method = RequestMethod.GET)
	public ModelAndView clientInfoEdit(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
	{
		ModelAndView mv = new ModelAndView("admin/clientInfoEdit");
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ClientInfo clientInfo = clientInfoService.getClientInfoById(id);
		if (clientInfo == null)
		{
			throw new RuntimeException("客户端信息ID不存在！");
		}
		mv.addObject("clientInfo", clientInfo);
		mv.addObject("serverUrl", configInfoService.getServerUrl());
		return mv;
	}
}
