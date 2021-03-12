package com.rhlinkcon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@PropertySources({ @PropertySource(value = "file:${datasourceExternalPath}", ignoreResourceNotFound = true) })
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${allowed.origin}")
	private String origin;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(origin, "http://localhost:3000").allowedMethods("*");
	}
}
