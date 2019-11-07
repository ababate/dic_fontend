package com.timortech.imagecul.service.impl;

import com.timortech.imagecul.config.ImageConfig;
import com.timortech.imagecul.service.FileService;
import com.timortech.imagecul.utils.UniqueKeyUtils;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: loccen
 * @project: huibida
 * @date: 2018/8/13
 * @time: 6:21
 * @desc：本地文件存储
 */
@Service
@Slf4j
public class LocalFileServiceImpl implements FileService {

	private final ImageConfig imageConfig;

	@Autowired
	public LocalFileServiceImpl(ImageConfig imageConfig) {
		this.imageConfig = imageConfig;
	}

	@Override
	public String saveImage(MultipartFile multipartFile) throws IOException {
		log.info("保存文件");
		File file = new File(imageConfig.getLocation()+ "\\testfile" );
		if (!file.exists()) {
			file.mkdirs();
		}
		String suffix = multipartFile.getOriginalFilename();
		suffix = suffix.substring(suffix.lastIndexOf("."));
		FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
//		fileInputStream = (FileInputStream) compress(fileInputStream,100,100, (float) 0.5,1.0);
		String fileName = UniqueKeyUtils.getUniqueKey() + suffix;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageConfig.getLocation() + "\\testfile\\" + fileName));
		byte[] bs = new byte[1024];
		int len;
		while ((len = fileInputStream.read(bs)) != -1) {
			bos.write(bs, 0, len);
		}
		bos.flush();
		bos.close();
		log.info("保存成功");
		return fileName;
	}

	/**
	 * 指定大小 压缩图片
	 * 默认输出50%质量图片
	 *
	 * @param in      输出流
	 * @param width   图片长度
	 * @param height  图片宽度
	 * @param quality 图片压缩质量 范围：0.0~1.0，1为最高质量
	 * @param scale  按照比例进行缩放 0.0~1.0
	 * @return
	 * @throws IOException
	 */
	public static InputStream compress(InputStream in, Integer width, Integer height, Float quality, Double scale) throws IOException {
		if (in == null) {
			return null;
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Thumbnails.Builder builder=Thumbnails.of(in);
		if(width!=null && height!=null){
			builder.size(width,height);
		}

//		if(scale!=null){
//			builder.scale(0.9);
//		}
//		if(quality!=null){
//			builder.outputQuality(quality);
//		}
		builder.toOutputStream(os);
		builder.keepAspectRatio(false);//严格按宽高输出　
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		in.close();
		os.close();
		return is;
	}


	@Override
	public List<String> saveImage(MultipartFile[] multipartFile) throws IOException {
		List<String> urls = new ArrayList<>();
		for(MultipartFile file : multipartFile){
			urls.add(saveImage(file));
		}
		return urls;
	}
}
