package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;

import org.jrimum.utilix.Collections;

import com.rhlinkcon.model.ArrecadacaoIndice;
import com.rhlinkcon.model.ArrecadacaoIndiceAnoPeriodicidade;
import com.rhlinkcon.payload.DadoCadastralResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrecadacaoIndiceDto extends DadoCadastralResponse {

	private Long id;
	
	private String indice;
	
	private List<ArrecadacaoIndiceAnoPeriodicidadeDto> anos;
	
	public ArrecadacaoIndiceDto(){}
	
	public ArrecadacaoIndiceDto(Long id, String indice){
		setId(id);
		setIndice(indice);
	}
	
	public ArrecadacaoIndiceDto(ArrecadacaoIndice obj){
		setId(obj.getId());
		setIndice(obj.getIndice());
		
		if(Collections.isNotEmpty(obj.getAnos())) {
			setAnos(new ArrayList<>());
			for(ArrecadacaoIndiceAnoPeriodicidade e : obj.getAnos()) {
				getAnos().add(new ArrecadacaoIndiceAnoPeriodicidadeDto(e));
			}
		}
		
	}
}
