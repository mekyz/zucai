package com.bet.services;

import java.io.File;
import java.io.IOException;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.lrcall.common.models.ReturnInfo;

public interface CQrService
{
	/**
	 * 生成二维码图片信息<br>
	 * 
	 * @param content
	 *            内容
	 * @param filePath
	 *            保存路径
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @return
	 * @throws IOException
	 * @throws WriterException
	 */
	public ReturnInfo genQr(String content, String filePath, int width, int height) throws IOException, WriterException;

	/**
	 * 生成二维码图片信息<br>
	 * 
	 * @param content
	 *            内容
	 * @param filePath
	 *            保存路径
	 * @return
	 * @throws IOException
	 * @throws WriterException
	 */
	public ReturnInfo genQr(String content, String filePath);

	/**
	 * 解析二维码<br>
	 * 
	 * @param imageFile
	 *            二维码图片文件
	 * @return
	 * @throws NotFoundException
	 */
	public ReturnInfo parseQr(File imageFile) throws NotFoundException, IOException;

	/**
	 * 更新用户二维码<br>
	 * 二维码包含用户ID和微信OPENID
	 * 
	 * @param userId
	 *            用户ID
	 * @param serverRootPath
	 *            服务器路径
	 * @return
	 */
	public ReturnInfo updateUserQr(String userId, String serverRootPath);
}
