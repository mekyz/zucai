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
import com.bet.daos.IAdviceInfoDao;
import com.bet.daos.IAdviceTypeInfoDao;
import com.bet.orms.AdminInfo;
import com.bet.orms.AdviceInfo;
import com.bet.orms.AdviceReplyInfo;
import com.bet.orms.AdviceTypeInfo;
import com.bet.services.IAdviceInfoService;
import com.bet.services.IAdviceReplyInfoService;
import com.bet.services.IAdviceTypeInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminAdviceAction")
@RequestMapping("/admin")
public class AdviceAction extends BaseAdminController
{
	@Autowired
	private IAdviceTypeInfoService adviceTypeInfoService;
	@Autowired
	private IAdviceInfoService adviceInfoService;
	@Autowired
	private IAdviceReplyInfoService adviceReplyInfoService;

	@RequestMapping(value = "/ajaxAddAdviceTypeInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddAdviceTypeInfo(HttpServletRequest request, @ModelAttribute("adviceTypeInfo") AdviceTypeInfo adviceTypeInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			adviceTypeInfo.setTypeId(adviceTypeInfoService.genId());
			adviceTypeInfo = adviceTypeInfoService.addAdviceTypeInfo(adviceTypeInfo);
			if (adviceTypeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加意见反馈类型信息失败！");
			}
			return toObjectReturnInfo(adviceTypeInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateAdviceTypeInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateAdviceTypeInfo(HttpServletRequest request, @ModelAttribute("adviceTypeInfo") AdviceTypeInfo adviceTypeInfo)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IAdviceTypeInfoDao.NAME, adviceTypeInfo.getName());
			valueMap.put(IAdviceTypeInfoDao.UPDATE_DATE_LONG, current);
			if (!adviceTypeInfoService.updateValueByTypeId(adviceTypeInfo.getTypeId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新意见反馈类型信息失败！");
			}
			adviceTypeInfo = adviceTypeInfoService.getAdviceTypeInfoByTypeId(adviceTypeInfo.getTypeId());
			return toObjectReturnInfo(adviceTypeInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateAdviceTypeInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateAdviceTypeInfoStatus(HttpServletRequest request, @RequestParam(value = "typeId", required = true) String typeId,
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
			valueMap.put(IAdviceTypeInfoDao.STATUS, status);
			valueMap.put(IAdviceTypeInfoDao.UPDATE_DATE_LONG, current);
			if (!adviceTypeInfoService.updateValueByTypeId(typeId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新意见反馈类型信息失败！");
			}
			return toStringReturnInfo("更新反馈类型状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteAdviceTypeInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteAdviceTypeInfo(HttpServletRequest request, @RequestParam(value = "typeId", required = true) String typeId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			adviceTypeInfoService.deleteAdviceTypeInfoByTypeId(typeId);
			return toStringReturnInfo("删除意见反馈类型信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetAdviceTypeInfo")
	@ResponseBody
	public ReturnInfo ajaxGetAdviceTypeInfo(HttpServletRequest request, @RequestParam(value = "typeId", required = true) String typeId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			AdviceTypeInfo adviceTypeInfo = adviceTypeInfoService.getAdviceTypeInfoByTypeId(typeId);
			if (adviceTypeInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "意见反馈类型信息不存在！");
			}
			return toObjectReturnInfo(adviceTypeInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetAdviceTypeInfoList")
	@ResponseBody
	public TableData ajaxGetAdviceTypeInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IAdviceTypeInfoDao.TYPE_ID, IAdviceTypeInfoDao.NAME, IAdviceTypeInfoDao.STATUS });
			List<AdviceTypeInfo> list = adviceTypeInfoService.getAdviceTypeInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = adviceTypeInfoService.getAdviceTypeInfoListCount(searchInfos);
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
	 * 获取意见反馈信息接口<br>
	 * 
	 * @param request
	 * @param adviceId
	 *            意见反馈ID
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetAdviceInfo")
	@ResponseBody
	public ReturnInfo ajaxGetAdviceInfo(HttpServletRequest request, @RequestParam(value = "adviceId", required = true) String adviceId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			AdviceInfo adviceInfo = adviceInfoService.getAdviceInfoByAdviceId(adviceId);
			if (adviceInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "反馈不存在！");
			}
			return toObjectReturnInfo(adviceInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdateAdviceInfoReadStatus")
	@ResponseBody
	public ReturnInfo ajaxUpdateAdviceInfoReadStatus(HttpServletRequest request, @RequestParam(value = "adviceId", required = true) String adviceId)
	{
		try
		{
			AdminInfo sessionAdminInfo = getAdminSession(request);
			AdviceInfo adviceInfo = adviceInfoService.getAdviceInfoByAdviceId(adviceId);
			if (adviceInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "反馈不存在！");
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IAdviceInfoDao.READ_USER_ID, sessionAdminInfo.getUserId());
			valueMap.put(IAdviceInfoDao.READ_STATUS, StatusType.ENABLED.getStatus());
			if (!adviceInfoService.updateValueByAdviceId(adviceId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新意见反馈阅读状态失败！");
			}
			return toStringReturnInfo("更新意见反馈阅读状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取意见反馈列表接口<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetAdviceList")
	@ResponseBody
	public TableData ajaxGetAdviceList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			buildTable(request, tableMap, orderInfos, searchInfos,
				new String[] { IAdviceInfoDao.ADVICE_ID, IAdviceInfoDao.PLATFORM, IAdviceInfoDao.ADVICE_TYPE, IAdviceInfoDao.USER_ID, IAdviceInfoDao.NUMBER, IAdviceInfoDao.EMAIL,
					IAdviceInfoDao.CONTENT, IAdviceInfoDao.READ_USER_ID, IAdviceInfoDao.REPLY_USER_ID, IAdviceInfoDao.STATUS, IAdviceInfoDao.READ_STATUS, IAdviceInfoDao.REPLY_STATUS });
			List<AdviceInfo> list = adviceInfoService.getAdviceInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = adviceInfoService.getAdviceInfoListCount(searchInfos);
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
	 * 添加意见反馈回复
	 * 
	 * @param request
	 * @param adviceReplyInfo
	 *            回复信息
	 * @return
	 */
	@RequestMapping(value = "/ajaxAddReplyAdviceInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddReplyAdviceInfo(HttpServletRequest request, @ModelAttribute("adviceReplyInfo") AdviceReplyInfo adviceReplyInfo)
	{
		try
		{
			adviceReplyInfo.setContent(HtmlUtils.htmlUnescape(adviceReplyInfo.getContent()));
			AdminInfo sessionAdminInfo = getAdminSession(request);
			AdviceInfo adviceInfo = adviceInfoService.getAdviceInfoByAdviceId(adviceReplyInfo.getAdviceId());
			if (adviceInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "反馈不存在！");
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IAdviceInfoDao.REPLY_USER_ID, sessionAdminInfo.getUserId());
			valueMap.put(IAdviceInfoDao.REPLY_STATUS, StatusType.ENABLED.getStatus());
			if (!adviceInfoService.updateValueByAdviceId(adviceReplyInfo.getAdviceId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新意见反馈回复状态失败！");
			}
			adviceReplyInfo.setReplyId(adviceReplyInfoService.genId());
			adviceReplyInfo.setStatus(StatusType.ENABLED.getStatus());
			if (adviceReplyInfoService.addAdviceReplyInfo(adviceReplyInfo) == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "回复失败！");
			}
			return toObjectReturnInfo(adviceReplyInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
