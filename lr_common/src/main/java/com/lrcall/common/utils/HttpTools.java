package com.lrcall.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpTools
{
	public static final String UTF8 = "UTF-8";

	/**
	 * 取http页面的body部分
	 * 
	 * @param htmlContent
	 * @return
	 */
	public static String parseHtml(String htmlContent)
	{
		if (StringTools.isNull(htmlContent))
		{
			return htmlContent;
		}
		String result = htmlContent.trim();
		String comp = "<body>";
		if (result.indexOf(comp) >= 0)
		{
			result = result.substring(result.indexOf(comp) + comp.length());
		}
		comp = "</body>";
		if (result.indexOf(comp) >= 0)
		{
			result = result.substring(0, result.indexOf(comp));
		}
		return result;
	}

	/**
	 * HTTP GET方式提交，UTF-8编码
	 * 
	 * @param strUrl
	 * @return
	 */
	public static String doGet(String strUrl)
	{
		return doGet(strUrl, UTF8);
	}

	/**
	 * HTTP GET方式提交
	 * 
	 * @param strUrl
	 * @param encode
	 * @return
	 */
	public static String doGet(String strUrl, String encode)
	{
		System.out.println("strUrl:" + strUrl);
		StringBuffer sb = new StringBuffer();
		try
		{
			URL url = new URL(strUrl);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), encode));
			String line = "";
			while ((line = in.readLine()) != null)
			{
				sb.append(line);
			}
			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String result = sb.toString();
		System.out.println("result:" + result);
		return result;
	}

	/**
	 * HTTP POST方式提交，UTF-8编码
	 * 
	 * @param strUrl
	 * @param params
	 * @return
	 */
	public static String doPost(String strUrl, String params)
	{
		return doPost(strUrl, params, UTF8);
	}

	/**
	 * HTTP POST方式提交
	 * 
	 * @param strUrl
	 * @param params
	 * @return
	 */
	public static String doPost(String strUrl, String params, String encode)
	{
		System.out.println("strUrl:" + strUrl + ",params:" + params + ",encode:" + encode);
		StringBuffer sb = new StringBuffer();
		try
		{
			URL postUrl = new URL(strUrl);// 打开连接
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			// 设置是否向connection输出，因为这个是post请求，参数要放在 http正文内，因此需要设为true
			connection.setDoOutput(true);
			// Read from the connection. Default is true.
			connection.setDoInput(true);
			// Set the post method. Default is GET
			connection.setRequestMethod("POST");
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			// URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
			// connection.setFollowRedirects(true);
			// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
			connection.setInstanceFollowRedirects(true);
			// Set the content type to urlencoded,
			// because we will write
			// some URL-encoded content to the
			// connection. Settings above must be set before connect!
			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
			// 进行编码
			// connection.setRequestProperty("Content-Type", "text/xml");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
			String content = params;
			// "key=j0r53nmbbd78x7m1pqml06u2&type=1&toemail=jiucool@gmail.com" + "&activatecode=" +
			// URLEncoder.encode("久酷博客", "utf-8");
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
			out.writeBytes(content);
			// out.writeUTF(content);
			out.flush();
			out.close(); // flush and close
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));// 设置编码,否则中文乱码
			String line = "";
			while ((line = reader.readLine()) != null)
			{
				// line = new String(line.getBytes(), "utf-8");
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

	public static String doWxPost(String strUrl, String params)
	{
		System.out.println("strUrl:" + strUrl + ",params:" + params);
		String result = "";
		try
		{
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();
			HttpPost httpPost = new HttpPost(strUrl);
			// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
			StringEntity postEntity = new StringEntity(params, "UTF-8");
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.setEntity(postEntity);
			// 设置请求器的配置
			httpPost.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("返回值:" + result);
		return result;
	}

	/**
	 * HTTP POST方式提交，JSON格式
	 * 
	 * @param strUrl
	 * @param params
	 * @return
	 */
	public static String doJsonPost(String strUrl, String params, String encode)
	{
		System.out.println("strUrl:" + strUrl + ",params:" + params + ",encode:" + encode);
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
			out.append(params);
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
	public static String doHttpPostFile(String strUrl, Map<String, String> textMap, String fileElementName, File file)
	{
		System.out.println("strUrl:" + strUrl + ",params:" + textMap);
		StringBuffer sb = new StringBuffer();
		String BOUNDARY = "---------------------------123821742118716";
		String fileName = file.getName();
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
			String contentType = "image/jpeg";
			StringBuffer strBuf = new StringBuffer();
			strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
			strBuf.append("Content-Disposition: form-data; name=\"" + fileElementName + "\"; filename=\"" + fileName + "\"\r\n");
			strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
			System.out.println("文件域：" + strBuf.toString());
			out.write(strBuf.toString().getBytes());
			BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));
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
}
