package com.rhlinkcon.service;

import com.rhlinkcon.model.ArrecadacaoIndiceMes;
import com.rhlinkcon.model.MesEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrecadacaoIndiceMesDto {

	private Long id;
	
	private String mes;
	
	private Double aliquota;
	
	public ArrecadacaoIndiceMesDto() {}
	
	public ArrecadacaoIndiceMesDto(ArrecadacaoIndiceMes m) {
		setId(m.getId());
		setMes((m.getMes().getLabel()));
		setAliquota(m.getAliquota());		
	}
}
