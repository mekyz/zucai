package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.UserType;
import com.bet.orms.TeamLeaderProfitConfigInfo;
import com.bet.services.ITeamLeaderProfitConfigInfoService;

@Controller("adminTeamLeaderProfitConfigInfoController")
@RequestMapping(value = "/admin")
public class TeamLeaderProfitConfigInfoController extends BaseAdminController
{
	@Autowired
	private ITeamLeaderProfitConfigInfoService teamLeaderProfitConfigInfoService;

	@RequestMapping(value = "/manageTeamLeaderProfitConfigInfos", method = RequestMethod.GET)
	public ModelAndView manageTeamLeaderProfitConfigInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageTeamLeaderProfitConfigInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/teamLeaderProfitConfigInfoAdd", method = RequestMethod.GET)
	public ModelAndView teamLeaderProfitConfigInfoAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("teamLeaderProfitConfigInfoAdd"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("teamLeaderProfitConfigInfo", new TeamLeaderProfitConfigInfo());
		return mv;
	}

	@RequestMapping(value = "/teamLeaderProfitConfigInfoEdit", method = RequestMethod.GET)
	public ModelAndView teamLeaderProfitConfigInfoEdit(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId)
	{
		ModelAndView mv = new ModelAndView(getViewName("teamLeaderProfitConfigInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo = teamLeaderProfitConfigInfoService.getTeamLeaderProfitConfigInfoByConfigId(configId);
		if (teamLeaderProfitConfigInfo == null)
		{
			throw new RuntimeException("领导奖配置信息ID不存在！");
		}
		mv.addObject("teamLeaderProfitConfigInfo", teamLeaderProfitConfigInfo);
		mv.addObject("userTypeMap", UserType.getMap());
		return mv;
	}
}
