package com.timortech.imagecul.repository;

import com.timortech.imagecul.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:53
 * @desc：
 */

public interface UserRepository extends JpaRepository<User,Integer> {

	User getById(Integer id);

	User getByUsername(String name);

	User getByUsernameAndPassword(String username,String password);

	User getByToken(String token);
}
