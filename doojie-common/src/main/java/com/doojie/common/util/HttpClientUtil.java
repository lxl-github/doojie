package com.doojie.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 功能概述：http 请求客户端工具 <br/>
 * 创建时间：2015年8月27日上午11:23:16 <br/>
 * 修改记录：
 * 
 * @author lixiaoliang
 */
public class HttpClientUtil {

	// static Properties properties =
	// PropertiesUtil.getProperties("config.properties");

	private static PoolingHttpClientConnectionManager connectionManager = null;
	private static HttpClientBuilder httpBulder = null;
	private static RequestConfig requestConfig = null;

	private static int MAXCONNECTION = 10;

	private static int DEFAULTMAXCONNECTION = 5;

	// private static String IP = properties.getProperty("weixin_ip");
	private static int PORT = 80;

	static {
		// 设置http的状态参数
		requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000).build();

		HttpHost target = new HttpHost("api.weixin.qq.com", PORT);
		connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(MAXCONNECTION);
		connectionManager.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);
		connectionManager.setMaxPerRoute(new HttpRoute(target), 20);
		httpBulder = HttpClients.custom();
		httpBulder.setConnectionManager(connectionManager);
	}

	private static CloseableHttpClient getConnection() {
		CloseableHttpClient httpClient = httpBulder.build();
		httpClient = httpBulder.build();
		return httpClient;
	}

	private static HttpUriRequest getRequestMethod(Map<String, String> map, String url, String method) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<Map.Entry<String, String>> entrySet = map.entrySet();
		for (Map.Entry<String, String> e : entrySet) {
			String name = e.getKey();
			String value = e.getValue();
			NameValuePair pair = new BasicNameValuePair(name, value);
			params.add(pair);
		}
		HttpUriRequest reqMethod = null;
		if ("post".equals(method)) {
			reqMethod = RequestBuilder.post().setUri(url)
					.addParameters(params.toArray(new BasicNameValuePair[params.size()])).setConfig(requestConfig)
					.build();
		} else if ("get".equals(method)) {
			reqMethod = RequestBuilder.get().setUri(url)
					.addParameters(params.toArray(new BasicNameValuePair[params.size()])).setConfig(requestConfig)
					.build();
		}
		return reqMethod;
	}

	public static String post(Map<String, String> map, String url) throws IOException {
		HttpClient client = getConnection();
		HttpUriRequest post = getRequestMethod(map, url, "post");
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			String message = EntityUtils.toString(entity, "utf-8");
			return message;
		} else {
			return null;
		}
	}

	public static String get(Map<String, String> map, String url) throws IOException {
		HttpClient client = getConnection();
		HttpUriRequest post = getRequestMethod(map, url, "get");
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			String message = EntityUtils.toString(entity, "utf-8");
			return message;
		} else {
			return null;
		}
	}

	public static String getUrl(HttpServletRequest request) {
		StringBuffer requestUrl = request.getRequestURL();
		String url = requestUrl.toString();
		url.concat(url.indexOf("#") >= 0 ? url.substring(0, url.indexOf("#")) : url);
		return url;
	}

	/**
	 * 方法功能说明
	 * <br>
	 * 创建时间：2015年10月2日下午5:14:28
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param requestUrl	请求地址
	 * @param requestMethod 请求方式(get，post)
	 * @param params	(请求参数)
	 * @return
	 *
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String params) {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if (null != params) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(params.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			System.out.println("连接超时：" + ce);
		} catch (Exception e) {
			System.out.println("https请求异常：" + e);
		}
		return null;
	}

	public static void main(String args[]) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", "wxabfcd15f212155f9");
		map.put("secret", "7c383bf112cdd5f35273b3bc284dc55f");
		try {
			String message = get(map, "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential");
			Map<String, Object> map2 = JsonUtil.fromJson(message, Map.class);
			System.out.println(map2.get("access_token"));
			System.out.println(map2.get("expires_in"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
