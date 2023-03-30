package com.systempro.auth.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id = UUID.randomUUID().toString();
	private String name;
	private String cpf;

	private String password;
	private String email;

	private Set<String> telefones = new HashSet<>();

	private Set<String> roles = new HashSet<>();

	public UserNewDTO() {

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public Set<String> getRoles() {
		return roles;
	}
}
