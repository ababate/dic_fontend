package com.timortech.imagecul.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author: loccen
 * @project: huibida
 * @date: 2018/8/13
 * @time: 15:21
 * @desc：
 */
@Data
@Component
@ConfigurationProperties(prefix = "image")
public class ImageConfig {

	private String location;

	private String baseUrl;

	private String remoteUrl;

	private String postUrl;

	private String queryUrl;
}
