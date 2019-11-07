package com.timortech.imagecul.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/28
 * @time: 15:11
 * @desc：
 */
@Data
@Entity
public class Folder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private int userId;

	private int fileNum;

	private String status = "ACTIVED";
}
