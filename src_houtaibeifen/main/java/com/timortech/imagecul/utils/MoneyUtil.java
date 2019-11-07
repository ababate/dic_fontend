package com.timortech.imagecul.utils;

import java.math.BigDecimal;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/8
 * @time: 1:18
 * @desc：
 */

public class MoneyUtil {


	/**
	 * 元转分
	 * @param yuan
	 * @return
	 */
	public static Integer Yuan2Fen(Double yuan) {
		return new BigDecimal(String.valueOf(yuan)).movePointRight(2).intValue();
	}

	/**
	 * 分转元
	 * @param fen
	 * @return
	 */
	public static Double Fen2Yuan(Integer fen) {
		return new BigDecimal(fen).movePointLeft(2).doubleValue();
	}

}
