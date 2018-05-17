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
import com.bet.daos.IMonthStatisticsLogInfoDao;
import com.bet.orms.MonthStatisticsLogInfo;
import com.bet.services.CStatService;
import com.bet.services.IMonthStatisticsLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminMonthStatisticsLogInfoAction")
@RequestMapping(value = "/admin")
public class MonthStatisticsLogInfoAction extends BaseAdminController
{
	@Autowired
	private IMonthStatisticsLogInfoService monthStatisticsLogInfoService;
	@Autowired
	private CStatService cStatService;

	@RequestMapping(value = "/ajaxGetMonthStatisticsLogInfo")
	@ResponseBody
	public ReturnInfo ajaxGetMonthStatisticsLogInfo(HttpServletRequest request, @RequestParam(value = "dayId", required = true) String dayId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			MonthStatisticsLogInfo monthStatisticsLogInfo = monthStatisticsLogInfoService.getMonthStatisticsLogInfoByDayId(dayId);
			if (monthStatisticsLogInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "每月数据统计信息不存在！");
			}
			return toObjectReturnInfo(monthStatisticsLogInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMonthStatisticsLogInfoList")
	@ResponseBody
	public TableData ajaxGetMonthStatisticsLogInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IMonthStatisticsLogInfoDao.DAY_ID, IMonthStatisticsLogInfoDao.STATUS });
			List<MonthStatisticsLogInfo> list = monthStatisticsLogInfoService.getMonthStatisticsLogInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos,
				searchInfos);
			long count = monthStatisticsLogInfoService.getMonthStatisticsLogInfoListCount(searchInfos);
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
	@RequestMapping(value = "/ajaxUpdateMonthStatisticsJob")
	@ResponseBody
	public ReturnInfo ajaxUpdateMonthStatisticsJob(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cStatService.updateMonthStatisticsJob();
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
