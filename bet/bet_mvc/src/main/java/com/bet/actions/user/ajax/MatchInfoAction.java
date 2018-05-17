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
import com.bet.daos.IMatchInfoDao;
import com.bet.orms.MatchInfo;
import com.bet.services.IMatchInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.ConstValues;
import com.lrcall.lrweb.common.enums.SqlOrderType;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userMatchInfoAction")
@RequestMapping(value = "/user")
public class MatchInfoAction extends BaseUserController
{
	@Autowired
	private IMatchInfoService matchInfoService;

	@RequestMapping(value = "/ajaxGetMatchInfo")
	@ResponseBody
	public ReturnInfo ajaxGetMatchInfo(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(phaseId);
			if (matchInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "比赛信息不存在！");
			}
			return toObjectReturnInfo(ParseModel.parseMatchInfo(matchInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMatchInfoList")
	@ResponseBody
	public TableData ajaxGetMatchInfoList(HttpServletRequest request, @RequestParam(value = "isGameOver", required = false) Boolean isGameOver,
		@RequestParam(value = "startDateLong", required = false) Long startDateLong, @RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			orderInfos.add(new TableOrderInfo(0, IMatchInfoDao.SORT_INDEX, SqlOrderType.DESC.getType()));
			orderInfos.add(new TableOrderInfo(1, IMatchInfoDao.FINAL_RESULT_STATUS, SqlOrderType.DESC.getType()));
			if (isGameOver != null)
			{
				searchInfos.add(new TableSearchInfo(ConstValues.LEFT_BRACKET, "", true, true));
				if (isGameOver)
				{
					searchInfos.add(new TableSearchInfo(IMatchInfoDao.HALF_RESULT_STATUS, StatusType.ENABLED.getStatus() + "", true, false));
					searchInfos.add(new TableSearchInfo(IMatchInfoDao.FINAL_RESULT_STATUS, StatusType.ENABLED.getStatus() + "", true, false));
				}
				else
				{
					searchInfos.add(new TableSearchInfo(IMatchInfoDao.HALF_RESULT_STATUS, StatusType.DISABLED.getStatus() + "", true, true));
					searchInfos.add(new TableSearchInfo(IMatchInfoDao.FINAL_RESULT_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
				}
				searchInfos.add(new TableSearchInfo(ConstValues.RIGHT_BRACKET, "", true, false));
			}
			searchInfos.add(new TableSearchInfo(IMatchInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IMatchInfoDao.PHASE_ID, IMatchInfoDao.AWAY_TEAM, IMatchInfoDao.HOME_TEAM });
			List<MatchInfo> list = matchInfoService.getMatchInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos, startDateLong, endDateLong);
			long count = matchInfoService.getMatchInfoListCount(searchInfos, startDateLong, endDateLong);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseMatchInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
