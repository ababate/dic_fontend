package com.imgCul.services;

import com.imgCul.dao.ImgCulMapper;
import com.imgCul.entity.Task;
import com.imgCul.utils.Config;
import com.imgCul.utils.Dispatcher;
import com.imgCul.utils.FileHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/9/4
 * @time: 20:32
 * @desc：
 */
@Service
public class ImgCulServiceImpl implements ImgCulService {

	@Autowired
	private ImgCulMapper mapper;

	@Override
	public void insert(Long imgId, MultipartFile img1, MultipartFile img2, MultipartFile point, String params) {
		//获取文件夹名称，YYMMddHHmm + 4位随机数
		SimpleDateFormat df = new SimpleDateFormat("YYMMddHHmm");
		String dirName = df.format(new Date());
		String random = (int)((Math.random() + 1) * 1000) + "";
		String path = Config.filePath + File.separator + dirName + random;
		File file = new File(path);
		while(file.exists()){
			random = (int)((Math.random() + 1) * 1000) + "";
			path = Config.filePath + File.separator + dirName + random;
			file = new File(path);
		}
		dirName = dirName + random;
		if(!file.exists()){
			file.mkdirs();
		}
		//获取文件名
		String imgName1 = img1.getOriginalFilename();
		String imgName2 = img2.getOriginalFilename();
		String pointName = point.getOriginalFilename();
		if(point.getSize()<1){
			pointName = "NOFILE";
		}
		//保存文件并插入数据库
		try{
			FileHelper.saveFile(img1,path + File.separator + imgName1);
			FileHelper.saveFile(img2,path + File.separator + imgName2);
			FileHelper.saveFile(point,path + File.separator + pointName);
			mapper.insert(imgId,imgName1,imgName2,pointName,dirName,params);
			// Dispatcher分配线程开始计算
			Dispatcher.getInstance().startTask();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List<Task> query() {
		return mapper.query();
	}


	public List<Task> querysql(String state) {
		return mapper.querysql(state);
	}

	@Override
	public List<Task> freeTask() {
		return mapper.freeTask();
	}

	@Override
	public void error(Long id) {
		mapper.error(id);
	}

	@Override
	public void doing(Long id) {
		mapper.doing(id);
	}

	@Override
	public void finish(Long id) {
		mapper.finish(id);
	}


}
