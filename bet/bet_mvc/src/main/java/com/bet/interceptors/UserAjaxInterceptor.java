package com.bet.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.bet.orms.UserInfo;
import com.bet.services.IApiRequestLogInfoService;
import com.bet.services.IConfigInfoService;
import com.bet.services.IUserInfoService;
import com.bet.utils.BetConstValues;
import com.bet.utils.SessionManage.SessionUser;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;

public class UserAjaxInterceptor extends BaseInterceptor
{
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	public IConfigInfoService configInfoService;
	@Autowired
	public IApiRequestLogInfoService apiRequestLogInfoService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception
	{
		if (configInfoService.getBooleanValue(BetConstValues.CONFIG_ADMIN_MAINTAIN))
		{
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(new ReturnInfo(ErrorInfo.PASSWORD_ERROR, "系统正在维护！").toString());
			response.getWriter().close();
			return false;
		}
		String ver = request.getParameter("v");
		Integer v = null;
		try
		{
			v = Integer.parseInt(ver);
		}
		catch (Exception e)
		{
		}
		if (v != null && v < configInfoService.getIntValue(BetConstValues.CONFIG_API_VERSION))
		{
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(new ReturnInfo(ErrorInfo.FORBIDDEN_ERROR, BetConstValues.API_LOW).toString());
			response.getWriter().close();
			return false;
		}
		// try
		// {
		// String userId = request.getParameter("userId");
		// String appAgentId = request.getParameter("appAgentId");
		// String platform = request.getParameter("platform");
		// String deviceName = request.getParameter("deviceName");
		// String sysVersion = request.getParameter("sysVersion");
		// String versionCode = request.getParameter("versionCode");
		// String sign = request.getParameter("sign");
		// String sessionId = request.getParameter("sessionId");
		// List<ParamInfo> paramInfoList = new ArrayList<>();
		// Map<String, String[]> paramMap = request.getParameterMap();
		// if (paramMap != null)
		// {
		// for (String param : paramMap.keySet())
		// {
		// String[] strs = paramMap.get(param);
		// String value = "";
		// int index = 0;
		// for (String str : strs)
		// {
		// if (index == 0)
		// {
		// value += str;
		// }
		// else
		// {
		// value += ";;;;" + str;
		// }
		// index++;
		// }
		// paramInfoList.add(new ParamInfo(param, value));
		// }
		// }
		// long current = System.currentTimeMillis();
		// apiRequestLogInfoService.addApiRequestLogInfo(new ApiRequestLogInfo(userId, BaseUserController.getClientIp(request), request.getRequestURI(), appAgentId, platform, deviceName, sysVersion,
		// versionCode, sign, ver, sessionId, GsonTools.toJson(paramInfoList), current));
		// String paramsInfo = String.format("userId:%s,appPartnerId:%s,platform:%s,deviceName:%s,sysVersion:%s,versionCode:%s,sign:%s,v:%s,sessionId:%s", request.getParameter("userId"),
		// request.getParameter("appPartnerId"), request.getParameter("platform"), request.getParameter("deviceName"), request.getParameter("sysVersion"), request.getParameter("versionCode"),
		// request.getParameter("sign"), request.getParameter("v"), request.getParameter("sessionId"));
		// log.info("preHandle", String.format("contextPath:%s,paramsInfo:[%s].", request.getRequestURI(), paramsInfo));
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// }
		// 判断用户是否已登录
		UserInfo userInfo = (UserInfo) (request.getSession()).getAttribute(SessionUser.USER_INFO.getName());
		if (userInfo == null)
		{
			// 如果没登录，再检查是否携带了登录session
			try
			{
				String userId = request.getParameter("userId");
				String sessionId = request.getParameter("sessionId");
				userInfo = userInfoService.loginBySession(userId, sessionId);
				if (userInfo == null)
				{
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(new ReturnInfo(ErrorInfo.PASSWORD_ERROR, "请先登录！").toString());
					response.getWriter().close();
					return false;
				}
				if (userInfo.getStatus() != StatusType.ENABLED.getStatus())
				{
					String kefuNumber = configInfoService.getValue(BetConstValues.CONFIG_KEFU_NUMBER);
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(new ReturnInfo(ErrorInfo.PASSWORD_ERROR, "账号已被禁用，如有疑问请联系客服，电话：" + kefuNumber + "！").toString());
					response.getWriter().close();
					return false;
				}
				(request.getSession()).setAttribute(SessionUser.USER_INFO.getName(), userInfo);
				return true;
			}
			catch (Exception e)
			{
				// e.printStackTrace();
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(new ReturnInfo(ErrorInfo.PASSWORD_ERROR, "请先登录！").toString());
				response.getWriter().close();
				return false;
			}
		}
		return true;
	}
}
