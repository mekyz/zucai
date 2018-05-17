package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.TeamInfo;
import com.bet.services.ITeamInfoService;

@Controller("adminTeamInfoController")
@RequestMapping(value = "/admin")
public class TeamInfoController extends BaseAdminController
{
	@Autowired
	private ITeamInfoService teamInfoService;

	@RequestMapping(value = "/manageTeamInfos", method = RequestMethod.GET)
	public ModelAndView manageTeamInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageTeamInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/teamInfoAdd", method = RequestMethod.GET)
	public ModelAndView teamInfoAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("teamInfoAdd"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("teamInfo", new TeamInfo());
		return mv;
	}

	@RequestMapping(value = "/teamInfoEdit", method = RequestMethod.GET)
	public ModelAndView teamInfoEdit(HttpServletRequest request, @RequestParam(value = "teamId", required = true) String teamId)
	{
		ModelAndView mv = new ModelAndView(getViewName("teamInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		TeamInfo teamInfo = teamInfoService.getTeamInfoByTeamId(teamId);
		if (teamInfo == null)
		{
			throw new RuntimeException("球队信息ID不存在！");
		}
		mv.addObject("teamInfo", teamInfo);
		return mv;
	}
}
