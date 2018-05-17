package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.TeamProfitType;
import com.bet.orms.UserInfo;
import com.bet.utils.ParseModel;

@Controller("userUserBonusInfoController")
@RequestMapping(value = "/user")
public class UserBonusInfoController extends BaseUserController
{
	// @Autowired
	// private IUserBonusInfoService userBonusInfoService;
	/**
	 * 团队奖金页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/teamBonus", method = RequestMethod.GET)
	public ModelAndView teamBonus(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("teamBonus"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 团队奖金页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/teamBonus_m", method = RequestMethod.GET)
	public ModelAndView teamBonus_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("teamBonus_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 领袖奖金页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/teamLeaderBonus", method = RequestMethod.GET)
	public ModelAndView teamLeaderBonus(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("teamLeaderBonus"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 领袖奖金页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/teamLeaderBonus_m", method = RequestMethod.GET)
	public ModelAndView teamLeaderBonus_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("teamLeaderBonus_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus", method = RequestMethod.GET)
	public ModelAndView bonus(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus_m", method = RequestMethod.GET)
	public ModelAndView bonus_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus/{phaseId}", method = RequestMethod.GET)
	public ModelAndView bonus(HttpServletRequest request, @PathVariable(value = "phaseId") String phaseId)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		mv.addObject("phaseId", phaseId);
		mv.addObject("teamProfitTypeMap", TeamProfitType.getMap());
		return mv;
	}

	/**
	 * 奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus_m/{phaseId}", method = RequestMethod.GET)
	public ModelAndView bonus_m(HttpServletRequest request, @PathVariable(value = "phaseId") String phaseId)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		mv.addObject("phaseId", phaseId);
		mv.addObject("teamProfitTypeMap", TeamProfitType.getMap());
		return mv;
	}

	/**
	 * 分享奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus_share", method = RequestMethod.GET)
	public ModelAndView bonus_share(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus_share"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 分享奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus_share_m", method = RequestMethod.GET)
	public ModelAndView bonus_share_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus_share_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 代理奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus_agent", method = RequestMethod.GET)
	public ModelAndView bonus_agent(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus_agent"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 代理奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus_agent_m", method = RequestMethod.GET)
	public ModelAndView bonus_agent_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus_agent_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 代理平级奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus_agent_same", method = RequestMethod.GET)
	public ModelAndView bonus_agent_same(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus_agent_same"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 代理平级奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus_agent_same_m", method = RequestMethod.GET)
	public ModelAndView bonus_agent_same_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus_agent_same_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 福利奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus_benfit", method = RequestMethod.GET)
	public ModelAndView bonus_benfit(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus_benfit"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 福利奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus_benfit_m", method = RequestMethod.GET)
	public ModelAndView bonus_benfit_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus_benfit_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 福利平级奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus_benfit_same", method = RequestMethod.GET)
	public ModelAndView bonus_benfit_same(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus_benfit_same"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 福利平级奖金明细页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bonus_benfit_same_m", method = RequestMethod.GET)
	public ModelAndView bonus_benfit_same_m(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("bonus_benfit_same_m"));
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}
}
