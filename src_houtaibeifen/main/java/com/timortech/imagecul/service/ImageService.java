package com.timortech.imagecul.service;

import com.timortech.imagecul.domain.ImageDomain;
import com.timortech.imagecul.entity.Image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:54
 * @desc：
 */

public interface ImageService {
	ImageDomain upload(MultipartFile image) throws IOException;

	List<ImageDomain> getImages(Integer folderId);

	List<Image> getImages(List<Integer> ids);

	Image getImage(Integer id);

	ImageDomain getImageDomain(Integer id);

	void delete(Integer id);
}
