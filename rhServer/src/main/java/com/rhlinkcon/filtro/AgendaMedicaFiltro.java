package com.rhlinkcon.filtro;

import java.time.LocalDate;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaMedicaFiltro {
	
	private Long id;

	private String nome;
	
	private ArrayList<Long> especialidadeMedicaList; 
	
	private LocalDate dataInicio;
	
	private LocalDate dataFim; 
	
	private LocalDate dataAgenda; 
	
}
