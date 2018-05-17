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
import com.bet.daos.IMatchProfitInfoDao;
import com.bet.orms.MatchProfitInfo;
import com.bet.services.IMatchProfitInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminMatchProfitInfoAction")
@RequestMapping(value = "/admin")
public class MatchProfitInfoAction extends BaseAdminController
{
	@Autowired
	private IMatchProfitInfoService matchProfitInfoService;

	@RequestMapping(value = "/ajaxAddMatchProfitInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddMatchProfitInfo(HttpServletRequest request, @ModelAttribute("matchProfitInfo") MatchProfitInfo matchProfitInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IMatchProfitInfoDao.PHASE_ID, matchProfitInfo.getPhaseId(), true, false));
			searchInfos.add(new TableSearchInfo(IMatchProfitInfoDao.MATCH_TYPE, matchProfitInfo.getMatchType(), true, false));
			searchInfos.add(new TableSearchInfo(IMatchProfitInfoDao.SCORE1, matchProfitInfo.getScore1() + "", true, false));
			searchInfos.add(new TableSearchInfo(IMatchProfitInfoDao.SCORE2, matchProfitInfo.getScore2() + "", true, false));
			long count = matchProfitInfoService.getMatchProfitInfoListCount(searchInfos);
			if (count > 0)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "已有此赛事波胆信息！");
			}
			matchProfitInfo.setProfitId(matchProfitInfoService.genId());
			matchProfitInfo.setSaledAmount(0);
			matchProfitInfo.setStatus(StatusType.ENABLED.getStatus());
			matchProfitInfo = matchProfitInfoService.addMatchProfitInfo(matchProfitInfo);
			if (matchProfitInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加赛事波胆信息失败！");
			}
			return toObjectReturnInfo(matchProfitInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateMatchProfitInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateMatchProfitInfo(HttpServletRequest request, @ModelAttribute("matchProfitInfo") MatchProfitInfo matchProfitInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			// valueMap.put(IMatchProfitInfoDao.PHASE_ID, matchProfitInfo.getPhaseId());
			// valueMap.put(IMatchProfitInfoDao.MATCH_TYPE, matchProfitInfo.getMatchType());
			// valueMap.put(IMatchProfitInfoDao.SCORE1, matchProfitInfo.getScore1());
			// valueMap.put(IMatchProfitInfoDao.SCORE2, matchProfitInfo.getScore2());
			valueMap.put(IMatchProfitInfoDao.PROFIT_PERCENT, matchProfitInfo.getProfitPercent());
			valueMap.put(IMatchProfitInfoDao.AMOUNT, matchProfitInfo.getAmount());
			valueMap.put(IMatchProfitInfoDao.UPDATE_DATE_LONG, current);
			if (!matchProfitInfoService.updateValueByProfitId(matchProfitInfo.getProfitId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新赛事波胆信息失败！");
			}
			return toObjectReturnInfo(matchProfitInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateMatchProfitInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateMatchProfitInfoStatus(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId,
		@RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IMatchProfitInfoDao.STATUS, status);
			valueMap.put(IMatchProfitInfoDao.UPDATE_DATE_LONG, current);
			if (!matchProfitInfoService.updateValueByProfitId(profitId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新赛事波胆信息状态失败！");
			}
			return toStringReturnInfo("更新赛事波胆信息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteMatchProfitInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteMatchProfitInfo(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			matchProfitInfoService.deleteMatchProfitInfoByProfitId(profitId);
			return toStringReturnInfo("删除赛事波胆信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMatchProfitInfo")
	@ResponseBody
	public ReturnInfo ajaxGetMatchProfitInfo(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			MatchProfitInfo matchProfitInfo = matchProfitInfoService.getMatchProfitInfoByProfitId(profitId);
			if (matchProfitInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "赛事波胆信息不存在！");
			}
			return toObjectReturnInfo(matchProfitInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMatchProfitInfoList")
	@ResponseBody
	public TableData ajaxGetMatchProfitInfoList(HttpServletRequest request, @RequestParam(value = "phaseId", required = false) String phaseId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			if (!StringTools.isNull(phaseId))
			{
				searchInfos.add(new TableSearchInfo(IMatchProfitInfoDao.PHASE_ID, phaseId, true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IMatchProfitInfoDao.PROFIT_ID, IMatchProfitInfoDao.PHASE_ID, IMatchProfitInfoDao.STATUS });
			List<MatchProfitInfo> list = matchProfitInfoService.getMatchProfitInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = matchProfitInfoService.getMatchProfitInfoListCount(searchInfos);
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
