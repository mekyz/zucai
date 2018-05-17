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
import com.bet.daos.IMsgInfoDao;
import com.bet.orms.MsgInfo;
import com.bet.services.IMsgInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminMsgInfoAction")
@RequestMapping(value = "/admin")
public class MsgInfoAction extends BaseAdminController
{
	@Autowired
	private IMsgInfoService msgInfoService;

	@RequestMapping(value = "/ajaxAddMsgInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddMsgInfo(HttpServletRequest request, @ModelAttribute("msgInfo") MsgInfo msgInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			msgInfo.setMsgId(msgInfoService.genId());
			msgInfo.setStatus(StatusType.ENABLED.getStatus());
			msgInfo = msgInfoService.addMsgInfo(msgInfo);
			if (msgInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加消息信息失败！");
			}
			return toObjectReturnInfo(msgInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateMsgInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateMsgInfo(HttpServletRequest request, @ModelAttribute("msgInfo") MsgInfo msgInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IMsgInfoDao.SORT_ID, msgInfo.getSortId());
			valueMap.put(IMsgInfoDao.TITLE, msgInfo.getTitle());
			valueMap.put(IMsgInfoDao.USER_ID, msgInfo.getUserId());
			valueMap.put(IMsgInfoDao.SHORT_CONTENT, msgInfo.getShortContent());
			valueMap.put(IMsgInfoDao.UPDATE_DATE_LONG, current);
			if (!msgInfoService.updateValueByMsgId(msgInfo.getMsgId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新消息信息失败！");
			}
			return toObjectReturnInfo(msgInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateMsgInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateMsgInfoStatus(HttpServletRequest request, @RequestParam(value = "msgId", required = true) String msgId, @RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IMsgInfoDao.STATUS, status);
			valueMap.put(IMsgInfoDao.UPDATE_DATE_LONG, current);
			if (!msgInfoService.updateValueByMsgId(msgId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新消息信息状态失败！");
			}
			return toStringReturnInfo("更新消息信息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteMsgInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteMsgInfo(HttpServletRequest request, @RequestParam(value = "msgId", required = true) String msgId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			msgInfoService.deleteMsgInfoByMsgId(msgId);
			return toStringReturnInfo("删除消息信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMsgInfo")
	@ResponseBody
	public ReturnInfo ajaxGetMsgInfo(HttpServletRequest request, @RequestParam(value = "msgId", required = true) String msgId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			MsgInfo msgInfo = msgInfoService.getMsgInfoByMsgId(msgId);
			if (msgInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "消息信息不存在！");
			}
			return toObjectReturnInfo(msgInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMsgInfoList")
	@ResponseBody
	public TableData ajaxGetMsgInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IMsgInfoDao.MSG_ID, IMsgInfoDao.USER_ID, IMsgInfoDao.SORT_ID, IMsgInfoDao.STATUS });
			List<MsgInfo> list = msgInfoService.getMsgInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = msgInfoService.getMsgInfoListCount(searchInfos);
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
