package com.timortech.imagecul.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: loccen
 * @project: huibida
 * @date: 2018/8/5
 * @time: 22:17
 * @desc：
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
public class WechatPayConfig {

	private final WechatPayProperties payProperties;

	@Autowired
	public WechatPayConfig(WechatPayProperties payProperties) {
		this.payProperties = payProperties;
	}

	@Bean
	@ConditionalOnMissingBean
	public WxPayService wxPayService(){
		WxPayConfig wxPayConfig = new WxPayConfig();
		wxPayConfig.setAppId(StringUtils.trimToNull(this.payProperties.getAppId()));
		wxPayConfig.setMchId(StringUtils.trimToNull(this.payProperties.getMchId()));
		wxPayConfig.setMchKey(StringUtils.trimToNull(this.payProperties.getMchKey()));
		wxPayConfig.setKeyPath(StringUtils.trimToNull(this.payProperties.getKeyPath()));
		WxPayService wxPayService = new WxPayServiceImpl();
		wxPayService.setConfig(wxPayConfig);
		return wxPayService;
	}

//	@Bean
//	public BestPayServiceImpl bestPayService(){
//		BestPayServiceImpl bestPayService = new BestPayServiceImpl();
//		bestPayService.setWxPayH5Config(wxPayH5Config());
//		return bestPayService;
//	}
//
//	@Bean
//	WxPayH5Config wxPayH5Config(){
//		WxPayH5Config wxPayH5Config = new WxPayH5Config();
//		wxPayH5Config.setAppId(payProperties.getMpAppId());
//		//wxPayH5Config.setAppSecret(payProperties.getSecret());
//		wxPayH5Config.setMchId(payProperties.getMchId());
//		wxPayH5Config.setMchKey(payProperties.getMchKey());
//		wxPayH5Config.setKeyPath(payProperties.getKeyPath());
//		wxPayH5Config.setNotifyUrl(payProperties.getNotifyUrl());
//		return wxPayH5Config;
//	}
}
