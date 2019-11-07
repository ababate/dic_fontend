package com.imgCul.entity;

/**
 * @author: sws558
 * @project: huibida
 * @date: 2018/5/22
 * @time: 19:00
 * @desc：
 */

public class User {
	private Long id;
	private String name;
	private Long age;
	private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
