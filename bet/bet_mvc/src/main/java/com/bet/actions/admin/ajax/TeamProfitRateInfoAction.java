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
import com.bet.daos.ITeamProfitRateInfoDao;
import com.bet.orms.TeamProfitRateInfo;
import com.bet.services.ITeamProfitRateInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminTeamProfitRateInfoAction")
@RequestMapping(value = "/admin")
public class TeamProfitRateInfoAction extends BaseAdminController
{
	@Autowired
	private ITeamProfitRateInfoService teamProfitRateInfoService;

	// @RequestMapping(value = "/ajaxAddTeamProfitRateInfo", method = RequestMethod.POST)
	// @ResponseBody
	// public ReturnInfo ajaxAddTeamProfitRateInfo(HttpServletRequest request, @ModelAttribute("teamProfitRateInfo") TeamProfitRateInfo teamProfitRateInfo)
	// {
	// try
	// {
	// AdminInfo sessionAdminInfo = getAdminSession(request);
	// teamProfitRateInfo.setRateId(teamProfitRateInfoService.genId());
	// teamProfitRateInfo.setStatus(StatusType.ENABLED.getStatus());
	// teamProfitRateInfo = teamProfitRateInfoService.addTeamProfitRateInfo(teamProfitRateInfo);
	// if (teamProfitRateInfo == null)
	// {
	// throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加团队返利比率信息失败！");
	// }
	// return toObjectReturnInfo(teamProfitRateInfo);
	// }
	// catch (HibernateJsonResultException e)
	// {
	// return toExceptionReturnInfo(e);
	// }
	// }
	@RequestMapping(value = "/ajaxUpdateTeamProfitRateInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateTeamProfitRateInfo(HttpServletRequest request, @ModelAttribute("teamProfitRateInfo") TeamProfitRateInfo teamProfitRateInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(ITeamProfitRateInfoDao.NAME, teamProfitRateInfo.getName());
			valueMap.put(ITeamProfitRateInfoDao.TYPE1, teamProfitRateInfo.getType1());
			valueMap.put(ITeamProfitRateInfoDao.TYPE2, teamProfitRateInfo.getType2());
			valueMap.put(ITeamProfitRateInfoDao.TYPE3, teamProfitRateInfo.getType3());
			valueMap.put(ITeamProfitRateInfoDao.TYPE4, teamProfitRateInfo.getType4());
			valueMap.put(ITeamProfitRateInfoDao.TYPE5, teamProfitRateInfo.getType5());
			valueMap.put(ITeamProfitRateInfoDao.TYPE6, teamProfitRateInfo.getType6());
			valueMap.put(ITeamProfitRateInfoDao.TYPE7, teamProfitRateInfo.getType7());
			valueMap.put(ITeamProfitRateInfoDao.STATUS, teamProfitRateInfo.getStatus());
			valueMap.put(ITeamProfitRateInfoDao.UPDATE_DATE_LONG, current);
			if (!teamProfitRateInfoService.updateValueByRateId(teamProfitRateInfo.getRateId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新团队返利比率信息失败！");
			}
			return toObjectReturnInfo(teamProfitRateInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateTeamProfitRateInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateTeamProfitRateInfoStatus(HttpServletRequest request, @RequestParam(value = "rateId", required = true) String rateId,
		@RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(ITeamProfitRateInfoDao.STATUS, status);
			valueMap.put(ITeamProfitRateInfoDao.UPDATE_DATE_LONG, current);
			if (!teamProfitRateInfoService.updateValueByRateId(rateId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新团队返利比率信息状态失败！");
			}
			return toStringReturnInfo("更新团队返利比率信息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	// @RequestMapping(value = "/ajaxDeleteTeamProfitRateInfo", method = RequestMethod.POST)
	// @ResponseBody
	// public ReturnInfo ajaxDeleteTeamProfitRateInfo(HttpServletRequest request, @RequestParam(value = "rateId", required = true) String rateId)
	// {
	// try
	// {
	// AdminInfo sessionAdminInfo = getAdminSession(request);
	// teamProfitRateInfoService.deleteTeamProfitRateInfoByRateId(rateId);
	// return toStringReturnInfo("删除团队返利比率信息成功！");
	// }
	// catch (HibernateJsonResultException e)
	// {
	// return toExceptionReturnInfo(e);
	// }
	// }
	@RequestMapping(value = "/ajaxGetTeamProfitRateInfo")
	@ResponseBody
	public ReturnInfo ajaxGetTeamProfitRateInfo(HttpServletRequest request, @RequestParam(value = "rateId", required = true) String rateId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			TeamProfitRateInfo teamProfitRateInfo = teamProfitRateInfoService.getTeamProfitRateInfoByRateId(rateId);
			if (teamProfitRateInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "团队返利比率信息不存在！");
			}
			return toObjectReturnInfo(teamProfitRateInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetTeamProfitRateInfoList")
	@ResponseBody
	public TableData ajaxGetTeamProfitRateInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { ITeamProfitRateInfoDao.RATE_ID, ITeamProfitRateInfoDao.STATUS });
			List<TeamProfitRateInfo> list = teamProfitRateInfoService.getTeamProfitRateInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = teamProfitRateInfoService.getTeamProfitRateInfoListCount(searchInfos);
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
