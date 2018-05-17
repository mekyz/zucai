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
import com.bet.daos.INewsInfoDao;
import com.bet.orms.NewsInfo;
import com.bet.orms.UserInfo;
import com.bet.orms.UserNewsStatusInfo;
import com.bet.services.INewsInfoService;
import com.bet.services.IUserNewsStatusInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.GsonTools;
import com.lrcall.lrweb.common.enums.SqlOrderType;
import com.lrcall.lrweb.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userNewsAction")
@RequestMapping("/user")
public class NewsAction extends BaseUserController
{
	@Autowired
	private INewsInfoService newsInfoService;
	@Autowired
	private IUserNewsStatusInfoService userNewsStatusInfoService;

	/**
	 * 获取消息信息接口<br/>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetNewsInfo")
	@ResponseBody
	public ReturnInfo ajaxGetNewsInfo(HttpServletRequest request, @RequestParam(value = "newsId", required = true) String newsId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			NewsInfo newsInfo = newsInfoService.getNewsInfoByNewsId(newsId);
			if (newsInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "消息不存在！");
			}
			if (sessionUserInfo != null)
			{
				UserNewsStatusInfo userNewsStatusInfo = userNewsStatusInfoService.getUserNewsStatusInfo(sessionUserInfo.getUserId(), newsId);
				if (userNewsStatusInfo == null)
				{
					userNewsStatusInfo = new UserNewsStatusInfo(sessionUserInfo.getUserId(), newsId, StatusType.ENABLED.getStatus(), System.currentTimeMillis());
					userNewsStatusInfoService.addUserNewsStatusInfo(userNewsStatusInfo);
				}
			}
			return new ReturnInfo(ErrorInfo.SUCCESS, GsonTools.toJson(newsInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取消息列表<br/>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetNewsList")
	@ResponseBody
	public TableData ajaxGetNewsList(HttpServletRequest request, @RequestParam(value = "topStatus", required = false) Byte topStatus, @RequestParam(value = "sortId", required = false) String sortId)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			orderInfos.add(new TableOrderInfo(100, INewsInfoDao.ADD_DATE_LONG, SqlOrderType.DESC.getType()));
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			searchInfos.add(new TableSearchInfo(INewsInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
			if (topStatus != null)
			{
				searchInfos.add(new TableSearchInfo(INewsInfoDao.TOP_STATUS, topStatus + "", true, false));
			}
			if (!StringTools.isNull(sortId))
			{
				searchInfos.add(new TableSearchInfo(INewsInfoDao.SORT_ID, sortId, true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { INewsInfoDao.TITLE, INewsInfoDao.AUTHOR, INewsInfoDao.SHORT_CONTENT, INewsInfoDao.CONTENT });
			List<NewsInfo> newsInfoList = newsInfoService.getNewsInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = newsInfoService.getNewsInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseNewsInfoList(newsInfoList));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取消息列表数量<br/>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetNewsListCount")
	@ResponseBody
	public long ajaxGetNewsListCount(HttpServletRequest request)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			searchInfos.add(new TableSearchInfo(INewsInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { INewsInfoDao.TITLE, INewsInfoDao.AUTHOR, INewsInfoDao.SHORT_CONTENT, INewsInfoDao.CONTENT });
			long count = newsInfoService.getNewsInfoListCount(searchInfos);
			return count;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获取未读消息列表数量<br/>
	 * 
	 * @param request
	 * @return
	 */
	// @RequestMapping(value = "/ajaxGetUnReadNewsListCount")
	// @ResponseBody
	// public long ajaxGetUnReadNewsListCount(HttpServletRequest request)
	// {
	// try
	// {
	// UserInfo sessionUserInfo = getUserSession(request);
	// Map<String, Object> tableMap = TableTools.getTableParams(request);
	// List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
	// List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
	// searchInfos.add(new TableSearchInfo(INewsInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
	// buildTable(request, tableMap, orderInfos, searchInfos, new String[] { INewsInfoDao.TITLE, INewsInfoDao.AUTHOR, INewsInfoDao.SHORT_CONTENT, INewsInfoDao.CONTENT });
	// long count = newsInfoService.getNewsInfoListCount(searchInfos);
	// return count;
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// return 0;
	// }
}
