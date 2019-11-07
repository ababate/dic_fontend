package com.timortech.imagecul.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 21:12
 * @desc：
 */
@Data
public class LoginDomain implements Serializable {
	private static final long serialVersionUID = 8710522311741156348L;

	private String username;

	private String password;
}
