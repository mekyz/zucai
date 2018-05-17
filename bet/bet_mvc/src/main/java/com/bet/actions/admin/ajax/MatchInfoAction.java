package com.bet.actions.admin.ajax;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bet.actions.admin.BaseAdminController;
import com.bet.daos.IMatchInfoDao;
import com.bet.daos.IMatchProfitTemplateInfoDao;
import com.bet.daos.ITeamInfoDao;
import com.bet.models.MatchData;
import com.bet.orms.MatchInfo;
import com.bet.orms.MatchProfitInfo;
import com.bet.orms.MatchProfitTemplateInfo;
import com.bet.orms.TeamInfo;
import com.bet.services.CBetService;
import com.bet.services.CBonusService;
import com.bet.services.CStatService;
import com.bet.services.IMatchInfoService;
import com.bet.services.IMatchProfitInfoService;
import com.bet.services.IMatchProfitTemplateInfoService;
import com.bet.services.ITeamInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.GsonTools;
import com.lrcall.common.utils.RandomTools;
import com.lrcall.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminMatchInfoAction")
@RequestMapping(value = "/admin")
public class MatchInfoAction extends BaseAdminController
{
	@Autowired
	private IMatchInfoService matchInfoService;
	@Autowired
	private ITeamInfoService teamInfoService;
	@Autowired
	private IMatchProfitInfoService matchProfitInfoService;
	@Autowired
	private IMatchProfitTemplateInfoService matchProfitTemplateInfoService;
	@Autowired
	private CStatService cStatService;
	@Autowired
	private CBetService cBetService;
	@Autowired
	private CBonusService cBonusService;

