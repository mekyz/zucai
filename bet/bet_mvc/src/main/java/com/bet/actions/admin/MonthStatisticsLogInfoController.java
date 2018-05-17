package com.bet.actions.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.MonthStatisticsLogInfo;
import com.bet.services.IMonthStatisticsLogInfoService;

@Controller("adminMonthStatisticsLogInfoController")
@RequestMapping(value = "/admin")
public class MonthStatisticsLogInfoController extends BaseAdminController
{
	@Autowired
	private IMonthStatisticsLogInfoService monthStatisticsLogInfoService;

	@RequestMapping(value = "/manageMonthStatisticsLogInfos", method = RequestMethod.GET)
	public ModelAndView manageMonthStatisticsLogInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageMonthStatisticsLogInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/monthStatisticsLogInfoEdit", method = RequestMethod.GET)
	public ModelAndView monthStatisticsLogInfoEdit(HttpServletRequest request, @RequestParam(value = "dayId", required = true) String dayId)
	{
		ModelAndView mv = new ModelAndView(getViewName("monthStatisticsLogInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		MonthStatisticsLogInfo monthStatisticsLogInfo = monthStatisticsLogInfoService.getMonthStatisticsLogInfoByDayId(dayId);
		if (monthStatisticsLogInfo == null)
		{
			throw new RuntimeException("每月数据统计信息ID不存在！");
		}
		mv.addObject("monthStatisticsLogInfo", monthStatisticsLogInfo);
		return mv;
	}
}
