package com.timortech.imagecul.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/6
 * @time: 0:10
 * @desc：
 */
@Data
@Entity
public class Result {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int taskId;

	private String filename;

	private long size;

	private String type;
}
