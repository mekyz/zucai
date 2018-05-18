package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.PayeeType;
import com.bet.orms.PayeeInfo;
import com.bet.orms.UserInfo;
import com.bet.orms.UserRechargeInfo;
import com.bet.services.IPayeeInfoService;
import com.bet.services.IUserRechargeInfoService;
import com.bet.utils.ParseModel;

@Controller("userUserRechargeInfoController")
@RequestMapping(value = "/user")
public class UserRechargeInfoController extends BaseUserController
{
	@Autowired
	private IUserRechargeInfoService userRechargeInfoService;
	@Autowired
	private IPayeeInfoService payeeInfoService;

	/**
	 * 充值申请页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rechargereply", method = RequestMethod.GET)
	public ModelAndView rechargereply(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("rechargereply"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 充值申请页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/rechargereply_m", method = RequestMethod.GET)
	public ModelAndView rechargereply_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("rechargereply_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	@RequestMapping(value = "/rechargerecord", method = RequestMethod.GET)
	public ModelAndView rechargerecord(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("rechargerecord"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	@RequestMapping(value = "/rechargerecord_m", method = RequestMethod.GET)
	public ModelAndView rechargerecord_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("rechargerecord_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	@RequestMapping(value = "/userRechargeInfo", method = RequestMethod.GET)
	public ModelAndView userRechargeInfo(HttpServletRequest request, @RequestParam(value = "rechargeId", required = true) String rechargeId)
	{
		ModelAndView mv = new ModelAndView(getViewName("userRechargeInfo"));
		UserInfo sessionUserInfo = getUserSession(request);
		UserRechargeInfo userRechargeInfo = userRechargeInfoService.getUserRechargeInfoByRechargeId(rechargeId);
		if (userRechargeInfo != null && sessionUserInfo.getUserId().equals(userRechargeInfo.getUserId()))
		{
			mv.addObject("userRechargeInfo", ParseModel.parseUserRechargeInfo(userRechargeInfo));
		}
		return mv;
	}

	@RequestMapping(value = "/reply_put1", method = RequestMethod.GET)
	public ModelAndView reply_put1(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("reply_put1"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		mv.addObject("userRechargeInfo", new UserRechargeInfo());
		// mv.addObject("rmbRate", configInfoService.getDoubleValue(BetConstValues.CONFIG_RMB_RATE));
		PayeeInfo payeeInfo = payeeInfoService.getPayeeInfoByPayeeType(PayeeType.PAY_ONLINE.getType());
		mv.addObject("payeeInfo", payeeInfo);
		return mv;
	}

	@RequestMapping(value = "/reply_put1_m", method = RequestMethod.GET)
	public ModelAndView reply_put1_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("reply_put1_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		mv.addObject("userRechargeInfo", new UserRechargeInfo());
		// mv.addObject("rmbRate", configInfoService.getDoubleValue(BetConstValues.CONFIG_RMB_RATE));
		PayeeInfo payeeInfo = payeeInfoService.getPayeeInfoByPayeeType(PayeeType.PAY_ONLINE.getType());
		mv.addObject("payeeInfo", payeeInfo);
		return mv;
	}

	@RequestMapping(value = "/reply_put2", method = RequestMethod.GET)
	public ModelAndView reply_put2(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("reply_put2"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		mv.addObject("userRechargeInfo", new UserRechargeInfo());
		// mv.addObject("rmbRate", configInfoService.getDoubleValue(BetConstValues.CONFIG_RMB_RATE));
		PayeeInfo payeeInfo = payeeInfoService.getPayeeInfoByPayeeType(PayeeType.UNIONPAY.getType());
		mv.addObject("payeeInfo", payeeInfo);
		return mv;
	}

	@RequestMapping(value = "/reply_put2_m", method = RequestMethod.GET)
	public ModelAndView reply_put2_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("reply_put2_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		mv.addObject("userRechargeInfo", new UserRechargeInfo());
		// mv.addObject("rmbRate", configInfoService.getDoubleValue(BetConstValues.CONFIG_RMB_RATE));
		PayeeInfo payeeInfo = payeeInfoService.getPayeeInfoByPayeeType(PayeeType.UNIONPAY.getType());
		mv.addObject("payeeInfo", payeeInfo);
		return mv;
	}

	@RequestMapping(value = "/reply_put3", method = RequestMethod.GET)
	public ModelAndView reply_put3(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("reply_put3"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		mv.addObject("userRechargeInfo", new UserRechargeInfo());
		// mv.addObject("rmbRate", configInfoService.getDoubleValue(BetConstValues.CONFIG_RMB_RATE));
		PayeeInfo payeeInfo = payeeInfoService.getPayeeInfoByPayeeType(PayeeType.ALIPAY.getType());
		mv.addObject("payeeInfo", payeeInfo);
		return mv;
	}

	@RequestMapping(value = "/reply_put3_m", method = RequestMethod.GET)
	public ModelAndView reply_put3_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("reply_put3_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		mv.addObject("userRechargeInfo", new UserRechargeInfo());
		// mv.addObject("rmbRate", configInfoService.getDoubleValue(BetConstValues.CONFIG_RMB_RATE));
		PayeeInfo payeeInfo = payeeInfoService.getPayeeInfoByPayeeType(PayeeType.ALIPAY.getType());
		mv.addObject("payeeInfo", payeeInfo);
		return mv;
	}
	@RequestMapping(value = "/reply_put5", method = RequestMethod.GET)
	public ModelAndView reply_put5(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("reply_put5"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		mv.addObject("userRechargeInfo", new UserRechargeInfo());
		// mv.addObject("rmbRate", configInfoService.getDoubleValue(BetConstValues.CONFIG_RMB_RATE));
		PayeeInfo payeeInfo = payeeInfoService.getPayeeInfoByPayeeType(PayeeType.WECHATPAY.getType());
		mv.addObject("payeeInfo", payeeInfo);
		return mv;
	}

	@RequestMapping(value = "/userRechargeInfoEdit", method = RequestMethod.GET)
	public ModelAndView userRechargeInfoEdit(HttpServletRequest request, @RequestParam(value = "rechargeId", required = true) String rechargeId)
	{
		ModelAndView mv = new ModelAndView(getViewName("userRechargeInfoEdit"));
		UserInfo sessionUserInfo = getUserSession(request);
		UserRechargeInfo userRechargeInfo = userRechargeInfoService.getUserRechargeInfoByRechargeId(rechargeId);
		if (userRechargeInfo == null)
		{
			throw new RuntimeException("用户充值信息ID不存在！");
		}
		if (sessionUserInfo.getUserId().equals(userRechargeInfo.getUserId()))
		{
			mv.addObject("userRechargeInfo", userRechargeInfo);
		}
		return mv;
	}
}
