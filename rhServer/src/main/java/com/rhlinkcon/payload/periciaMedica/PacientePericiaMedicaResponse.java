package com.rhlinkcon.payload.periciaMedica;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PacientePericiaMedicaResponse {

	private Long id;

	private String cpf;

	private String nome;
}
