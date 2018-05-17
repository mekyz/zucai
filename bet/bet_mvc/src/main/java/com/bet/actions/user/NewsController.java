package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.NewsInfo;
import com.bet.orms.UserInfo;
import com.bet.services.INewsInfoService;

@Controller("userNewsController")
@RequestMapping("/user")
public class NewsController extends BaseUserController
{
	@Autowired
	private INewsInfoService newsInfoService;

	/**
	 * 公司公告页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public ModelAndView news(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("news"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		return mv;
	}

	/**
	 * 公司公告页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/news_m", method = RequestMethod.GET)
	public ModelAndView news_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("news_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		return mv;
	}

	/**
	 * 常见问题页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pews", method = RequestMethod.GET)
	public ModelAndView pews(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("pews"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		return mv;
	}

	/**
	 * 常见问题页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pews_m", method = RequestMethod.GET)
	public ModelAndView pews_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("pews_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		return mv;
	}

	/**
	 * 公告页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shownews/{newsId}", method = RequestMethod.GET)
	public ModelAndView shownews(HttpServletRequest request, @PathVariable(value = "newsId") String newsId)
	{
		ModelAndView mv = new ModelAndView(getViewName("shownews"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		NewsInfo newsInfo = newsInfoService.getNewsInfoByNewsId(newsId);
		mv.addObject("newsInfo", newsInfo);
		return mv;
	}

	/**
	 * 公告页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shownews_m/{newsId}", method = RequestMethod.GET)
	public ModelAndView shownews_m(HttpServletRequest request, @PathVariable(value = "newsId") String newsId)
	{
		ModelAndView mv = new ModelAndView(getViewName("shownews_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		NewsInfo newsInfo = newsInfoService.getNewsInfoByNewsId(newsId);
		mv.addObject("newsInfo", newsInfo);
		return mv;
	}
}
