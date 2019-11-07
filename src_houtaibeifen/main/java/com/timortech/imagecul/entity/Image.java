package com.timortech.imagecul.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:45
 * @desc：
 */
@Data
@Entity
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String imageName;

	private int project;

//	private String thumbnailName;

	private int pixelX;

	private int pixelY;

	private long size;

	private Date time;

	private String status = "ACTIVED";

}
