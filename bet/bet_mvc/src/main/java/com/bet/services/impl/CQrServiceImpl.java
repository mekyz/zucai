package com.bet.services.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bet.daos.IUserInfoDao;
import com.bet.orms.UserInfo;
import com.bet.services.CQrService;
import com.bet.services.IConfigInfoService;
import com.bet.services.IUserInfoService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.utils.GsonTools;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("cQrService")
public class CQrServiceImpl implements CQrService
{
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IConfigInfoService configInfoService;

	@Override
	public ReturnInfo genQr(String content, String filePath, int width, int height) throws IOException, WriterException
	{
		String ext = "";
		int index = filePath.lastIndexOf(".");
		if (index > 0)
		{
			ext = filePath.substring(index + 1);
		}
		if (ext.equalsIgnoreCase("png"))
		{
			ext = "png";
		}
		else
		{
			ext = "jpg";
		}
		String format = ext;
		Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 0);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		// Path file = new File(filePath).toPath();
		// MatrixToImageWriter.writeToPath(bitMatrix, format, file);
		// 1.1去白边
		int whiteWidth = 0;
		int[] rec = bitMatrix.getEnclosingRectangle();
		int resWidth = rec[2] + whiteWidth * 2;
		int resHeight = rec[3] + whiteWidth * 2;
		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
		resMatrix.clear();
		for (int i = 0; i < resWidth; i++)
		{
			for (int j = 0; j < resHeight; j++)
			{
				if (bitMatrix.get(i + rec[0], j + rec[1]))
				{
					resMatrix.set(i + whiteWidth, j + whiteWidth);
				}
			}
		}
		// 2
		int width1 = resMatrix.getWidth();
		int height1 = resMatrix.getHeight();
		BufferedImage image = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width1; x++)
		{
			for (int y = 0; y < height1; y++)
			{
				image.setRGB(x, y, resMatrix.get(x, y) == true ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
			}
		}
		// 3
		ImageIO.write(image, format, new File(filePath));
		return new ReturnInfo(ErrorInfo.SUCCESS, "生成二维码成功！");
	}

	@Override
	public ReturnInfo parseQr(File imageFile) throws NotFoundException, IOException
	{
		MultiFormatReader formatReader = new MultiFormatReader();
		// File file = new File("D:/new.png");
		BufferedImage image = ImageIO.read(imageFile);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
		Map<DecodeHintType, Object> hints = new Hashtable<>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		Result result = formatReader.decode(binaryBitmap, hints);
		// System.err.println("解析结果：" + result.toString());
		// System.out.println(result.getBarcodeFormat());
		// System.out.println(result.getText());
		return new ReturnInfo(ErrorInfo.SUCCESS, GsonTools.toJson(result));
	}

	@Override
	public ReturnInfo genQr(String content, String filePath)
	{
		File pic = new File(filePath);
		// 如果目录不存在，则创建
		if (!pic.exists() && !pic.getParentFile().exists())
		{
			pic.getParentFile().mkdirs();
		}
		try
		{
			ReturnInfo returnInfo = genQr(content, filePath, 320, 320);
			return returnInfo;
		}
		catch (IOException | WriterException e)
		{
			e.printStackTrace();
			return new ReturnInfo(ErrorInfo.UNKNOWN_ERROR, "生成二维码失败！1");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ReturnInfo(ErrorInfo.UNKNOWN_ERROR, "生成二维码失败！2");
		}
	}

	@Override
	public ReturnInfo updateUserQr(String userId, String serverRootPath)
	{
		if (StringTools.isNull(userId))
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "用户ID不能为空！");
		}
		UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
		if (userInfo == null)
		{
			return new ReturnInfo(ErrorInfo.NOT_EXIST_ERROR, "用户不存在！");
		}
		// long current = System.currentTimeMillis();
		String sortId = "user_qr";
		String picDir = "file/upload/images/" + sortId + "/";
		String realDir = serverRootPath + picDir;
		String fileName = userId + ".jpg";
		File pic = new File(realDir);
		// 如果目录不存在，则创建
		if (!pic.exists())
		{
			pic.mkdirs();
		}
		try
		{
			ReturnInfo returnInfo = genQr(configInfoService.getServerUrl() + "user/register?userId=" + userId, realDir + fileName, 200, 200);
			if (ReturnInfo.isSuccess(returnInfo))
			{
				// userInfo.setImgQr(picDir + fileName);
				// userInfo.setUpdateDateLong(current);
				// userInfoService.updateUserInfo(userInfo);
				Map<String, Object> valueMap = new HashMap<>();
				valueMap.put(IUserInfoDao.QR_URL, picDir + fileName);
				if (!userInfoService.updateValueByUserId(userInfo.getUserId(), valueMap))
				{
					throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户二维码信息失败！");
				}
				userInfo.setQrUrl(picDir + fileName);
			}
		}
		catch (IOException | WriterException e)
		{
			e.printStackTrace();
			return new ReturnInfo(ErrorInfo.UNKNOWN_ERROR, "生成用户二维码失败！1");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ReturnInfo(ErrorInfo.UNKNOWN_ERROR, "生成用户二维码失败！2");
		}
		return new ReturnInfo(ErrorInfo.SUCCESS, GsonTools.toJson(userInfo));
	}
}
