package com.lrcall.lrweb.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.lrcall.common.utils.LogTools;

public class HttpsTools
{
	/**
	 * 获得KeyStore.
	 * 
	 * @param keyStorePath
	 *            密钥库路径
	 * @param password
	 *            密码
	 * @return 密钥库
	 * @throws Exception
	 */
	public static KeyStore getKeyStore(String password, String keyStorePath) throws Exception
	{
		// 实例化密钥库 KeyStore用于存放证书，创建对象时 指定交换数字证书的加密标准
		// 指定交换数字证书的加密标准
		KeyStore ks = KeyStore.getInstance("JKS");
		// 获得密钥库文件流
		FileInputStream is = new FileInputStream(keyStorePath);
		// 加载密钥库
		ks.load(is, password.toCharArray());
		// 关闭密钥库文件流
		is.close();
		return ks;
	}

	/**
	 * 获得SSLSocketFactory.
	 * 
	 * @param password
	 *            密码
	 * @param keyStorePath
	 *            密钥库路径
	 * @param trustStorePath
	 *            信任库路径
	 * @return SSLSocketFactory
	 * @throws Exception
	 */
	public static SSLContext getSSLContext(String password, String keyStorePath, String trustStorePath) throws Exception
	{
		// 实例化密钥库 KeyManager选择证书证明自己的身份
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		// 获得密钥库
		KeyStore keyStore = getKeyStore(password, keyStorePath);
		// 初始化密钥工厂
		keyManagerFactory.init(keyStore, password.toCharArray());
		// 实例化信任库 TrustManager决定是否信任对方的证书
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		// 获得信任库
		KeyStore trustStore = getKeyStore(password, trustStorePath);
		// 初始化信任库
		trustManagerFactory.init(trustStore);
		// 实例化SSL上下文
		SSLContext ctx = SSLContext.getInstance("TLS");
		// 初始化SSL上下文
		ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
		// 获得SSLSocketFactory
		return ctx;
	}

	/**
	 * 发送请求.
	 * 
	 * @param httpsUrl
	 *            请求的地址
	 * @param xmlStr
	 *            请求的数据
	 */
	public static String post(String p12Path, String password, String httpsUrl, String xmlStr)
	{
		LogTools.getInstance().debug("https post", "httpsUrl:" + httpsUrl + ",xmlStr:" + xmlStr);
		String result = "";
		try
		{
			char[] pwd = password.toCharArray();
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			ResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource resource = resourceLoader.getResource(p12Path);
			keyStore.load(resource.getInputStream(), pwd);// 设置证书密码
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, pwd).build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();
			HttpPost httpPost = new HttpPost(httpsUrl);
			// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
			StringEntity postEntity = new StringEntity(xmlStr, "UTF-8");
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.setEntity(postEntity);
			// 设置请求器的配置
			httpPost.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("result:" + result);
		return result;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		// 密码
		String password = "1244450902";
		String path = "cert/apiclient_cert.p12";// AutoGeneration.ROOT_PATH +
		// 密钥库
		// String keyStorePath = "tomcat.keystore";
		// 信任库
		// String trustStorePath = "tomcat.keystore";
		// 本地起的https服务
		String httpsUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
		// 传输文本
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fruitShop><fruits><fruit>&lt;kind>萝卜</kind></fruit><fruit><kind>菠萝</kind></fruit></fruits></fruitShop>";
		// SSLContext sslContext = HttpsTools.initHttpsURLConnection(password, path, path);
		// 发起请求
		HttpsTools.post(path, password, httpsUrl, xmlStr);
	}
}
