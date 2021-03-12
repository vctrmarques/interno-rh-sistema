package com.rhlinkcon.payload.especialidadeMedica;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.model.EspecialidadeMedica;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class EspecialidadeMedicaDto extends DadoCadastralResponse {

	private Long id;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String codigo;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String nome;

	public EspecialidadeMedicaDto() {

	}

	public EspecialidadeMedicaDto(EspecialidadeMedica especialidadeMedicaDto) {
		this.id = especialidadeMedicaDto.getId();
		this.codigo = especialidadeMedicaDto.getCodigo();
		this.nome = especialidadeMedicaDto.getNome();
		this.setCriadoEm(especialidadeMedicaDto.getCreatedAt());
		this.setAlteradoEm(especialidadeMedicaDto.getUpdatedAt());
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
