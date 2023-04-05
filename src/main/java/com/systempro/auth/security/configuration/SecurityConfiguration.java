package com.systempro.auth.security.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	private final Environment env;
	

	public SecurityConfiguration(Environment env) {
		this.env = env;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// test environment released;
				if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
					http.authorizeHttpRequests().requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
							.and().csrf().ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).and()
							.headers(headers -> headers.frameOptions().sameOrigin());
				}
				return http.csrf().disable()
		                .authorizeHttpRequests()
		                .requestMatchers("/users/register").permitAll()
		                .anyRequest()
		                .authenticated().and()
		                .sessionManagement()
		                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		                .and()
		                .build();
			

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
