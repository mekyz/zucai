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
import com.bet.daos.IDayUserStatisticsLogInfoDao;
import com.bet.orms.DayUserStatisticsLogInfo;
import com.bet.services.CStatService;
import com.bet.services.IDayUserStatisticsLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminDayUserStatisticsLogInfoAction")
@RequestMapping(value = "/admin")
public class DayUserStatisticsLogInfoAction extends BaseAdminController
{
	@Autowired
	private IDayUserStatisticsLogInfoService dayUserStatisticsLogInfoService;
	@Autowired
	private CStatService cStatService;

	@RequestMapping(value = "/ajaxGetDayUserStatisticsLogInfo")
	@ResponseBody
	public ReturnInfo ajaxGetDayUserStatisticsLogInfo(HttpServletRequest request, @RequestParam(value = "logId", required = true) String logId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			DayUserStatisticsLogInfo dayUserStatisticsLogInfo = dayUserStatisticsLogInfoService.getDayUserStatisticsLogInfoByLogId(logId);
			if (dayUserStatisticsLogInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户每日数据统计信息不存在！");
			}
			return toObjectReturnInfo(dayUserStatisticsLogInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetDayUserStatisticsLogInfoList")
	@ResponseBody
	public TableData ajaxGetDayUserStatisticsLogInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IDayUserStatisticsLogInfoDao.USER_ID, IDayUserStatisticsLogInfoDao.DAY_ID, IDayUserStatisticsLogInfoDao.STATUS });
			List<DayUserStatisticsLogInfo> list = dayUserStatisticsLogInfoService.getDayUserStatisticsLogInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH),
				orderInfos, searchInfos);
			long count = dayUserStatisticsLogInfoService.getDayUserStatisticsLogInfoListCount(searchInfos);
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
	@RequestMapping(value = "/ajaxUpdateDayUserStatisticsJob")
	@ResponseBody
	public ReturnInfo ajaxUpdateDayUserStatisticsJob(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cStatService.updateDayUserStatisticsJob();
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
