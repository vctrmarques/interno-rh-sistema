package com.rhlinkcon.filtro;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaMedicaDataFiltro {

	private Long id;
	
	private Long especialidadeMedicaId; 
	
	private LocalDate dataAgenda;
	
	private boolean usarMaiorQuePraDataAgendaPericiaMedica; 
}
