package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.MsgType;
import com.bet.orms.MsgInfo;
import com.bet.services.IMsgInfoService;

@Controller("adminMsgInfoController")
@RequestMapping(value = "/admin")
public class MsgInfoController extends BaseAdminController
{
	@Autowired
	private IMsgInfoService msgInfoService;

	@RequestMapping(value = "/manageMsgInfos", method = RequestMethod.GET)
	public ModelAndView manageMsgInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageMsgInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/msgInfoAdd", method = RequestMethod.GET)
	public ModelAndView msgInfoAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("msgInfoAdd"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("msgInfo", new MsgInfo());
		mv.addObject("msgTypeMap", MsgType.getMap());
		return mv;
	}

	@RequestMapping(value = "/msgInfoEdit", method = RequestMethod.GET)
	public ModelAndView msgInfoEdit(HttpServletRequest request, @RequestParam(value = "msgId", required = true) String msgId)
	{
		ModelAndView mv = new ModelAndView(getViewName("msgInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		MsgInfo msgInfo = msgInfoService.getMsgInfoByMsgId(msgId);
		if (msgInfo == null)
		{
			throw new RuntimeException("消息信息ID不存在！");
		}
		mv.addObject("msgInfo", msgInfo);
		mv.addObject("msgTypeMap", MsgType.getMap());
		return mv;
	}
}
