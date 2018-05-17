package com.bet.actions.admin.ajax;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bet.actions.admin.BaseAdminController;
import com.bet.orms.PicInfo;
import com.bet.services.IPicInfoService;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.utils.PinyinTools;
import com.lrcall.common.utils.StringTools;
import com.lrcall.lrweb.common.utils.ServerTools;

@Controller("adminFileAction")
@RequestMapping("/admin")
public class FileAction extends BaseAdminController
{
	@Autowired
	private IPicInfoService picInfoService;

	/**
	 * 上传图片接口<br>
	 * 
	 * @param request
	 * @param file
	 *            图片文件
	 * @param sortId
	 *            图片分类
	 * @return
	 */
	@RequestMapping(value = "/ajaxUploadPic")
	@ResponseBody
	public ReturnInfo ajaxUploadPic(HttpServletRequest request, @RequestParam(value = "pic", required = true) MultipartFile file, @RequestParam(value = "sortId") String sortId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			if (StringTools.isNull(sortId))
			{
				sortId = "default";
			}
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
		@RequestParam(value = "sortId", required = false) String sortId)
	{
		try
		{
			base64Str = base64Str.replace("data:image/png;base64,", "");
			PicInfo picInfo = picInfoService.uploadPic(base64Str, ServerTools.getServerRealRootPath(request), sortId, null, null);
			return toObjectReturnInfo(picInfo);
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 上传文件接口<br>
	 * 
	 * @param request
	 * @param file
	 *            文件
	 * @return
	 */
	@RequestMapping(value = "/ajaxUploadFile")
	@ResponseBody
	public ReturnInfo ajaxUploadFile(HttpServletRequest request, @RequestParam(value = "upload", required = true) MultipartFile file)
	{
		// AdminInfo sessionAdminInfo = getAdminSession(request);
		String fileDir = "file/upload/";
		String fileName = file.getOriginalFilename();
		if (!StringTools.isNull(fileName) && (fileName.endsWith("apk") || fileName.endsWith("APK")))
		{
			fileDir += "android/apk/";
		}
		String path = request.getSession().getServletContext().getRealPath("/" + fileDir);
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
			return toStringReturnInfo(fileDir + fileName);
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 获取图片接口<br>
	 * 
	 * @param request
	 * @param file
	 *            图片文件
	 * @param sortId
	 *            图片分类
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetPic")
	@ResponseBody
	public ReturnInfo ajaxGetPic(HttpServletRequest request, @RequestParam(value = "picId") String picId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			PicInfo picInfo = picInfoService.getPicInfoByPicId(picId);
			return toObjectReturnInfo(picInfo);
		}
		catch (Exception e)
		{
			return toExceptionReturnInfo(e);
		}
	}
}
