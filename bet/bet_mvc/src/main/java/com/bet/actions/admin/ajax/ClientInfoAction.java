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
import com.bet.daos.IClientInfoDao;
import com.bet.orms.ClientInfo;
import com.bet.services.IClientInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminClientInfoAction")
@RequestMapping(value = "/admin")
public class ClientInfoAction extends BaseAdminController
{
	@Autowired
	private IClientInfoService clientInfoService;

	@RequestMapping(value = "/ajaxAddClientInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddClientInfo(HttpServletRequest request, @ModelAttribute("clientInfo") ClientInfo clientInfo)
	{
		try
		{
			clientInfo.setUrl(HtmlUtils.htmlUnescape(clientInfo.getUrl()));
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			clientInfo.setStatus(StatusType.ENABLED.getStatus());
			clientInfo = clientInfoService.addClientInfo(clientInfo);
			if (clientInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加客户端信息失败！");
			}
			return toObjectReturnInfo(clientInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateClientInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateClientInfo(HttpServletRequest request, @ModelAttribute("clientInfo") ClientInfo clientInfo)
	{
		try
		{
			clientInfo.setUrl(HtmlUtils.htmlUnescape(clientInfo.getUrl()));
			clientInfo.setContent(HtmlUtils.htmlUnescape(clientInfo.getContent()));
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IClientInfoDao.PLATFORM, clientInfo.getPlatform());
			valueMap.put(IClientInfoDao.AGENT_ID, clientInfo.getAgentId());
			valueMap.put(IClientInfoDao.VERSION_NAME, clientInfo.getVersionName());
			valueMap.put(IClientInfoDao.VERSION_CODE, clientInfo.getVersionCode());
			valueMap.put(IClientInfoDao.DEBUG_SIGN_KEY, clientInfo.getDebugSignKey());
			valueMap.put(IClientInfoDao.RELEASE_SIGN_KEY, clientInfo.getReleaseSignKey());
			valueMap.put(IClientInfoDao.URL, clientInfo.getUrl());
			valueMap.put(IClientInfoDao.CONTENT, clientInfo.getContent());
			valueMap.put(IClientInfoDao.UPDATE_DATE_LONG, current);
			if (!clientInfoService.updateValueById(clientInfo.getId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新客户端信息失败！");
			}
			clientInfo = clientInfoService.getClientInfoById(clientInfo.getId());
			return toObjectReturnInfo(clientInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateClientInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateClientInfoStatus(HttpServletRequest request, @RequestParam(value = "id", required = true) int id, @RequestParam(value = "status", required = true) byte status)
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
			valueMap.put(IClientInfoDao.STATUS, status);
			valueMap.put(IClientInfoDao.UPDATE_DATE_LONG, current);
			if (!clientInfoService.updateValueById(id, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新客户端信息状态失败！");
			}
			return toStringReturnInfo("更新客户端信息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteClientInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteClientInfo(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			clientInfoService.deleteClientInfoById(id);
			return toStringReturnInfo("删除客户端信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetClientInfo")
	@ResponseBody
	public ReturnInfo ajaxGetClientInfo(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			ClientInfo clientInfo = clientInfoService.getClientInfoById(id);
			if (clientInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "客户端信息不存在！");
			}
			return toObjectReturnInfo(clientInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetClientInfoList")
	@ResponseBody
	public TableData ajaxGetClientInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			buildTable(request, tableMap, orderInfos, searchInfos,
				new String[] { IClientInfoDao.PLATFORM, IClientInfoDao.AGENT_ID, IClientInfoDao.STATUS, IClientInfoDao.VERSION_NAME, IClientInfoDao.VERSION_CODE });
			List<ClientInfo> list = clientInfoService.getClientInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = clientInfoService.getClientInfoListCount(searchInfos);
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
