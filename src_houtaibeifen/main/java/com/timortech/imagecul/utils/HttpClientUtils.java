package com.timortech.imagecul.utils;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.timortech.imagecul.base.TimorException;
import com.timortech.imagecul.enums.ExceptionEnum;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/7
 * @time: 1:05
 * @desc：
 */
@Slf4j
public class HttpClientUtils{


	/**
	 * 发送get请求，获取json结果
	 * @param url
	 * @return
	 */
	public static String httpGet(String url){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				String result = EntityUtils.toString(httpResponse.getEntity());
				httpClient.close();
				return result;
			}
			else {
				httpClient.close();
				throw new TimorException(ExceptionEnum.TASK_QUERY_FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new TimorException(ExceptionEnum.TASK_QUERY_FAIL);
		}
	}

	/**
	 * 上传本地图片至远程服务器
	 * @param imageA
	 * @param imageB
	 * @param taskId
	 * @param postUrl
	 * @throws IOException
	 */
	public static void uploadFile(File imageA,File imageB,File pointFile,Integer taskId,String postUrl,String params) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(postUrl);

		FileBody fileBodyA = new FileBody(imageA);
		FileBody fileBodyB = new FileBody(imageB);
		FileBody filePoint = new FileBody(pointFile);

		//设置传输参数，编码和浏览器兼容模式
		MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create()
				.setCharset(Charset.forName("UTF-8")).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		entityBuilder.addPart("img1",fileBodyA);
		entityBuilder.addPart("img2",fileBodyB);
		entityBuilder.addPart("point",filePoint);
		//设置额外参数
		entityBuilder.addPart("taskId",new StringBody(taskId.toString(),ContentType.create("text/plain",Consts.UTF_8)));
		entityBuilder.addPart("params",new StringBody(params,ContentType.create("text/plain",Consts.UTF_8)));

		//发起请求
		HttpEntity httpEntity = entityBuilder.build();
		httpPost.setEntity(httpEntity);
		try {
			log.info("【post请求】{}",httpPost.getURI());
			CloseableHttpResponse response = httpClient.execute(httpPost);
			log.info("【文件上传】上传文件：{},返回码：{}",imageA.getName(),response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode()!=200){
				//文件上传失败
				throw new IOException();
			}
			HttpEntity resEntity = response.getEntity();
			log.info("【文件上传】返回内容：{}",EntityUtils.toString(resEntity,Charset.forName("UTF-8")));
			EntityUtils.consume(resEntity);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
