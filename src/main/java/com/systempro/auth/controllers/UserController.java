package com.systempro.auth.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.systempro.auth.entities.User;
import com.systempro.auth.entities.UserNewDTO;
import com.systempro.auth.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable UUID id){
		User user = service.findById(id);
		return ResponseEntity.ok().body(user);
		
	}	

	@PostMapping("/register")
	public ResponseEntity<Void> insert(@Valid @RequestBody UserNewDTO objDTO) {
		User obj = service.fromNewDTO(objDTO);
		obj = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
