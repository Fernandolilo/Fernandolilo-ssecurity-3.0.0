package com.systempro.auth.services;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systempro.auth.entities.User;
import com.systempro.auth.entities.dto.AuthenticationDTO;
import com.systempro.auth.entities.dto.UserNewDTO;
import com.systempro.auth.repositories.UserRepository;
import com.systempro.auth.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

	private final UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public Optional<User> fromAuthentication(AuthenticationDTO request) throws ObjectNotFoundException {

		var auth = repository.findByEmail(request.getEmail());
		var password = repository.findByPassword(request.getPassword());

		if (!password.getPassword().equals(request.getPassword())) {
			new ObjectNotFoundException("Senha invalidos");
		}
		return Optional.of(auth.orElseThrow(() -> new ObjectNotFoundException("email ou senha invalidos")));
	}

	@Transactional(readOnly = true)
	public User findById(UUID id) {
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não existe"));
	}

	public User create(User obj) {
		if (obj.getId() == null) {
			repository.save(obj);
		}
		return obj;
	}

	public User fromNewDTO(UserNewDTO obj) {
		User user = new User(null, obj.getName(), obj.getCpf(), obj.getEmail(), obj.getPassword());

		for (String telefones : obj.getTelefones()) {
			user.getTelefones().add(telefones);
		}
		for (String roles : obj.getRoles()) {
			user.getRoles().add(roles);
		}
		return user;
	}

}
