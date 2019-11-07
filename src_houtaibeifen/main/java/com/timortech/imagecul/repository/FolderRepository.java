package com.timortech.imagecul.repository;

import com.timortech.imagecul.entity.Folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/28
 * @time: 15:12
 * @desc：
 */

public interface FolderRepository extends JpaRepository<Folder,Integer> {

	@Query(value = "update Folder set fileNum = fileNum + 1 where " +
			"id = ?1")
	@Modifying
	@Transactional
	void addFileNum(int folderId);

	List<Folder> getAllByUserIdAndStatus(Integer userId,String status);

	Folder getByUserIdAndFileNumLessThanAndStatus(Integer userId,Integer num,String status);

	Folder getById(Integer folderId);
}
