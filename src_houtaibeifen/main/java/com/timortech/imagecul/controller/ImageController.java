package com.timortech.imagecul.controller;

import com.timortech.imagecul.base.MessageResponse;
import com.timortech.imagecul.base.ResultResponse;
import com.timortech.imagecul.base.TimorException;
import com.timortech.imagecul.domain.ImageDomain;
import com.timortech.imagecul.enums.ExceptionEnum;
import com.timortech.imagecul.service.FileService;
import com.timortech.imagecul.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/5
 * @time: 21:31
 * @desc：
 */
@Slf4j
@RestController
@RequestMapping(value = "/image")
@Api(tags = "图片",description = "/image/*")
public class ImageController {

	private final ImageService imageService;

	private final FileService fileService;

	@Autowired
	public ImageController(ImageService imageService, FileService fileService) {
		this.imageService = imageService;
		this.fileService = fileService;
	}

	@PostMapping(value = "/upload")
	@ApiOperation(value = "图片上传")
	public ResultResponse<ImageDomain> upload(MultipartFile image){
		try {
			ImageDomain imageDomain = imageService.upload(image);
			return new ResultResponse<>(imageDomain);
		} catch (IOException e) {
			throw new TimorException(ExceptionEnum.FILE_UPLOAD_FAIL);
		}
	}

	@PostMapping(value = "/test")
	public MessageResponse test(MultipartFile imagea,MultipartFile imageb,Integer taskId) throws IOException {
		fileService.saveImage(imagea);
		fileService.saveImage(imageb);
		log.info("任务id:{}",taskId);
		return MessageResponse.success();
	}

	@GetMapping(value = "/delete")
	@ApiOperation(value = "删除文件")
	public MessageResponse delete(@RequestParam(value = "id") Integer id){
		imageService.delete(id);
		return MessageResponse.success();
	}
}
