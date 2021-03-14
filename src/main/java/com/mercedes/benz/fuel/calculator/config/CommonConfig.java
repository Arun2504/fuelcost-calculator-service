package com.mercedes.benz.fuel.calculator.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCaching
@EnableScheduling
public class CommonConfig {
		
	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
		
		return builder.build();
	}

}
