package com.imgCul.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/9/4
 * @time: 18:11
 * @desc：
 */

public class Task {
	public Long id;              //id
	public Long imgId;           //imgid
	public String imgName1;      //图片1文件名
	public String imgName2;      //图片2文件名
	public String pointName;    //点阵文件名
	public String dirName;       //文件夹名（在filePath之下的文件夹名称，每个task都有一个文件夹）
	public Long state;           //状态 0：未完成 1：已完成 2：错误
	public String params;
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
	public Date createTime;      //创建时间
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
	public Date finishTime;      //完成时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getImgId() {
		return imgId;
	}

	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}

	public String getImgName1() {
		return imgName1;
	}

	public void setImgName1(String imgName1) {
		this.imgName1 = imgName1;
	}

	public String getImgName2() {
		return imgName2;
	}

	public void setImgName2(String imgName2) {
		this.imgName2 = imgName2;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getParams() {
        return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
}
