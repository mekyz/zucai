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
import com.bet.daos.ITeamLeaderProfitConfigInfoDao;
import com.bet.orms.TeamLeaderProfitConfigInfo;
import com.bet.services.ITeamLeaderProfitConfigInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userTeamLeaderProfitConfigInfoAction")
@RequestMapping(value = "/user")
public class TeamLeaderProfitConfigInfoAction extends BaseUserController
{
	@Autowired
	private ITeamLeaderProfitConfigInfoService teamLeaderProfitConfigInfoService;

	@RequestMapping(value = "/ajaxGetTeamLeaderProfitConfigInfo")
	@ResponseBody
	public ReturnInfo ajaxGetTeamLeaderProfitConfigInfo(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			TeamLeaderProfitConfigInfo teamLeaderProfitConfigInfo = teamLeaderProfitConfigInfoService.getTeamLeaderProfitConfigInfoByConfigId(configId);
			if (teamLeaderProfitConfigInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "领袖奖配置信息不存在！");
			}
			return toObjectReturnInfo(ParseModel.parseTeamLeaderProfitConfigInfo(teamLeaderProfitConfigInfo));
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
			// UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(ITeamLeaderProfitConfigInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { ITeamLeaderProfitConfigInfoDao.STATUS });
			List<TeamLeaderProfitConfigInfo> list = teamLeaderProfitConfigInfoService.getTeamLeaderProfitConfigInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH),
				orderInfos, searchInfos);
			long count = teamLeaderProfitConfigInfoService.getTeamLeaderProfitConfigInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseTeamLeaderProfitConfigInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
