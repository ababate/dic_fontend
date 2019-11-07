package com.timortech.imagecul.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/29
 * @time: 11:33
 * @desc：
 */
@Data
public class Point implements Serializable {
	private static final long serialVersionUID = 344191019464525153L;

	private double x;

	private double y;
}
