package com.systempro.auth.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.systempro.auth.entities.User;
import com.systempro.auth.repositories.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	/*
	 * @Override public UserDetails loadUserByUsernamee(String username) throws
	 * UsernameNotFoundException { Optional<User> cli =
	 * repository.findByEmail(username); if
	 * (!cli.equals(repository.findByEmail(username))) { throw new
	 * ObjectNotFoundException("Email ou senha inválido"); } return new
	 * UserSecurityDetails( cli.get().getPassword(), cli.get().getEmail(),
	 * cli.get().getRoles()); }
	 */

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userInfo = repository.findByEmail(email);
		return userInfo.map(UserSecurityDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + email));

	}
}
