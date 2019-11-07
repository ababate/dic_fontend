package com.timortech.imagecul.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2019/1/16
 * @time: 1:40
 * @desc：
 */
@Data
@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private int userId;

	private String referenceImages;

	private String deformedImages;

	private String params;

	private String results;

	private Date createTime;

	private Date updateTime;

	private String status;
}
