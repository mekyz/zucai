package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.MoneyUnit;
import com.bet.enums.SexType;
import com.bet.enums.UserBalanceLogType;
import com.bet.orms.UserInfo;
import com.bet.services.CQrService;
import com.bet.services.IUserInfoService;
import com.bet.utils.BetConstValues;
import com.bet.utils.ParseModel;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.utils.GsonTools;
import com.lrcall.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.ServerTools;

@Controller("userUserController")
@RequestMapping("/user")
public class UserController extends BaseUserController
{
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private CQrService cQrService;

	/**
	 * 用户登录页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("login"));
		mv.addObject("userInfo", new UserInfo());
		mv.addObject("apiVersion", configInfoService.getIntValue(BetConstValues.CONFIG_API_VERSION));
		return mv;
	}

	/**
	 * 用户注册页面
	 * 
	 * @param request
	 * @param referrerId
	 *            推荐人ID
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(HttpServletRequest request, @RequestParam(value = "userId", required = false) String referrerId)
	{
		ModelAndView mv = new ModelAndView(getViewName("register"));
		mv.addObject("sexTypeMap", SexType.getMap());
		if (StringTools.isNull(referrerId))
		{
			referrerId = "";
		}
		mv.addObject("referrerId", referrerId);
		mv.addObject("userInfo", new UserInfo());
		mv.addObject("apiVersion", configInfoService.getIntValue(BetConstValues.CONFIG_API_VERSION));
		return mv;
	}

	/**
	 * 退出登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request)
	{
		setUserSession(request, null);
		ModelAndView mv = new ModelAndView(getRedirectViewName("login"));
		mv.addObject("userInfo", new UserInfo());
		return mv;
	}

	/**
	 * 二级密码验证页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/auth2", method = RequestMethod.GET)
	public ModelAndView auth2(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("auth2"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		return mv;
	}

	/**
	 * 二级密码验证页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/auth2_m", method = RequestMethod.GET)
	public ModelAndView auth2_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("auth2_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", sessionUserInfo);
		return mv;
	}

	/**
	 * 用户详情页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user_info", method = RequestMethod.GET)
	public ModelAndView user_info(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("user_info"));
		UserInfo sessionUserInfo = getUserSession(request);
		sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
		if (StringTools.isNull(sessionUserInfo.getQrUrl()))
		{
			ReturnInfo returnInfo = cQrService.updateUserQr(sessionUserInfo.getUserId(), ServerTools.getServerRealRootPath(request));
			if (ReturnInfo.isSuccess(returnInfo))
			{
				sessionUserInfo = GsonTools.getReturnObject(returnInfo, UserInfo.class);
			}
		}
		setUserSession(request, sessionUserInfo);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		mv.addObject("serverUrl", configInfoService.getServerUrl());
		return mv;
	}

	/**
	 * 用户详情页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user_info_m", method = RequestMethod.GET)
	public ModelAndView user_info_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("user_info_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
		if (StringTools.isNull(sessionUserInfo.getQrUrl()))
		{
			ReturnInfo returnInfo = cQrService.updateUserQr(sessionUserInfo.getUserId(), ServerTools.getServerRealRootPath(request));
			if (ReturnInfo.isSuccess(returnInfo))
			{
				sessionUserInfo = GsonTools.getReturnObject(returnInfo, UserInfo.class);
			}
		}
		setUserSession(request, sessionUserInfo);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		mv.addObject("serverUrl", configInfoService.getServerUrl());
		return mv;
	}

	/**
	 * 修改密码页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatepwd", method = RequestMethod.GET)
	public ModelAndView updatepwd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("updatepwd"));
		UserInfo sessionUserInfo = getUserSession(request);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(sessionUserInfo.getUserId());
		mv.addObject("userInfo", userInfo);
		return mv;
	}

	/**
	 * 修改密码页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatepwd_m", method = RequestMethod.GET)
	public ModelAndView updatepwd_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("updatepwd_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(sessionUserInfo.getUserId());
		mv.addObject("userInfo", userInfo);
		return mv;
	}

	/**
	 * 重置密码页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/lookPwdByPhone", method = RequestMethod.GET)
	public ModelAndView lookPwdByPhone(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("lookPwdByPhone"));
		// mv.addObject("userInfo", new UserInfo());
		mv.addObject("apiVersion", configInfoService.getIntValue(BetConstValues.CONFIG_API_VERSION));
		return mv;
	}

	/**
	 * 重置密码页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/lookPwdByPhone_m", method = RequestMethod.GET)
	public ModelAndView lookPwdByPhone_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("lookPwdByPhone_m"));
		// mv.addObject("userInfo", new UserInfo());
		mv.addObject("apiVersion", configInfoService.getIntValue(BetConstValues.CONFIG_API_VERSION));
		return mv;
	}

	/**
	 * 修改用户资料页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatemembermsg", method = RequestMethod.GET)
	public ModelAndView updatemembermsg(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("updatemembermsg"));
		UserInfo sessionUserInfo = getUserSession(request);
		sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
		setUserSession(request, sessionUserInfo);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		mv.addObject("sexTypeMap", SexType.getMap());
		return mv;
	}

	/**
	 * 修改用户资料页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatemembermsg_m", method = RequestMethod.GET)
	public ModelAndView updatemembermsg_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("updatemembermsg_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		sessionUserInfo = userInfoService.getUserInfoByUserId(sessionUserInfo.getUserId());
		setUserSession(request, sessionUserInfo);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		mv.addObject("sexTypeMap", SexType.getMap());
		return mv;
	}

	/**
	 * 直推会员页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userindex", method = RequestMethod.GET)
	public ModelAndView userindex(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("userindex"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 直推会员页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userindex_m", method = RequestMethod.GET)
	public ModelAndView userindex_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("userindex_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 注册会员页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/registuser", method = RequestMethod.GET)
	public ModelAndView registuser(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("registuser"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		mv.addObject("apiVersion", configInfoService.getIntValue(BetConstValues.CONFIG_API_VERSION));
		return mv;
	}

	/**
	 * 注册会员页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/registuser_m", method = RequestMethod.GET)
	public ModelAndView registuser_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("registuser_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		mv.addObject("apiVersion", configInfoService.getIntValue(BetConstValues.CONFIG_API_VERSION));
		return mv;
	}

	/**
	 * 推荐关系页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ttree", method = RequestMethod.GET)
	public ModelAndView ttree(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("ttree"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 推荐关系页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ttree_m", method = RequestMethod.GET)
	public ModelAndView ttree_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("ttree_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 财务流水页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/financialflow", method = RequestMethod.GET)
	public ModelAndView financialflow(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("financialflow"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		mv.addObject("userBalanceLogTypeMap", UserBalanceLogType.getMap());
		mv.addObject("moneyUnitMap", MoneyUnit.getMap());
		return mv;
	}

	/**
	 * 财务流水页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/financialflow_m", method = RequestMethod.GET)
	public ModelAndView financialflow_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("financialflow_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		mv.addObject("userBalanceLogTypeMap", UserBalanceLogType.getMap());
		mv.addObject("moneyUnitMap", MoneyUnit.getMap());
		return mv;
	}

	/**
	 * 在线客服页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/service_list", method = RequestMethod.GET)
	public ModelAndView service_list(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("service_list"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 在线客服页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/service_list_m", method = RequestMethod.GET)
	public ModelAndView service_list_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("service_list_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 友情链接页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/link_list", method = RequestMethod.GET)
	public ModelAndView link_list(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("link_list"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 友情链接页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/link_list_m", method = RequestMethod.GET)
	public ModelAndView link_list_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("link_list_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}
}
