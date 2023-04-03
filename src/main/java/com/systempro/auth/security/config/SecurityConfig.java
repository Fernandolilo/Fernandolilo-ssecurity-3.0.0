package com.systempro.auth.security.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	private final Environment environment;
	
	public SecurityConfig(Environment environment) {
		
		this.environment = environment;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
	// test environment released;
		if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			http.authorizeHttpRequests().requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
					.and().csrf().ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).and()
					.headers(headers -> headers.frameOptions().sameOrigin());
		}

		http.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/users/register/**").permitAll()		
		.anyRequest().authenticated()
		.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		
		return http.build();
	}

}
