package com.bet.actions.user.ajax;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bet.actions.user.BaseUserController;
import com.bet.orms.PicInfo;
import com.bet.services.IPicInfoService;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.utils.PinyinTools;
import com.lrcall.lrweb.common.utils.ServerTools;

@Controller("userFileAction")
@RequestMapping("/user")
public class FileAction extends BaseUserController
{
	@Autowired
	private IPicInfoService picInfoService;

	/**
	 * 上传图片接口<br/>
	 * 
	 * @param request
	 * @param file
	 *            图片文件
	 * @param sortId
	 *            存储的文件夹
	 * @return
	 */
	@RequestMapping(value = "/ajaxUploadPic", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUploadPic(HttpServletRequest request, @RequestParam(value = "pic", required = true) MultipartFile file, @RequestParam(value = "sortId", required = false) String sortId)
	{
		try
		{
			PicInfo picInfo = picInfoService.uploadPic(file, ServerTools.getServerRealRootPath(request), sortId, null);
			return toObjectReturnInfo(picInfo);
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 上传消息图片接口<br/>
	 * 
	 * @param request
	 * @param file
	 *            图片文件
	 * @param sortId
	 *            存储的文件夹
	 * @return
	 */
	@RequestMapping(value = "/ajaxUploadMsgPic", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUploadMsgPic(HttpServletRequest request, @RequestParam(value = "imageFile", required = true) MultipartFile file,
		@RequestParam(value = "sortId", required = false) String sortId)
	{
		try
		{
			PicInfo picInfo = picInfoService.uploadPic(file, ServerTools.getServerRealRootPath(request), sortId, null);
			return toObjectReturnInfo(picInfo);
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 上传图片接口<br/>
	 * 上传的图片格式为base64编码
	 * 
	 * @param request
	 * @param base64Str
	 *            base64编码图片信息
	 * @param sortId
	 *            分类ID
	 * @return
	 */
	@RequestMapping(value = "/ajaxUploadBase64Pic", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUploadBase64Pic(HttpServletRequest request, @RequestParam(value = "upload", required = true) String base64Str,
		@RequestParam(value = "sortId", required = false) String sortId, @RequestParam(value = "name", required = false) String name)
	{
		try
		{
			base64Str = base64Str.replace("data:image/png;base64,", "");
			PicInfo picInfo = picInfoService.uploadPic(base64Str, ServerTools.getServerRealRootPath(request), sortId, name, null);
			return toObjectReturnInfo(picInfo);
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 上传文件接口<br/>
	 * 
	 * @param request
	 * @param file
	 *            文件
	 * @return
	 */
	@RequestMapping(value = "/ajaxUploadFile", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUploadFile(HttpServletRequest request, @RequestParam(value = "upload", required = true) MultipartFile file)
	{
		String fileDir = "file/upload/user/files/";
		String path = request.getSession().getServletContext().getRealPath("/" + fileDir);
		String fileName = file.getOriginalFilename();
		log.debug("ajaxUploadFile", "server path:" + path);
		fileName = PinyinTools.Chinese2Pinyin(fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists())
		{
			targetFile.mkdirs();
		}
		// 保存
		try
		{
			file.transferTo(targetFile);
			return toStringReturnInfo(path + fileName);
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 上传BUG文件接口<br/>
	 * 
	 * @param request
	 * @param file
	 *            文件
	 * @return
	 */
	@RequestMapping(value = "/ajaxUploadAndroidBugFile", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUploadBugFile(HttpServletRequest request, @RequestParam(value = "upload", required = true) MultipartFile file,
		@RequestParam(value = "platform", required = true) String platform)
	{
		String fileDir = "file/upload/" + platform + "/bugs/";
		String path = request.getSession().getServletContext().getRealPath("/" + fileDir);
		String fileName = file.getOriginalFilename();
		log.debug("ajaxUploadAndroidBugFile", "server path:" + path);
		fileName = PinyinTools.Chinese2Pinyin(fileName);
		File targetFile = new File(fileDir, fileName);
		if (!targetFile.exists())
		{
			targetFile.mkdirs();
		}
		// 保存
		try
		{
			file.transferTo(targetFile);
			return toStringReturnInfo(path + fileName);
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
