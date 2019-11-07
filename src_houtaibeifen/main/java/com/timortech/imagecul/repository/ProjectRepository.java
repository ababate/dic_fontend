package com.timortech.imagecul.repository;

import com.timortech.imagecul.entity.Project;

import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2019/1/16
 * @time: 1:47
 * @desc：
 */

public interface ProjectRepository extends JpaRepository<Project,Integer> {

	List<Project> findProjectsByUserId(int userId);

	Project findProjectById(Integer projectId);

	Project findProjectByStatusAndUserId(String open, int id);
}
