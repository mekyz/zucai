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
import com.bet.daos.ITeamInfoDao;
import com.bet.orms.TeamInfo;
import com.bet.services.ITeamInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userTeamInfoAction")
@RequestMapping(value = "/user")
public class TeamInfoAction extends BaseUserController
{
	@Autowired
	private ITeamInfoService teamInfoService;

	@RequestMapping(value = "/ajaxGetTeamInfo")
	@ResponseBody
	public ReturnInfo ajaxGetTeamInfo(HttpServletRequest request, @RequestParam(value = "teamId", required = true) String teamId)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			TeamInfo teamInfo = teamInfoService.getTeamInfoByTeamId(teamId);
			if (teamInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "球队信息不存在！");
			}
			return toObjectReturnInfo(ParseModel.parseTeamInfo(teamInfo));
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
			// UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { ITeamInfoDao.TEAM_ID, ITeamInfoDao.NAME });
			List<TeamInfo> list = teamInfoService.getTeamInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = teamInfoService.getTeamInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseTeamInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
