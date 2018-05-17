package com.bet.actions.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.NewsInfo;
import com.bet.orms.NewsSortInfo;
import com.bet.services.INewsInfoService;
import com.bet.services.INewsSortInfoService;

@Controller("adminNewsController")
@RequestMapping("/admin")
public class NewsController extends BaseAdminController
{
	@Autowired
	private INewsSortInfoService newsSortInfoService;
	@Autowired
	private INewsInfoService newsInfoService;

	/**
	 * 消息分类管理页面<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manageNewsSorts", method = RequestMethod.GET)
	public ModelAndView manageNewsSorts(HttpServletRequest request)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/manageNewsSorts");
		return mv;
	}

	/**
	 * 添加消息分类页面<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/newsSortAdd", method = RequestMethod.GET)
	public ModelAndView addNewstSort(HttpServletRequest request)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/newsSortAdd");
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("", "请选择");
		List<NewsSortInfo> newsSortInfoList = newsSortInfoService.getNewsSortInfoList(0, -1, null, null);
		if (newsSortInfoList != null && newsSortInfoList.size() > 0)
		{
			for (NewsSortInfo newsSortInfo : newsSortInfoList)
			{
				modelMap.put(newsSortInfo.getSortId(), newsSortInfo.getName());
			}
		}
		mv.addObject("sortIds", modelMap);
		mv.addObject("newsSortInfo", new NewsSortInfo());
		return mv;
	}

	/**
	 * 修改消息分类页面<br>
	 * 
	 * @param request
	 * @param sortId
	 *            消息分类ID
	 * @return
	 */
	@RequestMapping(value = "/newsSortEdit", method = RequestMethod.GET)
	public ModelAndView newsSortEdit(HttpServletRequest request, @RequestParam(value = "sortId", required = true) String sortId)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/newsSortEdit");
		NewsSortInfo sortInfo = newsSortInfoService.getNewsSortInfoBySortId(sortId);
		if (sortInfo == null)
		{
			throw new RuntimeException("分类ID不存在！");
		}
		mv.addObject("newsSortInfo", sortInfo);
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("", "请选择");
		List<NewsSortInfo> newsSortInfoList = newsSortInfoService.getNewsSortInfoList(0, -1, null, null);
		if (newsSortInfoList != null && newsSortInfoList.size() > 0)
		{
			for (NewsSortInfo newsSortInfo : newsSortInfoList)
			{
				if (!newsSortInfo.getSortId().equals(sortId))
				{
					modelMap.put(newsSortInfo.getSortId(), newsSortInfo.getName());
				}
			}
		}
		mv.addObject("sortIds", modelMap);
		return mv;
	}

	/**
	 * 消息管理页面<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manageNews", method = RequestMethod.GET)
	public ModelAndView manageNews(HttpServletRequest request)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/manageNews");
		return mv;
	}

	/**
	 * 添加消息页面<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/newsAdd", method = RequestMethod.GET)
	public ModelAndView newsAdd(HttpServletRequest request)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/newsAdd");
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("", "请选择");
		List<NewsSortInfo> newsSortInfoList = newsSortInfoService.getNewsSortInfoList(0, -1, null, null);
		if (newsSortInfoList != null && newsSortInfoList.size() > 0)
		{
			for (NewsSortInfo newsSortInfo : newsSortInfoList)
			{
				modelMap.put(newsSortInfo.getSortId(), newsSortInfo.getName());
			}
		}
		mv.addObject("sortIds", modelMap);
		mv.addObject("newsInfo", new NewsInfo());
		return mv;
	}

	/**
	 * 修改消息页面<br>
	 * 
	 * @param request
	 * @param newsId
	 *            消息ID
	 * @return
	 */
	@RequestMapping(value = "/newsEdit", method = RequestMethod.GET)
	public ModelAndView newsEdit(HttpServletRequest request, @RequestParam(value = "newsId", required = true) String newsId)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/newsEdit");
		NewsInfo newsInfo = newsInfoService.getNewsInfoByNewsId(newsId);
		if (newsInfo == null)
		{
			throw new RuntimeException("消息ID不存在！");
		}
		// newsInfo.setContent(HtmlUtils.htmlUnescape(newsInfo.getContent()));
		mv.addObject("newsInfo", newsInfo);
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("", "请选择");
		List<NewsSortInfo> newsSortInfoList = newsSortInfoService.getNewsSortInfoList(0, -1, null, null);
		if (newsSortInfoList != null && newsSortInfoList.size() > 0)
		{
			for (NewsSortInfo newsSortInfo : newsSortInfoList)
			{
				modelMap.put(newsSortInfo.getSortId(), newsSortInfo.getName());
			}
		}
		mv.addObject("sortIds", modelMap);
		return mv;
	}
}
