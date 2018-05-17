package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.DayStatisticsLogInfo;
import com.bet.services.IDayStatisticsLogInfoService;

@Controller("adminDayStatisticsLogInfoController")
@RequestMapping(value = "/admin")
public class DayStatisticsLogInfoController extends BaseAdminController
{
	@Autowired
	private IDayStatisticsLogInfoService dayStatisticsLogInfoService;

	@RequestMapping(value = "/manageDayStatisticsLogInfos", method = RequestMethod.GET)
	public ModelAndView manageDayStatisticsLogInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageDayStatisticsLogInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/dayStatisticsLogInfoEdit", method = RequestMethod.GET)
	public ModelAndView dayStatisticsLogInfoEdit(HttpServletRequest request, @RequestParam(value = "dayId", required = true) String dayId)
	{
		ModelAndView mv = new ModelAndView(getViewName("dayStatisticsLogInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		DayStatisticsLogInfo dayStatisticsLogInfo = dayStatisticsLogInfoService.getDayStatisticsLogInfoByDayId(dayId);
		if (dayStatisticsLogInfo == null)
		{
			throw new RuntimeException("每日数据统计信息ID不存在！");
		}
		mv.addObject("dayStatisticsLogInfo", dayStatisticsLogInfo);
		return mv;
	}
}
