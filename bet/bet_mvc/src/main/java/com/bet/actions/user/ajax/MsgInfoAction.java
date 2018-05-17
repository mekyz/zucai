package com.bet.actions.user.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bet.actions.user.BaseUserController;
import com.bet.daos.IMsgInfoDao;
import com.bet.orms.MsgInfo;
import com.bet.services.IMsgInfoService;
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

@Controller("userMsgInfoAction")
@RequestMapping(value = "/user")
public class MsgInfoAction extends BaseUserController
{
	@Autowired
	private IMsgInfoService msgInfoService;

	@RequestMapping(value = "/ajaxGetMsgInfo")
	@ResponseBody
	public ReturnInfo ajaxGetMsgInfo(HttpServletRequest request, @RequestParam(value = "msgId", required = true) String msgId)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			MsgInfo msgInfo = msgInfoService.getMsgInfoByMsgId(msgId);
			if (msgInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "消息信息不存在！");
			}
			return toObjectReturnInfo(ParseModel.parseMsgInfo(msgInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetMsgInfoList")
	@ResponseBody
	public TableData ajaxGetMsgInfoList(HttpServletRequest request)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			orderInfos.add(new TableOrderInfo(IMsgInfoDao.UPDATE_DATE_LONG, SqlOrderType.DESC.getType()));
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IMsgInfoDao.STATUS, StatusType.ENABLED.getStatus() + "", true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] { IMsgInfoDao.MSG_ID, IMsgInfoDao.USER_ID, IMsgInfoDao.SORT_ID, IMsgInfoDao.STATUS });
			List<MsgInfo> list = msgInfoService.getMsgInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = msgInfoService.getMsgInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseMsgInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
