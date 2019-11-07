package com.timortech.imagecul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 21:04
 * @desc：关于swagger的配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket docket(){
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.paths(PathSelectors.any()).build();

	}
	//构建api文档的详细信息函数
	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				//页面标题
				.title("提莫科技-数字图像匹配系统RESTful API")
				.description("自动构建restful接口")
				//版本号
				.version("1.0")
				.build();
	}

}
