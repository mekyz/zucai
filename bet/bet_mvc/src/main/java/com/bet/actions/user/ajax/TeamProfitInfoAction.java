package com.bet.actions.user.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.user.BaseUserController;
import com.bet.daos.ITeamProfitInfoDao;
import com.bet.orms.TeamProfitInfo;
import com.bet.orms.UserInfo;
import com.bet.services.ITeamProfitInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userTeamProfitInfoAction")
@RequestMapping(value = "/user")
public class TeamProfitInfoAction extends BaseUserController
{
	@Autowired
	private ITeamProfitInfoService teamProfitInfoService;

	@RequestMapping(value = "/ajaxGetTeamProfitInfo")
	@ResponseBody
	public ReturnInfo ajaxGetTeamProfitInfo(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			TeamProfitInfo teamProfitInfo = teamProfitInfoService.getTeamProfitInfoByProfitId(profitId);
			if (teamProfitInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "团队奖信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(teamProfitInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			return toObjectReturnInfo(ParseModel.parseTeamProfitInfo(teamProfitInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetTeamProfitInfoList")
	@ResponseBody
	public TableData ajaxGetTeamProfitInfoList(HttpServletRequest request, @RequestParam(value = "phaseId", required = false) String phaseId,
		@RequestParam(value = "betUserId", required = false) String betUserId, @RequestParam(value = "profitType", required = false) String profitType,
		@RequestParam(value = "startDateLong", required = false) Long startDateLong, @RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.USER_ID, sessionUserInfo.getUserId(), true, false));
			if (!StringTools.isNull(phaseId))
			{
				searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PHASE_ID, phaseId, true, false));
			}
			if (!StringTools.isNull(betUserId))
			{
				searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.BET_USER_ID, betUserId, true, false));
			}
			if (!StringTools.isNull(profitType))
			{
				searchInfos.add(new TableSearchInfo(ITeamProfitInfoDao.PROFIT_TYPE, profitType, true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { ITeamProfitInfoDao.BET_ID, ITeamProfitInfoDao.PHASE_ID, ITeamProfitInfoDao.USER_ID, ITeamProfitInfoDao.BET_USER_ID,
				ITeamProfitInfoDao.STATUS, ITeamProfitInfoDao.PROFIT_STATUS });
			List<TeamProfitInfo> list = teamProfitInfoService.getTeamProfitInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos, startDateLong,
				endDateLong);
			long count = teamProfitInfoService.getTeamProfitInfoListCount(searchInfos, startDateLong, endDateLong);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseTeamProfitInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
