package com.systempro.auth.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.systempro.auth.entities.User;

public class UserSecurityDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;

	private List<SimpleGrantedAuthority> authorities;

	public UserSecurityDetails(User user) {
		email = user.getEmail();
		password = user.getPassword();
		 authorities= Arrays.stream(user.getRoles().split(","))
	                .map(SimpleGrantedAuthority::new)
	                .collect(Collectors.toList());
	}

	/*
	 * public UserSecurityDetails(String password, String email, Set<String> roles)
	 * { this.password = password; this.email = email; this.authorities =
	 * roles.stream().map(x -> new SimpleGrantedAuthority(x.getBytes().toString()))
	 * .collect(Collectors.toList()); }
	 */

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
