package com.bet.actions.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.MsgInfo;
import com.bet.services.IMsgInfoService;
import com.bet.utils.ParseModel;

@Controller("userMsgInfoController")
@RequestMapping(value = "/user")
public class MsgInfoController extends BaseUserController
{
	@Autowired
	private IMsgInfoService msgInfoService;

	@RequestMapping(value = "/msgInfoList", method = RequestMethod.GET)
	public ModelAndView msgInfoList(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("msgInfoList"));
		// UserInfo sessionUserInfo = getUserSession(request);
		return mv;
	}

	@RequestMapping(value = "/msgInfo", method = RequestMethod.GET)
	public ModelAndView msgInfo(HttpServletRequest request, @RequestParam(value = "msgId", required = true) String msgId)
	{
		ModelAndView mv = new ModelAndView(getViewName("msgInfo"));
		// UserInfo sessionUserInfo = getUserSession(request);
		MsgInfo msgInfo = msgInfoService.getMsgInfoByMsgId(msgId);
		mv.addObject("msgInfo", ParseModel.parseMsgInfo(msgInfo));
		return mv;
	}
}
