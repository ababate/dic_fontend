package com.timortech.imagecul.controller;

import com.timortech.imagecul.base.MessageResponse;
import com.timortech.imagecul.base.ResultResponse;
import com.timortech.imagecul.domain.ImageDomain;
import com.timortech.imagecul.domain.ProjectDomain;
import com.timortech.imagecul.service.ImageService;
import com.timortech.imagecul.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2019/1/16
 * @time: 1:56
 * @desc：
 */

@Slf4j
@RestController
@RequestMapping(value = "/project")
@Api(tags = "项目",description = "/project/*")
public class ProjectController {

	private final ProjectService projectService;

	private final ImageService imageService;

	@Autowired
	public ProjectController(ProjectService projectService, ImageService imageService) {
		this.projectService = projectService;
		this.imageService = imageService;
	}

	@GetMapping(value = "/get")
	public ResultResponse<ProjectDomain> get(@RequestParam(value = "pid") int projectId){
		ProjectDomain projectDomain = projectService.findById(projectId);
		return new ResultResponse<>(projectDomain);
	}

	@GetMapping(value = "/get/user")
	public ResultResponse<List<ProjectDomain>> getByUser(){
		List<ProjectDomain> projectDomains = projectService.findProjectsByUserId();
		//projectDomains.add(getData());
		return new ResultResponse<>(projectDomains);
	}

	@GetMapping(value = "/open")
	public MessageResponse open(@RequestParam(value = "id") int projectId){
		try {
			projectService.open(projectId);
			return MessageResponse.success();
		}catch (Exception e){
			return new MessageResponse(-1,e.getMessage());
		}
	}

	@GetMapping(value = "/setParams")
	public MessageResponse setParams(@RequestParam(value = "params") String params){
		try {
			params = params.replace("dd","{").replace("bb","}");
			projectService.setParams(params);
			return MessageResponse.success();
		}catch (Exception e){
			return new MessageResponse(-1,e.getMessage());
		}
	}

	@GetMapping(value = "/saveImage")
	public MessageResponse saveImage(@RequestParam(value = "type") String type,
	                                 @RequestParam(value = "imageid") Integer imageId){
		try {
			projectService.saveImage(type,imageId);
			return MessageResponse.success();
		}catch (Exception e){
			return new MessageResponse(-1,e.getMessage());
		}
	}

	@GetMapping(value = "/create")
	public ResultResponse<ProjectDomain> create(@RequestParam(value = "name") String name){
		ProjectDomain projectDomain = projectService.create(name);
		return new ResultResponse<>(projectDomain);
	}

	@GetMapping(value = "/list/images")
	@ApiOperation(value = "展示项目下的图片文件")
	public ResultResponse<List<ImageDomain>> getImages(@RequestParam(value = "id") Integer projectId){
		List<ImageDomain> images = imageService.getImages(projectId);
		return new ResultResponse<>(images);
	}

	/*private ProjectDomain getData(){
		ProjectDomain projectDomain = new ProjectDomain();
		projectDomain.setId(3);
		projectDomain.setName("initial project");
		projectDomain.setUserId(1);
		projectDomain.setCreateTime(new Date());
		projectDomain.setUpdateTime(new Date());
		projectDomain.setReferenceNum(4);
		projectDomain.setDeformedNum(8);
		projectDomain.setTaskNum(32);
		projectDomain.setResultNum(28);
		projectDomain.setLambda("3.12");
		projectDomain.setBeta("23.3");
		projectDomain.setPyramidLevels("8.21");
		projectDomain.setPyramidFactor("2.12");
		projectDomain.setConvergenceAccuracy("92");
		projectDomain.setCoreNumber("2");
		projectDomain.setRelevanceThreshold("32");
		//projectDomain.setAlgorithm("Accurate TV-L1");
		projectDomain.setChooseVar("主应变");
		projectDomain.setCustomVar("灰度");
		return  projectDomain;
	}*/
}
