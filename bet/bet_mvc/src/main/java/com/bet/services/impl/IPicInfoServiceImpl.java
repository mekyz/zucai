package com.bet.services.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.bet.daos.IPicInfoDao;
import com.bet.models.DrawImgInfo;
import com.bet.models.DrawStrInfo;
import com.bet.orms.PicInfo;
import com.bet.services.IPicInfoService;
import com.lrcall.common.enums.StatusType;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.Base64Tools;
import com.lrcall.common.utils.LogTools;
import com.lrcall.common.utils.PinyinTools;
import com.lrcall.common.utils.crypto.CryptoTools;
import com.lrcall.lrweb.common.utils.ServerTools;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("picInfoService")
public class IPicInfoServiceImpl implements IPicInfoService
{
	@Autowired
	private IPicInfoDao picInfoDao;

	@Override
	public String genId()
	{
		return picInfoDao.genId();
	}

	@Override
	public PicInfo addPicInfo(PicInfo picInfo) throws HibernateJsonResultException
	{
		return picInfoDao.add(picInfo);
	}

	@Override
	public PicInfo updatePicInfo(PicInfo picInfo) throws HibernateJsonResultException
	{
		return picInfoDao.update(picInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		return picInfoDao.updateValue(PicInfo.class, valueMap, valueMap);
	}

	@Override
	public void deletePicInfo(PicInfo picInfo) throws HibernateJsonResultException
	{
		picInfoDao.delete(picInfo);
	}

	@Override
	public void deletePicInfoByPicId(String picId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { picId }, new String[] { "picId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		picInfoDao.deletePicInfoByPicId(picId);
	}

	@Override
	public PicInfo getPicInfoByPicId(String picId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { picId }, new String[] { "picId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return picInfoDao.getPicInfoByPicId(picId);
	}

	@Override
	public boolean updateValueByPicId(String picId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IPicInfoDao.PIC_ID, picId);
		return picInfoDao.updateValue(PicInfo.class, valueMap, whereMap) > 0;
	}

	public List<PicInfo> getPicInfoList(String whereClause, Map<String, Object> params, int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos)
		throws HibernateJsonResultException
	{
		return picInfoDao.getList(PicInfo.class, whereClause, params, start, size, orderInfos, searchInfos);
	}

	@Override
	public List<PicInfo> getPicInfoList(int start, int size, List<TableOrderInfo> orderInfos, List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return picInfoDao.getList(PicInfo.class, null, null, start, size, orderInfos, searchInfos);
	}

	@Override
	public long getPicInfoListCount(List<TableSearchInfo> searchInfos) throws HibernateJsonResultException
	{
		return picInfoDao.getListCount(PicInfo.class, null, null, searchInfos);
	}
	/* 自定义方法 */

	@Override
	public PicInfo uploadPic(MultipartFile file, String serverRootPath, String sortId, Integer maxSize) throws HibernateJsonResultException, IOException
	{
		if (file == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "请选择图片！");
		}
		int size = 10 * 1024 * 1024;
		if (maxSize != null)
		{
			size = maxSize * 1024;
		}
		if (file.getSize() > size) // 2M
		{
			String sizeStr = "";
			if (size > 1024 * 1024)
			{
				sizeStr = size / (1024 * 1024) + "MB";
			}
			else if (size > 1024)
			{
				sizeStr = size / 1024 + "KB";
			}
			else
			{
				sizeStr = size + "B";
			}
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "图片过大，请上传" + sizeStr + "以下的图片！");
		}
		if (StringTools.isNull(sortId))
		{
			sortId = "common";
		}
		if (sortId.length() > 16)
		{
			sortId = sortId.substring(0, 16);
		}
		long current = System.currentTimeMillis();
		String fileName = file.getOriginalFilename();
		fileName = PinyinTools.Chinese2Pinyin(fileName);
		fileName = fileName.replace(" ", "");
		String ext = ".jpg";// 扩展名
		String picFileName = fileName;
		int index = picFileName.lastIndexOf(".");
		if (index > 0)
		{
			ext = picFileName.substring(index).toLowerCase();
			if (!ext.equalsIgnoreCase(".jpg") && !ext.equalsIgnoreCase(".jpeg") && !ext.equalsIgnoreCase(".png"))
			{
				// throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "请上传JPG/PNG/BMP格式的图片！");
			}
		}
		else
		{
			// throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "请上传JPG/PNG/BMP格式的图片！");
		}
		picFileName = picFileName.replace(ext, "") + "_" + current + ext;
		String picDir = "file/upload/images/" + sortId + "/";
		String realPath = serverRootPath + picDir;
		File pic = new File(realPath, picFileName);
		// 如果目录不存在，则创建
		if (!pic.getParentFile().exists())
		{
			pic.getParentFile().mkdirs();
		}
		file.transferTo(pic);
		LogTools.getInstance().debug("uploadPic", "imageFileName:" + pic.getName() + ",filePath:" + pic.getPath() + ",realPath: " + realPath);
		// 保存大图片
		BufferedImage rawImage = ImageIO.read(pic);
		// String imgName = picFileName.replace(ext, "") + current % 1000000 + "_" +
		// rawImage.getWidth() + "x" + rawImage.getHeight() + ext;
		// File saveRawFile = new File(realPath, imgName);
		// // 如果目录不存在，则创建
		// if (!saveRawFile.getParentFile().exists())
		// {
		// saveRawFile.mkdirs();
		// }
		// // 上传图片
		// FileUtils.copyFile(pic, saveRawFile);
		// // 保存小图片
		// double scale = 3;
		// String imgSmallName = picFileName.replace(ext, "") + "_" + ((int) (rawImage.getWidth() /
		// scale)) + "x" + ((int) (rawImage.getHeight() / scale)) + ext;
		// scale(saveRawFile, new File(new File(realPath), imgSmallName), scale, false);
		// // 保存中等图片
		// scale = 1.5;
		// String imgMiddleName = picFileName.replace(ext, "") + "_" + ((int) (rawImage.getWidth() /
		// scale)) + "x" + ((int) (rawImage.getHeight() / scale)) + ext;
		// scale(saveRawFile, new File(new File(realPath), imgMiddleName), scale, false);
		// 添加信息到数据库
		// GsonTools.toJson(new PicSizeInfo(picDir + imgSmallName, picDir + imgMiddleName, picDir +
		// imgName))
		PicInfo picInfo = new PicInfo(picInfoDao.genId(), sortId, picDir + picFileName, rawImage.getWidth(), rawImage.getHeight(), "", StatusType.ENABLED.getStatus());
		picInfo = picInfoDao.add(picInfo);
		return picInfo;
	}

