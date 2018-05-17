package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.ClientBugInfo;
import com.bet.services.IClientBugInfoService;

@Controller("adminClientBugInfoController")
@RequestMapping(value = "/admin")
public class ClientBugInfoController extends BaseAdminController
{
	@Autowired
	private IClientBugInfoService clientBugInfoService;

	@RequestMapping(value = "/manageClientBugInfos", method = RequestMethod.GET)
	public ModelAndView manageClientBugInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("admin/manageClientBugInfos");
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/clientBugInfoEdit", method = RequestMethod.GET)
	public ModelAndView clientBugInfoEdit(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
	{
		ModelAndView mv = new ModelAndView("admin/clientBugInfoEdit");
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ClientBugInfo clientBugInfo = clientBugInfoService.getClientBugInfoById(id);
		if (clientBugInfo == null)
		{
			throw new RuntimeException("客户端BUG信息ID不存在！");
		}
		mv.addObject("clientBugInfo", clientBugInfo);
		return mv;
	}
}
