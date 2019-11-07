package com.imgCul.utils;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/9/5
 * @time: 0:17
 * @desc：
 */
@Slf4j
public class CalThread implements  Runnable{

	String imgPath1;
	String imgPath2;
	String dirPath;
	// int type;
	boolean hasdots;
	String dotsPath;
	Long taskId;
	Long imgId;
	String params;

	public void setTaskPath(String taskPath) {
		this.taskPath = taskPath;
	}

	String taskPath;
	CalThread(){

	}



	public void setData(String imgPath1, String imgPath2, String dirpath, /*int type,*/ boolean hasdots, String dotsPath, Long taskId, String params){
		this.dirPath = dirpath;
		this.imgPath1 = imgPath1;
		this. imgPath2 = imgPath2;
		// this.type = type;
		this.hasdots = hasdots;
		this.dotsPath = dotsPath;
		this.taskId = taskId;
		this.params = params;
	}


	@Override
	public void run() {

		try {
			//调用某某计算方法
			// doing~~~~~~~
//			Thread.sleep(10000);
			log.info("start imgCul");
			culFunc.imgCul(this.imgPath1,this.imgPath2,this.dirPath,/*this.type,*/this.hasdots,this.dotsPath,this.params);
			log.info("end imgCul");
			//结束任务
			Dispatcher.getInstance().finishTask(false,taskId);
			String url = Config.requestUrl + "/task/finish/?taskId=" + imgId + "&iserror=false"+"&taskpath="+taskPath;
			String zipPath = this.dirPath + "\\all.zip";
			File2Zip.toZip(this.dirPath, zipPath,true);
			try {
				log.info(url);
				HttpClientUtils.httpGet(url);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}catch (Exception e){
			File file=new File(this.dirPath + "\\YES.txt");
			if (file.exists()) {
				log.info("-------------------文件存在");
				//结束任务
				Dispatcher.getInstance().finishTask(false,taskId);
				String url = Config.requestUrl + "/task/finish/?taskId=" + imgId + "&iserror=false"+"&taskpath="+taskPath;
				String zipPath = this.dirPath + "\\all.zip";
				File2Zip.toZip(this.dirPath, zipPath,true);
				try {
					log.info(url);
					HttpClientUtils.httpGet(url);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				log.info("-------------------文件bu存在");
				Dispatcher.getInstance().finishTask(true,taskId);
				String url = Config.requestUrl + "/task/finish/?taskId=" + imgId + "&iserror=true";
				try {
					log.info(url);
					HttpClientUtils.httpGet(url);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		}
	}

	public void setImgPath1(String imgPath1) {
		this.imgPath1 = imgPath1;
	}

	public void setImgPath2(String imgPath2) {
		this.imgPath2 = imgPath2;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	// public void setType(int type) {
	// 	this.type = type;
	// }

	public void setHasdots(boolean hasdots) {
		this.hasdots = hasdots;
	}

	public void setDotsPath(String dotsPath) {
		this.dotsPath = dotsPath;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}

	public void setParams(String params) {
		this.params = params;
	}
}
