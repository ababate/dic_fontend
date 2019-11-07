package com.timortech.imagecul.service;

import com.timortech.imagecul.domain.ImageDomain;
import com.timortech.imagecul.domain.ProjectDomain;
import com.timortech.imagecul.entity.Project;

import java.util.List;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2019/1/16
 * @time: 1:50
 * @desc：
 */

public interface ProjectService {

	List<ProjectDomain> findProjectsByUserId();

	ProjectDomain findById(int projectId);

	ProjectDomain create(String name);

	List<ImageDomain> getImages(Integer projectId);

	Project getOpenProject();

	void open(int projectId);

	void setParams(String params);

	void saveImage(String type, Integer imageId);
}