	@RequestMapping(value = "/ajaxAddMatchInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddMatchInfo(HttpServletRequest request, @ModelAttribute("matchInfo") MatchInfo matchInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			if (StringTools.isNull(matchInfo.getHomeTeamId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "请选择主队！");
			}
			if (StringTools.isNull(matchInfo.getAwayTeamId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "请选择客队！");
			}
			if (matchInfo.getHomeTeamId().equals(matchInfo.getAwayTeamId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "主队和客队不能相同！");
			}
			TeamInfo homeTeamInfo = teamInfoService.getTeamInfoByTeamId(matchInfo.getHomeTeamId());
			if (homeTeamInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "主队不存在！");
			}
			TeamInfo awayTeamInfo = teamInfoService.getTeamInfoByTeamId(matchInfo.getAwayTeamId());
			if (awayTeamInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "客队不存在！");
			}
			if (matchInfo.getTimeEndsale() > matchInfo.getMatchDate())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "停止售票时间不能晚于开赛时间！");
			}
			matchInfo.setPhaseId(matchInfoService.genId());
			matchInfo.setHomeTeam(homeTeamInfo.getName());
			matchInfo.setHomeTeamPicUrl(homeTeamInfo.getPicUrl());
			matchInfo.setAwayTeam(awayTeamInfo.getName());
			matchInfo.setAwayTeamPicUrl(awayTeamInfo.getPicUrl());
			matchInfo.setHalfResultStatus(StatusType.DISABLED.getStatus());
			matchInfo.setFinalResultStatus(StatusType.DISABLED.getStatus());
			matchInfo.setHalfProfitStatus(StatusType.DISABLED.getStatus());
			matchInfo.setFinalProfitStatus(StatusType.DISABLED.getStatus());
			matchInfo.setLeaderProfitStatus(StatusType.DISABLED.getStatus());
			matchInfo.setStatus(StatusType.ENABLED.getStatus());
			matchInfo = matchInfoService.addMatchInfo(matchInfo);
			if (matchInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加比赛信息失败！");
			}
			return toObjectReturnInfo(matchInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateMatchInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateMatchInfo(HttpServletRequest request, @ModelAttribute("matchInfo") MatchInfo matchInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			MatchInfo matchInfo1 = matchInfoService.getMatchInfoByPhaseId(matchInfo.getPhaseId());
			if (matchInfo1 == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "比赛信息不存在！");
			}
			if (StringTools.isNull(matchInfo.getHomeTeamId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "请选择主队！");
			}
			if (StringTools.isNull(matchInfo.getAwayTeamId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "请选择客队！");
			}
			if (matchInfo.getHomeTeamId().equals(matchInfo.getAwayTeamId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "主队和客队不能相同！");
			}
			TeamInfo homeTeamInfo = teamInfoService.getTeamInfoByTeamId(matchInfo.getHomeTeamId());
			if (homeTeamInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "主队不存在！");
			}
			TeamInfo awayTeamInfo = teamInfoService.getTeamInfoByTeamId(matchInfo.getAwayTeamId());
			if (awayTeamInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "客队不存在！");
			}
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IMatchInfoDao.MATCH_NAME, matchInfo.getMatchName());
			valueMap.put(IMatchInfoDao.MATCH_NUM, matchInfo.getMatchNum());
			valueMap.put(IMatchInfoDao.MATCH_DATE, matchInfo.getMatchDate());
			valueMap.put(IMatchInfoDao.TIME_ENDSALE, matchInfo.getTimeEndsale());
			valueMap.put(IMatchInfoDao.HOME_TEAM_ID, matchInfo.getHomeTeamId());
			valueMap.put(IMatchInfoDao.HOME_TEAM, homeTeamInfo.getName());
			valueMap.put(IMatchInfoDao.HOME_TEAM_PIC_URL, homeTeamInfo.getPicUrl());
			valueMap.put(IMatchInfoDao.AWAY_TEAM_ID, matchInfo.getAwayTeamId());
			valueMap.put(IMatchInfoDao.AWAY_TEAM, awayTeamInfo.getName());
			valueMap.put(IMatchInfoDao.AWAY_TEAM_PIC_URL, awayTeamInfo.getPicUrl());
			valueMap.put(IMatchInfoDao.VIDEO_URL, matchInfo.getVideoUrl());
			valueMap.put(IMatchInfoDao.FINAL_SCORE1, matchInfo.getFinalScore1());
			valueMap.put(IMatchInfoDao.FINAL_SCORE2, matchInfo.getFinalScore2());
			valueMap.put(IMatchInfoDao.HALF_SCORE1, matchInfo.getHalfScore1());
			valueMap.put(IMatchInfoDao.HALF_SCORE2, matchInfo.getHalfScore2());
			valueMap.put(IMatchInfoDao.SORT_INDEX, matchInfo.getSortIndex());
			valueMap.put(IMatchInfoDao.UPDATE_DATE_LONG, current);
			if (matchInfo1.getUpdateHalfScoreDateLong() == null && matchInfo.getHalfScore1() != null && matchInfo.getHalfScore2() != null)
			{
				valueMap.put(IMatchInfoDao.HALF_RESULT_STATUS, StatusType.ENABLED.getStatus());
				valueMap.put(IMatchInfoDao.UPDATE_HALF_SCORE_DATE_LONG, current);
			}
			if (matchInfo1.getUpdateFinalScoreDateLong() == null && matchInfo.getFinalScore1() != null && matchInfo.getFinalScore2() != null)
			{
				valueMap.put(IMatchInfoDao.FINAL_RESULT_STATUS, StatusType.ENABLED.getStatus());
				valueMap.put(IMatchInfoDao.UPDATE_FINAL_SCORE_DATE_LONG, current);
				valueMap.put(IMatchInfoDao.HANDICAP, matchInfo.getFinalScore1() + matchInfo.getFinalScore2());
			}
			if (!matchInfoService.updateValueByPhaseId(matchInfo.getPhaseId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新比赛信息失败！");
			}
			return toObjectReturnInfo(matchInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateMatchHalfScore", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateMatchHalfScore(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId,
		@RequestParam(value = "halfScore1", required = true) byte halfScore1, @RequestParam(value = "halfScore2", required = true) byte halfScore2)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IMatchInfoDao.HALF_SCORE1, halfScore1);
			valueMap.put(IMatchInfoDao.HALF_SCORE2, halfScore2);
			valueMap.put(IMatchInfoDao.HALF_RESULT_STATUS, StatusType.ENABLED.getStatus());
			valueMap.put(IMatchInfoDao.UPDATE_DATE_LONG, current);
			if (!matchInfoService.updateValueByPhaseId(phaseId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新比赛信息失败！");
			}
			return toStringReturnInfo("更新比赛全场比分信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateMatchFinalScore", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateMatchFinalScore(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId,
		@RequestParam(value = "finalScore1", required = true) byte finalScore1, @RequestParam(value = "finalScore2", required = true) byte finalScore2)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IMatchInfoDao.FINAL_SCORE1, finalScore1);
			valueMap.put(IMatchInfoDao.FINAL_SCORE2, finalScore2);
			valueMap.put(IMatchInfoDao.FINAL_RESULT_STATUS, StatusType.ENABLED.getStatus());
			valueMap.put(IMatchInfoDao.UPDATE_DATE_LONG, current);
			if (!matchInfoService.updateValueByPhaseId(phaseId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新比赛信息失败！");
			}
			return toStringReturnInfo("更新比赛全场比分信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateMatchInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateMatchInfoStatus(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId,
		@RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			if (status != StatusType.ENABLED.getStatus())
			{
				status = StatusType.DISABLED.getStatus();
			}
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IMatchInfoDao.STATUS, status);
			valueMap.put(IMatchInfoDao.UPDATE_DATE_LONG, current);
			if (!matchInfoService.updateValueByPhaseId(phaseId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新比赛信息状态失败！");
			}
			return toStringReturnInfo("更新比赛信息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateMatchInfoStatusBat", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateMatchInfoStatusBat(HttpServletRequest request, @RequestParam(value = "phaseIds", required = true) String phaseIds,
		@RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			if (status != StatusType.ENABLED.getStatus())
			{
				status = StatusType.DISABLED.getStatus();
			}
			String[] phaseIdList = phaseIds.split(",");
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IMatchInfoDao.STATUS, status);
			valueMap.put(IMatchInfoDao.UPDATE_DATE_LONG, current);
			for (String phaseId : phaseIdList)
			{
				if (!matchInfoService.updateValueByPhaseId(phaseId, valueMap))
				{
					throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新比赛信息状态失败！");
				}
			}
			return toStringReturnInfo("更新比赛信息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteMatchInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteMatchInfo(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			matchInfoService.deleteMatchInfoByPhaseId(phaseId);
			return toStringReturnInfo("删除比赛信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMatchInfo")
	@ResponseBody
	public ReturnInfo ajaxGetMatchInfo(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			MatchInfo matchInfo = matchInfoService.getMatchInfoByPhaseId(phaseId);
			if (matchInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "比赛信息不存在！");
			}
			return toObjectReturnInfo(matchInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMatchInfoList")
	@ResponseBody
	public TableData ajaxGetMatchInfoList(HttpServletRequest request, @RequestParam(value = "startDateLong", required = false) Long startDateLong,
		@RequestParam(value = "endDateLong", required = false) Long endDateLong, @RequestParam(value = "status", required = false) Byte status,
		@RequestParam(value = "finalResultStatus", required = false) Byte finalResultStatus)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			if (status != null)
			{
				searchInfos.add(new TableSearchInfo(IMatchInfoDao.STATUS, status + "", true, false));
			}
			if (finalResultStatus != null)
			{
				searchInfos.add(new TableSearchInfo(IMatchInfoDao.FINAL_RESULT_STATUS, finalResultStatus + "", true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos,
				new String[] { IMatchInfoDao.PHASE_ID, IMatchInfoDao.MATCH_NAME, IMatchInfoDao.MATCH_NUM, IMatchInfoDao.HOME_TEAM, IMatchInfoDao.AWAY_TEAM });
			List<MatchInfo> list = matchInfoService.getMatchInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos, startDateLong, endDateLong);
			long count = matchInfoService.getMatchInfoListCount(searchInfos, startDateLong, endDateLong);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, list);
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算比赛的中奖情况
	 * 
	 * @param request
	 * @param phaseId
	 * @return
	 */
	// @RequestMapping(value = "/ajaxUpdateMatchBonus")
	// @ResponseBody
	// public ReturnInfo ajaxUpdateMatchBonus(HttpServletRequest request)
	// {
	// try
	// {
	// // AdminInfo sessionAdminInfo = getAdminSession(request);
	// return cStatService.updateCheckMatchBonusJob();
	// }
	// catch (HibernateJsonResultException e)
	// {
	// return toExceptionReturnInfo(e);
	// }
	// }
	/**
	 * 计算团队奖
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserBetTeamBonus")
	@ResponseBody
	public ReturnInfo ajaxUpdateUserBetTeamBonus(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cStatService.updateCheckUserBetTeamBonusJob();
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 汇总团队奖
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaUpdateCheckTeamBonusJob")
	@ResponseBody
	public ReturnInfo ajaUpdateCheckTeamBonusJob(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cStatService.updateCheckTeamBonusJob();
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 发放团队奖
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateTeamBonus")
	@ResponseBody
	public ReturnInfo ajaxUpdateTeamBonus(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cStatService.updateTeamBonusJob();
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 撤销赛事下注
	 * 
	 * @param request
	 * @param phaseId
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "/ajaxDeleteUserBets")
	@ResponseBody
	public ReturnInfo ajaxDeleteUserBets(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId, @RequestParam(value = "remark", required = true) String remark)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cBetService.deleteUserBetInfosByPhaseId(phaseId, remark);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 撤销赛事下注奖金
	 * 
	 * @param request
	 * @param phaseId
	 * @return
	 */
	@RequestMapping(value = "/ajaxRemoveMatchBonus")
	@ResponseBody
	public ReturnInfo ajaxRemoveMatchBonus(HttpServletRequest request, @RequestParam(value = "phaseId", required = true) String phaseId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cBonusService.checkAndUpdateRemoveMatchBonusJob(phaseId);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 导入未开始的比赛xls数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxImportMatchData")
	@ResponseBody
	public ReturnInfo ajaxImportMatchData(HttpServletRequest request, @RequestParam(value = "upload", required = true) MultipartFile file)
	{
		try
		{
			log.info("ajaxImportMatchData", file.getContentType());
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			// String path = "D:/雷速体育赛程表-3天内.xls";
			// InputStream inputStream = new FileInputStream(path);
			InputStream inputStream = file.getInputStream();
			Workbook workbook = new HSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter formatter = new DataFormatter();
			List<MatchData> matchDataList = new ArrayList<>();
			int i = 0;
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IMatchProfitTemplateInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
			List<MatchProfitTemplateInfo> matchProfitTemplateInfoList = matchProfitTemplateInfoService.getMatchProfitTemplateInfoList(0, -1, null, searchInfos);
			for (Row row : sheet)
			{
				i++;
				if (i == 1)
				{
					String text = formatter.formatCellValue(row.getCell(4));
					// System.out.println("表:" + text);
					if (!text.contains("主场"))
					{
						return toStringReturnInfo("文件数据不对！");
					}
					continue;
				}
				MatchData matchData = new MatchData();
				for (Cell cell : row)
				{
					int index = cell.getColumnIndex();
					CellReference cellRef = new CellReference(row.getRowNum(), index);
					// 单元格名称
					System.out.print(cellRef.formatAsString());
					System.out.print(" - ");
					// 通过获取单元格值并应用任何数据格式（Date，0.00，1.23e9，$ 1.23等），获取单元格中显示的文本
					String text = formatter.formatCellValue(cell);
					System.out.print(text + "    ");
					matchData.setId(index + "");
					if (index == 0)
					{
						matchData.setDate(text);
					}
					else if (index == 1)
					{
						matchData.setName(text);
					}
					else if (index == 2)
					{
						matchData.setNum(text);
					}
					else if (index == 3)
					{
						matchData.setTime(text);
					}
					else if (index == 4)
					{
						matchData.setHomeTeam(text);
					}
					else if (index == 5)
					{
						matchData.setAwayTeam(text);
					}
					else if (index == 6)
					{
						matchData.setUrl(text);
					}
					// 获取值并自己格式化
					// switch (cell.getCellType())
					// {
					// case Cell.CELL_TYPE_STRING:// 字符串型
					// System.out.println(cell.getRichStringCellValue().getString());
					//
					// break;
					// case Cell.CELL_TYPE_NUMERIC:// 数值型
					// if (DateUtil.isCellDateFormatted(cell))
					// { // 如果是date类型则 ，获取该cell的date值
					// System.out.println(cell.getDateCellValue());
					// }
					// else
					// {// 纯数字
					// System.out.println(cell.getNumericCellValue());
					// }
					// break;
					// case Cell.CELL_TYPE_BOOLEAN:// 布尔
					// System.out.println(cell.getBooleanCellValue());
					// break;
					// case Cell.CELL_TYPE_FORMULA:// 公式型
					// System.out.println(cell.getCellFormula());
					// break;
					// case Cell.CELL_TYPE_BLANK:// 空值
					// System.out.println();
					// break;
					// case Cell.CELL_TYPE_ERROR: // 故障
					// System.out.println();
					// break;
					// default:
					// System.out.println();
					// }
				}
				matchDataList.add(matchData);
				System.out.println();
			}
			System.out.println(GsonTools.toJson(matchDataList));
			int success = 0, fail = 0;
			long current = System.currentTimeMillis();
			if (matchDataList.size() > 0)
			{
				for (MatchData matchData : matchDataList)
				{
					searchInfos.clear();
					searchInfos.add(new TableSearchInfo(IMatchInfoDao.HOME_TEAM, matchData.getHomeTeam(), true, false));
					searchInfos.add(new TableSearchInfo(IMatchInfoDao.AWAY_TEAM, matchData.getAwayTeam(), true, false));
					searchInfos.add(new TableSearchInfo(IMatchInfoDao.MATCH_NAME, matchData.getName(), true, false));
					long count = matchInfoService.getMatchInfoListCount(searchInfos);
					if (count < 1)
					{
						try
						{
							TeamInfo homeTeam = null, awayTeam = null;
							searchInfos.clear();
							searchInfos.add(new TableSearchInfo(ITeamInfoDao.NAME, matchData.getHomeTeam(), true, false));
							List<TeamInfo> homeTeamList = teamInfoService.getTeamInfoList(0, 1, null, searchInfos);
							if (homeTeamList != null && homeTeamList.size() > 0)
							{
								homeTeam = homeTeamList.get(0);
							}
							else
							{
								homeTeam = new TeamInfo(teamInfoService.genId(), matchData.getHomeTeam(), "", StatusType.ENABLED.getStatus(), current, current);
								homeTeam = teamInfoService.addTeamInfo(homeTeam);
							}
							searchInfos.clear();
							searchInfos.add(new TableSearchInfo(ITeamInfoDao.NAME, matchData.getAwayTeam(), true, false));
							List<TeamInfo> awayTeamList = teamInfoService.getTeamInfoList(0, 1, null, searchInfos);
							if (awayTeamList != null && awayTeamList.size() > 0)
							{
								awayTeam = awayTeamList.get(0);
							}
							else
							{
								awayTeam = new TeamInfo(teamInfoService.genId(), matchData.getAwayTeam(), "", StatusType.ENABLED.getStatus(), current, current);
								awayTeam = teamInfoService.addTeamInfo(awayTeam);
							}
							String date = matchData.getDate();
							String time = matchData.getTime();
							int year = Integer.parseInt(date.substring(0, 4));
							int month = Integer.parseInt(date.substring(4, 6));
							int day = Integer.parseInt(date.substring(6));
							int hour = Integer.parseInt(StringTools.getValue(time, "", ":"));
							int minute = Integer.parseInt(StringTools.getValue(time, ":", ""));
							Calendar calendar = Calendar.getInstance();
							calendar.set(year, month - 1, day, hour, minute, 0);
							long matchDate = calendar.getTimeInMillis();
							MatchInfo matchInfo = new MatchInfo(matchInfoService.genId(), null, matchData.getName(), matchData.getNum(), matchDate, matchDate - 10L * 60 * 1000, homeTeam.getTeamId(),
								matchData.getHomeTeam(), homeTeam.getPicUrl(), awayTeam.getTeamId(), matchData.getAwayTeam(), awayTeam.getPicUrl(), matchData.getUrl(), null, null, null, null, null,
								null, null, StatusType.DISABLED.getStatus(), StatusType.DISABLED.getStatus(), StatusType.DISABLED.getStatus(), StatusType.DISABLED.getStatus(),
								StatusType.DISABLED.getStatus(), 100, StatusType.ENABLED.getStatus(), current, current);
							matchInfo = matchInfoService.addMatchInfo(matchInfo);
							success++;
							// 增加波胆
							if (matchProfitTemplateInfoList != null && matchProfitTemplateInfoList.size() > 0)
							{
								for (MatchProfitTemplateInfo matchProfitTemplateInfo : matchProfitTemplateInfoList)
								{
									try
									{
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
										matchProfitInfoService.addMatchProfitInfo(new MatchProfitInfo(matchProfitInfoService.genId(), matchInfo.getPhaseId(), matchProfitTemplateInfo.getMatchType(),
											matchProfitTemplateInfo.getScore1(), matchProfitTemplateInfo.getScore2(), profitPercent, amount, 0, StatusType.ENABLED.getStatus(), current, current));
									}
									catch (Exception e)
									{
										System.out.println("增加波胆失败：" + GsonTools.toJson(matchProfitTemplateInfo));
									}
								}
							}
						}
						catch (Exception e)
						{
							System.out.println("增加赛事失败：" + GsonTools.toJson(matchData));
							fail++;
						}
					}
				}
			}
			return toStringReturnInfo(String.format("共%d场比赛，导入成功%d场比赛，导入失败%d场比赛！", matchDataList.size(), success, fail));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 导入已完成的比赛xls数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxImportMatchData2")
	@ResponseBody
	public ReturnInfo ajaxImportMatchData2(HttpServletRequest request, @RequestParam(value = "upload", required = true) MultipartFile file)
	{
		try
		{
			log.info("ajaxImportMatchData2", file.getContentType());
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			InputStream inputStream = file.getInputStream();
			Workbook workbook = new HSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter formatter = new DataFormatter();
			List<MatchData> matchDataList = new ArrayList<>();
			int i = 0;
			for (Row row : sheet)
			{
				i++;
				if (i == 1)
				{
					String text = formatter.formatCellValue(row.getCell(5));
					// System.out.println("表:" + text);
					if (!text.contains("主场"))
					{
						return toStringReturnInfo("文件数据不对！");
					}
					continue;
				}
				MatchData matchData = new MatchData();
				for (Cell cell : row)
				{
					int index = cell.getColumnIndex();
					String text = formatter.formatCellValue(cell);
					matchData.setId(index + "");
					if (index == 0)
					{
						matchData.setDate(text);
					}
					else if (index == 1)
					{
						matchData.setName(text);
					}
					else if (index == 2)
					{
						matchData.setNum(text);
					}
					else if (index == 3)
					{
						matchData.setTime(text);
					}
					else if (index == 4)
					{
						matchData.setStatus(text);
					}
					else if (index == 5)
					{
						matchData.setHomeTeam(text);
					}
					else if (index == 6)
					{
						matchData.setFinalScore(text);
					}
					else if (index == 7)
					{
						matchData.setAwayTeam(text);
					}
					else if (index == 8)
					{
						matchData.setHalfScore(text);
					}
					else if (index == 9)
					{
						matchData.setCount(text);
					}
				}
				matchDataList.add(matchData);
			}
			System.out.println(GsonTools.toJson(matchDataList));
			int success = 0, fail = 0;
			long current = System.currentTimeMillis();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			// searchInfos.add(new TableSearchInfo(IMatchProfitTemplateInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
			// List<MatchProfitTemplateInfo> matchProfitTemplateInfoList = matchProfitTemplateInfoService.getMatchProfitTemplateInfoList(0, -1, null, searchInfos);
			if (matchDataList.size() > 0)
			{
				for (MatchData matchData : matchDataList)
				{
					String date = matchData.getDate();
					String time = matchData.getTime();
					int year = Integer.parseInt(date.substring(0, 4));
					int month = Integer.parseInt(date.substring(4, 6));
					int day = Integer.parseInt(date.substring(6));
					int hour = Integer.parseInt(StringTools.getValue(time, "", ":"));
					int minute = Integer.parseInt(StringTools.getValue(time, ":", ""));
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month - 1, day, hour, minute, 0);
					// long matchDate = calendar.getTimeInMillis();
					searchInfos.clear();
					searchInfos.add(new TableSearchInfo(IMatchInfoDao.HOME_TEAM, matchData.getHomeTeam(), true, false));
					searchInfos.add(new TableSearchInfo(IMatchInfoDao.AWAY_TEAM, matchData.getAwayTeam(), true, false));
					searchInfos.add(new TableSearchInfo(IMatchInfoDao.MATCH_NAME, matchData.getName(), true, false));
					// searchInfos.add(new TableSearchInfo(IMatchInfoDao.MATCH_DATE, matchDate + "", true, false));
					List<MatchInfo> list = matchInfoService.getMatchInfoList(0, 1, null, searchInfos);
					if (list != null && list.size() > 0)
					{
						try
						{
							MatchInfo matchInfo = list.get(0);
							String finalScore = matchData.getFinalScore();
							String halfScore = matchData.getHalfScore();
							byte finalScore1 = Byte.parseByte(StringTools.getValue(finalScore, "", "-"));
							byte finalScore2 = Byte.parseByte(StringTools.getValue(finalScore, "-", ""));
							byte halfScore1 = Byte.parseByte(StringTools.getValue(halfScore, "", "-"));
							byte halfScore2 = Byte.parseByte(StringTools.getValue(halfScore, "-", ""));
							matchInfo.setFinalScore1(finalScore1);
							matchInfo.setFinalScore2(finalScore2);
							matchInfo.setUpdateFinalScoreDateLong(current);
							matchInfo.setFinalResultStatus(StatusType.ENABLED.getStatus());
							matchInfo.setHalfScore1(halfScore1);
							matchInfo.setHalfScore2(halfScore2);
							matchInfo.setUpdateHalfScoreDateLong(current);
							matchInfo.setHalfResultStatus(StatusType.ENABLED.getStatus());
							matchInfo.setHandicap((finalScore1 + finalScore2) + "");
							// matchInfo = matchInfoService.updateMatchInfo(matchInfo);
							Map<String, Object> valueMap = new HashMap<>();
							valueMap.put(IMatchInfoDao.FINAL_SCORE1, finalScore1);
							valueMap.put(IMatchInfoDao.FINAL_SCORE2, finalScore2);
							valueMap.put(IMatchInfoDao.UPDATE_FINAL_SCORE_DATE_LONG, current);
							valueMap.put(IMatchInfoDao.FINAL_RESULT_STATUS, StatusType.ENABLED.getStatus());
							valueMap.put(IMatchInfoDao.HALF_SCORE1, halfScore1);
							valueMap.put(IMatchInfoDao.HALF_SCORE2, halfScore2);
							valueMap.put(IMatchInfoDao.UPDATE_HALF_SCORE_DATE_LONG, current);
							valueMap.put(IMatchInfoDao.HALF_RESULT_STATUS, StatusType.ENABLED.getStatus());
							valueMap.put(IMatchInfoDao.HANDICAP, (finalScore1 + finalScore2));
							if (!matchInfoService.updateValueByPhaseId(matchInfo.getPhaseId(), valueMap))
							{
							}
							success++;
						}
						catch (Exception e)
						{
							System.out.println(GsonTools.toJson(matchData));
							fail++;
						}
					}
					// else
					// {
					// try
					// {
					// TeamInfo homeTeam = null, awayTeam = null;
					// searchInfos.clear();
					// searchInfos.add(new TableSearchInfo(ITeamInfoDao.NAME, matchData.getHomeTeam(), true, false));
					// List<TeamInfo> homeTeamList = teamInfoService.getTeamInfoList(0, 1, null, searchInfos);
					// if (homeTeamList != null && homeTeamList.size() > 0)
					// {
					// homeTeam = homeTeamList.get(0);
					// }
					// else
					// {
					// homeTeam = new TeamInfo(teamInfoService.genId(), matchData.getHomeTeam(), "", StatusType.ENABLED.getStatus(), current, current);
					// homeTeam = teamInfoService.addTeamInfo(homeTeam);
					// }
					// searchInfos.clear();
					// searchInfos.add(new TableSearchInfo(ITeamInfoDao.NAME, matchData.getAwayTeam(), true, false));
					// List<TeamInfo> awayTeamList = teamInfoService.getTeamInfoList(0, 1, null, searchInfos);
					// if (awayTeamList != null && awayTeamList.size() > 0)
					// {
					// awayTeam = awayTeamList.get(0);
					// }
					// else
					// {
					// awayTeam = new TeamInfo(teamInfoService.genId(), matchData.getAwayTeam(), "", StatusType.ENABLED.getStatus(), current, current);
					// awayTeam = teamInfoService.addTeamInfo(awayTeam);
					// }
					// MatchInfo matchInfo = new MatchInfo(matchInfoService.genId(), null, matchData.getName(), matchData.getNum(), matchDate, matchDate - 10L * 60 * 1000, homeTeam.getTeamId(),
					// matchData.getHomeTeam(), homeTeam.getPicUrl(), awayTeam.getTeamId(), matchData.getAwayTeam(), awayTeam.getPicUrl(), matchData.getUrl(), null, null, null, null, null,
					// null, null, StatusType.DISABLED.getStatus(), StatusType.DISABLED.getStatus(), StatusType.DISABLED.getStatus(), StatusType.DISABLED.getStatus(),
					// StatusType.DISABLED.getStatus(), StatusType.DISABLED.getStatus(), current, current);
					// System.out.println("增加赛事：" + GsonTools.toJson(matchInfo));
					// matchInfo = matchInfoService.addMatchInfo(matchInfo);
					// success++;
					// // 增加波胆
					// if (matchProfitTemplateInfoList != null && matchProfitTemplateInfoList.size() > 0)
					// {
					// for (MatchProfitTemplateInfo matchProfitTemplateInfo : matchProfitTemplateInfoList)
					// {
					// try
					// {
					// matchProfitInfoService.addMatchProfitInfo(new MatchProfitInfo(matchProfitInfoService.genId(), matchInfo.getPhaseId(), matchProfitTemplateInfo.getMatchType(),
					// matchProfitTemplateInfo.getScore1(), matchProfitTemplateInfo.getScore2(), matchProfitTemplateInfo.getProfitPercent(), matchProfitTemplateInfo.getAmount(),
					// 0, StatusType.ENABLED.getStatus(), current, current));
					// }
					// catch (Exception e)
					// {
					// System.out.println("增加波胆失败：" + GsonTools.toJson(matchProfitTemplateInfo));
					// }
					// }
					// }
					// }
					// catch (Exception e)
					// {
					// System.out.println("增加赛事失败：" + GsonTools.toJson(matchData));
					// fail++;
					// }
					// }
				}
			}
			return toStringReturnInfo(String.format("共%d场比赛结果，导入成功%d场比赛，导入失败%d场比赛！", matchDataList.size(), success, fail));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return toExceptionReturnInfo(e);
		}
	}
}
