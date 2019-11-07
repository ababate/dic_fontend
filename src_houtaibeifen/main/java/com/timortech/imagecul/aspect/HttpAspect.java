package com.timortech.imagecul.aspect;

import com.timortech.imagecul.base.BaseService;
import com.timortech.imagecul.base.TimorException;
import com.timortech.imagecul.entity.User;
import com.timortech.imagecul.enums.ExceptionEnum;
import com.timortech.imagecul.utils.CookieUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/6
 * @time: 13:01
 * @desc：
 */

@Aspect
@Component
@Slf4j
public class HttpAspect extends BaseService {

	@Pointcut("execution(public * com.timortech.imagecul.controller..*.*(..))"+
			"&& !execution(public * com.timortech.imagecul.controller.UserController..*(..))" +
			"&& !execution(public * com.timortech.imagecul.controller.ImageController.test())")
	public void point() {

	}


	/**
	 * http请求处理之前进行处理
	 */
	@Before("point()")
	public void before(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		log.info("【接收到请求：{}】，from={},method={},class_method={},args:{}",
				request.getRequestURL(),
				request.getRemoteHost(),
				request.getMethod(),
				joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
				joinPoint.getArgs());
		if(joinPoint.getSignature().getName().endsWith("notify") ||
				joinPoint.getSignature().getName().endsWith("finish") ||
				joinPoint.getSignature().getName().endsWith("info") ||
				joinPoint.getSignature().getName().endsWith("error") ||
				joinPoint.getSignature().getName().endsWith("adminRecharge")){
			return;
		}
		User user = getUser();
		if(user == null){
			throw new TimorException(ExceptionEnum.TOKEN_INVALID);
		}
	}
}
