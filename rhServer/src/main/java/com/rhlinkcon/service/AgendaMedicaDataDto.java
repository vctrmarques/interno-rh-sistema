package com.rhlinkcon.service;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaMedicaDataDto {
	
	private Long id;
	
	private String diaSemana;
	
	private String horario;
	
	private LocalDate data;
	
}
