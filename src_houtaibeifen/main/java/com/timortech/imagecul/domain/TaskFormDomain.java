package com.timortech.imagecul.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/29
 * @time: 11:32
 * @desc：
 */
@Data
public class TaskFormDomain implements Serializable {

	private static final long serialVersionUID = 37174010175511569L;

	private int imageaId;

	private int imagebId;

	private List<Point> points;


}
