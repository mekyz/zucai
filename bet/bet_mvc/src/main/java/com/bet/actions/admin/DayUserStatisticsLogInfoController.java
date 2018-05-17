package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.DayUserStatisticsLogInfo;
import com.bet.services.IDayUserStatisticsLogInfoService;

@Controller("adminDayUserStatisticsLogInfoController")
@RequestMapping(value = "/admin")
public class DayUserStatisticsLogInfoController extends BaseAdminController
{
	@Autowired
	private IDayUserStatisticsLogInfoService dayUserStatisticsLogInfoService;

	@RequestMapping(value = "/manageDayUserStatisticsLogInfos", method = RequestMethod.GET)
	public ModelAndView manageDayUserStatisticsLogInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageDayUserStatisticsLogInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/dayUserStatisticsLogInfoEdit", method = RequestMethod.GET)
	public ModelAndView dayUserStatisticsLogInfoEdit(HttpServletRequest request, @RequestParam(value = "logId", required = true) String logId)
	{
		ModelAndView mv = new ModelAndView(getViewName("dayUserStatisticsLogInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		DayUserStatisticsLogInfo dayUserStatisticsLogInfo = dayUserStatisticsLogInfoService.getDayUserStatisticsLogInfoByLogId(logId);
		if (dayUserStatisticsLogInfo == null)
		{
			throw new RuntimeException("用户每日数据统计信息ID不存在！");
		}
		mv.addObject("dayUserStatisticsLogInfo", dayUserStatisticsLogInfo);
		return mv;
	}
}
