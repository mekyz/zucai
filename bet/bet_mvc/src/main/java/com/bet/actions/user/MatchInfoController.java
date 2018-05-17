package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.MatchInfo;
import com.bet.orms.UserInfo;
import com.bet.services.IMatchInfoService;
import com.bet.utils.ParseModel;

@Controller("userMatchInfoController")
@RequestMapping(value = "/user")
public class MatchInfoController extends BaseUserController
{
	@Autowired
	private IMatchInfoService matchInfoService;

	// @RequestMapping(value = "/matchInfoList", method = RequestMethod.GET)
	// public ModelAndView matchInfoList(HttpServletRequest request)
	// {
	// ModelAndView mv = new ModelAndView(getViewName("matchInfoList"));
	// // UserInfo sessionUserInfo = getUserSession(request);
	// return mv;
	// }
	/**
	 * 赛事结果页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/game_result", method = RequestMethod.GET)
	public ModelAndView game_result(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("game_result"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 赛事结果页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/game_result_m", method = RequestMethod.GET)
	public ModelAndView game_result_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("game_result_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	@RequestMapping(value = "/matchInfo/{phaseId}", method = RequestMethod.GET)
	public ModelAndView matchInfo(HttpServletRequest request, @PathVariable(value = "phaseId") String phaseId)
	{
		ModelAndView mv = new ModelAndView(getViewName("matchInfo"));
		// UserInfo sessionUserInfo = getUserSession(request);
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(phaseId);
		mv.addObject("matchInfo", ParseModel.parseMatchInfo(matchInfo));
		return mv;
	}
}
