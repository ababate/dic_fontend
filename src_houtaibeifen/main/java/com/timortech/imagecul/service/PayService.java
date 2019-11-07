package com.timortech.imagecul.service;


import com.github.binarywang.wxpay.exception.WxPayException;
import com.timortech.imagecul.entity.Pay;

import java.util.List;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:54
 * @desc：
 */

public interface PayService {
	Pay recharge(double amount);

	void notify(String notifyData) throws WxPayException;

	List<Pay> list();

	void adminRecharge(String userName, double amount);

	String queryStatus(Integer id);
}
