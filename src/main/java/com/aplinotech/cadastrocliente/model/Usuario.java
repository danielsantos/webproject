package com.aplinotech.cadastrocliente.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(length = 100)
	private String nome;
	
	@Column
	private String username;

	@Column(length = 80)
	private String email;
	
	@Transient
	private String confirmeEmail;

	@Column
	private String password;

	@Column
	private Date dataCadastro;

	@Column
	private String situacao;

	@Column
	private String tokenCadastro;

	@Transient
	private String passwordConfirm;

	@ManyToMany
	@JoinTable(name = "usuario_role", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getConfirmeEmail() {
		return confirmeEmail;
	}

	public void setConfirmeEmail(String confirmeEmail) {
		this.confirmeEmail = confirmeEmail;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getTokenCadastro() {
		return tokenCadastro;
	}

	public void setTokenCadastro(String tokenCadastro) {
		this.tokenCadastro = tokenCadastro;
	}

}