	@Override
	public PicInfo uploadPic(String base64Str, String serverRootPath, String sortId, String name, Integer maxSize) throws HibernateJsonResultException, IOException
	{
		if (StringTools.isNull(base64Str))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "请选择图片！");
		}
		int size = 10 * 1024 * 1024;
		if (maxSize != null)
		{
			size = maxSize * 1024;
		}
		if (base64Str.length() > size) // 2M
		{
			String sizeStr = "";
			if (size > 1024 * 1024)
			{
				sizeStr = size / (1024 * 1024) + "MB";
			}
			else if (size > 1024)
			{
				sizeStr = size / 1024 + "KB";
			}
			else
			{
				sizeStr = size + "B";
			}
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "图片过大，请上传" + sizeStr + "以下的图片！");
		}
		if (StringTools.isNull(sortId))
		{
			sortId = "common";
		}
		if (sortId.length() > 16)
		{
			sortId = sortId.substring(0, 16);
		}
		if (StringTools.isNull(name))
		{
			long current = System.currentTimeMillis();
			name = Math.abs(new Random(current).nextInt() % 100) + "_" + current;
		}
		String picFileName = name + ".jpg";
		String picDir = "file/upload/images/" + sortId + "/";
		String realDir = serverRootPath + picDir;
		File pic = new File(realDir, picFileName);
		// 如果目录不存在，则创建
		if (!pic.getParentFile().exists())
		{
			pic.getParentFile().mkdirs();
		}
		Base64Tools.GenerateImage(base64Str, realDir + "/" + picFileName);
		LogTools.getInstance().debug("uploadPic", "imageFileName:" + pic.getName() + ",filePath:" + pic.getPath() + ",realDir: " + realDir);
		// 保存大图片
		BufferedImage rawImage = ImageIO.read(pic);
		PicInfo picInfo = new PicInfo(picInfoDao.genId(), sortId, picDir + picFileName, rawImage.getWidth(), rawImage.getHeight(), "", StatusType.ENABLED.getStatus());
		picInfo = picInfoDao.add(picInfo);
		return picInfo;
	}

	/**
	 * 缩放图像
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param scale
	 *            缩放比例
	 * @param flag
	 *            缩放选择:true 放大; false 缩小;
	 */
	public static void scale(File srcImageFile, File result, double scale, boolean flag)
	{
		try
		{
			BufferedImage src = ImageIO.read(srcImageFile); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag)
			{
				// 放大
				width = (int) (width * scale);
				height = (int) (height * scale);
			}
			else
			{
				// 缩小
				width = (int) (width / scale);
				height = (int) (height / scale);
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "jpg", result);// 输出到文件流
			// System.out.println("保存图片" + (b ? "成功" : "失败") + "：" + result.getName());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public PicInfo uploadUrlPic(String urlStr, String serverRootPath, String sortId, String name) throws HibernateJsonResultException, IOException
	{
		if (StringTools.isNull(urlStr))
		{
			throw new HibernateJsonResultException(ErrorInfo.PARAM_ERROR, "请选择图片！");
		}
		if (StringTools.isNull(sortId))
		{
			sortId = "common";
		}
		if (sortId.length() > 16)
		{
			sortId = sortId.substring(0, 16);
		}
		String picFileName = name + ".jpg";
		String picDir = "file/upload/images/" + sortId + "/";
		String realDir = serverRootPath + picDir;// request.getSession().getServletContext().getRealPath("/" + picDir);
		File pic = new File(realDir, picFileName);
		// 如果目录不存在，则创建
		if (!pic.getParentFile().exists())
		{
			pic.getParentFile().mkdirs();
		}
		// 构造URL
		URL url = new URL(urlStr);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为5s
		con.setConnectTimeout(5 * 1000);
		// 输入流
		InputStream is = con.getInputStream();
		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		OutputStream os = new FileOutputStream(pic);
		// 开始读取
		while ((len = is.read(bs)) != -1)
		{
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
		LogTools.getInstance().debug("uploadPic", "imageFileName:" + pic.getName() + ",filePath:" + pic.getPath() + ",realDir: " + realDir);
		// 保存大图片
		// pic = new File(realDir, picFileName);
		BufferedImage rawImage = ImageIO.read(pic);
		PicInfo picInfo = new PicInfo(picInfoDao.genId(), sortId, picDir + picFileName, rawImage.getWidth(), rawImage.getHeight(), "", StatusType.ENABLED.getStatus());
		picInfo = picInfoDao.add(picInfo);
		return picInfo;
	}

	private String getBufferedImageHash(BufferedImage img)
	{
		String str = img.getData().toString() + "";
		String[] propNames = img.getPropertyNames();
		if (propNames != null)
		{
			for (String prop : propNames)
			{
				str += prop + img.getProperty(prop);
			}
		}
		return CryptoTools.md5(str);
	}

	@Override
	public ReturnInfo compoundImg(BufferedImage bgImg, List<DrawImgInfo> imgInfoList, List<DrawStrInfo> strInfoList)
	{
		try
		{
			String imgData = "";
			if (imgInfoList != null && imgInfoList.size() > 0)
			{
				for (DrawImgInfo drawImgInfo : imgInfoList)
				{
					imgData += getBufferedImageHash(drawImgInfo.getBufferedImage()) + drawImgInfo.getX() + drawImgInfo.getY() + drawImgInfo.getWidth() + drawImgInfo.getHeight();
				}
			}
			if (strInfoList != null && strInfoList.size() > 0)
			{
				for (DrawStrInfo drawStrInfo : strInfoList)
				{
					imgData += drawStrInfo.getContent() + drawStrInfo.getX() + drawStrInfo.getY();
					if (drawStrInfo.getFont() != null)
					{
						imgData += drawStrInfo.getFont().toString();
					}
				}
			}
			String newImgPath = String.format("file/upload/images/qr_bg/%s.jpg", getBufferedImageHash(bgImg) + "_" + CryptoTools.md5(imgData) + System.currentTimeMillis());
			String localNewImgPath = String.format("%s/%s", ServerTools.getServerRealRootPath(), newImgPath);
			File outputfile = new File(localNewImgPath);
			if (outputfile != null && outputfile.exists() && outputfile.isFile() && outputfile.length() > 1)
			{
				return new ReturnInfo(ErrorInfo.SUCCESS, newImgPath);
			}
			else if (!outputfile.getParentFile().exists())
			{
				outputfile.getParentFile().mkdirs();
			}
			Graphics2D g = bgImg.createGraphics();
			if (imgInfoList != null && imgInfoList.size() > 0)
			{
				for (DrawImgInfo drawImgInfo : imgInfoList)
				{
					imgData += drawImgInfo.getBufferedImage().getData().toString() + drawImgInfo.getX() + drawImgInfo.getY() + drawImgInfo.getWidth() + drawImgInfo.getHeight();
					g.drawImage(drawImgInfo.getBufferedImage(), drawImgInfo.getX(), drawImgInfo.getY(), drawImgInfo.getWidth(), drawImgInfo.getHeight(), null);
				}
			}
			if (strInfoList != null && strInfoList.size() > 0)
			{
				for (DrawStrInfo drawStrInfo : strInfoList)
				{
					Font font = null;
					if (drawStrInfo.getFont() != null)
					{
						font = drawStrInfo.getFont();
					}
					else
					{
						font = new Font("宋体", Font.BOLD, 24); // 添加字体的属性设置
					}
					Color color = null;
					if (drawStrInfo.getColor() != null)
					{
						color = drawStrInfo.getColor();
					}
					else
					{
						color = Color.BLACK;
					}
					g.setFont(font);
					g.setColor(color);
					// int strWidth = g.getFontMetrics().stringWidth(drawStrInfo.getContent());
					g.drawString(drawStrInfo.getContent(), drawStrInfo.getX(), drawStrInfo.getY());
				}
			}
			g.dispose();
			BufferedImage img = bgImg;
			ImageIO.write(img, "jpg", outputfile);
			return new ReturnInfo(ErrorInfo.SUCCESS, newImgPath);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ReturnInfo(ErrorInfo.UNKNOWN_ERROR, e.getMessage());
		}
	}

	@Override
	public ReturnInfo compoundImg(String imgPath, String bgImgPath)
	{
		try
		{
			BufferedImage bg = ImageIO.read(new File(bgImgPath));
			BufferedImage b = ImageIO.read(new File(imgPath));
			int width = b.getWidth();
			int height = b.getHeight();
			int x = (bg.getWidth() - width) / 2;
			int y = (bg.getHeight() - height) / 2;
			List<DrawImgInfo> imgInfoList = new ArrayList<>();
			imgInfoList.add(new DrawImgInfo(b, x, y, width, height));
			return compoundImg(bg, imgInfoList, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ReturnInfo(ErrorInfo.UNKNOWN_ERROR, e.getMessage());
		}
	}
}
