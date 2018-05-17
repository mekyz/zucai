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
import com.bet.daos.ITeamLeaderProfitInfoDao;
import com.bet.orms.TeamLeaderProfitInfo;
import com.bet.services.ITeamLeaderProfitInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminTeamLeaderProfitInfoAction")
@RequestMapping(value = "/admin")
public class TeamLeaderProfitInfoAction extends BaseAdminController
{
	@Autowired
	private ITeamLeaderProfitInfoService teamLeaderProfitInfoService;

	// @RequestMapping(value = "/ajaxDeleteTeamLeaderProfitInfo", method = RequestMethod.POST)
	// @ResponseBody
	// public ReturnInfo ajaxDeleteTeamLeaderProfitInfo(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId)
	// {
	// try
	// {
	// AdminInfo sessionAdminInfo = getAdminSession(request);
	// teamLeaderProfitInfoService.deleteTeamLeaderProfitInfoByProfitId(profitId);
	// return toStringReturnInfo("删除团队领导奖信息成功！");
	// }
	// catch (HibernateJsonResultException e)
	// {
	// return toExceptionReturnInfo(e);
	// }
	// }
	@RequestMapping(value = "/ajaxGetTeamLeaderProfitInfo")
	@ResponseBody
	public ReturnInfo ajaxGetTeamLeaderProfitInfo(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			TeamLeaderProfitInfo teamLeaderProfitInfo = teamLeaderProfitInfoService.getTeamLeaderProfitInfoByProfitId(profitId);
			if (teamLeaderProfitInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "团队领导奖信息不存在！");
			}
			return toObjectReturnInfo(teamLeaderProfitInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetTeamLeaderProfitInfoList")
	@ResponseBody
	public TableData ajaxGetTeamLeaderProfitInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { ITeamLeaderProfitInfoDao.PHASE_ID, ITeamLeaderProfitInfoDao.USER_ID, ITeamLeaderProfitInfoDao.PROFIT_TYPE,
				ITeamLeaderProfitInfoDao.USER_BET_MONEY, ITeamLeaderProfitInfoDao.PROFIT_STATUS, ITeamLeaderProfitInfoDao.STATUS });
			List<TeamLeaderProfitInfo> list = teamLeaderProfitInfoService.getTeamLeaderProfitInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos,
				searchInfos);
			long count = teamLeaderProfitInfoService.getTeamLeaderProfitInfoListCount(searchInfos);
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
