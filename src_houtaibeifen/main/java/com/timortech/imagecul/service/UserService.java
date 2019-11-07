package com.timortech.imagecul.service;

import com.timortech.imagecul.domain.LoginDomain;
import com.timortech.imagecul.domain.PasswordDomain;
import com.timortech.imagecul.entity.User;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:55
 * @desc：
 */

public interface UserService {

	User getByToken(String token);

	void register(User user);

	String login(LoginDomain loginDomain);

	void changePsw(PasswordDomain passwordDomain);

	User verify(String username, String password);

	User update(User user);

	User get();
}
