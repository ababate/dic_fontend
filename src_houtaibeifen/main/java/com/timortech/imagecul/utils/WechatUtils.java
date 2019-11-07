package com.timortech.imagecul.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/8
 * @time: 1:04
 * @desc：
 */

public class WechatUtils {

	/**
	 * 生成签名
	 * @param params
	 * @param signKey
	 * @return
	 */
	public static String sign(Map<String, String> params, String signKey) {
		SortedMap<String, String> sortedMap = new TreeMap<>(params);

		StringBuilder toSign = new StringBuilder();
		for (String key : sortedMap.keySet()) {
			String value = params.get(key);
			if (StringUtils.isNotEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
				toSign.append(key).append("=").append(value).append("&");
			}
		}

		toSign.append("key=").append(signKey);
		return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
	}
}
