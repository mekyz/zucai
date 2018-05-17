package com.bet.actions.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.MatchInfo;
import com.bet.orms.TeamInfo;
import com.bet.services.IMatchInfoService;
import com.bet.services.ITeamInfoService;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

@Controller("adminMatchInfoController")
@RequestMapping(value = "/admin")
public class MatchInfoController extends BaseAdminController
{
	@Autowired
	private IMatchInfoService matchInfoService;
	@Autowired
	private ITeamInfoService teamInfoService;

	@RequestMapping(value = "/manageMatchInfos", method = RequestMethod.GET)
	public ModelAndView manageMatchInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageMatchInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	/**
	 * 比赛90分钟后未开奖的赛事
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manageUnOpenMatchInfos", method = RequestMethod.GET)
	public ModelAndView manageUnOpenMatchInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageUnOpenMatchInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/matchInfoAdd", method = RequestMethod.GET)
	public ModelAndView matchInfoAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("matchInfoAdd"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("matchInfo", new MatchInfo());
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		List<TeamInfo> teamInfoList = teamInfoService.getTeamInfoList(0, -1, orderInfos, searchInfos);
		mv.addObject("teamInfoList", teamInfoList);
		return mv;
	}

	@RequestMapping(value = "/matchInfoEdit", method = RequestMethod.GET)
	public ModelAndView matchInfoEdit(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId)
	{
		ModelAndView mv = new ModelAndView(getViewName("matchInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(phaseId);
		if (matchInfo == null)
		{
			throw new RuntimeException("比赛信息ID不存在！");
		}
		mv.addObject("matchInfo", matchInfo);
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		List<TeamInfo> teamInfoList = teamInfoService.getTeamInfoList(0, -1, orderInfos, searchInfos);
		mv.addObject("teamInfoList", teamInfoList);
		return mv;
	}
}
