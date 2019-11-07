package com.imgCul.controller;

import com.imgCul.entity.Response;
import com.imgCul.entity.Task;
import com.imgCul.entity.User;
import com.imgCul.services.ImgCulService;
import com.imgCul.utils.Config;
import com.imgCul.utils.Dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@Api("基本方法")
public class BaseController {


	@Autowired
	private ImgCulService service;

	@ApiOperation("获取当前任务队列的任务")
	@GetMapping(value = "/query")
	public Response query(){
		Response res = new Response();
		res.setData(service.query());
		return res;
	}

	@PostMapping(value = "/add")
	@ApiOperation("添加任务")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "form", name = "img1", dataType = "file", required = true,value = "图片1"),
			@ApiImplicitParam(paramType = "form", name = "img2", dataType = "file", required = true,value = "图片2"),
			@ApiImplicitParam(paramType = "form", name = "point", dataType = "file", required = true,value = "点图"),
			@ApiImplicitParam(paramType = "form", name = "taskId", dataType = "long", required = true,value = "图片组id")

	})
	public Response add(MultipartFile img1,
	                MultipartFile img2,
                    MultipartFile point,
                    @RequestParam("taskId") Long taskId,
                    @RequestParam("params") String params
	                ){
		Response res = new Response();
		try {
			service.insert(taskId,img1,img2,point,params);
			return res;
		}catch (Exception e){
			res.setCode(500);
			res.setMsg(e.toString());
			return  res;
		}
	}

	@PostMapping(value = "/test")
	@ApiOperation("测试接口")
	public Response test(@RequestParam("state") String state){
		Response res = new Response();
//		service.doing(1l);
//		service.error(2l);
//		service.finish(3l);
//		res.setData(service.freeTask());
//		Dispatcher.getInstance().startTask();
//		Dispatcher.getInstance().startTask();
//		Dispatcher.getInstance().startTask();
//		Dispatcher.getInstance().startTask();
		res.setData(service.querysql(state));
		res.setMsg(Config.filePath);
		return res;
	}
//	@ApiOperation("你好")
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "body",name = "user",dataType = "User",required = false,value = "id值",defaultValue = "123"),
//			@ApiImplicitParam(paramType = "query",name = "address",dataType = "String",required = false,value = "id值",defaultValue = "123")
//	})
//	@PostMapping(value={"/say"})
//	public String say(@RequestParam("address") String address){
//	//public String say(@RequestBody User user){
//
//		return address+ Config.filePath;
//	}

}























