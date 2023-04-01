package com.systempro.auth.security.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		 http
	        .csrf()
	        .disable()
	        .authorizeHttpRequests()
	        .requestMatchers("/users/register/**")
	        .permitAll()
	        .requestMatchers("/users/authenticate/**")
	          .permitAll()
	        .anyRequest()
	          .authenticated()
	        .and()
	          .sessionManagement()
	          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return http.build();
		
	}

}
