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
import com.bet.daos.IMatchProfitInfoDao;
import com.bet.orms.MatchProfitInfo;
import com.bet.services.IMatchProfitInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userMatchProfitInfoAction")
@RequestMapping(value = "/user")
public class MatchProfitInfoAction extends BaseUserController
{
	@Autowired
	private IMatchProfitInfoService matchProfitInfoService;

	@RequestMapping(value = "/ajaxGetMatchProfitInfo")
	@ResponseBody
	public ReturnInfo ajaxGetMatchProfitInfo(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			MatchProfitInfo matchProfitInfo = matchProfitInfoService.getMatchProfitInfoByProfitId(profitId);
			if (matchProfitInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "赛事波胆信息不存在！");
			}
			return toObjectReturnInfo(ParseModel.parseMatchProfitInfo(matchProfitInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMatchProfitInfoList")
	@ResponseBody
	public TableData ajaxGetMatchProfitInfoList(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId,
		@RequestParam(value = "type", required = true) String type)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IMatchProfitInfoDao.PHASE_ID, phaseId, true, false));
			searchInfos.add(new TableSearchInfo(IMatchProfitInfoDao.MATCH_TYPE, type, true, false));
			searchInfos.add(new TableSearchInfo(IMatchProfitInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IMatchProfitInfoDao.PROFIT_ID, IMatchProfitInfoDao.PHASE_ID, IMatchProfitInfoDao.STATUS });
			List<MatchProfitInfo> list = matchProfitInfoService.getMatchProfitInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = matchProfitInfoService.getMatchProfitInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseMatchProfitInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
