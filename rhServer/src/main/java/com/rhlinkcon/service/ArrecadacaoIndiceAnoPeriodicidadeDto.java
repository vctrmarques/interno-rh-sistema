package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;

import org.jrimum.utilix.Collections;

import com.rhlinkcon.model.ArrecadacaoIndiceAnoPeriodicidade;
import com.rhlinkcon.model.ArrecadacaoIndiceMes;
import com.rhlinkcon.model.PeriodicidadeMesEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrecadacaoIndiceAnoPeriodicidadeDto {

	private Long id;
	
	private Long ano;
	
	private String periodicidade;
	
	private List<ArrecadacaoIndiceMesDto> meses;
	
	public ArrecadacaoIndiceAnoPeriodicidadeDto() {}
	
	public ArrecadacaoIndiceAnoPeriodicidadeDto(Long ano){
		setAno(ano);
	}
	
	public ArrecadacaoIndiceAnoPeriodicidadeDto(ArrecadacaoIndiceAnoPeriodicidade a) {
		setId(a.getId());
		setAno(a.getAno());
		setPeriodicidade(a.getPeriodicidadeMes().getLabel());
		
		if(Collections.isNotEmpty(a.getArrecadacaoIndiceMes())) {
			setMeses(new ArrayList<>());
			for(ArrecadacaoIndiceMes e : a.getArrecadacaoIndiceMes()) {
				getMeses().add(new ArrecadacaoIndiceMesDto(e));
			}
		}
	}
}
