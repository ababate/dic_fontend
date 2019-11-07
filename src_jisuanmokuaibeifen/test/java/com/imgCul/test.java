package com.imgCul;

import com.imgCul.utils.HttpClientUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/9/7
 * @time: 16:17
 * @desc：
 */

@Slf4j
public class test {
	public static void main(String[] args){
		try{
			String msg = HttpClientUtils.httpGet("http://192.168.1.150:8080/imgCul/query");
			log.info(msg);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
