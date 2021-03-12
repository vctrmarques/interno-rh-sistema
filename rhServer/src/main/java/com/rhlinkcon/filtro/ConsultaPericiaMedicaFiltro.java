package com.rhlinkcon.filtro;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaPericiaMedicaFiltro {
	
	private String search;
	
	private LocalDate dataAgendamento; 
	
	private Long especialidadeMedicaId;
	
	private Boolean compareceu;
	
	private Long medicoId;
	
	private String nomeMedico;
	
	private String dtAgendamento;
	
}
