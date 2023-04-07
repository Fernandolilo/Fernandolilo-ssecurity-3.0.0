package com.systempro.auth.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systempro.auth.entities.User;
import com.systempro.auth.entities.dto.AuthenticationDTO;
import com.systempro.auth.entities.dto.UserNewDTO;
import com.systempro.auth.repositories.UserRepository;
import com.systempro.auth.security.jwt.JwtService;
import com.systempro.auth.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

	private final UserRepository repository;
	private final BCryptPasswordEncoder encoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public UserService(UserRepository repository, BCryptPasswordEncoder encoder, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		this.repository = repository;
		this.encoder = encoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public String fromAuthentication(AuthenticationDTO auth) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword()));
	
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(auth.getEmail());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

	@Transactional(readOnly = true)
	public User findById(UUID id) {
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não existe"));
	}

	public User create(User obj) {
		repository.save(obj);
		return obj;
	}

	public User fromNewDTO(UserNewDTO obj) {
		User user = new User(null, obj.getName(), obj.getCpf(), obj.getEmail(), encoder.encode(obj.getPassword()), obj.getRoles());
		for (String telefones : obj.getTelefones()) {
			user.getTelefones().add(telefones);
		}
	
		return user;
	}

	public void add() {
		User user = new User(null, "fernando", "123", "fernando@", encoder.encode("123"), "ROLE_ADMIN");
		user.getTelefones().add("1112345678");
		repository.save(user);
	}

}
