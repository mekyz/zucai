package com.bet.actions.admin;

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
import com.bet.orms.AdminInfo;
import com.bet.orms.AdviceInfo;
import com.bet.orms.AdviceReplyInfo;
import com.bet.orms.AdviceTypeInfo;
import com.bet.services.IAdviceInfoService;
import com.bet.services.IAdviceReplyInfoService;
import com.bet.services.IAdviceTypeInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

@Controller("adminAdviceController")
@RequestMapping("/admin")
public class AdviceController extends BaseAdminController
{
	@Autowired
	private IAdviceTypeInfoService adviceTypeInfoService;
	@Autowired
	private IAdviceInfoService adviceInfoService;
	@Autowired
	private IAdviceReplyInfoService adviceReplyInfoService;

	@RequestMapping(value = "/manageAdviceTypeInfos", method = RequestMethod.GET)
	public ModelAndView manageAdviceTypeInfos(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("admin/manageAdviceTypeInfos");
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		return mv;
	}

	@RequestMapping(value = "/adviceTypeInfoAdd", method = RequestMethod.GET)
	public ModelAndView adviceTypeInfoAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("admin/adviceTypeInfoAdd");
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		mv.addObject("adviceTypeInfo", new AdviceTypeInfo());
		return mv;
	}

	@RequestMapping(value = "/adviceTypeInfoEdit", method = RequestMethod.GET)
	public ModelAndView adviceTypeInfoEdit(HttpServletRequest request, @RequestParam(value = "typeId", required = true) String typeId)
	{
		ModelAndView mv = new ModelAndView("admin/adviceTypeInfoEdit");
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		AdviceTypeInfo adviceTypeInfo = adviceTypeInfoService.getAdviceTypeInfoByTypeId(typeId);
		if (adviceTypeInfo == null)
		{
			throw new RuntimeException("意见反馈类型信息ID不存在！");
		}
		mv.addObject("adviceTypeInfo", adviceTypeInfo);
		return mv;
	}

	/**
	 * 意见反馈管理页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manageAdvice", method = RequestMethod.GET)
	public ModelAndView manageAdvices(HttpServletRequest request)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/manageAdvice");
		mv.addObject("adviceInfo", new AdviceInfo());
		return mv;
	}

	/**
	 * 意见反馈详情页面
	 * 
	 * @param request
	 * @param adviceId
	 *            意见反馈ID
	 * @return
	 */
	@RequestMapping(value = "/advice", method = RequestMethod.GET)
	public ModelAndView advice(HttpServletRequest request, @RequestParam(value = "adviceId", required = true) String adviceId)
	{
		AdminInfo sessionAdminInfo = getAdminSession(request);
		ModelAndView mv = new ModelAndView("admin/advice");
		AdviceInfo adviceInfo = adviceInfoService.getAdviceInfoByAdviceId(adviceId);
		adviceInfo.setReadUserId(sessionAdminInfo.getUserId());
		adviceInfo.setReadStatus(StatusType.ENABLED.getStatus());
		adviceInfo = adviceInfoService.updateAdviceInfo(adviceInfo);
		mv.addObject("adviceInfo", adviceInfo);
		List<TableOrderInfo> orderInfos = new ArrayList<>();
		List<TableSearchInfo> searchInfos = new ArrayList<>();
		searchInfos.add(new TableSearchInfo(IAdviceReplyInfoDao.ADVICE_ID, adviceId, true, false));
		List<AdviceReplyInfo> adviceReplyInfoList = adviceReplyInfoService.getAdviceReplyInfoList(0, -1, orderInfos, searchInfos);
		mv.addObject("adviceReplyInfoList", adviceReplyInfoList);
		mv.addObject("adviceReplyInfo", new AdviceReplyInfo());
		return mv;
	}
}
