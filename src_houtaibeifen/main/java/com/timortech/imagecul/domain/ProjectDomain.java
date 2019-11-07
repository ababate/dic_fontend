package com.timortech.imagecul.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/27
 * @time: 16:59
 * @desc：
 */
@Data
public class ProjectDomain implements Serializable {


	private static final long serialVersionUID = -7245786531139369581L;
	private Integer id;

	private String name;

	private int userId;

	private List<Integer> referenceImages = new ArrayList<>();

	private List<Integer> deformedImages = new ArrayList<>();

	private String params;

	private String results;

	private Date createTime;

	private Date updateTime;

	private int referenceNum;

	private int deformedNum;

	private int taskNum;

	private int resultNum;

	private String lambda;

	private String beta;

	private String pyramidLevels;

	private String pyramidFactor;

	private String convergenceAccuracy;

	private String coreNumber;

	private String relevanceThreshold;
	
	private String algorithm;
	
	private String spaRes;

	private String domainX;

	private String domainY;

	private String domainW;

	private String domainH;

	private String chooseVar;

	private String customVar;

	private String status;
}
