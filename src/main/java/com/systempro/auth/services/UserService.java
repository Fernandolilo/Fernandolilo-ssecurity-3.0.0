package com.systempro.auth.services;

import org.springframework.stereotype.Service;

import com.systempro.auth.entities.User;
import com.systempro.auth.entities.UserNewDTO;
import com.systempro.auth.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
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
