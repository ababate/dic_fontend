package com.timortech.imagecul.enums;

import com.timortech.imagecul.entity.TaskStatus;

import lombok.Getter;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/7
 * @time: 22:35
 * @desc：
 */
@Getter
public enum TaskStatusEnum {

	WAIT(0,"排队中"),
	COMPLETED(1,"已完成"),
	ONWORK(2,"计算中"),
	FAIL(3,"发生错误"),
	UNKNOWN(-1,"未知状态"),
	;

	private Integer code;

	private String status;

	TaskStatusEnum(Integer code, String status) {
		this.code = code;
		this.status = status;
	}

	public static TaskStatusEnum build(Integer code){
		switch (code){

			case 0:return TaskStatusEnum.WAIT;

			case 1:return TaskStatusEnum.COMPLETED;

			case 2:return TaskStatusEnum.ONWORK;

			case 3:return TaskStatusEnum.FAIL;
		}
		return TaskStatusEnum.UNKNOWN;
	}
}
