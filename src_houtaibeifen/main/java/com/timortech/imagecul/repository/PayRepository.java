package com.timortech.imagecul.repository;

import com.timortech.imagecul.entity.Pay;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import io.swagger.models.auth.In;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:49
 * @desc：
 */

public interface PayRepository extends JpaRepository<Pay,Integer> {

	Pay getById(Integer id);

	Pay getBySerialNum(String serailNum);

	List<Pay> getByUserIdAndStatusOrderByCreateTimeDesc(Integer userId, String stataus);
}
