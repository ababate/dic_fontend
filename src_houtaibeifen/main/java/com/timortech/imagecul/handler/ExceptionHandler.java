package com.timortech.imagecul.handler;

import com.timortech.imagecul.base.MessageResponse;
import com.timortech.imagecul.base.TimorException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 21:23
 * @desc：
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandler {

	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(value = TimorException.class)
	@ResponseStatus(code = HttpStatus.OK)
	public MessageResponse handlerHuibidaException(TimorException e){
		log.error("Unexpected Exception, ",e);
		return new MessageResponse(e.getCode(),e.getMessage());
	}

	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public MessageResponse SystemException(Exception e){
		log.error("Unexpected Exception, ",e);

		return new MessageResponse(500,e.getMessage());
	}
}
