package com.imgCul.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/9/4
 * @time: 21:24
 * @desc：
 */

public class FileHelper {
	//filePath 是绝对路径，需要先异常检测，本方法内不处理异常
	public static void saveFile(MultipartFile file,String filePath) throws IOException {
		File file1 = new File(filePath);
		if(!file1.exists()){
			file1.createNewFile();
		}
		InputStream inputStream = null;
		OutputStream os = null;
		try {
			inputStream = file.getInputStream();
			// 10k的数据缓冲
			byte[] bs = new byte[1024 * 10];
			// 独到的数据长度
			int len;
			//输出的文件流保存到本地
			os = new FileOutputStream(filePath);
			while((len = inputStream.read(bs)) != -1){
				os.write(bs, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			//完毕，关闭所有连接
			try {
				os.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
