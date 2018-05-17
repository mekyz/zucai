package com.bet.actions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bet.orms.PicInfo;
import com.bet.services.IPicInfoService;
import com.lrcall.lrweb.common.utils.ServerTools;

@Controller("commonFileAction")
@RequestMapping("/")
public class FileAction extends BaseAction
{
	@Autowired
	private IPicInfoService picInfoService;

	@RequestMapping(value = "/ajaxUploadPic", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String ajaxUploadPic(HttpServletRequest request, @RequestParam(value = "upload", required = true) MultipartFile file)
	{
		try
		{
			PicInfo picInfo = picInfoService.uploadPic(file, ServerTools.getServerRealRootPath(request), "common", null);
			// CKEditor提交的很重要的一个参数
			String callback = request.getParameter("CKEditorFuncNum");
			// 返回"图像"选项卡并显示图片 request.getContextPath()为web项目名
			String result = String.format("<script>window.parent.CKEDITOR.tools.callFunction(%s,'%s/%s','')</script>", callback, request.getContextPath(), picInfo.getPicUrl());
			return result;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
