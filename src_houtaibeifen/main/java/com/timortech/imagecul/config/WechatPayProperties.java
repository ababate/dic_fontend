package com.timortech.imagecul.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

import lombok.Data;

/**
 * @author: loccen
 * @project: huibida
 * @date: 2018/8/5
 * @time: 22:22
 * @desc：
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat.pay")
public class WechatPayProperties {

	/**
	 * 公众平台id
	 */
	private String appId;

	/**
	 * 商户号
	 */
	private String mchId;

	/**
	 * 商户密钥
	 */
	private String mchKey;

	/**
	 * 商户证书路径
	 */
	private String keyPath;
//
//	/**
//	 * 微信支付异步通知地址
//	 */
//	private String notifyUrl;
//
//	/**
//	 * 重定向地址
//	 */
//	private String returnUrl;
//
//	/**
//	 * 微信模版id
//	 */
//	private Map<String, String> templateId;
}
