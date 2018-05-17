package com.lrcall.lrweb.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.lrcall.common.utils.GsonTools;
import com.lrcall.common.utils.StringTools;

public class HttpTools extends com.lrcall.common.utils.HttpTools
{
	/**
	 * HTTP POST方式提交，JSON格式
	 * 
	 * @param strUrl
	 * @param params
	 * @return
	 */
	public static String doJsonPost(String strUrl, Map<String, String> params, String encode)
	{
		System.out.println("strUrl:" + strUrl + ",params:" + GsonTools.toJson(params) + ",encode:" + encode);
		StringBuffer sb = new StringBuffer();
		try
		{
			URL postUrl = new URL(strUrl);
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), UTF8); // utf-8编码
			out.append(GsonTools.toJson(params));
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));// 设置编码,否则中文乱码
			String line = "";
			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
			reader.close();
			connection.disconnect();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String result = sb.toString();
		System.out.println("返回值:" + result);
		return result;
	}

	/**
	 * 模拟表单提交
	 * 
	 * @param strUrl
	 * @param textMap
	 * @param fileElementName
	 * @param file
	 * @return
	 */
	public static String doHttpPostFile(String strUrl, Map<String, String> textMap, String fileElementName, MultipartFile file)
	{
		System.out.println("strUrl:" + strUrl + ",params:" + textMap);
		StringBuffer sb = new StringBuffer();
		String BOUNDARY = "---------------------------123821742118716";
		String fileName = file.getOriginalFilename();
		try
		{
			URL postUrl = new URL(strUrl);
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Connection", "Keep-Alive");
			// connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY); // 设置发送数据的格式"multipart/form-data;
																										// boundary="
																										// +
																										// BOUNDARY
			// connection.connect();
			BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());
			if (textMap != null)
			{
				StringBuffer strBuf = new StringBuffer();
				Iterator<?> iter = textMap.entrySet().iterator();
				while (iter.hasNext())
				{
					Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null)
					{
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}
			String contentType = file.getContentType();
			if (!StringTools.isNull(contentType))
			{
				if (fileName.endsWith(".png"))
				{
					contentType = "image/png";
				}
				else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".jpe"))
				{
					contentType = "image/jpeg";
				}
				else if (fileName.endsWith(".gif"))
				{
					contentType = "image/gif";
				}
				else if (fileName.endsWith(".ico"))
				{
					contentType = "image/image/x-icon";
				}
			}
			if (StringTools.isNull(contentType))
			{
				contentType = "application/octet-stream";
			}
			StringBuffer strBuf = new StringBuffer();
			strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
			strBuf.append("Content-Disposition: form-data; name=\"" + fileElementName + "\"; filename=\"" + fileName + "\"\r\n");
			strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
			System.out.println("文件域：" + strBuf.toString());
			out.write(strBuf.toString().getBytes());
			BufferedInputStream fileInputStream = new BufferedInputStream(file.getInputStream());
			byte[] buf = new byte[1024];
			int length = 0;
			length = fileInputStream.read(buf);
			while (length != -1)
			{
				out.write(buf, 0, length);
				length = fileInputStream.read(buf);
			}
			fileInputStream.close();
			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();
			// 读取返回数据
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), HttpTools.UTF8));// 设置编码,否则中文乱码
			String line = "";
			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
			reader.close();
			connection.disconnect();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String result = sb.toString();
		System.out.println("返回值:" + result);
		return result;
	}
	/**
	 * 模拟表单提交
	 * 
	 * @param strUrl
	 * @param textMap
	 * @param base64Img
	 * @param file
	 * @return
	 */
	// public static String doHttpPostFile(String strUrl, Map<String, String> textMap, String base64Img)
	// {
	// System.out.println("strUrl:" + strUrl + ",params:" + textMap);
	// StringBuffer sb = new StringBuffer();
	// String BOUNDARY = "---------------------------123821742118716";
	// try
	// {
	// URL postUrl = new URL(strUrl);
	// HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
	// connection.setDoOutput(true);
	// connection.setDoInput(true);
	// connection.setRequestMethod("POST");
	// connection.setUseCaches(false);
	// connection.setInstanceFollowRedirects(true);
	// connection.setRequestProperty("Connection", "Keep-Alive");
	// // connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
	// connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
	// // 设置发送数据的格式"multipart/form-data;boundary=" + BOUNDARY
	// // connection.connect();
	// BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());
	// if (textMap != null)
	// {
	// StringBuffer strBuf = new StringBuffer();
	// Iterator<?> iter = textMap.entrySet().iterator();
	// while (iter.hasNext())
	// {
	// Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
	// String inputName = (String) entry.getKey();
	// String inputValue = (String) entry.getValue();
	// if (inputValue == null)
	// {
	// continue;
	// }
	// strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
	// strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
	// strBuf.append(inputValue);
	// }
	// out.write(strBuf.toString().getBytes());
	// }
	// String contentType = "image/jpeg";
	// StringBuffer strBuf = new StringBuffer();
	// strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
	// strBuf.append("Content-Disposition: form-data; name=\"imgFileName\"; filename=\"imgName.jpg\"\r\n");
	// strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
	// strBuf.append(base64Img);
	// System.out.println("文件域：" + strBuf.toString());
	// out.write(strBuf.toString().getBytes());
	// byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
	// out.write(endData);
	// out.flush();
	// out.close();
	// // 读取返回数据
	// BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), HttpTools.UTF8));// 设置编码,否则中文乱码
	// String line = "";
	// while ((line = reader.readLine()) != null)
	// {
	// sb.append(line);
	// }
	// reader.close();
	// connection.disconnect();
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// String result = sb.toString();
	// System.out.println("返回值:" + result);
	// return result;
	// }
}
