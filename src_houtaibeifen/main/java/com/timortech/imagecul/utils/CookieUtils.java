package com.timortech.imagecul.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: loccen
 * @project: huibida
 * @date: 2018/8/8
 * @time: 23:31
 * @desc：
 */

public class CookieUtils {

	public static String getCookie(HttpServletRequest request, String name){
		Map<String,Cookie> cookieMap = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie:cookies){
				cookieMap.put(cookie.getName(),cookie);
			}
		}
		Cookie cookie = cookieMap.get(name);
		if(cookie==null){
			return null;
		}
		return cookie.getValue();
	}

	/**
	 * 设置
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void set(HttpServletResponse response,
	                       String name,
	                       String value,
	                       int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain("sws.natapp1.cc");
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
}
