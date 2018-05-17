package com.bet.actions.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bet.daos.IAdviceReplyInfoDao;
import com.bet.orms.AdviceInfo;
import com.bet.orms.AdviceReplyInfo;
import com.bet.orms.UserInfo;
import com.bet.services.IAdviceInfoService;
import com.bet.services.IAdviceReplyInfoService;
import com.bet.utils.ParseModel;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

@Controller("userAdviceController")
@RequestMapping("/user")
public class AdviceController extends BaseUserController
{
	@Autowired
	private IAdviceInfoService adviceInfoService;
	@Autowired
	private IAdviceReplyInfoService adviceReplyInfoService;

	/**
	 * 意见反馈页面
	 * 
	 * @param request
	 * @param adviceId
	 *            意见反馈ID
	 * @return
	 */
	@RequestMapping(value = "/advice", method = RequestMethod.GET)
	public ModelAndView advice(HttpServletRequest request, @RequestParam(value = "adviceId", required = true) String adviceId)
	{
		ModelAndView mv = new ModelAndView("user/advice");
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		AdviceInfo adviceInfo = adviceInfoService.getAdviceInfoByAdviceId(adviceId);
		if (adviceInfo.getUserId().equals(sessionUserInfo.getUserId()))
		{
			mv.addObject("adviceInfo", adviceInfo);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IAdviceReplyInfoDao.ADVICE_ID, adviceId, true, false));
			List<AdviceReplyInfo> adviceReplyInfoList = adviceReplyInfoService.getAdviceReplyInfoList(0, -1, orderInfos, searchInfos);
			mv.addObject("adviceReplyInfoList", adviceReplyInfoList);
		}
		return mv;
	}

	/**
	 * 添加意见反馈页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/adviceAdd", method = RequestMethod.GET)
	public ModelAndView adviceAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("user/adviceAdd");
		mv.addObject("adviceInfo", new AdviceInfo());
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}

	/**
	 * 意见反馈列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/adviceList", method = RequestMethod.GET)
	public ModelAndView adviceList(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("user/adviceList");
		UserInfo sessionUserInfo = getUserSession(request);
		mv.addObject("userInfo", ParseModel.parseUserInfo(sessionUserInfo));
		return mv;
	}
}
