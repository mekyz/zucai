package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.UserInfo;
import com.bet.utils.ParseModel;

@Controller("userUserBetInfoController")
@RequestMapping(value = "/user")
public class UserBetInfoController extends BaseUserController
{
	/**
	 * 投注记录页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/transaction_detail", method = RequestMethod.GET)
	public ModelAndView transaction_detail(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("transaction_detail"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 投注记录页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/transaction_detail_m", method = RequestMethod.GET)
	public ModelAndView transaction_detail_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("transaction_detail_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 投注结果页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/game_log", method = RequestMethod.GET)
	public ModelAndView game_log(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("game_log"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 投注结果页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/game_log_m", method = RequestMethod.GET)
	public ModelAndView game_log_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("game_log_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 团队投注页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/team_bets", method = RequestMethod.GET)
	public ModelAndView team_bets(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("team_bets"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 团队投注页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/team_bets_m", method = RequestMethod.GET)
	public ModelAndView team_bets_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("team_bets_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}
}
