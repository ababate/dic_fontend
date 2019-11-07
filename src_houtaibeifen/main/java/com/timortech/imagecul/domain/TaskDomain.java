package com.timortech.imagecul.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/5
 * @time: 21:46
 * @desc：
 */
@Data
public class TaskDomain implements Serializable {
	private static final long serialVersionUID = -1561202350359174494L;

	private int id;

	private int imageaId;

	private int imagebId;

	private String imageaUrl;

	private String imagebUrl;

	private double price;

	private String status;

	//private String resultUrl;

	private int position;
	
	private double[][] resultU;
	
	private double[][] resultV;
	
	private double[][] resultExx;
	
	private double[][] resultExy;
	
	private double[][] resultEyy;
	
	private double[][] resultEf;
	
	private double[][] resultEs;
}
