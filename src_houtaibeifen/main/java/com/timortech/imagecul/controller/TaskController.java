package com.timortech.imagecul.controller;

import com.timortech.imagecul.base.MessageResponse;
import com.timortech.imagecul.base.ResultResponse;
import com.timortech.imagecul.domain.TaskDomain;
import com.timortech.imagecul.domain.TaskFormDomain;
import com.timortech.imagecul.entity.Task;
import com.timortech.imagecul.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/5
 * @time: 21:30
 * @desc：
 */
@RestController
@RequestMapping(value = "/task")
@Api(tags = "任务",description = "/task/*")
public class TaskController {

	private final TaskService taskService;

	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping(value = "/create/tasks")
	@ApiOperation(value = "创建多个任务")
	public MessageResponse createTasks(@RequestBody List<TaskDomain> tasks){
		taskService.create(tasks);
		return MessageResponse.success();
	}

	@RequestMapping(value = "/run")
	public MessageResponse run(){
		taskService.run();
		return MessageResponse.success();
	}

	@PostMapping(value = "/create")
	@ApiOperation(value = "创建单个任务")
	public ResultResponse<Double> create(@RequestBody TaskFormDomain task) throws IOException {
		double price = taskService.create(task);
		return new ResultResponse<>(price);
	}

	@GetMapping(value = "/start")
	@ApiOperation(value = "重新开始某个任务")
	public MessageResponse start(@RequestParam(value = "taskId") Integer taskId){
		taskService.start(taskId);
		return MessageResponse.success();
	}

	@GetMapping(value = "/query")
	@ApiOperation(value = "查询计算状态")
	public ResultResponse<List<TaskDomain>> query(){
		List<TaskDomain> taskDomains = taskService.qury();
		return new ResultResponse<>(taskDomains);
	}

	@GetMapping(value = "/finish")
	@ApiOperation(value = "结束计算")
	public MessageResponse finish(@RequestParam(value = "taskId") Integer taskId,
	                              @RequestParam(value = "iserror") boolean iserror,
	                              @RequestParam(value = "taskpath",required = false) String taskPath){
		taskService.finish(taskId,iserror,taskPath);
		return MessageResponse.success();
	}

	@GetMapping(value = "/result")
	@ApiOperation(value = "查询结算结果")
	public ResultResponse<List<TaskDomain>> getResult(){
		List<TaskDomain> taskDomains = taskService.getResult();
		return new ResultResponse<>(taskDomains);
	}
}
