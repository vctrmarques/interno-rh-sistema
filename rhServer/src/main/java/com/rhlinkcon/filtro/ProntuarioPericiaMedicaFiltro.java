package com.rhlinkcon.filtro;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProntuarioPericiaMedicaFiltro {

	private String nome;
	
	private LocalDate dataAgendamento;
	
	private Boolean status;
}
