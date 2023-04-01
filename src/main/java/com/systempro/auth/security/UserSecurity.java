package com.systempro.auth.security;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSecurity implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private String id = UUID.randomUUID().toString();
	private String name;
	private String password;
	private String email;
	private Collection<? extends GrantedAuthority> autorities;

	public UserSecurity(String id, String name, String password, String email, Set<String> roles) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.autorities = roles.stream().map(x -> new SimpleGrantedAuthority(x.getBytes().toString()))
				.collect(Collectors.toList());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return autorities;
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
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean hasRole(String roles) {
		return getAutorities().contains(new SimpleGrantedAuthority(roles.getBytes().toString()));
	}

	public Collection<? extends GrantedAuthority> getAutorities() {
		return autorities;
	}

}
