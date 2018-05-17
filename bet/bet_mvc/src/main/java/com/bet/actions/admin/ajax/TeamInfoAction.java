package com.bet.actions.admin.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.admin.BaseAdminController;
import com.bet.daos.ITeamInfoDao;
import com.bet.orms.TeamInfo;
import com.bet.services.ITeamInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminTeamInfoAction")
@RequestMapping(value = "/admin")
public class TeamInfoAction extends BaseAdminController
{
	@Autowired
	private ITeamInfoService teamInfoService;

	@RequestMapping(value = "/ajaxAddTeamInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddTeamInfo(HttpServletRequest request, @ModelAttribute("teamInfo") TeamInfo teamInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			teamInfo.setTeamId(teamInfoService.genId());
			teamInfo.setStatus(StatusType.ENABLED.getStatus());
			teamInfo = teamInfoService.addTeamInfo(teamInfo);
			if (teamInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加球队信息失败！");
			}
			return toObjectReturnInfo(teamInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateTeamInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateTeamInfo(HttpServletRequest request, @ModelAttribute("teamInfo") TeamInfo teamInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(ITeamInfoDao.NAME, teamInfo.getName());
			valueMap.put(ITeamInfoDao.PIC_URL, teamInfo.getPicUrl());
			valueMap.put(ITeamInfoDao.UPDATE_DATE_LONG, current);
			if (!teamInfoService.updateValueByTeamId(teamInfo.getTeamId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新球队信息失败！");
			}
			return toObjectReturnInfo(teamInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateTeamInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateTeamInfoStatus(HttpServletRequest request, @RequestParam(value = "teamId", required = true) String teamId, @RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			if (status != StatusType.ENABLED.getStatus())
			{
				status = StatusType.DISABLED.getStatus();
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(ITeamInfoDao.STATUS, status);
			if (!teamInfoService.updateValueByTeamId(teamId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新球队信息状态失败！");
			}
			return toStringReturnInfo("更新球队信息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteTeamInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteTeamInfo(HttpServletRequest request, @RequestParam(value = "teamId", required = true) String teamId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			teamInfoService.deleteTeamInfoByTeamId(teamId);
			return toStringReturnInfo("删除球队信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetTeamInfo")
	@ResponseBody
	public ReturnInfo ajaxGetTeamInfo(HttpServletRequest request, @RequestParam(value = "teamId", required = true) String teamId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			TeamInfo teamInfo = teamInfoService.getTeamInfoByTeamId(teamId);
			if (teamInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "球队信息不存在！");
			}
			return toObjectReturnInfo(teamInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetTeamInfoList")
	@ResponseBody
	public TableData ajaxGetTeamInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { ITeamInfoDao.TEAM_ID, ITeamInfoDao.NAME });
			List<TeamInfo> list = teamInfoService.getTeamInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = teamInfoService.getTeamInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, list);
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
