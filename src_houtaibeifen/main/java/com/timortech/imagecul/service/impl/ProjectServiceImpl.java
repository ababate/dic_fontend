package com.timortech.imagecul.service.impl;

import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.timortech.imagecul.base.BaseService;
import com.timortech.imagecul.domain.ImageDomain;
import com.timortech.imagecul.domain.ProjectDomain;
import com.timortech.imagecul.entity.Project;
import com.timortech.imagecul.entity.User;
import com.timortech.imagecul.repository.ProjectRepository;
import com.timortech.imagecul.service.ProjectService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2019/1/16
 * @time: 1:52
 * @desc：
 */
@Slf4j
@Service
public class ProjectServiceImpl extends BaseService implements ProjectService {

	private final ProjectRepository projectRepository;

	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}


	@Override
	public List<ProjectDomain> findProjectsByUserId() {
		int userId = getUser().getId();
		List<Project> projects = projectRepository.findProjectsByUserId(userId);
		List<ProjectDomain> projectDomains = new ArrayList<>();
		projects.forEach(project -> {
			ProjectDomain projectDomain = new ProjectDomain();
			BeanUtils.copyProperties(project,projectDomain);
			projectDomains.add(parseParams(parseImage(project,projectDomain)));
		});
		return projectDomains;
	}

	@Override
	public ProjectDomain findById(int projectId) {
		Project project = projectRepository.findProjectById(projectId);
		ProjectDomain projectDomain = new ProjectDomain();
		BeanUtils.copyProperties(project,projectDomain);
		return parseParams(parseImage(project,projectDomain));
	}

	@Override
	public ProjectDomain create(String name) {
		Project project = new Project();
		project.setName(name);
		project.setUserId(getUser().getId());
		project.setCreateTime(new Date());
		project.setUpdateTime(new Date());
		Project result = projectRepository.save(project);
		ProjectDomain projectDomain = new ProjectDomain();
		BeanUtils.copyProperties(result,projectDomain);
		open(result.getId());
		return parseParams(parseImage(project,projectDomain));
	}

	@Override
	public List<ImageDomain> getImages(Integer projectId) {

		return null;
	}

	@Override
	public Project getOpenProject() {
		User user = getUser();
		return projectRepository.findProjectByStatusAndUserId("OPEN",user.getId());
	}

	@Override
	public void open(int projectId) {
		Project project = getOpenProject();
		if(project != null){
			project.setStatus("CLOSE");
			projectRepository.saveAndFlush(project);
		}
		Project newProject = projectRepository.findProjectById(projectId);
		newProject.setStatus("OPEN");
		projectRepository.saveAndFlush(newProject);
	}

	@Override
	public void setParams(String params) {
		Project project = getOpenProject();
		if(project != null){
			project.setParams(params);
			projectRepository.saveAndFlush(project);
		}
	}

	@Override
	public void saveImage(String type, Integer imageId) {
		Project project = getOpenProject();
		if(project == null)
			return;
		String image = String.valueOf(imageId);
		if(type.equals("A")){

			project.setReferenceImages(image);
			projectRepository.saveAndFlush(project);

		}else if(type.equals("B")){

			project.setDeformedImages(image);
			projectRepository.saveAndFlush(project);
		}

	}

	private ProjectDomain parseParams(ProjectDomain projectDomain){
		JsonParser parser = new JsonParser();
		if(projectDomain.getParams() == null){
			return projectDomain;
		}
		JsonElement element = parser.parse(projectDomain.getParams());
		JsonObject object = element.getAsJsonObject();
		projectDomain.setLambda(object.get("lambda") == null ? "" : object.get("lambda").getAsString());
		projectDomain.setBeta(object.get("beta") == null ? "" : object.get("beta").getAsString());
		projectDomain.setPyramidLevels(object.get("pyramid_levels") == null ? "" : object.get("pyramid_levels").getAsString());
		projectDomain.setPyramidFactor(object.get("pyramid_factor") == null ? "" : object.get("pyramid_factor").getAsString());
		projectDomain.setConvergenceAccuracy(object.get("convergenceAccuracy") == null ? "" : object.get("convergenceAccuracy").getAsString());
		projectDomain.setCoreNumber(object.get("coreNumber") == null ? "" : object.get("coreNumber").getAsString());
		projectDomain.setRelevanceThreshold(object.get("relevanceThreshold") == null ? "" : object.get("relevanceThreshold").getAsString());
		projectDomain.setAlgorithm(object.get("algorithm") == null ? "" : object.get("algorithm").getAsString());
		projectDomain.setSpaRes(object.get("spaRes") == null ? "" : object.get("spaRes").getAsString());
		projectDomain.setDomainX(object.get("domainX") == null ? "" : object.get("domainX").getAsString());
		projectDomain.setDomainY(object.get("domainY") == null ? "" : object.get("domainY").getAsString());
		projectDomain.setDomainW(object.get("domainW") == null ? "" : object.get("domainW").getAsString());
		projectDomain.setDomainH(object.get("domainH") == null ? "" : object.get("domainH").getAsString());
		log.info(projectDomain.toString());
		return projectDomain;
	}

	private ProjectDomain parseImage(Project project,ProjectDomain projectDomain){
		String imageA = project.getReferenceImages();
		String imageB = project.getDeformedImages();

		if(imageA != null){
			List<Integer> imageR = new ArrayList<>();
			imageR.add(Integer.valueOf(imageA));
			projectDomain.setReferenceImages(imageR);
		}

		if(imageB != null){
			List<Integer> imageD = new ArrayList<>();
			imageD.add(Integer.valueOf(imageB));
			projectDomain.setDeformedImages(imageD);
		}
		return projectDomain;
	}

}
