package com.timortech.imagecul.base;

import com.timortech.imagecul.enums.ExceptionEnum;

import lombok.Getter;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 21:25
 * @desc：
 */

@Getter
public class TimorException extends RuntimeException {
	private static final long serialVersionUID = -8005010583649298457L;
	private Integer code;

	public TimorException(ExceptionEnum exceptionEnum){
		super(exceptionEnum.getMessage());
		this.code = exceptionEnum.getCode();
	}

}
