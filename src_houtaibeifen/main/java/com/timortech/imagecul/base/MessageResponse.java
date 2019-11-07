package com.timortech.imagecul.base;

/**
 * @author: loccen
 * @project: huibida
 * @date: 2018/7/18
 * @time: 20:52
 * @desc：http返回结果最外层封装
 */

public class MessageResponse {

	/**
	 * 返回码
	 * 当http请求错误时，为http状态码
	 * 当为系统内部异常时，为自定义的错误码
	 */
	private int code;

	/**
	 * 返回的信息
	 */
	private String msg;

	MessageResponse(){
		super();
	}

	public MessageResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

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

	public static MessageResponse success(){
		return new MessageResponse(200,"OK");
	}
}
