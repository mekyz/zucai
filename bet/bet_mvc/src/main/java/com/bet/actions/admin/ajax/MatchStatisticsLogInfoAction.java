package com.bet.actions.admin.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.admin.BaseAdminController;
import com.bet.daos.IMatchStatisticsLogInfoDao;
import com.bet.orms.MatchStatisticsLogInfo;
import com.bet.services.CStatService;
import com.bet.services.IMatchStatisticsLogInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminMatchStatisticsLogInfoAction")
@RequestMapping(value = "/admin")
public class MatchStatisticsLogInfoAction extends BaseAdminController
{
	@Autowired
	private IMatchStatisticsLogInfoService matchStatisticsLogInfoService;
	@Autowired
	private CStatService cStatService;

	@RequestMapping(value = "/ajaxDeleteMatchStatisticsLogInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteMatchStatisticsLogInfo(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			matchStatisticsLogInfoService.deleteMatchStatisticsLogInfoByPhaseId(phaseId);
			return toStringReturnInfo("删除赛事统计信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMatchStatisticsLogInfo")
	@ResponseBody
	public ReturnInfo ajaxGetMatchStatisticsLogInfo(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			MatchStatisticsLogInfo matchStatisticsLogInfo = matchStatisticsLogInfoService.getMatchStatisticsLogInfoByPhaseId(phaseId);
			if (matchStatisticsLogInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "赛事统计信息不存在！");
			}
			return toObjectReturnInfo(matchStatisticsLogInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMatchStatisticsLogInfoList")
	@ResponseBody
	public TableData ajaxGetMatchStatisticsLogInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IMatchStatisticsLogInfoDao.PHASE_ID, IMatchStatisticsLogInfoDao.STATUS });
			List<MatchStatisticsLogInfo> list = matchStatisticsLogInfoService.getMatchStatisticsLogInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos,
				searchInfos);
			long count = matchStatisticsLogInfoService.getMatchStatisticsLogInfoListCount(searchInfos);
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
	 * 统计比赛的金额情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateMatchStatisticsJob")
	@ResponseBody
	public ReturnInfo ajaxUpdateMatchStatisticsJob(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cStatService.updateMatchStatisticsJob();
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
