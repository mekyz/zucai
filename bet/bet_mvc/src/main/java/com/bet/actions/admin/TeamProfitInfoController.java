package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.TeamProfitInfo;
import com.bet.services.ITeamProfitInfoService;

@Controller("adminTeamProfitInfoController")
@RequestMapping(value = "/admin")
public class TeamProfitInfoController extends BaseAdminController
{
	@Autowired
	private ITeamProfitInfoService teamProfitInfoService;

	@RequestMapping(value = "/manageTeamProfitInfos", method = RequestMethod.GET)
	public ModelAndView manageTeamProfitInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageTeamProfitInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/teamProfitInfoEdit", method = RequestMethod.GET)
	public ModelAndView teamProfitInfoEdit(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId)
	{
		ModelAndView mv = new ModelAndView(getViewName("teamProfitInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		TeamProfitInfo teamProfitInfo = teamProfitInfoService.getTeamProfitInfoByProfitId(profitId);
		if (teamProfitInfo == null)
		{
			throw new RuntimeException("团队奖信息ID不存在！");
		}
		mv.addObject("teamProfitInfo", teamProfitInfo);
		return mv;
	}
}
