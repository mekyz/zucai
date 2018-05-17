package com.bet.actions.admin.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.admin.BaseAdminController;
import com.bet.daos.IUserInfoDao;
import com.bet.enums.MoneyUnit;
import com.bet.enums.UserBalanceLogType;
import com.bet.enums.UserType;
import com.bet.orms.AdminInfo;
import com.bet.orms.UserBalanceLogInfo;
import com.bet.orms.UserInfo;
import com.bet.orms.UserTypeChangeLogInfo;
import com.bet.services.CStatService;
import com.bet.services.IUserBalanceLogInfoService;
import com.bet.services.IUserInfoService;
import com.bet.services.IUserTypeChangeLogInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.StringTools;
import com.lrcall.common.utils.crypto.UserCryptoTools;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminUserAction")
@RequestMapping("/admin")
public class UserAction extends BaseAdminController
{
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IUserTypeChangeLogInfoService userTypeChangeLogInfoService;
	@Autowired
	private CStatService cStatService;
	@Autowired
	private IUserBalanceLogInfoService userBalanceLogInfoService;

	/**
	 * 更新用户状态接口<br>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @param status
	 *            状态
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserInfoStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserInfoStatus(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId, @RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
			if (userInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "用户ID不存在！");
			}
			if (status != StatusType.ENABLED.getStatus())
			{
				status = StatusType.DISABLED.getStatus();
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserInfoDao.STATUS, status);
			if (!userInfoService.updateValueByUserId(userInfo.getUserId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户状态失败！");
			}
			return toStringReturnInfo("更新用户状态为" + (status == StatusType.ENABLED.getStatus() ? "已启用" : "已禁用") + "成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户系统状态接口<br>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @param status
	 *            状态
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserInfoSysStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserInfoSysStatus(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId,
		@RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
			if (userInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "用户ID不存在！");
			}
			if (status != StatusType.ENABLED.getStatus())
			{
				status = StatusType.DISABLED.getStatus();
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserInfoDao.SYS_STATUS, status);
			if (!userInfoService.updateValueByUserId(userInfo.getUserId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户系统状态失败！");
			}
			return toStringReturnInfo("更新用户为" + (status == StatusType.ENABLED.getStatus() ? "" : "非") + "系统用户成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户类型接口<br>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @param userType
	 *            用户类型
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserType", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserType(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId, @RequestParam(value = "userType", required = true) byte userType)
	{
		try
		{
			AdminInfo sessionAdminInfo = getAdminSession(request);
			UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
			if (userInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "用户ID不存在！");
			}
			if (StringTools.isNull(UserType.getDesc(userType)) || "未知类型".equals(UserType.getDesc(userType)))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "用户类型错误！");
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserInfoDao.USER_TYPE, userType);
			if (!userInfoService.updateValueByUserId(userInfo.getUserId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户类型失败！");
			}
			long current = System.currentTimeMillis();
			try
			{
				userTypeChangeLogInfoService.addUserTypeChangeLogInfo(new UserTypeChangeLogInfo(userId, sessionAdminInfo.getUserId(), userType, current));
			}
			catch (Exception e)
			{
				log.error("ajaxUpdateUserType", userId + "更新为" + userType + "失败！");
			}
			return toStringReturnInfo("更新用户类型为" + UserType.getDesc(userType) + "成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取用户信息接口<br>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserInfo")
	@ResponseBody
	public ReturnInfo ajaxGetUserInfo(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
			if (userInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "用户ID不存在！");
			}
			return toObjectReturnInfo(userInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取用户列表接口<br>
	 * 
	 * @param request
	 * @param userType
	 *            用户类型
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserInfoList")
	@ResponseBody
	public TableData ajaxGetUserInfoList(HttpServletRequest request, @RequestParam(value = "parentId", required = false) String parentId,
		@RequestParam(value = "userType", required = false) Byte userType)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			if (!StringTools.isNull(parentId))
			{
				searchInfos.add(new TableSearchInfo(IUserInfoDao.PARENT_ID, parentId, true, false));
			}
			if (userType != null)
			{
				searchInfos.add(new TableSearchInfo(IUserInfoDao.USER_TYPE, userType + "", true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserInfoDao.USER_ID, IUserInfoDao.PARENT_ID, IUserInfoDao.PROVINCE, IUserInfoDao.CITY, IUserInfoDao.ADDRESS,
				IUserInfoDao.REFERRER_ID, IUserInfoDao.NUMBER, IUserInfoDao.USER_TYPE, IUserInfoDao.STATUS, IUserInfoDao.REMARK });
			List<UserInfo> userInfoList = userInfoService.getUserInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = userInfoService.getUserInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, userInfoList);
			return tableData;
		}
		catch (Exception e)
		{
			toExceptionReturnInfo(e);
		}
		return null;
	}

	/**
	 * 获取用户列表数量接口<br>
	 * 
	 * @param request
	 * @param userType
	 *            用户类型
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserInfoListCount")
	@ResponseBody
	public long ajaxGetUserInfoListCount(HttpServletRequest request, @RequestParam(value = "userType", required = false) Byte userType)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			if (userType != null)
			{
				searchInfos.add(new TableSearchInfo(IUserInfoDao.USER_TYPE, userType + "", true, false));
			}
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserInfoDao.USER_ID, IUserInfoDao.PARENT_ID, IUserInfoDao.PROVINCE, IUserInfoDao.CITY, IUserInfoDao.ADDRESS,
				IUserInfoDao.REFERRER_ID, IUserInfoDao.NUMBER, IUserInfoDao.USER_TYPE, IUserInfoDao.STATUS, IUserInfoDao.REMARK });
			long count = userInfoService.getUserInfoListCount(searchInfos);
			return count;
		}
		catch (Exception e)
		{
			toExceptionReturnInfo(e);
		}
		return 0;
	}

	/**
	 * 获取代理商用户列表接口<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetAgentUserInfoList")
	@ResponseBody
	public TableData ajaxGetAgentUserInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			searchInfos.add(new TableSearchInfo(IUserInfoDao.USER_TYPE, UserType.AGENT1.getType() + "", true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IUserInfoDao.USER_ID, IUserInfoDao.PARENT_ID, IUserInfoDao.PROVINCE, IUserInfoDao.CITY, IUserInfoDao.ADDRESS,
				IUserInfoDao.REFERRER_ID, IUserInfoDao.NUMBER, IUserInfoDao.USER_TYPE, IUserInfoDao.STATUS, IUserInfoDao.REMARK });
			List<UserInfo> userInfoList = userInfoService.getUserInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = userInfoService.getUserInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, userInfoList);
			return tableData;
		}
		catch (Exception e)
		{
			toExceptionReturnInfo(e);
		}
		return null;
	}

	/**
	 * 获取今日注册用户数接口<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetTodayUserCount")
	@ResponseBody
	public long ajaxGetTodayUserCount(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			long count = userInfoService.getTodayUserInfoListCount();
			return count;
		}
		catch (HibernateJsonResultException e)
		{
			toExceptionReturnInfo(e);
		}
		return 0;
	}

	/**
	 * 更新用户密码接口<br>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserPwd", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserPwd(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId, @RequestParam(value = "newPwd", required = true) String newPwd)
	{
		try
		{
			UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
			if (userInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "用户ID不存在！");
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserInfoDao.PASSWORD, UserCryptoTools.getCryptoPwd(userId, newPwd));
			if (!userInfoService.updateValueByUserId(userId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "重置用户登录密码失败！");
			}
			return toStringReturnInfo("重置用户登录密码成功！");
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户二级密码接口<br>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserPwd2", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserPwd2(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId, @RequestParam(value = "newPwd", required = true) String newPwd)
	{
		try
		{
			UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
			if (userInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "用户ID不存在！");
			}
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IUserInfoDao.PASSWORD2, UserCryptoTools.getCryptoPwd2(userId, newPwd));
			if (!userInfoService.updateValueByUserId(userId, valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "重置用户二级密码失败！");
			}
			return toStringReturnInfo("重置用户二级密码成功！");
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 检查用户有效接口<br>
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxCheckUserValidateDate", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxCheckUserValidateDate(HttpServletRequest request)
	{
		try
		{
			return cStatService.updateCheckUserValideDateJob();
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 检查用户父节点接口<br>
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxCheckUserParents", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxCheckUserParents(HttpServletRequest request)
	{
		try
		{
			return cStatService.updateCheckUserParentsJob();
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 检查用户父节点接口<br>
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxCheckUserParentsByUserId")
	@ResponseBody
	public ReturnInfo ajaxCheckUserParentsByUserId(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId)
	{
		try
		{
			return userInfoService.checkAndUpdateUserParentInfoJob(userId);
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户体验金接口<br>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserInfoGivePoint", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserInfoGivePoint(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
			if (userInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "用户ID不存在！");
			}
			if (userInfo.getGivePoint() < 0)
			{
				long givePoint = -userInfo.getGivePoint() + 20000;
				if (userInfoService.updatePoint(IUserInfoDao.GIVE_POINT, userId, givePoint))
				{
					userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userId, givePoint, MoneyUnit.GIVE_POINT.getType(), UserBalanceLogType.GIVE_POINT.getType(), null, null, null,
						null, String.format("系统异常补偿金%.2f元。", (double) givePoint / 100), System.currentTimeMillis()));
				}
			}
			else
			{
			}
			return toStringReturnInfo("更新成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户彩金接口<br>
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateUserInfoPoint", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateUserInfoPoint(HttpServletRequest request, @RequestParam(value = "userId", required = true) String userId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
			if (userInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "用户ID不存在！");
			}
			if (userInfo.getPoint() < 0)
			{
				long point = -userInfo.getPoint();
				if (point > 200)
				{
					point = 200;
				}
				if (userInfoService.updatePoint(IUserInfoDao.POINT, userId, point))
				{
					userBalanceLogInfoService.addUserBalanceLogInfo(new UserBalanceLogInfo(userId, point, MoneyUnit.POINT.getType(), UserBalanceLogType.EXCHANGE_IN.getType(), null, null, null, null,
						String.format("系统异常补偿金%.2f元。", (double) point / 100), System.currentTimeMillis()));
				}
			}
			else
			{
			}
			return toStringReturnInfo("更新成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
