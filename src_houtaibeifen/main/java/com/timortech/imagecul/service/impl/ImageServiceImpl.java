package com.timortech.imagecul.service.impl;

import com.timortech.imagecul.base.BaseService;
import com.timortech.imagecul.base.TimorException;
import com.timortech.imagecul.config.ImageConfig;
import com.timortech.imagecul.domain.ImageDomain;
import com.timortech.imagecul.entity.Folder;
import com.timortech.imagecul.entity.Image;
import com.timortech.imagecul.entity.Project;
import com.timortech.imagecul.entity.User;
import com.timortech.imagecul.enums.ExceptionEnum;
import com.timortech.imagecul.repository.ImageRepository;
import com.timortech.imagecul.service.FileService;
import com.timortech.imagecul.service.FolderService;
import com.timortech.imagecul.service.ImageService;
import com.timortech.imagecul.service.ProjectService;
import com.timortech.imagecul.utils.ImageUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
 
import org.apache.commons.codec.binary.Base64;
 
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:58
 * @desc：
 */
@Service
@Slf4j
public class ImageServiceImpl extends BaseService implements ImageService {

	private final ImageRepository imageRepository;

	private final FileService fileService;

	private final ImageConfig imageConfig;

	private final FolderService folderService;

	private final ProjectService projectService;

	@Autowired
	public ImageServiceImpl(ImageRepository imageRepository, FileService fileService, ImageConfig imageConfig, FolderService folderService, ProjectService projectService) {
		this.imageRepository = imageRepository;
		this.fileService = fileService;
		this.imageConfig = imageConfig;
		this.folderService = folderService;
		this.projectService = projectService;
	}

	@Override
	public ImageDomain upload(MultipartFile file) throws IOException {
		if(file == null){
			throw new TimorException(ExceptionEnum.FILE_IS_NULL);
		}
		User user = getUser();
		/*Folder folder;
		synchronized (folderService){
			folder = folderService.getLess100(user.getId());
			if(folder == null){
				//如果所有文件夹已经存有100张图片，则新建文件夹
				folder = folderService.createFolder(user.getId());
			}
		}*/
		Project project = projectService.getOpenProject();

		Image image = ImageUtils.saveFile(imageConfig.getLocation(),user.getUsername(),project.getName(),file);
		image.setProject(project.getId());
		image = imageRepository.save(image);
		/*folderService.addFile(folder.getId());*/
		ImageDomain imageDomain = new ImageDomain();
		BeanUtils.copyProperties(image,imageDomain);
		String originalUrl = ImageToBase64(imageConfig.getBaseUrl() + user.getUsername() + "/original/" + project.getName()+"/"+image.getImageName());
//		String originalUrl = imageConfig.getBaseUrl() + user.getUsername() + "/original/" + project.getName()+"/"+image.getImageName();
		String thumbnailUrl = ImageToBase64(imageConfig.getBaseUrl() + user.getUsername() + "/thumbnail/" + project.getName()+"/"+image.getImageName());
//		String thumbnailUrl = imageConfig.getBaseUrl() + user.getUsername() + "/thumbnail/" + project.getName()+"/"+image.getImageName();
		imageDomain.setOriginalUrl(originalUrl);
		imageDomain.setThumbnailUrl(thumbnailUrl);
		return imageDomain;
	}

	@Override
	public List<ImageDomain> getImages(Integer projectId) {
		List<Image> images = imageRepository.findAllByProjectAndStatus(projectId,"ACTIVED");
		User user = getUser();
		Project project = projectService.getOpenProject();
		List<ImageDomain> imageDomains = new ArrayList<>();
		for(Image image : images){
			ImageDomain imageDomain = new ImageDomain();
			BeanUtils.copyProperties(image,imageDomain);
			String originalUrl = ImageToBase64(imageConfig.getBaseUrl() + user.getUsername() + "/original/" + project.getName()+"/"+image.getImageName());
//			String originalUrl = imageConfig.getBaseUrl() + user.getUsername() + "/original/" + project.getName()+"/"+image.getImageName();
			String thumbnailUrl = ImageToBase64(imageConfig.getBaseUrl() + user.getUsername() + "/thumbnail/" + project.getName()+"/"+image.getImageName());
//			String thumbnailUrl = imageConfig.getBaseUrl() + user.getUsername() + "/thumbnail/" + project.getName()+"/"+image.getImageName();
			imageDomain.setOriginalUrl(originalUrl);
			imageDomain.setThumbnailUrl(thumbnailUrl);
			imageDomains.add(imageDomain);
		}
		return imageDomains;
	}

	@Override
	public List<Image> getImages(List<Integer> ids) {
		return imageRepository.findAllById(ids);
	}

	@Override
	public Image getImage(Integer id) {
		return imageRepository.getById(id);
	}

	@Override
	public ImageDomain getImageDomain(Integer id) {
		User user = getUser();
		Image image = imageRepository.getById(id);
		Project project = projectService.getOpenProject();
		ImageDomain imageDomain = new ImageDomain();
		BeanUtils.copyProperties(image,imageDomain);
		String originalUrl = ImageToBase64(imageConfig.getBaseUrl() + user.getUsername() + "/original/" + project.getName()+"/"+image.getImageName());
//		String originalUrl = imageConfig.getBaseUrl() + user.getUsername() + "/original/" + project.getName()+"/"+image.getImageName();
		String thumbnailUrl = ImageToBase64(imageConfig.getBaseUrl() + user.getUsername() + "/thumbnail/" + project.getName()+"/"+image.getImageName());
//		String thumbnailUrl = imageConfig.getBaseUrl() + user.getUsername() + "/thumbnail/" + project.getName()+"/"+image.getImageName();
		imageDomain.setOriginalUrl(originalUrl);
		imageDomain.setThumbnailUrl(thumbnailUrl);
		return imageDomain;
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		Image image = imageRepository.getById(id);
		image.setStatus("DELETED");
		imageRepository.save(image);
	}
	
	public static String ImageToBase64(String imgURL) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		try {
			// 创建URL
			URL url = new URL(imgURL);
			byte[] by = new byte[1024];
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			InputStream is = conn.getInputStream();
			// 将内容读取内存中
			int len = -1;
			while ((len = is.read(by)) != -1) {
				data.write(by, 0, len);
			}
			// 关闭流
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		String output = "data:image/jpg;base64," + Base64.encodeBase64String(data.toByteArray());
		return output;
	}


}
