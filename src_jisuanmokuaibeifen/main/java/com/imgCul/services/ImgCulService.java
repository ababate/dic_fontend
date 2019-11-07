package com.imgCul.services;

import com.imgCul.entity.Task;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/9/4
 * @time: 20:31
 * @desc：
 */

public interface ImgCulService {
	void insert(Long imgId, MultipartFile img1, MultipartFile img2, MultipartFile point, String params);

	List<Task> query();

	List<Task> freeTask();

	void error(Long id);

	void doing(Long id);

	void finish(Long id);
	public List<Task> querysql(String state);

}
