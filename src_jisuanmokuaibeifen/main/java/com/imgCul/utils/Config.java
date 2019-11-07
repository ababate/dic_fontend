package com.imgCul.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/9/4
 * @time: 18:05
 * @desc：
 */
@Component
public class Config {

	// 任务状态
	public static Long STATE_UNFINISHED = 0l; // 未完成
	public static Long STATE_FINISHED = 1l;   // 已完成
	public static Long STATE_DOING = 2l;      // 执行中
	public static Long STATE_ERROR = 3l;      // 错误


	//文件存放路径
	public static String filePath;
	public static String rootPath;
	public static String requestUrl;

	public static String getFilePath() {
		return filePath;
	}

	public static String getRootPath() {
		return rootPath;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	@Value("${fileroot}")
	public void setFilePath(String filePath) {
		Config.filePath = filePath;
	}

	@Value("${demoroot}")
	public void setRootPath(String rootPath) { Config.rootPath = rootPath; }

	@Value("${requestUrl}")
	public void setRequestUrl(String requestUrl) { this.requestUrl = requestUrl; }
}
