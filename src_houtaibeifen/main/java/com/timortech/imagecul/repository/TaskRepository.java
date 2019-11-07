package com.timortech.imagecul.repository;

import com.timortech.imagecul.entity.Task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:53
 * @desc：
 */

public interface TaskRepository extends JpaRepository<Task,Integer> {

	Task getById(Integer id);

	List<Task> findAllByStatus(String status);

	List<Task> findAllByUserIdAndStatusIn(Integer userId,List<String> status);
}
