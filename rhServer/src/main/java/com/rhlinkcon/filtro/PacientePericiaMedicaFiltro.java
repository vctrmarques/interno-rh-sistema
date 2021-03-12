package com.rhlinkcon.filtro;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacientePericiaMedicaFiltro {

	private Long especialidadeMedicaList; 
	
	private LocalDate dataAgenda; 
}
