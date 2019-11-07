package com.timortech.imagecul.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author: loccen
 * @project: huibida
 * @date: 2018/8/13
 * @time: 6:20
 * @desc：
 */

public interface FileService {

	/**
	 * 将文件进行存储，返回文件的静态访问url
	 * @return
	 */
	String saveImage(MultipartFile multipartFile) throws IOException;

	List<String> saveImage(MultipartFile[] multipartFile) throws IOException;

}
