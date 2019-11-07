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
 * @time: 20:43
 * @desc：
 */

@Data
@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int userId;

	private int imageaId;

	private int imagebId;

	private double price;

	private String status = "WAIT";

	private String reason;

	private String resultUrl;

	private Date startTime;

	private Date endTime;
}
