package com.systempro.auth.security.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.systempro.auth.security.UserDetailsServiceImpl;
import com.systempro.auth.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	private final JwtAuthenticationFilter auth;
	private final Environment env;

	public SecurityConfiguration(JwtAuthenticationFilter auth, Environment env) {
		this.auth = auth;
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
	                .requestMatchers("/users/authenticate").permitAll()
	                .requestMatchers("/users/register/**").permitAll()
	                .requestMatchers("/users/**").authenticated()
	                .anyRequest()
	                .authenticated().and()
	                .sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and()
	                .authenticationProvider(authenticationProvider())
	                .addFilterBefore(auth, UsernamePasswordAuthenticationFilter.class)
	                .build();

	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}


	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
