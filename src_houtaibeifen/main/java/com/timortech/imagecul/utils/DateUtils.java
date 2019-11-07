package com.timortech.imagecul.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/22
 * @time: 16:48
 * @desc：
 */

public class DateUtils {

	public static String getFormatDate(){
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return simpleDateFormat.format(date);
	}
}
