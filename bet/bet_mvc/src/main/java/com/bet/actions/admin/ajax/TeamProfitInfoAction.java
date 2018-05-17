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
import com.bet.daos.ITeamProfitInfoDao;
import com.bet.orms.TeamProfitInfo;
import com.bet.services.CBonusService;
import com.bet.services.ITeamProfitInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminTeamProfitInfoAction")
@RequestMapping(value = "/admin")
public class TeamProfitInfoAction extends BaseAdminController
{
	@Autowired
	private ITeamProfitInfoService teamProfitInfoService;
	@Autowired
	private CBonusService cBonusService;

	@RequestMapping(value = "/ajaxUpdateTeamProfitInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateTeamProfitInfo(HttpServletRequest request, @ModelAttribute("teamProfitInfo") TeamProfitInfo teamProfitInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(ITeamProfitInfoDao.BET_ID, teamProfitInfo.getBetId());
			valueMap.put(ITeamProfitInfoDao.BET_USER_ID, teamProfitInfo.getBetUserId());
			valueMap.put(ITeamProfitInfoDao.PHASE_ID, teamProfitInfo.getPhaseId());
			valueMap.put(ITeamProfitInfoDao.USER_ID, teamProfitInfo.getUserId());
			valueMap.put(ITeamProfitInfoDao.WIN_MONEY, teamProfitInfo.getWinMoney());
			valueMap.put(ITeamProfitInfoDao.PROFIT_TYPE, teamProfitInfo.getProfitType());
			valueMap.put(ITeamProfitInfoDao.PROFIT_MONEY, teamProfitInfo.getProfitMoney());
			valueMap.put(ITeamProfitInfoDao.PROFIT_USER_MONEY, teamProfitInfo.getProfitUserMoney());
			valueMap.put(ITeamProfitInfoDao.FEE, teamProfitInfo.getFee());
			valueMap.put(ITeamProfitInfoDao.PROFIT_STATUS, teamProfitInfo.getProfitStatus());
			valueMap.put(ITeamProfitInfoDao.STATUS, teamProfitInfo.getStatus());
			valueMap.put(ITeamProfitInfoDao.UPDATE_DATE_LONG, current);
			if (!teamProfitInfoService.updateValueByProfitId(teamProfitInfo.getProfitId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新团队奖信息失败！");
			}
			return toObjectReturnInfo(teamProfitInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteTeamProfitInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteTeamProfitInfo(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			teamProfitInfoService.deleteTeamProfitInfoByProfitId(profitId);
			return toStringReturnInfo("删除团队奖信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetTeamProfitInfo")
	@ResponseBody
	public ReturnInfo ajaxGetTeamProfitInfo(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			TeamProfitInfo teamProfitInfo = teamProfitInfoService.getTeamProfitInfoByProfitId(profitId);
			if (teamProfitInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "团队奖信息不存在！");
			}
			return toObjectReturnInfo(teamProfitInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetTeamProfitInfoList")
	@ResponseBody
	public TableData ajaxGetTeamProfitInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { ITeamProfitInfoDao.BET_ID, ITeamProfitInfoDao.PHASE_ID, ITeamProfitInfoDao.USER_ID, ITeamProfitInfoDao.BET_USER_ID,
				ITeamProfitInfoDao.STATUS, ITeamProfitInfoDao.PROFIT_STATUS });
			List<TeamProfitInfo> list = teamProfitInfoService.getTeamProfitInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = teamProfitInfoService.getTeamProfitInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, list);
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/ajaxUpdateMatchTeamBonusJob")
	@ResponseBody
	public ReturnInfo ajaxUpdateMatchTeamBonusJob(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cBonusService.checkAndUpdateMatchTeamBonusJob(phaseId);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
