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
import com.bet.daos.ISmsCodeConfigInfoDao;
import com.bet.enums.AdminType;
import com.bet.orms.AdminInfo;
import com.bet.orms.SmsCodeConfigInfo;
import com.bet.services.ISmsCodeConfigInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminSmsCodeConfigAction")
@RequestMapping(value = "/admin")
public class SmsCodeConfigAction extends BaseAdminController
{
	@Autowired
	private ISmsCodeConfigInfoService smsCodeConfigInfoService;

	/**
	 * 添加短信验证码配置接口<br>
	 * 
	 * @param request
	 * @param smsCodeConfigInfo
	 *            短信验证码配置信息
	 * @return
	 */
	@RequestMapping(value = "/ajaxAddSmsCodeConfigInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddSmsCodeConfigInfo(HttpServletRequest request, @ModelAttribute("smsCodeConfigInfo") SmsCodeConfigInfo smsCodeConfigInfo)
	{
		try
		{
			smsCodeConfigInfo.setUrl(HtmlUtils.htmlUnescape(smsCodeConfigInfo.getUrl()));
			smsCodeConfigInfo.setTopic(HtmlUtils.htmlUnescape(smsCodeConfigInfo.getTopic()));
			smsCodeConfigInfo.setSignName(HtmlUtils.htmlUnescape(smsCodeConfigInfo.getSignName()));
			smsCodeConfigInfo.setSmsParams(HtmlUtils.htmlUnescape(smsCodeConfigInfo.getSmsParams()));
			smsCodeConfigInfo.setMnsEndpoint(HtmlUtils.htmlUnescape(smsCodeConfigInfo.getMnsEndpoint()));
			smsCodeConfigInfo.setRemark(HtmlUtils.htmlUnescape(smsCodeConfigInfo.getRemark()));
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			smsCodeConfigInfo.setConfigId(smsCodeConfigInfoService.genId());
			smsCodeConfigInfo = smsCodeConfigInfoService.addSmsCodeConfigInfo(smsCodeConfigInfo);
			if (smsCodeConfigInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加验证码配置失败！");
			}
			return toObjectReturnInfo(smsCodeConfigInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新短信验证码配置接口<br>
	 * 
	 * @param request
	 * @param smsCodeConfigInfo
	 *            短信验证码配置信息
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateSmsCodeConfigInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateSmsCodeConfigInfo(HttpServletRequest request, @ModelAttribute("smsCodeConfigInfo") SmsCodeConfigInfo smsCodeConfigInfo)
	{
		try
		{
			smsCodeConfigInfo.setUrl(HtmlUtils.htmlUnescape(smsCodeConfigInfo.getUrl()));
			smsCodeConfigInfo.setTopic(HtmlUtils.htmlUnescape(smsCodeConfigInfo.getTopic()));
			smsCodeConfigInfo.setSignName(HtmlUtils.htmlUnescape(smsCodeConfigInfo.getSignName()));
			smsCodeConfigInfo.setSmsParams(HtmlUtils.htmlUnescape(smsCodeConfigInfo.getSmsParams()));
			smsCodeConfigInfo.setMnsEndpoint(HtmlUtils.htmlUnescape(smsCodeConfigInfo.getMnsEndpoint()));
			smsCodeConfigInfo.setRemark(HtmlUtils.htmlUnescape(smsCodeConfigInfo.getRemark()));
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(ISmsCodeConfigInfoDao.NAME, smsCodeConfigInfo.getName());
			valueMap.put(ISmsCodeConfigInfoDao.PLATFORM, smsCodeConfigInfo.getPlatform());
			valueMap.put(ISmsCodeConfigInfoDao.URL, smsCodeConfigInfo.getUrl());
			valueMap.put(ISmsCodeConfigInfoDao.TOPIC, smsCodeConfigInfo.getTopic());
			valueMap.put(ISmsCodeConfigInfoDao.APP_KEY, smsCodeConfigInfo.getAppKey());
			valueMap.put(ISmsCodeConfigInfoDao.APP_SECRET, smsCodeConfigInfo.getAppSecret());
			valueMap.put(ISmsCodeConfigInfoDao.SMS_TYPE, smsCodeConfigInfo.getSmsType());
			valueMap.put(ISmsCodeConfigInfoDao.SIGN_NAME, smsCodeConfigInfo.getSignName());
			valueMap.put(ISmsCodeConfigInfoDao.SMS_PARAMS, smsCodeConfigInfo.getSmsParams());
			valueMap.put(ISmsCodeConfigInfoDao.SMS_TEMPLATE_CODE, smsCodeConfigInfo.getSmsTemplateCode());
			valueMap.put(ISmsCodeConfigInfoDao.MNS_ENDPOINT, smsCodeConfigInfo.getMnsEndpoint());
			valueMap.put(ISmsCodeConfigInfoDao.SORT_INDEX, smsCodeConfigInfo.getSortIndex());
			valueMap.put(ISmsCodeConfigInfoDao.VALIDATE_SECONDS, smsCodeConfigInfo.getValidateSeconds());
			valueMap.put(ISmsCodeConfigInfoDao.REMARK, smsCodeConfigInfo.getRemark());
			valueMap.put(ISmsCodeConfigInfoDao.UPDATE_DATE_LONG, current);
			if (!smsCodeConfigInfoService.updateValueByConfigId(smsCodeConfigInfo.getConfigId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新验证码配置失败！");
			}
			smsCodeConfigInfo = smsCodeConfigInfoService.getSmsCodeConfigInfoByConfigId(smsCodeConfigInfo.getConfigId());
			return toObjectReturnInfo(smsCodeConfigInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新短信验证码配置状态接口<br>
	 * 
	 * @param request
	 * @param configId
	 *            配置ID
	 * @param status
	 *            状态
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateSmsCodeConfigInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateSmsCodeConfigInfoStatus(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId,
		@RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			SmsCodeConfigInfo smsCodeConfigInfo = smsCodeConfigInfoService.getSmsCodeConfigInfoByConfigId(configId);
			if (smsCodeConfigInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "验证码配置不存在！");
			}
			if (status != StatusType.ENABLED.getStatus())
			{
				status = StatusType.DISABLED.getStatus();
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(ISmsCodeConfigInfoDao.STATUS, status);
			if (!smsCodeConfigInfoService.updateValueByConfigId(configId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新验证码配置状态失败！");
			}
			smsCodeConfigInfo = smsCodeConfigInfoService.getSmsCodeConfigInfoByConfigId(configId);
			return toObjectReturnInfo(smsCodeConfigInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 删除短信验证码配置接口<br>
	 * 
	 * @param request
	 * @param configId
	 *            配置ID
	 * @return
	 */
	@RequestMapping(value = "/ajaxDeleteSmsCodeConfigInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteSmsCodeConfigInfo(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId)
	{
		try
		{
			AdminInfo sessionAdminInfo = getAdminSession(request);
			if (sessionAdminInfo.getUserLevel() != AdminType.ADMIN.getType())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "您无权限操作！");
			}
			smsCodeConfigInfoService.deleteSmsCodeConfigInfoByConfigId(configId);
			return toStringReturnInfo("删除验证码配置成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取短信验证码配置接口<br>
	 * 
	 * @param request
	 * @param configId
	 *            配置ID
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetSmsCodeConfigInfo")
	@ResponseBody
	public ReturnInfo ajaxGetSmsCodeConfigInfo(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			SmsCodeConfigInfo smsCodeConfigInfo = smsCodeConfigInfoService.getSmsCodeConfigInfoByConfigId(configId);
			if (smsCodeConfigInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "验证码配置不存在！");
			}
			return toObjectReturnInfo(smsCodeConfigInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取短信验证码配置列表接口<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetSmsCodeConfigInfoList")
	@ResponseBody
	public TableData ajaxGetSmsCodeConfigInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { ISmsCodeConfigInfoDao.CONFIG_ID, ISmsCodeConfigInfoDao.PLATFORM });
			List<SmsCodeConfigInfo> list = smsCodeConfigInfoService.getSmsCodeConfigInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = smsCodeConfigInfoService.getSmsCodeConfigInfoListCount(searchInfos);
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
