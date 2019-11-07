package com.timortech.imagecul.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/6
 * @time: 12:50
 * @desc：
 */
@Data
public class ImageDomain implements Serializable {

	private static final long serialVersionUID = 6955484799567161182L;
	private int id;

	private String imageName;

	private String originalUrl;

	private String thumbnailUrl;

	private int pixelX;

	private int pixelY;

	private long size;

}
