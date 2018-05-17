package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.UserInfo;
import com.bet.orms.WithdrawInfo;
import com.bet.services.IUserInfoService;
import com.bet.services.IWithdrawInfoService;
import com.bet.utils.BetConstValues;
import com.bet.utils.ParseModel;

@Controller("userWithdrawInfoController")
@RequestMapping(value = "/user")
public class WithdrawInfoController extends BaseUserController
{
	@Autowired
	private IWithdrawInfoService withdrawInfoService;
	@Autowired
	private IUserInfoService userInfoService;

	/**
	 * 用户提现页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.GET)
	public ModelAndView withdraw(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("withdraw"));
		UserInfo sessionUserInfo = getUserSession(request);
		sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
		setUserSession(request, sessionUserInfo);
		mv.addObject("userInfo", sessionUserInfo);
		return mv;
	}

	@RequestMapping(value = "/wdrecord", method = RequestMethod.GET)
	public ModelAndView wdrecord(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("wdrecord"));
		UserInfo sessionUserInfo = getUserSession(request);
		sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
		setUserSession(request, sessionUserInfo);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	@RequestMapping(value = "/wdrecord_m", method = RequestMethod.GET)
	public ModelAndView wdrecord_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("wdrecord_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
		setUserSession(request, sessionUserInfo);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	@RequestMapping(value = "/withdrawInfo", method = RequestMethod.GET)
	public ModelAndView withdrawInfo(HttpServletRequest request, @RequestParam(value = "withdrawId", required = true) String withdrawId)
	{
		ModelAndView mv = new ModelAndView(getViewName("withdrawInfo"));
		UserInfo sessionUserInfo = getUserSession(request);
		WithdrawInfo withdrawInfo = withdrawInfoService.getWithdrawInfoByWithdrawId(withdrawId);
		if (withdrawInfo != null && sessionUserInfo.getUserId().equals(withdrawInfo.getUserId()))
		{
			mv.addObject("withdrawInfo", ParseModel.parseWithdrawInfo(withdrawInfo));
		}
		return mv;
	}

	@RequestMapping(value = "/wdreply", method = RequestMethod.GET)
	public ModelAndView wdreply(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("wdreply"));
		UserInfo sessionUserInfo = getUserSession(request);
		sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
		setUserSession(request, sessionUserInfo);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		mv.addObject("withdrawInfo", new WithdrawInfo());
		mv.addObject("minWithdraw", configInfoService.getDoubleValue(BetConstValues.CONFIG_MIN_WITHDRAW));
		return mv;
	}

	@RequestMapping(value = "/wdreply_m", method = RequestMethod.GET)
	public ModelAndView wdreply_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("wdreply_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
		setUserSession(request, sessionUserInfo);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		mv.addObject("withdrawInfo", new WithdrawInfo());
		mv.addObject("minWithdraw", configInfoService.getDoubleValue(BetConstValues.CONFIG_MIN_WITHDRAW));
		return mv;
	}

	@RequestMapping(value = "/withdrawInfoEdit", method = RequestMethod.GET)
	public ModelAndView withdrawInfoEdit(HttpServletRequest request, @RequestParam(value = "withdrawId", required = true) String withdrawId)
	{
		ModelAndView mv = new ModelAndView(getViewName("withdrawInfoEdit"));
		UserInfo sessionUserInfo = getUserSession(request);
		WithdrawInfo withdrawInfo = withdrawInfoService.getWithdrawInfoByWithdrawId(withdrawId);
		if (withdrawInfo == null)
		{
			throw new RuntimeException("用户提现信息ID不存在！");
		}
		if (withdrawInfo != null && sessionUserInfo.getUserId().equals(withdrawInfo.getUserId()))
		{
			mv.addObject("withdrawInfo", withdrawInfo);
		}
		return mv;
	}
}
