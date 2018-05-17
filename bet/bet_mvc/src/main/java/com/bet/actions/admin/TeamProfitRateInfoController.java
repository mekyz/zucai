package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.TeamProfitRateInfo;
import com.bet.services.ITeamProfitRateInfoService;

@Controller("adminTeamProfitRateInfoController")
@RequestMapping(value = "/admin")
public class TeamProfitRateInfoController extends BaseAdminController
{
	@Autowired
	private ITeamProfitRateInfoService teamProfitRateInfoService;

	@RequestMapping(value = "/manageTeamProfitRateInfos", method = RequestMethod.GET)
	public ModelAndView manageTeamProfitRateInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageTeamProfitRateInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	// @RequestMapping(value = "/teamProfitRateInfoAdd", method = RequestMethod.GET)
	// public ModelAndView teamProfitRateInfoAdd(HttpServletRequest request)
	// {
	// ModelAndView mv = new ModelAndView(getViewName("teamProfitRateInfoAdd"));
	// AdminInfo sessionAdminInfo = getAdminSession(request);
	// mv.addObject("teamProfitRateInfo", new TeamProfitRateInfo());
	// return mv;
	// }
	@RequestMapping(value = "/teamProfitRateInfoEdit", method = RequestMethod.GET)
	public ModelAndView teamProfitRateInfoEdit(HttpServletRequest request, @RequestParam(value = "rateId", required = true) String rateId)
	{
		ModelAndView mv = new ModelAndView(getViewName("teamProfitRateInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		TeamProfitRateInfo teamProfitRateInfo = teamProfitRateInfoService.getTeamProfitRateInfoByRateId(rateId);
		if (teamProfitRateInfo == null)
		{
			throw new RuntimeException("团队返利比率信息ID不存在！");
		}
		mv.addObject("teamProfitRateInfo", teamProfitRateInfo);
		return mv;
	}
}
