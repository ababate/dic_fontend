package com.timortech.imagecul.base;

/**
 * @author: loccen
 * @project: huibida
 * @date: 2018/7/19
 * @time: 15:13
 * @desc：单个实体返回结果
 */

public class ResultResponse<T> extends MessageResponse{

	private T data;

	public ResultResponse(){
		super();
	}

	public ResultResponse(int code,String message){
		super(code,message);
	}

	public ResultResponse(T data){
		super(200,"OK");
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
