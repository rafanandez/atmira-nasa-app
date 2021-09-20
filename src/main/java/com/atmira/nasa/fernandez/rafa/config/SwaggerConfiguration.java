package com.atmira.nasa.fernandez.rafa.config;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	@Bean
	public Docket atmiraNasaApp() {
		return new Docket(DocumentationType.SWAGGER_2).enableUrlTemplating(true).apiInfo(apiInfo()).directModelSubstitute(LocalDateTime.class, Date.class).select().apis(RequestHandlerSelectors.basePackage("com.atmira.nasa.fernandez.rafa.controller")).paths(regex("/api.*")).build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Cliente REST").description("Cliente REST para consultar asterorides").version("2.0").build();
	}

}
