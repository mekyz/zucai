package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.UserExchangeInfo;
import com.bet.orms.UserInfo;
import com.bet.services.IUserExchangeInfoService;
import com.bet.utils.ParseModel;

@Controller("userUserExchangeInfoController")
@RequestMapping(value = "/user")
public class UserExchangeInfoController extends BaseUserController
{
	@Autowired
	private IUserExchangeInfoService userExchangeInfoService;

	@RequestMapping(value = "/tfrecord", method = RequestMethod.GET)
	public ModelAndView tfrecord(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("tfrecord"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	@RequestMapping(value = "/tfrecord_m", method = RequestMethod.GET)
	public ModelAndView tfrecord_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("tfrecord_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	@RequestMapping(value = "/userExchangeInfo", method = RequestMethod.GET)
	public ModelAndView userExchangeInfo(HttpServletRequest request, @RequestParam(value = "exchangeId", required = true) String exchangeId)
	{
		ModelAndView mv = new ModelAndView(getViewName("userExchangeInfo"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		UserExchangeInfo userExchangeInfo = userExchangeInfoService.getUserExchangeInfoByExchangeId(exchangeId);
		if (userExchangeInfo != null && sessionUserInfo.getUserId().equals(userExchangeInfo.getUserId()))
		{
			mv.addObject("userExchangeInfo", ParseModel.parseUserExchangeInfo(userExchangeInfo));
		}
		return mv;
	}

	@RequestMapping(value = "/tfreply", method = RequestMethod.GET)
	public ModelAndView tfreply(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("tfreply"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		mv.addObject("userExchangeInfo", new UserExchangeInfo());
		return mv;
	}

	@RequestMapping(value = "/tfreply_m", method = RequestMethod.GET)
	public ModelAndView tfreply_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("tfreply_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		mv.addObject("userExchangeInfo", new UserExchangeInfo());
		return mv;
	}

	@RequestMapping(value = "/userExchangeInfoEdit", method = RequestMethod.GET)
	public ModelAndView userExchangeInfoEdit(HttpServletRequest request, @RequestParam(value = "exchangeId", required = true) String exchangeId)
	{
		ModelAndView mv = new ModelAndView(getViewName("userExchangeInfoEdit"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		UserExchangeInfo userExchangeInfo = userExchangeInfoService.getUserExchangeInfoByExchangeId(exchangeId);
		if (userExchangeInfo == null)
		{
			throw new RuntimeException("用户转账信息ID不存在！");
		}
		if (sessionUserInfo.getUserId().equals(userExchangeInfo.getUserId()))
		{
			mv.addObject("userExchangeInfo", userExchangeInfo);
		}
		return mv;
	}
}
