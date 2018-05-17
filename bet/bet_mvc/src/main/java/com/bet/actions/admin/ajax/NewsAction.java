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
import org.springframework.web.util.HtmlUtils;

import com.bet.actions.admin.BaseAdminController;
import com.bet.daos.INewsInfoDao;
import com.bet.daos.INewsSortInfoDao;
import com.bet.orms.AdminInfo;
import com.bet.orms.NewsInfo;
import com.bet.orms.NewsSortInfo;
import com.bet.services.INewsInfoService;
import com.bet.services.INewsSortInfoService;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminNewsAction")
@RequestMapping("/admin")
public class NewsAction extends BaseAdminController
{
	@Autowired
	private INewsSortInfoService newsSortInfoService;
	@Autowired
	private INewsInfoService newsInfoService;

	/**
	 * 添加消息分类接口<br>
	 * 
	 * @param request
	 * @param newsSortInfo
	 *            消息分类信息
	 * @return
	 */
	@RequestMapping(value = "/ajaxAddNewsSortInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddNewsSortInfo(HttpServletRequest request, @ModelAttribute("newsSortInfo") NewsSortInfo newsSortInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			newsSortInfo.setSortId(newsSortInfoService.genId());
			if (StringTools.isNull(newsSortInfo.getParentId()))
			{
				newsSortInfo.setLevelId((byte) 1);
			}
			else
			{
				NewsSortInfo parentSortInfo = newsSortInfoService.getNewsSortInfoBySortId(newsSortInfo.getParentId());
				if (parentSortInfo != null)
				{
					newsSortInfo.setLevelId((byte) (parentSortInfo.getLevelId() + 1));
				}
			}
			newsSortInfo = newsSortInfoService.addNewsSortInfo(newsSortInfo);
			if (newsSortInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加消息分类失败！");
			}
			return toStringReturnInfo("添加消息分类成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新消息分类接口<br>
	 * 
	 * @param request
	 * @param newsSortInfo
	 *            消息分类信息
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateNewsSortInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateNewsSortInfo(HttpServletRequest request, @ModelAttribute("newsSortInfo") NewsSortInfo newsSortInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			if (StringTools.isNull(newsSortInfo.getParentId()))
			{
				newsSortInfo.setLevelId((byte) 1);
			}
			else
			{
				NewsSortInfo parentSortInfo = newsSortInfoService.getNewsSortInfoBySortId(newsSortInfo.getParentId());
				if (parentSortInfo != null)
				{
					newsSortInfo.setLevelId((byte) (parentSortInfo.getLevelId() + 1));
				}
			}
			newsSortInfo = newsSortInfoService.updateNewsSortInfo(newsSortInfo);
			if (newsSortInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新消息分类失败！");
			}
			return toStringReturnInfo("更新消息分类成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取消息分类接口<br>
	 * 
	 * @param request
	 * @param sortId
	 *            消息分类ID
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetNewsSortInfo")
	@ResponseBody
	public ReturnInfo ajaxGetNewsSortInfo(HttpServletRequest request, @RequestParam(value = "sortId", required = true) String sortId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			NewsSortInfo newsSortInfo = newsSortInfoService.getNewsSortInfoBySortId(sortId);
			if (newsSortInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "消息分类ID不存在！");
			}
			return toObjectReturnInfo(newsSortInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取消息分类列表接口<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetNewsSortList")
	@ResponseBody
	public TableData ajaxGetNewsSortList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { INewsSortInfoDao.SORT_ID, INewsSortInfoDao.NAME, INewsSortInfoDao.LEVEL_ID });
			List<NewsSortInfo> list = newsSortInfoService.getNewsSortInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = newsSortInfoService.getNewsSortInfoListCount(searchInfos);
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
	 * 添加消息接口<br>
	 * 
	 * @param request
	 * @param newsInfo
	 *            消息信息
	 * @return
	 */
	@RequestMapping(value = "/ajaxAddNewsInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddNewsInfo(HttpServletRequest request, @ModelAttribute("newsInfo") NewsInfo newsInfo)
	{
		try
		{
			newsInfo.setTitle(HtmlUtils.htmlUnescape(newsInfo.getTitle()));
			newsInfo.setShortContent(HtmlUtils.htmlUnescape(newsInfo.getShortContent()));
			newsInfo.setContent(HtmlUtils.htmlUnescape(newsInfo.getContent()));
			AdminInfo sessionAdminInfo = getAdminSession(request);
			newsInfo.setNewsId(newsInfoService.genId());
			newsInfo.setAuthor(sessionAdminInfo.getUserId());
			newsInfo = newsInfoService.addNewsInfo(newsInfo);
			if (newsInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加消息失败！");
			}
			return toStringReturnInfo("添加消息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新消息接口<br>
	 * 
	 * @param request
	 * @param newsInfo
	 *            消息信息
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateNewsInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateNewsInfo(HttpServletRequest request, @ModelAttribute("newsInfo") NewsInfo newsInfo)
	{
		try
		{
			newsInfo.setTitle(HtmlUtils.htmlUnescape(newsInfo.getTitle()));
			newsInfo.setShortContent(HtmlUtils.htmlUnescape(newsInfo.getShortContent()));
			newsInfo.setContent(HtmlUtils.htmlUnescape(newsInfo.getContent()));
			AdminInfo sessionAdminInfo = getAdminSession(request);
			newsInfo.setAuthor(sessionAdminInfo.getUserId());
			newsInfo = newsInfoService.updateNewsInfo(newsInfo);
			if (newsInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新消息失败！");
			}
			return toStringReturnInfo("更新消息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新消息顶部显示状态接口<br>
	 * 
	 * @param request
	 * @param newsId
	 *            消息ID
	 * @param status
	 *            状态
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateNewsInfoTopStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateNewsInfoTopStatus(HttpServletRequest request, @RequestParam(value = "newsId", required = true) String newsId,
		@RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			NewsInfo newsInfo = newsInfoService.getNewsInfoByNewsId(newsId);
			if (newsInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "消息ID不存在！");
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(INewsInfoDao.TOP_STATUS, status);
			if (!newsInfoService.updateValueByNewsId(newsId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新消息顶部显示状态失败！");
			}
			return toStringReturnInfo("更新消息顶部显示状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新消息状态接口<br>
	 * 
	 * @param request
	 * @param newsId
	 *            消息ID
	 * @param status
	 *            状态
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateNewsInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateNewsInfoStatus(HttpServletRequest request, @RequestParam(value = "newsId", required = true) String newsId, @RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			NewsInfo newsInfo = newsInfoService.getNewsInfoByNewsId(newsId);
			if (newsInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "消息ID不存在！");
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(INewsInfoDao.STATUS, status);
			if (!newsInfoService.updateValueByNewsId(newsId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新消息状态失败！");
			}
			return toStringReturnInfo("更新消息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新消息有效期接口<br>
	 * 
	 * @param request
	 * @param newsId
	 *            消息ID
	 * @param valideDateLong
	 *            有效期
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateNewsInfoValidateDateLong", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateNewsInfoValidateDateLong(HttpServletRequest request, @RequestParam(value = "newsId", required = true) String newsId,
		@RequestParam(value = "valideDateLong", required = true) long valideDateLong)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			NewsInfo newsInfo = newsInfoService.getNewsInfoByNewsId(newsId);
			if (newsInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "消息ID不存在！");
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(INewsInfoDao.VALIDE_DATE_LONG, valideDateLong);
			if (!newsInfoService.updateValueByNewsId(newsId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新消息有效期失败！");
			}
			return toStringReturnInfo("更新消息有效期成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取消息接口<br>
	 * 
	 * @param request
	 * @param newsId
	 *            消息ID
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetNewsInfo")
	@ResponseBody
	public ReturnInfo ajaxGetNewsInfo(HttpServletRequest request, @RequestParam(value = "newsId", required = true) String newsId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			NewsInfo newsInfo = newsInfoService.getNewsInfoByNewsId(newsId);
			if (newsInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "消息ID不存在！");
			}
			return toObjectReturnInfo(newsInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取消息列表接口<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetNewsList")
	@ResponseBody
	public TableData ajaxGetNewsList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { INewsInfoDao.TITLE, INewsInfoDao.AUTHOR, INewsInfoDao.SHORT_CONTENT, INewsInfoDao.CONTENT });
			List<NewsInfo> list = newsInfoService.getNewsInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = newsInfoService.getNewsInfoListCount(searchInfos);
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
