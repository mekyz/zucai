package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.MatchInfo;
import com.bet.orms.MatchProfitInfo;
import com.bet.orms.UserBetInfo;
import com.bet.services.IMatchInfoService;
import com.bet.services.IMatchProfitInfoService;
import com.bet.services.IUserBetInfoService;

@Controller("adminUserBetInfoController")
@RequestMapping(value = "/admin")
public class UserBetInfoController extends BaseAdminController
{
	@Autowired
	private IUserBetInfoService userBetInfoService;
	@Autowired
	private IMatchInfoService matchInfoService;
	@Autowired
	private IMatchProfitInfoService matchProfitInfoService;

	@RequestMapping(value = "/manageUserBetInfos", method = RequestMethod.GET)
	public ModelAndView manageUserBetInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageUserBetInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/userBetInfoEdit", method = RequestMethod.GET)
	public ModelAndView userBetInfoEdit(HttpServletRequest request, @RequestParam(value = "betId", required = true) String betId)
	{
		ModelAndView mv = new ModelAndView(getViewName("userBetInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		UserBetInfo userBetInfo = userBetInfoService.getUserBetInfoByBetId(betId);
		if (userBetInfo == null)
		{
			throw new RuntimeException("投注信息ID不存在！");
		}
		mv.addObject("userBetInfo", userBetInfo);
		MatchProfitInfo matchProfitInfo = matchProfitInfoService.getMatchProfitInfoByProfitId(userBetInfo.getProfitId());
		if (matchProfitInfo != null)
		{
			mv.addObject("matchProfitInfo", matchProfitInfo);
			MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(matchProfitInfo.getPhaseId());
			if (matchInfo != null)
			{
				mv.addObject("matchInfo", matchInfo);
			}
		}
		return mv;
	}
}
