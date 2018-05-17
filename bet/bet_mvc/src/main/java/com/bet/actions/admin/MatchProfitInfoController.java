package com.bet.actions.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.daos.IMatchInfoDao;
import com.bet.enums.BodanType;
import com.bet.orms.MatchInfo;
import com.bet.orms.MatchProfitInfo;
import com.bet.services.IMatchInfoService;
import com.bet.services.IMatchProfitInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.ConstValues;
import com.lrcall.lrweb.common.enums.SqlOrderType;

@Controller("adminMatchProfitInfoController")
@RequestMapping(value = "/admin")
public class MatchProfitInfoController extends BaseAdminController
{
	@Autowired
	private IMatchProfitInfoService matchProfitInfoService;
	@Autowired
	private IMatchInfoService matchInfoService;

	@RequestMapping(value = "/manageMatchProfitInfos", method = RequestMethod.GET)
	public ModelAndView manageMatchProfitInfos(HttpServletRequest request, @RequestParam(value = "phaseId", required = false) String phaseId)
	{
		ModelAndView mv = new ModelAndView(getViewName("manageMatchProfitInfos"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("phaseId", phaseId);
		return mv;
	}

	@RequestMapping(value = "/matchProfitInfoAdd", method = RequestMethod.GET)
	public ModelAndView matchProfitInfoAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("matchProfitInfoAdd"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("matchProfitInfo", new MatchProfitInfo());
		mv.addObject("bodanTypeMap", BodanType.getMap());
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		orderInfos.add(new TableOrderInfo(IMatchInfoDao.MATCH_DATE, SqlOrderType.DESC.getType()));
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IMatchInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
		searchInfos.add(new TableSearchInfo(ConstValues.LEFT_BRACKET, "", true, true));
		searchInfos.add(new TableSearchInfo(IMatchInfoDao.HALF_RESULT_STATUS, StatusType.DISABLED.getStatus() + "", true, true));
		searchInfos.add(new TableSearchInfo(IMatchInfoDao.FINAL_RESULT_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		searchInfos.add(new TableSearchInfo(ConstValues.RIGHT_BRACKET, "", true, false));
		Long startDateLong = System.currentTimeMillis();
		Long endDateLong = null;
		List<MatchInfo> list = matchInfoService.getMatchInfoList(0, -1, orderInfos, searchInfos, startDateLong, endDateLong);
		mv.addObject("matchInfoList", list);
		return mv;
	}

	@RequestMapping(value = "/matchProfitInfoEdit", method = RequestMethod.GET)
	public ModelAndView matchProfitInfoEdit(HttpServletRequest request, @RequestParam(value = "profitId", required = true) String profitId)
	{
		ModelAndView mv = new ModelAndView(getViewName("matchProfitInfoEdit"));
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		MatchProfitInfo matchProfitInfo = matchProfitInfoService.getMatchProfitInfoByProfitId(profitId);
		if (matchProfitInfo == null)
		{
			throw new RuntimeException("赛事波胆信息ID不存在！");
		}
		mv.addObject("matchProfitInfo", matchProfitInfo);
		mv.addObject("bodanTypeMap", BodanType.getMap());
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		orderInfos.add(new TableOrderInfo(IMatchInfoDao.MATCH_DATE, SqlOrderType.DESC.getType()));
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IMatchInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
		searchInfos.add(new TableSearchInfo(ConstValues.LEFT_BRACKET, "", true, true));
		searchInfos.add(new TableSearchInfo(IMatchInfoDao.HALF_RESULT_STATUS, StatusType.DISABLED.getStatus() + "", true, true));
		searchInfos.add(new TableSearchInfo(IMatchInfoDao.FINAL_RESULT_STATUS, StatusType.DISABLED.getStatus() + "", true, false));
		searchInfos.add(new TableSearchInfo(ConstValues.RIGHT_BRACKET, "", true, false));
		Long startDateLong = System.currentTimeMillis();
		Long endDateLong = null;
		List<MatchInfo> list = matchInfoService.getMatchInfoList(0, -1, orderInfos, searchInfos, startDateLong, endDateLong);
		mv.addObject("matchInfoList", list);
		return mv;
	}
}
