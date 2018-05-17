package com.bet.actions.user.ajax;

import java.util.ArrayList;
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

import com.bet.actions.user.BaseUserController;
import com.bet.daos.IAdviceInfoDao;
import com.bet.orms.AdviceInfo;
import com.bet.orms.UserInfo;
import com.bet.services.IAdviceInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.enums.SqlOrderType;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userAdviceAction")
@RequestMapping("/user")
public class AdviceAction extends BaseUserController
{
	@Autowired
	private IAdviceInfoService adviceInfoService;

	/**
	 * 添加意见反馈<br/>
	 * 
	 * @param request
	 * @param adviceInfo
	 *            意见反馈信息
	 * @return
	 */
	@RequestMapping(value = "/ajaxAddAdviceInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxAddAdviceInfo(HttpServletRequest request, @ModelAttribute("adviceInfo") AdviceInfo adviceInfo)
	{
		try
		{
			adviceInfo.setContent(HtmlUtils.htmlUnescape(adviceInfo.getContent()));
			UserInfo sessionUserInfo = getUserSession(request);
			adviceInfo.setAdviceId(adviceInfoService.genId());
			if (sessionUserInfo != null)
			{
				adviceInfo.setUserId(sessionUserInfo.getUserId());
			}
			adviceInfo.setReadStatus(StatusType.DISABLED.getStatus());
			adviceInfo.setReplyStatus(StatusType.DISABLED.getStatus());
			adviceInfo.setStatus(StatusType.ENABLED.getStatus());
			adviceInfo = adviceInfoService.addAdviceInfo(adviceInfo);
			if (adviceInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "添加意见反馈失败！");
			}
			return toStringReturnInfo("反馈成功！");
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取意见反馈信息<br/>
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
			UserInfo sessionUserInfo = getUserSession(request);
			AdviceInfo adviceInfo = adviceInfoService.getAdviceInfoByAdviceId(adviceId);
			if (adviceInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "反馈不存在！");
			}
			if (!adviceInfo.getUserId().equals(sessionUserInfo.getUserId()))
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "非法操作！");
			}
			return toObjectReturnInfo(adviceInfo);
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取意见反馈列表<br/>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetAdviceInfoList")
	@ResponseBody
	public TableData ajaxGetAdviceInfoList(HttpServletRequest request)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			orderInfos.add(new TableOrderInfo(100, IAdviceInfoDao.ADD_DATE_LONG, SqlOrderType.DESC.getType()));
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			searchInfos.add(new TableSearchInfo(IAdviceInfoDao.USER_ID, sessionUserInfo.getUserId(), true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IAdviceInfoDao.ADVICE_ID, IAdviceInfoDao.PLATFORM, IAdviceInfoDao.ADVICE_TYPE, IAdviceInfoDao.NUMBER,
				IAdviceInfoDao.EMAIL, IAdviceInfoDao.CONTENT, IAdviceInfoDao.STATUS, IAdviceInfoDao.READ_STATUS, IAdviceInfoDao.REPLY_STATUS });
			List<AdviceInfo> list = adviceInfoService.getAdviceInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = adviceInfoService.getAdviceInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseAdviceInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取意见反馈列表数量<br/>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetAdviceInfoListCount")
	@ResponseBody
	public long ajaxGetAdviceInfoListCount(HttpServletRequest request)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<TableOrderInfo>();
			List<TableSearchInfo> searchInfos = new ArrayList<TableSearchInfo>();
			searchInfos.add(new TableSearchInfo(IAdviceInfoDao.USER_ID, sessionUserInfo.getUserId(), true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IAdviceInfoDao.ADVICE_ID, IAdviceInfoDao.PLATFORM, IAdviceInfoDao.ADVICE_TYPE, IAdviceInfoDao.NAME,
				IAdviceInfoDao.NUMBER, IAdviceInfoDao.EMAIL, IAdviceInfoDao.CONTENT, IAdviceInfoDao.STATUS, IAdviceInfoDao.READ_STATUS, IAdviceInfoDao.REPLY_STATUS });
			long count = adviceInfoService.getAdviceInfoListCount(searchInfos);
			return count;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
}
