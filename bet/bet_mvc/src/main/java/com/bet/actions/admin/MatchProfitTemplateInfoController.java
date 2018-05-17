package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.enums.BodanType;
import com.bet.orms.MatchProfitTemplateInfo;
import com.bet.services.IMatchProfitTemplateInfoService;

@Controller("adminMatchProfitTemplateInfoController")
@RequestMapping(value = "/admin")
public class MatchProfitTemplateInfoController extends BaseAdminController
{
	@Autowired
	private IMatchProfitTemplateInfoService matchProfitTemplateInfoService;

	@RequestMapping(value = "/manageMatchProfitTemplateInfos", method = RequestMethod.GET)
	public ModelAndView manageMatchProfitTemplateInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageMatchProfitTemplateInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/matchProfitTemplateInfoAdd", method = RequestMethod.GET)
	public ModelAndView matchProfitTemplateInfoAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("matchProfitTemplateInfoAdd"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("matchProfitTemplateInfo", new MatchProfitTemplateInfo());
		mv.addObject("bodanTypeMap", BodanType.getMap());
		return mv;
	}

	@RequestMapping(value = "/matchProfitTemplateInfoEdit", method = RequestMethod.GET)
	public ModelAndView matchProfitTemplateInfoEdit(HttpServletRequest request, @RequestParam(value = "templateId", required = true) String templateId)
	{
		ModelAndView mv = new ModelAndView(getViewName("matchProfitTemplateInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		MatchProfitTemplateInfo matchProfitTemplateInfo = matchProfitTemplateInfoService.getMatchProfitTemplateInfoByTemplateId(templateId);
		if (matchProfitTemplateInfo == null)
		{
			throw new RuntimeException("波胆模板信息ID不存在！");
		}
		mv.addObject("matchProfitTemplateInfo", matchProfitTemplateInfo);
		mv.addObject("bodanTypeMap", BodanType.getMap());
		return mv;
	}
}
