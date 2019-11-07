package com.imgCul.entity;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/9/4
 * @time: 22:13
 * @desc：
 */

public class Response {
	int code = 200;
	String msg = "";
	Object data = null;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
