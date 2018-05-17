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
import com.bet.daos.IMatchProfitTemplateInfoDao;
import com.bet.orms.MatchInfo;
import com.bet.orms.MatchProfitInfo;
import com.bet.orms.MatchProfitTemplateInfo;
import com.bet.services.IMatchInfoService;
import com.bet.services.IMatchProfitInfoService;
import com.bet.services.IMatchProfitTemplateInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.RandomTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminMatchProfitTemplateInfoAction")
@RequestMapping(value = "/admin")
public class MatchProfitTemplateInfoAction extends BaseAdminController
{
	@Autowired
	private IMatchProfitTemplateInfoService matchProfitTemplateInfoService;
	@Autowired
	private IMatchProfitInfoService matchProfitInfoService;
	@Autowired
	private IMatchInfoService matchInfoService;

	@RequestMapping(value = "/ajaxAddMatchProfitTemplateInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddMatchProfitTemplateInfo(HttpServletRequest request, @ModelAttribute("matchProfitTemplateInfo") MatchProfitTemplateInfo matchProfitTemplateInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IMatchProfitTemplateInfoDao.MATCH_TYPE, matchProfitTemplateInfo.getMatchType(), true, false));
			searchInfos.add(new TableSearchInfo(IMatchProfitTemplateInfoDao.SCORE1, matchProfitTemplateInfo.getScore1() + "", true, false));
			searchInfos.add(new TableSearchInfo(IMatchProfitTemplateInfoDao.SCORE2, matchProfitTemplateInfo.getScore2() + "", true, false));
			long count = matchProfitTemplateInfoService.getMatchProfitTemplateInfoListCount(searchInfos);
			if (count > 0)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "已有此波胆信息！");
			}
			matchProfitTemplateInfo.setTemplateId(matchProfitTemplateInfoService.genId());
			matchProfitTemplateInfo.setStatus(StatusType.ENABLED.getStatus());
			matchProfitTemplateInfo = matchProfitTemplateInfoService.addMatchProfitTemplateInfo(matchProfitTemplateInfo);
			if (matchProfitTemplateInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加波胆模板信息失败！");
			}
			return toObjectReturnInfo(matchProfitTemplateInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateMatchProfitTemplateInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateMatchProfitTemplateInfo(HttpServletRequest request, @ModelAttribute("matchProfitTemplateInfo") MatchProfitTemplateInfo matchProfitTemplateInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			// valueMap.put(IMatchProfitTemplateInfoDao.MATCH_TYPE, matchProfitTemplateInfo.getMatchType());
			// valueMap.put(IMatchProfitTemplateInfoDao.SCORE1, matchProfitTemplateInfo.getScore1());
			// valueMap.put(IMatchProfitTemplateInfoDao.SCORE2, matchProfitTemplateInfo.getScore2());
			valueMap.put(IMatchProfitTemplateInfoDao.PROFIT_PERCENT, matchProfitTemplateInfo.getProfitPercent());
			valueMap.put(IMatchProfitTemplateInfoDao.AMOUNT, matchProfitTemplateInfo.getAmount());
			valueMap.put(IMatchProfitTemplateInfoDao.UPDATE_DATE_LONG, current);
			if (!matchProfitTemplateInfoService.updateValueByTemplateId(matchProfitTemplateInfo.getTemplateId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新波胆模板信息失败！");
			}
			return toObjectReturnInfo(matchProfitTemplateInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateMatchProfitTemplateInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateMatchProfitTemplateInfoStatus(HttpServletRequest request, @RequestParam(value = "templateId", required = true) String templateId,
		@RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			if (status != StatusType.ENABLED.getStatus())
			{
				status = StatusType.DISABLED.getStatus();
			}
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IMatchProfitTemplateInfoDao.STATUS, status);
			valueMap.put(IMatchProfitTemplateInfoDao.UPDATE_DATE_LONG, current);
			if (!matchProfitTemplateInfoService.updateValueByTemplateId(templateId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新波胆模板信息状态失败！");
			}
			return toStringReturnInfo("更新波胆模板信息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteMatchProfitTemplateInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteMatchProfitTemplateInfo(HttpServletRequest request, @RequestParam(value = "templateId", required = true) String templateId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			matchProfitTemplateInfoService.deleteMatchProfitTemplateInfoByTemplateId(templateId);
			return toStringReturnInfo("删除波胆模板信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMatchProfitTemplateInfo")
	@ResponseBody
	public ReturnInfo ajaxGetMatchProfitTemplateInfo(HttpServletRequest request, @RequestParam(value = "templateId", required = true) String templateId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			MatchProfitTemplateInfo matchProfitTemplateInfo = matchProfitTemplateInfoService.getMatchProfitTemplateInfoByTemplateId(templateId);
			if (matchProfitTemplateInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "波胆模板信息不存在！");
			}
			return toObjectReturnInfo(matchProfitTemplateInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 将模板更新到未开始的比赛
	 * 
	 * @param request
	 * @param templateId
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateMatchProfitTemplateInfoToUnBeginMatch")
	@ResponseBody
	public ReturnInfo ajaxUpdateMatchProfitTemplateInfoToUnBeginMatch(HttpServletRequest request, @RequestParam(value = "templateId", required = true) String templateId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			MatchProfitTemplateInfo matchProfitTemplateInfo = matchProfitTemplateInfoService.getMatchProfitTemplateInfoByTemplateId(templateId);
			if (matchProfitTemplateInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "波胆模板信息不存在！");
			}
			int start = 0;
			int size = 100;
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			// searchInfos.clear();
			searchInfos.add(new TableSearchInfo(IMatchProfitInfoDao.SCORE1, matchProfitTemplateInfo.getScore1() + "", true, false));
			searchInfos.add(new TableSearchInfo(IMatchProfitInfoDao.SCORE2, matchProfitTemplateInfo.getScore2() + "", true, false));
			List<MatchProfitInfo> matchProfitInfoList = null;
			int successCount = 0, failCount = 0;
			long current = System.currentTimeMillis();
			while (true)
			{
				matchProfitInfoList = matchProfitInfoService.getMatchProfitInfoList(start, size, orderInfos, searchInfos);
				if (matchProfitInfoList == null || matchProfitInfoList.size() < 1)
				{
					break;
				}
				for (MatchProfitInfo matchProfitInfo : matchProfitInfoList)
				{
					try
					{
						MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(matchProfitInfo.getPhaseId());
						if (matchInfo == null || matchInfo.getMatchDate() <= current)
						{
							continue;
						}
						int profitPercent = matchProfitTemplateInfo.getProfitPercent();
						profitPercent = profitPercent + RandomTools.genRandomInt(0, 500) - 200;
						while (profitPercent <= 0)
						{
							profitPercent = matchProfitTemplateInfo.getProfitPercent() + RandomTools.genRandomInt(0, 500) - 200;
						}
						long amount = matchProfitTemplateInfo.getAmount();
						amount = amount * RandomTools.genRandomInt(80, 120) / 100;
						while (amount <= 0)
						{
							amount = matchProfitTemplateInfo.getAmount() * RandomTools.genRandomInt(80, 120) / 100;
						}
						Map<String, Object> valueMap = new HashMap<>();
						valueMap.put(IMatchProfitInfoDao.PROFIT_PERCENT, profitPercent);
						valueMap.put(IMatchProfitInfoDao.AMOUNT, amount);
						if (matchProfitInfoService.updateValueByProfitId(matchProfitInfo.getProfitId(), valueMap))
						{
							successCount++;
						}
						else
						{
							failCount++;
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
						failCount++;
					}
				}
				if (matchProfitInfoList.size() < size)// 取完了结束循环
				{
					break;
				}
				else
				{
					start += size;
				}
			}
			return new ReturnInfo(ErrorInfo.SUCCESS, String.format("更新成功：%d个，更新失败：%d个。", successCount, failCount));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMatchProfitTemplateInfoList")
	@ResponseBody
	public TableData ajaxGetMatchProfitTemplateInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IMatchProfitTemplateInfoDao.MATCH_TYPE, IMatchProfitTemplateInfoDao.STATUS });
			List<MatchProfitTemplateInfo> list = matchProfitTemplateInfoService.getMatchProfitTemplateInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos,
				searchInfos);
			long count = matchProfitTemplateInfoService.getMatchProfitTemplateInfoListCount(searchInfos);
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
