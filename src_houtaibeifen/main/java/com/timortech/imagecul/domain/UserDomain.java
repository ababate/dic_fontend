package com.timortech.imagecul.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/27
 * @time: 16:59
 * @desc：
 */
@Data
public class UserDomain implements Serializable {

	private static final long serialVersionUID = 5341530941247121986L;
	private int id;

	private String username;

	private String name;

	private Date time;

	private double balance = 0;

}
