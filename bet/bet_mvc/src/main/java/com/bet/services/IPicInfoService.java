package com.bet.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.bet.models.DrawImgInfo;
import com.bet.models.DrawStrInfo;
import com.bet.orms.PicInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;

public interface IPicInfoService
{
	public String genId();

	public PicInfo addPicInfo(PicInfo picInfo) throws HibernateJsonResultException;

	public PicInfo updatePicInfo(PicInfo picInfo) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public void deletePicInfo(PicInfo picInfo) throws HibernateJsonResultException;

	public void deletePicInfoByPicId(String picId) throws HibernateJsonResultException;

	public PicInfo getPicInfoByPicId(String picId) throws HibernateJsonResultException;

	public boolean updateValueByPicId(String picId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public List<PicInfo> getPicInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;

	public long getPicInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException;
	/* 自定义方法 */

	/**
	 * 上传图片
	 * 
	 * @param pic
	 *            上传的图片
	 * @param serverRootPath
	 *            服务器根目录
	 * @param sortId
	 *            文件夹
	 * @param maxSize
	 *            图片最大大小,以KB计算
	 * @return
	 * @throws HibernateJsonResultException
	 *             上传不成功的异常信息
	 * @throws IOException
	 */
	public PicInfo uploadPic(MultipartFile pic, String serverRootPath, String sortId, Integer maxSize) throws HibernateJsonResultException, IOException;

	public PicInfo uploadPic(String base64Str, String serverRootPath, String sortId, String name, Integer maxSize) throws HibernateJsonResultException, IOException;

	/**
	 * 保存指定url的图片
	 * 
	 * @param urlStr
	 *            图片url
	 * @param serverRootPath
	 *            服务器项目真实根路径，用于存储用户头像
	 * @param sortId
	 *            图片分类
	 * @param name
	 *            图片名称，不带后缀
	 * @return
	 * @throws HibernateJsonResultException
	 * @throws IOException
	 */
	public PicInfo uploadUrlPic(String urlStr, String serverRootPath, String sortId, String name) throws HibernateJsonResultException, IOException;

	/**
	 * 合成图片
	 * 
	 * @param imgPath
	 * @param bgImgPath
	 * @return
	 */
	public ReturnInfo compoundImg(BufferedImage bgImg, List<DrawImgInfo> imgInfoList, List<DrawStrInfo> strInfoList);

	public ReturnInfo compoundImg(String imgPath, String bgImgPath);
}
