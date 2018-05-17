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
import com.bet.daos.IPayConfigInfoDao;
import com.bet.orms.PayConfigInfo;
import com.bet.services.IPayConfigInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminPayConfigInfoAction")
@RequestMapping(value = "/admin")
public class PayConfigInfoAction extends BaseAdminController
{
	@Autowired
	private IPayConfigInfoService payConfigInfoService;

	@RequestMapping(value = "/ajaxAddPayConfigInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddPayConfigInfo(HttpServletRequest request, @ModelAttribute("payConfigInfo") PayConfigInfo payConfigInfo)
	{
		try
		{
			payConfigInfo.setPayKey(HtmlUtils.htmlUnescape(payConfigInfo.getPayKey()));
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			payConfigInfo.setConfigId(payConfigInfoService.genId());
			payConfigInfo.setStatus(StatusType.ENABLED.getStatus());
			payConfigInfo = payConfigInfoService.addPayConfigInfo(payConfigInfo);
			if (payConfigInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加支付配置信息失败！");
			}
			return toObjectReturnInfo(payConfigInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdatePayConfigInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdatePayConfigInfo(HttpServletRequest request, @ModelAttribute("payConfigInfo") PayConfigInfo payConfigInfo)
	{
		try
		{
			payConfigInfo.setPayKey(HtmlUtils.htmlUnescape(payConfigInfo.getPayKey()));
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IPayConfigInfoDao.PLATFORM, payConfigInfo.getPlatform());
			valueMap.put(IPayConfigInfoDao.MCH_ID, payConfigInfo.getMchId());
			valueMap.put(IPayConfigInfoDao.PAY_KEY, payConfigInfo.getPayKey());
			valueMap.put(IPayConfigInfoDao.SORT_INDEX, payConfigInfo.getSortIndex());
			valueMap.put(IPayConfigInfoDao.UPDATE_DATE_LONG, current);
			if (!payConfigInfoService.updateValueByConfigId(payConfigInfo.getConfigId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新支付配置信息失败！");
			}
			return toObjectReturnInfo(payConfigInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxUpdatePayConfigInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdatePayConfigInfoStatus(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId,
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
			valueMap.put(IPayConfigInfoDao.STATUS, status);
			valueMap.put(IPayConfigInfoDao.UPDATE_DATE_LONG, current);
			if (!payConfigInfoService.updateValueByConfigId(configId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新支付配置信息状态失败！");
			}
			return toStringReturnInfo("更新支付配置信息状态成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeletePayConfigInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeletePayConfigInfo(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			payConfigInfoService.deletePayConfigInfoByConfigId(configId);
			return toStringReturnInfo("删除支付配置信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetPayConfigInfo")
	@ResponseBody
	public ReturnInfo ajaxGetPayConfigInfo(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			PayConfigInfo payConfigInfo = payConfigInfoService.getPayConfigInfoByConfigId(configId);
			if (payConfigInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "支付配置信息不存在！");
			}
			return toObjectReturnInfo(payConfigInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetPayConfigInfoList")
	@ResponseBody
	public TableData ajaxGetPayConfigInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IPayConfigInfoDao.CONFIG_ID, IPayConfigInfoDao.MCH_ID, IPayConfigInfoDao.STATUS });
			List<PayConfigInfo> list = payConfigInfoService.getPayConfigInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = payConfigInfoService.getPayConfigInfoListCount(searchInfos);
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
