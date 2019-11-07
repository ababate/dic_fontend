package com.timortech.imagecul.service;

import com.timortech.imagecul.entity.Folder;

import java.util.List;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/28
 * @time: 15:18
 * @desc：
 */

public interface FolderService {

	Folder createFolder(Integer userId);

	List<Folder> getFolders();

	void addFile(Integer userId);

	Folder getLess100(Integer userId);

	Folder get(Integer folderId);

	void delete(Integer id);
}
