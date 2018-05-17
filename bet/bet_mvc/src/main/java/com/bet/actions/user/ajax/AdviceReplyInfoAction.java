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
import com.bet.daos.IAdviceReplyInfoDao;
import com.bet.orms.AdviceInfo;
import com.bet.orms.AdviceReplyInfo;
import com.bet.orms.UserInfo;
import com.bet.services.IAdviceInfoService;
import com.bet.services.IAdviceReplyInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("userAdviceReplyInfoAction")
@RequestMapping(value = "/user")
public class AdviceReplyInfoAction extends BaseUserController
{
	@Autowired
	private IAdviceReplyInfoService adviceReplyInfoService;
	@Autowired
	private IAdviceInfoService adviceInfoService;

	@RequestMapping(value = "/ajaxGetAdviceReplyInfo")
	@ResponseBody
	public ReturnInfo ajaxGetAdviceReplyInfo(HttpServletRequest request, @RequestParam(value = "replyId", required = true) String replyId)
	{
		try
		{
			// UserInfo sessionUserInfo = getUserSession(request);
			AdviceReplyInfo adviceReplyInfo = adviceReplyInfoService.getAdviceReplyInfoByReplyId(replyId);
			if (adviceReplyInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "意见反馈回复记录不存在！");
			}
			return toObjectReturnInfo(ParseModel.parseAdviceReplyInfo(adviceReplyInfo));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetAdviceReplyInfoList")
	@ResponseBody
	public TableData ajaxGetAdviceReplyInfoList(HttpServletRequest request, @RequestParam(value = "adviceId", required = true) String adviceId)
	{
		try
		{
			UserInfo sessionUserInfo = getUserSession(request);
			AdviceInfo adviceInfo = adviceInfoService.getAdviceInfoByAdviceId(adviceId);
			if (adviceInfo == null || !adviceInfo.getUserId().equals(sessionUserInfo.getUserId()))
			{
				return null;
			}
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IAdviceReplyInfoDao.ADVICE_ID, adviceId, true, false));
			buildTable(request, tableMap, orderInfos, searchInfos, new String[] {});
			List<AdviceReplyInfo> list = adviceReplyInfoService.getAdviceReplyInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = adviceReplyInfoService.getAdviceReplyInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, ParseModel.parseAdviceReplyInfoList(list));
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
