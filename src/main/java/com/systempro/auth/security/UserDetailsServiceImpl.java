package com.systempro.auth.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.systempro.auth.entities.User;
import com.systempro.auth.repositories.UserRepository;
import com.systempro.auth.services.exceptions.ObjectNotFoundException;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository repository;

	public UserDetailsServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> cli = repository.findByEmail(username);
		if (!cli.equals(repository.findByEmail(username))) {
			throw new ObjectNotFoundException("Email ou senha inválido");
		}
		return new UserSecurityDetails(
				  cli.get().getPassword(), cli.get().getEmail(), cli.get().getRoles());
	}

}
