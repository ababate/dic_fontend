package com.timortech.imagecul.service;

import com.timortech.imagecul.base.TimorException;
import com.timortech.imagecul.config.ImageConfig;
import com.timortech.imagecul.entity.Image;
import com.timortech.imagecul.entity.Project;
import com.timortech.imagecul.entity.Task;
import com.timortech.imagecul.entity.User;
import com.timortech.imagecul.enums.ExceptionEnum;
import com.timortech.imagecul.enums.TaskStatusEnum;
import com.timortech.imagecul.repository.TaskRepository;
import com.timortech.imagecul.utils.HttpClientUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/10/1
 * @time: 16:46
 * @desc：
 */

@Service
public class TaskExecService {

	private final ImageService imageService;

	private final FolderService folderService;

	private final ImageConfig imageConfig;

	private final UserService userService;

	private final TaskRepository taskRepository;

	private final ProjectService projectService;

	@Autowired
	public TaskExecService(ImageService imageService, FolderService folderService, ImageConfig imageConfig, UserService userService, TaskRepository taskRepository, ProjectService projectService) {
		this.imageService = imageService;
		this.folderService = folderService;
		this.imageConfig = imageConfig;
		this.userService = userService;
		this.taskRepository = taskRepository;
		this.projectService = projectService;
	}

	@Async
	public void execTask(Task task, File pointFile, User user,Project project){
		Image imageA = imageService.getImage(task.getImageaId());
		Image imageB = imageService.getImage(task.getImagebId());
		File fileA = new File(imageConfig.getLocation()
				+File.separator+user.getUsername()
				+File.separator+"original"+File.separator+project.getName()+File.separator
				+imageA.getImageName());
		File fileB = new File(imageConfig.getLocation()
				+File.separator+user.getUsername()
				+File.separator+"original"+File.separator+project.getName()+File.separator
				+imageB.getImageName());
		try{
			HttpClientUtils.uploadFile(fileA,fileB,pointFile,task.getId(),imageConfig.getPostUrl(),project.getParams());
			//如果没有异常，表明文件传输成功，任务开始排队
			task.setStatus(TaskStatusEnum.WAIT.toString());
			taskRepository.save(task);
		}catch (Exception e){
			//文件上传失败，返回余额,重置状态
			user.setBalance(user.getBalance()+task.getPrice());
			task.setStatus("FAIL");
			userService.update(user);
			task.setReason(ExceptionEnum.FILE_TRANS_FAIL.getMessage());
			taskRepository.save(task);
			//并把错误抛出
			throw new TimorException(ExceptionEnum.FILE_TRANS_FAIL);
		}
	}

}
