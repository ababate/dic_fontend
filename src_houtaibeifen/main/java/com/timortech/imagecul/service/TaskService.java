package com.timortech.imagecul.service;

import com.timortech.imagecul.domain.TaskDomain;
import com.timortech.imagecul.domain.TaskFormDomain;

import java.io.IOException;
import java.util.List;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:55
 * @desc：
 */

public interface TaskService {

	void create(List<TaskDomain> taskDomains);

	double create(TaskFormDomain task) throws IOException;

	void start(Integer taskId);

	List<TaskDomain> qury();

	void finish(Integer taskId, boolean iserror, String taskPath);

	List<TaskDomain> getResult();

	void run();
}
