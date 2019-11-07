package com.timortech.imagecul.controller;

import com.timortech.imagecul.base.MessageResponse;
import com.timortech.imagecul.base.ResultResponse;
import com.timortech.imagecul.domain.ImageDomain;
import com.timortech.imagecul.entity.Folder;
import com.timortech.imagecul.service.FolderService;
import com.timortech.imagecul.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/28
 * @time: 21:45
 * @desc：
 */
@Slf4j
@RestController
@RequestMapping(value = "/folder")
@Api(tags = "文件夹",description = "/folder/*")
public class FolderController {

	private final FolderService folderService;

	private final ImageService imageService;

	@Autowired
	public FolderController(FolderService folderService, ImageService imageService) {
		this.folderService = folderService;
		this.imageService = imageService;
	}

	@PostMapping(value = "/list")
	@ApiOperation(value = "展示文件夹")
	public ResultResponse<List<Folder>> getFolders(){
		List<Folder> folders = folderService.getFolders();
		return new ResultResponse<>(folders);
	}

	@GetMapping(value = "/list/images")
	@ApiOperation(value = "展示文件夹里的文件")
	public ResultResponse<List<ImageDomain>> getImages(@RequestParam(value = "folderid") Integer folderId){
		List<ImageDomain> images = imageService.getImages(folderId);
		return new ResultResponse<>(images);
	}

	@GetMapping(value = "/delete")
	@ApiOperation(value = "删除文件夹")
	public MessageResponse delete(@RequestParam(value = "id") Integer id){
		folderService.delete(id);
		return MessageResponse.success();
	}
}
