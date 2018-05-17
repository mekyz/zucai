package com.bet.actions.admin.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.admin.BaseAdminController;
import com.bet.daos.IDayStatisticsLogInfoDao;
import com.bet.orms.DayStatisticsLogInfo;
import com.bet.services.CStatService;
import com.bet.services.IDayStatisticsLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminDayStatisticsLogInfoAction")
@RequestMapping(value = "/admin")
public class DayStatisticsLogInfoAction extends BaseAdminController
{
	@Autowired
	private IDayStatisticsLogInfoService dayStatisticsLogInfoService;
	@Autowired
	private CStatService cStatService;

	@RequestMapping(value = "/ajaxGetDayStatisticsLogInfo")
	@ResponseBody
	public ReturnInfo ajaxGetDayStatisticsLogInfo(HttpServletRequest request, @RequestParam(value = "dayId", required = true) String dayId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			DayStatisticsLogInfo dayStatisticsLogInfo = dayStatisticsLogInfoService.getDayStatisticsLogInfoByDayId(dayId);
			if (dayStatisticsLogInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "每日数据统计信息不存在！");
			}
			return toObjectReturnInfo(dayStatisticsLogInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetDayStatisticsLogInfoList")
	@ResponseBody
	public TableData ajaxGetDayStatisticsLogInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IDayStatisticsLogInfoDao.DAY_ID, IDayStatisticsLogInfoDao.STATUS });
			List<DayStatisticsLogInfo> list = dayStatisticsLogInfoService.getDayStatisticsLogInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos,
				searchInfos);
			long count = dayStatisticsLogInfoService.getDayStatisticsLogInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, list);
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateDayStatisticsJob")
	@ResponseBody
	public ReturnInfo ajaxUpdateDayStatisticsJob(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cStatService.updateDayStatisticsJob();
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
