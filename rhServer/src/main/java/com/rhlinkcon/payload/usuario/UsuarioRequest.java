package com.rhlinkcon.payload.usuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioRequest {

	private Long id;

	@NotBlank
	@Size(min = 4, max = 40)
	private String nome;

	@NotBlank
	@Size(min = 3, max = 15)
	private String login;

	@Size(min = 6, max = 20)
	private String senha;

	@NotBlank
	@Size(min = 11, max = 11)
	private String cpf;

	@NotNull
	private Boolean ativo;

	private Long anexoId;

	@NotBlank
    @Size(max = 40)
    @Email
    private String email;
	
	@NotNull
	private Long empresaFilialId;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Long getAnexoId() {
		return anexoId;
	}

	public void setAnexoId(Long anexoId) {
		this.anexoId = anexoId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getEmpresaFilialId() {
		return empresaFilialId;
	}

	public void setEmpresaFilialId(Long empresaFilialId) {
		this.empresaFilialId = empresaFilialId;
	}
	
}