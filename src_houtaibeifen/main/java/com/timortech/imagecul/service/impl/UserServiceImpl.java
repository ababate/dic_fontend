package com.timortech.imagecul.service.impl;

import com.timortech.imagecul.base.BaseService;
import com.timortech.imagecul.base.TimorException;
import com.timortech.imagecul.domain.LoginDomain;
import com.timortech.imagecul.domain.PasswordDomain;
import com.timortech.imagecul.entity.User;
import com.timortech.imagecul.enums.ExceptionEnum;
import com.timortech.imagecul.repository.UserRepository;
import com.timortech.imagecul.service.UserService;
import com.timortech.imagecul.utils.MD5Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:59
 * @desc：
 */

@Service
public class UserServiceImpl extends BaseService implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User getByToken(String token) {
		User user = userRepository.getByToken(token);
		if(user==null){
			throw new TimorException(ExceptionEnum.TOKEN_INVALID);
		}
		return user;
	}

	@Override
	public void register(User user) {
		//验证用户名是否已经存在
		User tempUser = userRepository.getByUsername(user.getUsername());
		if(tempUser!=null){
			throw new TimorException(ExceptionEnum.USER_EXIT);
		}
		//密码进行MD5加密
		user.setPassword(MD5Utils.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Override
	public String login(LoginDomain loginDomain) {
		User user = verify(loginDomain.getUsername(),loginDomain.getPassword());
		//登录成功，设置token
		String token = MD5Utils.encode(user.getUsername()+user.getPassword()+System.currentTimeMillis());
		user.setToken(token);
		userRepository.save(user);
		return token;
	}

	@Override
	public void changePsw(PasswordDomain passwordDomain) {
		User user = getUser();
		//getUser方法里已经进行过token验证，无需再次验证
		user = verify(user.getUsername(),passwordDomain.getOldPsw());
		user.setPassword(MD5Utils.encode(passwordDomain.getNewPsw()));
		user.setToken("");
		userRepository.save(user);
	}

	@Override
	public User verify(String username, String password) {
		if(userRepository.getByUsername(username)==null){
			throw new TimorException(ExceptionEnum.USER_NOT_EXIT);
		}
		User user = userRepository.getByUsernameAndPassword(username,MD5Utils.encode(password));
		if(user==null){
			throw new TimorException(ExceptionEnum.PASSWORD_WRONG);
		}
		return user;
	}

	@Override
	public User update(User user) {
		return userRepository.save(user);
	}

	@Override
	public User get() {
		User user= getUser();
		return user;
	}
}
