package com.bet.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.bet.daos.IUserBalanceLogInfoDao;
import com.bet.daos.IUserInfoDao;
import com.bet.orms.SmsCodeConfigInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.ConstValues;
import com.lrcall.common.utils.GsonTools;
import com.lrcall.common.utils.LogTools;
import com.lrcall.lrweb.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

public class BaseAction
{
	protected static final LogTools log = LogTools.getInstance();

	/**
	 * 获取客户端IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request)
	{
		try
		{
			String ip = request.getRemoteAddr();
			if (StringTools.isNull(ip) || ip.equals(ConstValues.LOCAL_IP))
			{
				if (!StringTools.isNull(request.getHeader("X-Real-IP")))
				{
					ip = request.getHeader("X-Real-IP");
				}
				else
				{
					ip = ConstValues.LOCAL_IP;
				}
			}
			return ip;
		}
		catch (Exception e)
		{
			return "127.0.0.1";
		}
	}

	/**
	 * 获取代理信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserAgent(HttpServletRequest request)
	{
		try
		{
			String userAgent = request.getHeader("User-Agent");
			return userAgent;
		}
		catch (Exception e)
		{
			return "未知";
		}
	}

	/**
	 * 判断是否是移动客户端
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isMobile(HttpServletRequest request)
	{
		String userAgent = getUserAgent(request);
		System.out.println("浏览器代理信息：" + userAgent);
		if (userAgent == null)
		{
			return false;
		}
		// if (userAgent.matches("/AppleWebKit.*Mobile.*/"))// 移动终端
		// {
		// return true;
		// }
		// if (userAgent.matches("/(i[^;]+;( U;)? CPU.+Mac OS X/"))// ios终端
		// {
		// return true;
		// }
		if (userAgent.indexOf("Mobile") > -1)// 移动终端
		{
			return true;
		}
		if (userAgent.indexOf("Android") > -1 || userAgent.indexOf("Linux") > -1)// android终端或者uc浏览器
		{
			return true;
		}
		if (userAgent.indexOf("iPhone") > -1 || userAgent.indexOf("iPad") > -1)// iPhone、iPad或者QQHD浏览器
		{
			return true;
		}
		if (userAgent.indexOf("MicroMessenger") > -1 || userAgent.indexOf("QQ") > -1)// 微信或QQ
		{
			return true;
		}
		return false;
	}

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder)
	{
		/**
		 * 自动转换日期类型的字段格式
		 */
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		/**
		 * 防止XSS攻击
		 */
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
	}

	/**
	 * 获取Session
	 * 
	 * @param request
	 * @return
	 */
	protected Object getSession(HttpServletRequest request, String name)
	{
		return (request.getSession()).getAttribute(name);
	}

	/**
	 * 设置Session
	 * 
	 * @param request
	 * @param userInfo
	 */
	protected void setSession(HttpServletRequest request, String name, Object object)
	{
		(request.getSession()).setAttribute(name, object);
	}

	/**
	 * 正常转化为ReturnInfo
	 * 
	 * @param object
	 *            对象
	 * @return ReturnInfo
	 */
	public static ReturnInfo toObjectReturnInfo(Object object)
	{
		return new ReturnInfo(ErrorInfo.SUCCESS, GsonTools.toJson(object));
	}

	/**
	 * 正常的String类型转化为ReturnInfo
	 * 
	 * @param msg
	 *            输出信息
	 * @return ReturnInfo
	 */
	public static ReturnInfo toStringReturnInfo(String msg)
	{
		return new ReturnInfo(ErrorInfo.SUCCESS, msg);
	}

	/**
	 * 异常转化为ReturnInfo
	 * 
	 * @param e
	 *            业务抛出的异常
	 * @return ReturnInfo
	 */
	public static ReturnInfo toExceptionReturnInfo(Exception e)
	{
		String msg = e.getMessage();
		if (e.getCause() != null)
		{
			msg += ":" + e.getCause().getMessage();
		}
		if (e instanceof HibernateJsonResultException)
		{
			return GsonTools.getReturnInfo(msg);
		}
		else
		{
			LogTools.getInstance().error("toExceptionReturnInfo", msg);
			return new ReturnInfo(ErrorInfo.UNKNOWN_ERROR, "未知错误：" + msg);
		}
	}

	public static boolean buildTable(HttpServletRequest request, Map<String, Object> tableMap, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, String[] params, String[] CONSTANTS)
	{
		if (!StringTools.isNull((String) tableMap.get(TableTools.ORDER_COLUMN_NAME)))
		{
			if (orderInfos != null)
			{
				orderInfos.add(new TableOrderInfo(10, (String) tableMap.get(TableTools.ORDER_COLUMN_NAME), (String) tableMap.get(TableTools.ORDER_DIR)));
			}
		}
		List<String> valueList = new ArrayList<>();
		boolean advancedSearch = false;
		for (String param : params)
		{
			String value = request.getParameter(param);
			if (!StringTools.isNull(value))
			{
				advancedSearch = true;
			}
			valueList.add(value);
		}
		if (!advancedSearch) // 如果是模糊搜索
		{
			String searchValue = (String) tableMap.get(TableTools.SEARCH_VALUE);
			if (!StringTools.isNull(searchValue))
			{
				for (int i = 0; i < CONSTANTS.length; i++)
				{
					searchInfos.add(new TableSearchInfo(CONSTANTS[i], searchValue, false, true));
				}
			}
		}
		else// 如果是高级搜索，搜索具体的某项值
		{
			for (int i = 0; i < CONSTANTS.length; i++)
			{
				if (!StringTools.isNull(valueList.get(i)))
				{
					boolean isFullMatch = false;
					if (CONSTANTS[i].equals("status") || CONSTANTS[i].equals(IUserInfoDao.USER_TYPE) || CONSTANTS[i].equals(IUserBalanceLogInfoDao.MONEY_UNIT))
					{
						isFullMatch = true;
					}
					searchInfos.add(new TableSearchInfo(CONSTANTS[i], valueList.get(i), isFullMatch, false));
				}
			}
		}
		return advancedSearch;
	}

	public static boolean buildTable(HttpServletRequest request, Map<String, Object> tableMap, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos, String[] CONSTANTS)
	{
		String[] params = new String[CONSTANTS.length];
		for (int i = 0; i < CONSTANTS.length; i++)
		{
			params[i] = "s" + StringTools.getClassName(CONSTANTS[i]);
		}
		return buildTable(request, tableMap, orderInfos, searchInfos, params, CONSTANTS);
	}

	public static String sendSmsMsg(SmsCodeConfigInfo smsCodeConfigInfo, String number, String content) throws IOException
	{
		if (StringTools.isChinaMobilePhoneNumber(number))
		{
			number = "86" + number;
		}
		String host = smsCodeConfigInfo.getUrl() + "?";
		Map<String, String> parmas = new HashMap<>();
		// parmas.put("access_key", "c81e728d9d4c2f63");
		// parmas.put("mobile", "8613800138000");
		// parmas.put("content", "您的验证码是1234，请查收！");
		parmas.put("access_key", smsCodeConfigInfo.getAppKey());
		parmas.put("mobile", number);
		parmas.put("content", content);
		List<String> request = new ArrayList<>();
		for (String st : parmas.keySet())
		{
			request.add(URLEncoder.encode(st, "UTF-8") + "=" + URLEncoder.encode(parmas.get(st), "UTF-8"));
		}
		String strUrl = host;
		for (int i = 0; i < request.size(); i++)
		{
			if (i > 0)
			{
				strUrl += "&";
			}
			strUrl += request.get(i);
		}
		URL url = new URL(strUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String lines;
		String result = "";
		while ((lines = reader.readLine()) != null)
		{
			result = result + lines;
		}
		reader.close();
		System.out.println(result);
		return result;
	}
}
