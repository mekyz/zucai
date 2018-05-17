package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.MatchStatisticsLogInfo;
import com.bet.services.IMatchStatisticsLogInfoService;

@Controller("adminMatchStatisticsLogInfoController")
@RequestMapping(value = "/admin")
public class MatchStatisticsLogInfoController extends BaseAdminController
{
	@Autowired
	private IMatchStatisticsLogInfoService matchStatisticsLogInfoService;

	@RequestMapping(value = "/manageMatchStatisticsLogInfos", method = RequestMethod.GET)
	public ModelAndView manageMatchStatisticsLogInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageMatchStatisticsLogInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/matchStatisticsLogInfoEdit", method = RequestMethod.GET)
	public ModelAndView matchStatisticsLogInfoEdit(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId)
	{
		ModelAndView mv = new ModelAndView(getViewName("matchStatisticsLogInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		MatchStatisticsLogInfo matchStatisticsLogInfo = matchStatisticsLogInfoService.getMatchStatisticsLogInfoByPhaseId(phaseId);
		if (matchStatisticsLogInfo == null)
		{
			throw new RuntimeException("赛事统计信息ID不存在！");
		}
		mv.addObject("matchStatisticsLogInfo", matchStatisticsLogInfo);
		return mv;
	}
}
