package com.imgCul.utils;


import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/9/7
 * @time: 14:59
 * @desc：
 */

public class HttpClientUtils {
	public static String httpGet(String url) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				String result = EntityUtils.toString(httpResponse.getEntity());
				return result;
			}
			else {
				throw new Exception();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String httpPost(String url, Map<String,String> maps) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost();
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		for(Map.Entry<String,String> entry : maps.entrySet()){
			formParams.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
		}
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formParams,"UTF-8");
			httpPost.setEntity(uefEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				String result = EntityUtils.toString(httpResponse.getEntity());
				return result;
			}
			else {
				throw new Exception();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String httpPost(String url, Map<String,String> args, Map<String,File> files) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost();
		//设置传输参数，编码和浏览器兼容模式
		MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create()
				.setCharset(Charset.forName("UTF-8")).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

		try {
			for(Map.Entry<String,String> entry: args.entrySet()){
				entityBuilder.addPart(entry.getKey(),new StringBody(entry.getValue(),ContentType.create("text/plain",Consts.UTF_8)));
			}
			for(Map.Entry<String,File> entry: files.entrySet()){
				entityBuilder.addPart(entry.getKey(),new FileBody(entry.getValue()));
			}
			httpPost.setEntity(entityBuilder.build());
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				String result = EntityUtils.toString(httpResponse.getEntity());
				return result;
			}
			else {
				throw new Exception();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
