package com.bet.actions.user.ajax;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.user.BaseUserController;
import com.bet.orms.UserInfo;
import com.bet.services.IConfigInfoService;
import com.bet.utils.BetConstValues;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.utils.UrlFilter;

@Controller("userConfigAction")
@RequestMapping(value = "/user")
public class ConfigAction extends BaseUserController
{
	@Autowired
	private IConfigInfoService configInfoService;

	/**
	 * 用户分享信息接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxUserShareApp")
	@ResponseBody
	public ReturnInfo ajaxUserShareApp(HttpServletRequest request)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			String text = configInfoService.getValue(BetConstValues.CONFIG_SHARE_TEXT);
			String serverUrl = configInfoService.getServerUrl();
			Map<String, String> replaceStrs = new HashMap<>();
			replaceStrs.put("{serverUrl}", serverUrl);
			if (sessionUserInfo != null)
			{
				replaceStrs.put("{userId}", sessionUserInfo.getUserId());
			}
			text = UrlFilter.filter(text, replaceStrs);
			return toStringReturnInfo(text);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
