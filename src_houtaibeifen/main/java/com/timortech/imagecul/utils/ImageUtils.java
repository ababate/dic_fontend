package com.timortech.imagecul.utils;

import com.timortech.imagecul.entity.Image;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/8
 * @time: 1:48
 * @desc：
 */
@Slf4j
public class ImageUtils {

	public static void byte2File(byte[] bytes,String filePath) throws IOException {
		File file = new File(filePath);
		log.info("{}",filePath);
		if(!file.exists()){
			file.createNewFile();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		fileOutputStream.write(bytes);
		fileOutputStream.close();
	}

	public static Image saveFile(String imageLocation,
	                            String userName,
	                            String folderName,
	                            MultipartFile file) throws IOException {
		Image image = new Image();
		String originalImage = imageLocation + File.separator +
				userName + File.separator + "original" + File.separator + folderName;
		String thumbnail = imageLocation + File.separator +
				userName + File.separator + "thumbnail" + File.separator + folderName;
		File temp = new File(originalImage);
		//如果文件夹不存在，则创建文件夹
		if(!temp.exists()){
			temp.mkdirs();
		}
		temp = new File(thumbnail);
		if(!temp.exists()){
			temp.mkdirs();
		}
		String fileName = file.getOriginalFilename();
		//如果文件已存在，则更名
		temp = new File(originalImage+File.separator+fileName);
		String tempName = fileName;
		int index = 1;
		String name = fileName.substring(0,fileName.lastIndexOf("."));
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
		while (temp.exists()){
			tempName = name+ "(" + index +")"+"."+suffix;
			temp = new File(originalImage+File.separator+tempName);
			index++;
		}
		fileName = tempName;

		long size = file.getSize();

		FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
		//获取图片信息
		BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

		int x = bufferedImage.getWidth();
		int y = bufferedImage.getHeight();

		//原图输出流
		BufferedOutputStream bigBos = new BufferedOutputStream(
				new FileOutputStream(originalImage+File.separator+fileName)
		);

		byte[] bs = new byte[1024];
		int len;
		while ((len = fileInputStream.read(bs)) != -1) {
			bigBos.write(bs, 0, len);
		}
		bigBos.flush();
		bigBos.close();
		log.info("原图保存成功");
		//保存缩略图  限制大小，长宽最大为100  按比例进行缩放需要进行更改
		Thumbnails.of(file.getInputStream()).size(100,100).toFile(thumbnail+File.separator+fileName);
		log.info("缩略图保存成功");

		image.setImageName(fileName);
		image.setSize(size);
		image.setPixelX(x);
		image.setPixelY(y);
		return image;
	}
}
