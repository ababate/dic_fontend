package com.imgCul.utils;

import com.imgCul.entity.Task;
import com.imgCul.services.ImgCulService;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/9/5
 * @time: 0:27
 * @desc：
 */
@Slf4j
public class Dispatcher {

	//单例对象
	public static Dispatcher instance = null;
	// 当前线程数
	private Integer TASKNUM = 0;
	// 最大线程数
	private Integer TASKMAX = 1;
	@Autowired
	ImgCulService service;

	// 获取单例
	public static Dispatcher getInstance(){
		if(instance == null){
			instance = new Dispatcher();
			instance.service = SpringUtils.getBean(ImgCulService.class);
		}
		return instance;
	}

	// 当前线程数加1
	public boolean startTask(){
		synchronized(TASKNUM){
			// 如果没有空余线程
			if(TASKNUM + 1 > TASKMAX){
				log.info("没有空余线程了");
				return false;
			}
			// 如果没有空余任务
			List<Task> tasks = service.freeTask();
			if(tasks.size() == 0){
				log.info("没有空余任务了");
				return false;
			}

			TASKNUM++;
			CalThread calThread = new CalThread();
			String dirPath = Config.filePath + File.separator + tasks.get(0).dirName;
			calThread.setDirPath(dirPath);
			calThread.setImgPath1(dirPath + File.separator + tasks.get(0).imgName1);
			calThread.setImgPath2(dirPath + File.separator + tasks.get(0).imgName2);
			// calThread.setType(1);
			if(tasks.get(0).pointName.equals("NOFILE")){
				calThread.setHasdots(false);
			}else {
				calThread.setHasdots(true);
			}
			calThread.setDotsPath(dirPath + File.separator + tasks.get(0).pointName);
			calThread.setTaskId(tasks.get(0).id);
			calThread.setTaskPath(tasks.get(0).dirName);
			calThread.setImgId(tasks.get(0).imgId);
			calThread.setParams(tasks.get(0).params);
			service.doing(tasks.get(0).id);
			log.info("开始任务："+tasks.get(0).id);
			Thread thread = new Thread(calThread);
			thread.start();
			return true;
		}
	}

	// 当前线程数减1
	public void finishTask(boolean isError,Long taskId){
		synchronized (TASKNUM){
			// 如果任务完成失败
			if(isError){
				service.error(taskId);
			}else {
				// 任务完成成功
				service.finish(taskId);
				log.info("任务完成："+taskId);
			}
			TASKNUM--;
		}
		startTask();
	}



}
