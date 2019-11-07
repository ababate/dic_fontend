package com.timortech.imagecul.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 21:14
 * @desc：
 */
@Data
public class PasswordDomain implements Serializable {
	private static final long serialVersionUID = -1026567023278663265L;

	private String oldPsw;

	private String newPsw;
}
