package com.mars.core.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientUtils {
	
	private static Logger logger = Logger.getLogger(HttpClientUtils.class);
	
	public static String doGet(String url){
		return HttpClientUtils.doGet(url,"utf-8");
	}
	
	public static String doGet(String url,String charsetName){
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			
			//设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(10000).build();
			httpGet.setConfig(requestConfig);
			
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = new String(EntityUtils.toByteArray(entity),charsetName);
			}
			httpGet.abort();
		} catch (Exception e) {
			logger.error("HttpClient请求出错！请求地址："+url,e);
		} finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("关闭CloseableHttpClient异常！",e);
			}
		}
		return result;
	}
	
	public static String doPost(String url,Map<String,String> param){
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			
			//设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(10000).build();
			httpPost.setConfig(requestConfig);
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
			for(Map.Entry<String, String> entry : param.entrySet()){
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
			}
			StringEntity e = new UrlEncodedFormEntity(nvps, "UTF-8");
	        httpPost.setEntity(e);
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = new String(EntityUtils.toByteArray(entity),"UTF-8");
			}
			httpPost.abort();
		} catch (Exception e) {
			logger.error("HttpClient请求出错！",e);
		} finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("关闭CloseableHttpClient异常！",e);
			}
		}
		return result;
	}
	
	public static String doPost(String url,String reqJson){
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			
			//设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(10000).build();
			httpPost.setConfig(requestConfig);
			
			httpPost.setEntity(new StringEntity(reqJson, "UTF-8"));
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = new String(EntityUtils.toByteArray(entity),"UTF-8");
			}
			httpPost.abort();
		} catch (Exception e) {
			logger.error("HttpClient请求出错！",e);
		} finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("关闭CloseableHttpClient异常！",e);
			}
		}
		return result;
	}
	
	public static String uploadFile(String url,byte[] bs,String fileName){
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			multipartEntityBuilder.addBinaryBody("media", bs, ContentType.create("application/octet-stream"),fileName);
			
			httpPost.setEntity(multipartEntityBuilder.build());
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = new String(EntityUtils.toByteArray(entity), "UTF-8");
			}
			httpPost.abort();
		} catch (Exception e) {
			logger.error("HttpClient请求出错！",e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("关闭CloseableHttpClient异常！", e);
			}
		}
		return result;
	}

	public static byte[] download(String url) {
		byte[] result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			//设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(10000).build();
			httpGet.setConfig(requestConfig);
			
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toByteArray(entity);
			}
			httpGet.abort();
		} catch (Exception e) {
			logger.error("HttpClient请求出错！",e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("关闭CloseableHttpClient异常！", e);
			}
		}
		return result;
	}
	
}
