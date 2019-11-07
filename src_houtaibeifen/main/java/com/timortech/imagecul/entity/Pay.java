package com.timortech.imagecul.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:38
 * @desc：
 */

@Data
@Entity
public class Pay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * 用户id
	 */
	private int userId;

	/**
	 * 支付类型
	 * WECHAT
	 * ALIPAY
	 */
	private String type;

	/**
	 * 支付流水号
	 */
	private String serialNum;

	/**
	 * 支付状态
	 * 未支付
	 * 已支付
	 */
	private String status = "未支付";

	/**
	 * 支付金额
	 */
	private double amount;

	/**
	 * 支付二维码url
	 */
	private String codeUrl;

	private Date createTime;

	private Date payTime;
}
