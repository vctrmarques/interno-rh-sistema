package com.rhlinkcon.payload.usuario;

import java.util.List;
import java.util.Objects;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.papel.PapelResponse;

public class UsuarioResponse extends DadoCadastralResponse {
	private Long id;

	private String nome;

	private String login;

	private Boolean ativo;

	private String cpf;

	private Long anexoId;

	private String anexoFileDownloadUri;

	private String email;

	private Long empresaFilialId;
	
	private DadoBasicoDto empresaFilial;

	private List<PapelResponse> papeis;

	public UsuarioResponse() {
	}

	public UsuarioResponse(Long id, String login, String nome) {
		this.id = id;
		this.login = login;
		this.nome = nome;
	}

	public UsuarioResponse(Long id, String login, String nome, String fileDownloadUri, String email) {
		this.id = id;
		this.login = login;
		this.nome = nome;
		if (!Objects.isNull(fileDownloadUri)) {
			this.anexoFileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileDownloadUri)
					.toUriString();
		}
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

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getAnexoId() {
		return anexoId;
	}

	public void setAnexoId(Long anexoId) {
		this.anexoId = anexoId;
	}

	public String getAnexoFileDownloadUri() {
		return anexoFileDownloadUri;
	}

	public void setAnexoFileDownloadUri(String anexoFileDownloadUri) {
		this.anexoFileDownloadUri = anexoFileDownloadUri;
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

	public List<PapelResponse> getPapeis() {
		return papeis;
	}

	public void setPapeis(List<PapelResponse> papeis) {
		this.papeis = papeis;
	}

	public DadoBasicoDto getEmpresaFilial() {
		return empresaFilial;
	}

	public void setEmpresaFilial(DadoBasicoDto empresaFilial) {
		this.empresaFilial = empresaFilial;
	}

	
}
