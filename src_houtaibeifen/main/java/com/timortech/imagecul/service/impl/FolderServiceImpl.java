package com.timortech.imagecul.service.impl;

import com.timortech.imagecul.base.BaseService;
import com.timortech.imagecul.entity.Folder;
import com.timortech.imagecul.entity.Image;
import com.timortech.imagecul.entity.User;
import com.timortech.imagecul.repository.FolderRepository;
import com.timortech.imagecul.repository.ImageRepository;
import com.timortech.imagecul.service.FolderService;
import com.timortech.imagecul.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/28
 * @time: 15:20
 * @desc：
 */
@Service
public class FolderServiceImpl extends BaseService implements FolderService {

	private final FolderRepository folderRepository;

	private final ImageRepository imageRepository;

	@Autowired
	public FolderServiceImpl(FolderRepository folderRepository, ImageRepository imageRepository) {
		this.folderRepository = folderRepository;
		this.imageRepository = imageRepository;
	}

	@Override
	public Folder createFolder(Integer userId) {
		Folder folder = new Folder();
		folder.setFileNum(0);
		folder.setUserId(userId);
		folder.setName(DateUtils.getFormatDate());
		//在保存之前，再次查询，避免高并发上传时导致同时创建相同文件夹
		Folder temp = folderRepository.getByUserIdAndFileNumLessThanAndStatus(userId,100,"ACTIVED");
		if(temp==null){
			return folderRepository.save(folder);
		}
		else {
			return temp;
		}
	}

	@Override
	public List<Folder> getFolders() {
		User user = getUser();
		return folderRepository.getAllByUserIdAndStatus(user.getId(),"ACTIVED");
	}

	@Override
	public void addFile(Integer folderId) {
		folderRepository.addFileNum(folderId);
	}

	@Override
	public Folder getLess100(Integer userId) {
		return folderRepository.getByUserIdAndFileNumLessThanAndStatus(userId,100,"ACTIVED");
	}

	@Override
	public Folder get(Integer folderId) {
		return folderRepository.getById(folderId);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		//应该同时删除文件夹里的文件
		List<Image> images = imageRepository.findAllByProjectAndStatus(id,"ACTIVED");
		images.forEach(image -> image.setStatus("DELETED"));
		imageRepository.saveAll(images);
		Folder folder = folderRepository.getById(id);
		folder.setStatus("DELETED");
		folderRepository.save(folder);
	}
}
