package com.rhlinkcon.filtro;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicoFiltro {

	private String nome;

	private Integer matricula;

	private ArrayList<Long> filialList; 
	
	private String statusMedico;
	
}
