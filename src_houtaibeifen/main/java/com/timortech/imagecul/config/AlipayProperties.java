package com.timortech.imagecul.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/22
 * @time: 16:18
 * @desc：
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AlipayProperties {

	private String appId;

	private String key;

	private String returnUrl;

	private String notifyUrl;
}
