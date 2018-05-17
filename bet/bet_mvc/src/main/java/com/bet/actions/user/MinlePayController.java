package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.UserInfo;
import com.bet.utils.ParseModel;

@Controller("userMinlePayController")
@RequestMapping(value = "/user")
public class MinlePayController extends BaseUserController
{
	/**
	 * 民乐支付结果返回页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/minlePayBack", method = RequestMethod.GET)
	public ModelAndView minlePayBack(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("minlePayBack"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}
}
