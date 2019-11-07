package com.timortech.imagecul.enums;

import lombok.Getter;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 21:22
 * @desc：
 */
@Getter
public enum ExceptionEnum {
	SUCCESS(200,"成功"),

	USER_EXIT(10001,"用户已经存在"),
	USER_NOT_EXIT(10002,"登录失败，用户不存在"),
	PASSWORD_WRONG(10003,"密码错误"),
	TOKEN_INVALID(10004,"登录信息已过期,请重新登录"),

	FILE_UPLOAD_FAIL(20001,"图片上传失败"),
	FILE_IS_NULL(20002,"图片不能为空"),

	BALANCE_NOT_ENOUGH(30001,"可用余额不足，任务创建失败，请充值"),
	FILE_TRANS_FAIL(30002,"任务执行失败，图片转发出错"),
	TASK_QUERY_FAIL(30003,"任务状态查询失败"),

	NOTIFY_FIAL(40001,"异步通知错误"),


	;

	private Integer code;

	private String message;

	ExceptionEnum(Integer code,String message){
		this.code = code;
		this.message = message;
	}
}
