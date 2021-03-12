package com.rhlinkcon.payload.cbo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.model.Cbo;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class CboDto extends DadoCadastralResponse {

	private Long id;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String codigo;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String nome;

	public CboDto() {

	}

	public CboDto(Cbo cbo) {
		this.id = cbo.getId();
		this.codigo = cbo.getCodigo();
		this.nome = cbo.getNome();
		this.setCriadoEm(cbo.getCreatedAt());
		this.setAlteradoEm(cbo.getUpdatedAt());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
