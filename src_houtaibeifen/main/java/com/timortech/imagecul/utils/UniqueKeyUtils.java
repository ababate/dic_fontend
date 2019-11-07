package com.timortech.imagecul.utils;

import java.util.Random;

/**
 * @author: loccen
 * @project: huibida
 * @date: 2018/8/7
 * @time: 13:12
 * @desc：唯一主键生成工具类
 */

public class UniqueKeyUtils {

	/**
	 * 生成唯一的主键
	 * 格式: 时间+随机数
	 * @return
	 */
	public static synchronized String getUniqueKey() {
		Random random = new Random();
		Integer number = random.nextInt(900000) + 100000;

		return System.currentTimeMillis() + String.valueOf(number);
	}
}
