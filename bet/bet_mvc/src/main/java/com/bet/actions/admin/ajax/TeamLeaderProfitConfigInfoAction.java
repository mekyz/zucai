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
import com.bet.daos.ITeamLeaderProfitConfigInfoDao;
import com.bet.orms.TeamLeaderProfitConfigInfo;
import com.bet.services.ITeamLeaderProfitConfigInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminTeamLeaderProfitConfigInfoAction")
@RequestMapping(value = "/admin")
public class TeamLeaderProfitConfigInfoAction extends BaseAdminController
{
	@Autowired
	private ITeamLeaderProfitConfigInfoService teamLeaderProfitConfigInfoService;

	@RequestMapping(value = "/ajaxUpdateTeamLeaderProfitConfigInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateTeamLeaderProfitConfigInfo(HttpServletRequest request, @ModelAttribute("teamLeaderProfitConfigInfo") TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(ITeamLeaderProfitConfigInfoDao.NAME, teamLeaderProfitConfigInfo.getName());
			valueMap.put(ITeamLeaderProfitConfigInfoDao.USER_TYPE, teamLeaderProfitConfigInfo.getUserType());
			valueMap.put(ITeamLeaderProfitConfigInfoDao.TEAM_BET_MONEY, teamLeaderProfitConfigInfo.getTeamBetMoney());
			valueMap.put(ITeamLeaderProfitConfigInfoDao.USER_BET_MONEY, teamLeaderProfitConfigInfo.getUserBetMoney());
			valueMap.put(ITeamLeaderProfitConfigInfoDao.PROFIT_PERCENT, teamLeaderProfitConfigInfo.getProfitPercent());
			valueMap.put(ITeamLeaderProfitConfigInfoDao.PROFIT_MAX_MONEY, teamLeaderProfitConfigInfo.getProfitMaxMoney());
			// valueMap.put(ITeamLeaderProfitConfigInfoDao.FEE, teamLeaderProfitConfigInfo.getFee());
			// valueMap.put(ITeamLeaderProfitConfigInfoDao.STATUS, teamLeaderProfitConfigInfo.getStatus());
			valueMap.put(ITeamLeaderProfitConfigInfoDao.UPDATE_DATE_LONG, current);
			if (!teamLeaderProfitConfigInfoService.updateValueByConfigId(teamLeaderProfitConfigInfo.getConfigId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新领导奖配置信息失败！");
			}
			return toObjectReturnInfo(teamLeaderProfitConfigInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateTeamLeaderProfitConfigInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateTeamLeaderProfitConfigInfoStatus(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId,
		@RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			if (status != StatusType.ENABLED.getStatus())
			{
				status = StatusType.DISABLED.getStatus();
			}
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(ITeamLeaderProfitConfigInfoDao.STATUS, status);
			valueMap.put(ITeamLeaderProfitConfigInfoDao.UPDATE_DATE_LONG, current);
			if (!teamLeaderProfitConfigInfoService.updateValueByConfigId(configId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新领导奖配置信息状态失败！");
			}
			return toStringReturnInfo("更新领导奖配置信息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	// @RequestMapping(value = "/ajaxDeleteTeamLeaderProfitConfigInfo", method = RequestMethod.POST)
	// @ResponseBody
	// public ReturnInfo ajaxDeleteTeamLeaderProfitConfigInfo(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId)
	// {
	// try
	// {
	// AdminInfo sessionAdminInfo = getAdminSession(request);
	// teamLeaderProfitConfigInfoService.deleteTeamLeaderProfitConfigInfoByConfigId(configId);
	// return toStringReturnInfo("删除领导奖配置信息成功！");
	// }
	// catch (HibernateJsonResultException e)
	// {
	// return toExceptionReturnInfo(e);
	// }
	// }
	@RequestMapping(value = "/ajaxGetTeamLeaderProfitConfigInfo")
	@ResponseBody
	public ReturnInfo ajaxGetTeamLeaderProfitConfigInfo(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo = teamLeaderProfitConfigInfoService.getTeamLeaderProfitConfigInfoByConfigId(configId);
			if (teamLeaderProfitConfigInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "领导奖配置信息不存在！");
			}
			return toObjectReturnInfo(teamLeaderProfitConfigInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetTeamLeaderProfitConfigInfoList")
	@ResponseBody
	public TableData ajaxGetTeamLeaderProfitConfigInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { ITeamLeaderProfitConfigInfoDao.STATUS });
			List<TeamLeaderProfitConfigInfo> list = teamLeaderProfitConfigInfoService.getTeamLeaderProfitConfigInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH),
				orderInfos, searchInfos);
			long count = teamLeaderProfitConfigInfoService.getTeamLeaderProfitConfigInfoListCount(searchInfos);
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
