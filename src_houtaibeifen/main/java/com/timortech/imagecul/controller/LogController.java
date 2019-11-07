package com.timortech.imagecul.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/10/2
 * @time: 1:52
 * @desc：
 */

@RestController
@RequestMapping(value = "/log")
public class LogController {

	private static final String LOGLOCATION = "/java/springboot/imagecul/log/";

	@GetMapping("/info")
	public String info(){
		return getContent("info");
	}

	@GetMapping("/error")
	public String error(){
		return getContent("error");
	}

	private String getContent(String type){
		String s="";
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String logDate = simpleDateFormat.format(date);
		String fileName = type+"."+ logDate + ".txt";
		File file = new File(LOGLOCATION+fileName);
		StringBuilder stringBuffer = new StringBuilder();
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(file),"UTF-8");
			BufferedReader br = new BufferedReader(in);
			while (s!=null){
				s = br.readLine();
				if(s == null) break;
				stringBuffer.append(s.trim()).append("<br/>");
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}
}
