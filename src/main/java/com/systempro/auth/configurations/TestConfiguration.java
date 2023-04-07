package com.systempro.auth.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.systempro.auth.services.UserService;

@Configuration
@Profile("test")
public class TestConfiguration {
	private final UserService service;

	public TestConfiguration(UserService service) {
		this.service = service;
	}

	@Bean
	public boolean initDBTests() {
		service.add();
		return true;
	}

}
