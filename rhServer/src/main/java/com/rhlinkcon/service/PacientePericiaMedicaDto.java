package com.rhlinkcon.service;

import java.time.Instant;
import java.util.List;

import com.rhlinkcon.controller.ConsultaPericiaMedicaDto;
import com.rhlinkcon.model.ConsultaPericiaMedica;
import com.rhlinkcon.payload.DadoCadastralResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacientePericiaMedicaDto extends DadoCadastralResponse {
	
	private Long id;
	
	private String cpf;
	
	private String paciente;
	
	private String tipoAnalise;
	
	private Long numeroProcesso;
	
	private List<String> telefone;
	
	private Integer contador;
	
	private String nomeMae;
	
	private char sexo;
	
	private Instant dataNascimento;
	
	private List<ConsultaPericiaMedicaDto> consultasPericiasMedicasDto;


	public PacientePericiaMedicaDto() {}
	
	public PacientePericiaMedicaDto(Long id) {
		setId(id);
	}
	
	public PacientePericiaMedicaDto(ConsultaPericiaMedica consultaPericiaMedica) {
		setId(consultaPericiaMedica.getId());
		
	}

}
