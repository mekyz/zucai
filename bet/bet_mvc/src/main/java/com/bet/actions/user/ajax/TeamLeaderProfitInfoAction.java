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
import com.bet.daos.ITeamLeaderProfitInfoDao;
import com.bet.orms.TeamLeaderProfitInfo;
import com.bet.orms.UserInfo;
import com.bet.services.ITeamLeaderProfitInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userTeamLeaderProfitInfoAction")
@RequestMapping(value = "/user")
public class TeamLeaderProfitInfoAction extends BaseUserController
{
	@Autowired
	private ITeamLeaderProfitInfoService teamLeaderProfitInfoService;

	@RequestMapping(value = "/ajaxGetTeamLeaderProfitInfo")
	@ResponseBody
	public ReturnInfo ajaxGetTeamLeaderProfitInfo(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			TeamLeaderProfitInfo teamLeaderProfitInfo = teamLeaderProfitInfoService.getTeamLeaderProfitInfoByProfitId(profitId);
			if (teamLeaderProfitInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "团队领导奖信息不存在！");
			}
			if (!sessionUserInfo.getUserId().equals(teamLeaderProfitInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			return toObjectReturnInfo(ParseModel.parseTeamLeaderProfitInfo(teamLeaderProfitInfo));
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
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(ITeamLeaderProfitInfoDao.USER_ID, sessionUserInfo.getUserId(), true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { ITeamLeaderProfitInfoDao.PHASE_ID, ITeamLeaderProfitInfoDao.USER_ID, ITeamLeaderProfitInfoDao.PROFIT_TYPE,
				ITeamLeaderProfitInfoDao.USER_BET_MONEY, ITeamLeaderProfitInfoDao.PROFIT_STATUS, ITeamLeaderProfitInfoDao.STATUS });
			List<TeamLeaderProfitInfo> list = teamLeaderProfitInfoService.getTeamLeaderProfitInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos,
				searchInfos);
			long count = teamLeaderProfitInfoService.getTeamLeaderProfitInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseTeamLeaderProfitInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
