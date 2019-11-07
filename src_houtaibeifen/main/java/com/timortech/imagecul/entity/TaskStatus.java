package com.timortech.imagecul.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/7
 * @time: 16:27
 * @desc：
 */
@Data
public class TaskStatus implements Serializable {

	private static final long serialVersionUID = -1856558367424297920L;

	private int imgId;

	private int state;
}
