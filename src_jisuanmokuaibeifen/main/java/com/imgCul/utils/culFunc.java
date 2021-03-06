package com.imgCul.utils;

//import com.mathworks.toolbox.javabuilder.MWArray;
//import com.mathworks.toolbox.javabuilder.MWClassID;
//import com.mathworks.toolbox.javabuilder.MWComplexity;
//import com.mathworks.toolbox.javabuilder.MWNumericArray;

//import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import call4java.Matlab4java;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/9/23
 * @time: 0:12
 * @desc：
 */
@Slf4j
public class culFunc {
	public static void imgCul(String imgPath1,String imgPath2,String dirPath, /*int type,*/ boolean hasdots, String dotsPath, String params) throws IOException, InterruptedException {
		log.info(Config.filePath);
		log.info(Config.rootPath);
		String jarPath = Config.rootPath + "\\lib\\matlab4jar.jar";
		String cmd = "java -jar " + jarPath;
		cmd += " " + imgPath1;
		cmd += " " + imgPath2;
		cmd += " " + dirPath;
		// cmd += " " + type + "";
		cmd += " " + hasdots + "";
		cmd += " " + params + "";


		if(hasdots){
			cmd += " " + dotsPath;
		}

		log.info("cmd: ");
		log.info(cmd);
		Process process = Runtime.getRuntime().exec(cmd);
//		BufferedInputStream bis = new BufferedInputStream(
//				process.getInputStream());
//		BufferedReader br = new BufferedReader(new InputStreamReader(bis));
//		String line;
//		while ((line = br.readLine()) != null) {
//			System.out.println(line);
//		}


		InputStream stderr = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		while ((line = br.readLine()) != null) {
    System.out.println(line);
		}
		process.waitFor();
		if (process.exitValue() != 0) {
//			System.out.println("error!");
			throw new RuntimeException();
		}
	}
}
