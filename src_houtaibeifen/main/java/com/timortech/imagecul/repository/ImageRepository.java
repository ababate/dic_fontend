package com.timortech.imagecul.repository;

import com.timortech.imagecul.entity.Image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:48
 * @desc：
 */

public interface ImageRepository extends JpaRepository<Image,Integer> {

	Image getById(Integer id);

	List<Image> findAllByProjectAndStatus(Integer projectId,String status);
}
