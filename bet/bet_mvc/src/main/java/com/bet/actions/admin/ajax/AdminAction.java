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
import com.bet.daos.IAdminInfoDao;
import com.bet.enums.AdminType;
import com.bet.orms.AdminInfo;
import com.bet.services.IAdminInfoService;
import com.bet.utils.AdminCryptoTools;
import com.bet.utils.BetConstValues;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminAdminAction")
@RequestMapping("/admin")
public class AdminAction extends BaseAdminController
{
	@Autowired
	private IAdminInfoService adminInfoService;

	/**
	 * 管理员登录接口<br>
	 * 
	 * @param request
	 * @param adminInfo
	 *            管理员信息
	 * @param code
	 *            验证码
	 * @return
	 */
	@RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxLogin(HttpServletRequest request, @ModelAttribute("adminInfo") AdminInfo adminInfo, @RequestParam(value = "code", required = false) String code)
	{
		try
		{
			if (StringTools.isNull(code))
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "请输入验证码！");
			}
			String serverCode = getCodeSession(request);
			if (StringTools.isNull(serverCode))
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "请先获取验证码！");
			}
			if (!serverCode.equalsIgnoreCase(code))
			{
				throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "验证码错误！");
			}
			String whiteList = configInfoService.getValue(BetConstValues.CONFIG_ADMIN_WHITE_LIST);
			if (!StringTools.isNull(whiteList))
			{
				String ip = getClientIp(request);
				if (!whiteList.contains(ip))
				{
					throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "您的IP地址" + ip + "被限制登录！");
				}
			}
			AdminInfo loginedAdminInfo = adminInfoService.login(adminInfo.getUserId(), adminInfo.getPassword());
			if (loginedAdminInfo.getStatus() != StatusType.ENABLED.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "您的账号已被禁用！");
			}
			setAdminSession(request, loginedAdminInfo);
			loginedAdminInfo.setPassword(null);
			return toStringReturnInfo("登录成功！");
		}
		catch (Exception e)
		{
			setCodeSession(request, "");
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 管理员修改密码接口<br>
	 * 
	 * @param request
	 * @param password
	 *            旧密码
	 * @param newPassword
	 *            新密码
	 * @return
	 */
	@RequestMapping(value = "/ajaxChangePwd", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxChangePwd(HttpServletRequest request, @RequestParam(value = "password", required = true) String password,
		@RequestParam(value = "newPassword", required = true) String newPassword)
	{
		try
		{
			AdminInfo sessionAdminInfo = getAdminSession(request);
			AdminInfo adminInfo1 = adminInfoService.getAdminInfoByUserId(sessionAdminInfo.getUserId());
			log.debug("ajaxChangePwd", "userId:" + sessionAdminInfo.getUserId() + ",password:" + password + "," + AdminCryptoTools.getCryptoPwd(sessionAdminInfo.getUserId(), password));
			if (!adminInfo1.getPassword().equalsIgnoreCase(AdminCryptoTools.getCryptoPwd(sessionAdminInfo.getUserId(), password)))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "原密码错误！");
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IAdminInfoDao.PASSWORD, AdminCryptoTools.getCryptoPwd(sessionAdminInfo.getUserId(), newPassword));
			valueMap.put(IAdminInfoDao.UPDATE_DATE_LONG, System.currentTimeMillis());
			if (!adminInfoService.updateValueByUserId(sessionAdminInfo.getUserId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新管理员密码失败！");
			}
			adminInfo1 = adminInfoService.getAdminInfoByUserId(sessionAdminInfo.getUserId());
			setAdminSession(request, adminInfo1);
			return toStringReturnInfo("修改密码成功！");
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 添加管理员客服接口<br>
	 * 
	 * @param request
	 * @param adminInfo
	 *            管理员信息
	 * @return
	 */
	@RequestMapping(value = "/ajaxAddAdminInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddAdminInfo(HttpServletRequest request, @ModelAttribute("adminInfo") AdminInfo adminInfo)
	{
		try
		{
			AdminInfo sessionAdminInfo = getAdminSession(request);
			if (sessionAdminInfo.getUserLevel() != AdminType.ADMIN.getType())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "您无权限操作！");
			}
			adminInfo.setUserLevel(AdminType.CUSTOMER.getType());
			adminInfo.setStatus(StatusType.ENABLED.getStatus());
			adminInfo = adminInfoService.addAdminInfo(adminInfo);
			if (adminInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加管理员账号失败！");
			}
			return toObjectReturnInfo(adminInfo);
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新管理员信息接口<br>
	 * 
	 * @param request
	 * @param adminInfo
	 *            管理员信息
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateAdminInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateAdminInfo(HttpServletRequest request, @ModelAttribute("adminInfo") AdminInfo adminInfo)
	{
		try
		{
			AdminInfo sessionAdminInfo = getAdminSession(request);
			if (sessionAdminInfo.getUserLevel() != AdminType.ADMIN.getType() && !sessionAdminInfo.getUserId().equals(adminInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			AdminInfo adminInfo1 = adminInfoService.getAdminInfoByUserId(adminInfo.getUserId());
			if (adminInfo1 == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "账号不存在！");
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IAdminInfoDao.NAME, adminInfo.getName());
			valueMap.put(IAdminInfoDao.PIC_URL, adminInfo.getPicUrl());
			if (!StringTools.isNull(adminInfo.getPassword()))
			{
				valueMap.put(IAdminInfoDao.PASSWORD, AdminCryptoTools.getCryptoPwd(adminInfo1.getUserId(), adminInfo.getPassword()));
			}
			valueMap.put(IAdminInfoDao.UPDATE_DATE_LONG, System.currentTimeMillis());
			if (!adminInfoService.updateValueByUserId(adminInfo.getUserId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新管理员信息失败！");
			}
			adminInfo1 = adminInfoService.getAdminInfoByUserId(adminInfo.getUserId());
			if (sessionAdminInfo.getUserId().equals(adminInfo1.getUserId()))
			{
				setAdminSession(request, adminInfo1);
			}
			return toObjectReturnInfo(adminInfo1);
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取管理员列表接口<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetAdminInfoList")
	@ResponseBody
	public TableData ajaxGetAdminInfoList(HttpServletRequest request)
	{
		try
		{
			AdminInfo sessionAdminInfo = getAdminSession(request);
			if (sessionAdminInfo.getUserLevel() != AdminType.ADMIN.getType())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "您无权限操作！");
			}
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IAdminInfoDao.USER_ID, IAdminInfoDao.NAME });
			List<AdminInfo> list = adminInfoService.getAdminInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = adminInfoService.getAdminInfoListCount(searchInfos);
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
