package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.TeamLeaderProfitInfo;
import com.bet.services.ITeamLeaderProfitInfoService;

@Controller("adminTeamLeaderProfitInfoController")
@RequestMapping(value = "/admin")
public class TeamLeaderProfitInfoController extends BaseAdminController
{
	@Autowired
	private ITeamLeaderProfitInfoService teamLeaderProfitInfoService;

	@RequestMapping(value = "/manageTeamLeaderProfitInfos", method = RequestMethod.GET)
	public ModelAndView manageTeamLeaderProfitInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageTeamLeaderProfitInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/teamLeaderProfitInfoEdit", method = RequestMethod.GET)
	public ModelAndView teamLeaderProfitInfoEdit(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId)
	{
		ModelAndView mv = new ModelAndView(getViewName("teamLeaderProfitInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		TeamLeaderProfitInfo teamLeaderProfitInfo = teamLeaderProfitInfoService.getTeamLeaderProfitInfoByProfitId(profitId);
		if (teamLeaderProfitInfo == null)
		{
			throw new RuntimeException("团队领导奖信息ID不存在！");
		}
		mv.addObject("teamLeaderProfitInfo", teamLeaderProfitInfo);
		return mv;
	}
}
