package com.sps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfiguration {

	@Bean(name = "template")
	public RestTemplate createRest() {
		return new RestTemplate();
	}

	@Bean(name = "webClient")
	public WebClient createWebClient() {
		return WebClient.create();
	}
}
