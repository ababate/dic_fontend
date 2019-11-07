package com.timortech.imagecul.base;

import com.timortech.imagecul.entity.User;
import com.timortech.imagecul.enums.ExceptionEnum;
import com.timortech.imagecul.service.UserService;
import com.timortech.imagecul.utils.CookieUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:55
 * @desc：
 */

public abstract class BaseService {

	@Autowired
	protected UserService userService;

	protected User getUser(){
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String token = CookieUtils.getCookie(request,"token");
		if(token==null||token.equals("")){
			throw new TimorException(ExceptionEnum.TOKEN_INVALID);
		}
		return userService.getByToken(token);
	}

}
