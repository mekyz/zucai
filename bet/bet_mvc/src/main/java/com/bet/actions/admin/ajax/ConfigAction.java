package com.bet.actions.admin.ajax;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.bet.actions.admin.BaseAdminController;
import com.bet.orms.ConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;

@Controller("adminConfigAction")
@RequestMapping("/admin")
public class ConfigAction extends BaseAdminController
{
	/**
	 * 更新参数接口<br>
	 * 
	 * @param request
	 * @param configId
	 *            参数ID
	 * @param content
	 *            参数值
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateConfig", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateConfig(HttpServletRequest request, @RequestParam(value = "configId", required = true) String configId, @RequestParam(value = "content", required = true) String content)
	{
		try
		{
			content = HtmlUtils.htmlUnescape(content);
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			ConfigInfo configInfo = configInfoService.getConfigInfoByConfigId(configId);
			if (configInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "配置不存在！");
			}
			configInfo.setContent(content);
			configInfo = configInfoService.updateConfigInfo(configInfo);
			if (configInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新失败！");
			}
			return toStringReturnInfo("更新成功！");
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
