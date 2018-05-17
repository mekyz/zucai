package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.MatchInfo;
import com.bet.orms.MatchProfitInfo;
import com.bet.orms.UserInfo;
import com.bet.services.IMatchInfoService;
import com.bet.services.IMatchProfitInfoService;
import com.bet.services.IUserInfoService;
import com.bet.utils.ParseModel;

@Controller("userMatchProfitInfoController")
@RequestMapping(value = "/user")
public class MatchProfitInfoController extends BaseUserController
{
	@Autowired
	private IMatchInfoService matchInfoService;
	@Autowired
	private IMatchProfitInfoService matchProfitInfoService;
	@Autowired
	private IUserInfoService userInfoService;

	/**
	 * 波胆列表页面
	 * 
	 * @param request
	 * @param phaseId
	 *            赛事ID
	 * @return
	 */
	@RequestMapping(value = "/participation_game/{phaseId}", method = RequestMethod.GET)
	public ModelAndView participation_game(HttpServletRequest request, @PathVariable(value = "phaseId") String phaseId)
	{
		ModelAndView mv = new ModelAndView(getViewName("participation_game"));
		UserInfo sessionUserInfo = getUserSession(request);
		sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
		setUserSession(request, sessionUserInfo);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(phaseId);
		mv.addObject("matchInfo", ParseModel.parseMatchInfo(matchInfo));
		return mv;
	}

	/**
	 * 波胆列表页面
	 * 
	 * @param request
	 * @param phaseId
	 *            赛事ID
	 * @return
	 */
	@RequestMapping(value = "/participation_game_m/{phaseId}", method = RequestMethod.GET)
	public ModelAndView participation_game_m(HttpServletRequest request, @PathVariable(value = "phaseId") String phaseId)
	{
		ModelAndView mv = new ModelAndView(getViewName("participation_game_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
		setUserSession(request, sessionUserInfo);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(phaseId);
		mv.addObject("matchInfo", ParseModel.parseMatchInfo(matchInfo));
		return mv;
	}

	/**
	 * 下注页面
	 * 
	 * @param request
	 * @param profitId
	 * @return
	 */
	@RequestMapping(value = "/xiazhu/{profitId}", method = RequestMethod.GET)
	public ModelAndView xiazhu(HttpServletRequest request, @PathVariable(value = "profitId") String profitId)
	{
		ModelAndView mv = new ModelAndView(getViewName("xiazhu"));
		UserInfo sessionUserInfo = getUserSession(request);
		sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
		setUserSession(request, sessionUserInfo);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		MatchProfitInfo matchProfitInfo = matchProfitInfoService.getMatchProfitInfoByProfitId(profitId);
		mv.addObject("matchProfitInfo", ParseModel.parseMatchProfitInfo(matchProfitInfo));
		MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(matchProfitInfo.getPhaseId());
		mv.addObject("matchInfo", ParseModel.parseMatchInfo(matchInfo));
		return mv;
	}
}
