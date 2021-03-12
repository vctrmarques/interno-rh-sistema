package com.rhlinkcon.payload.arrecadacaoAliquota;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jrimum.utilix.Collections;

import com.rhlinkcon.model.ArrecadacaoAliquotaEncargo;
import com.rhlinkcon.model.ArrecadacaoAliquotaPeriodo;
import com.rhlinkcon.payload.DadoCadastralResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrecadacaoAliquotaDto extends DadoCadastralResponse {

	private Long id;
	
	private LocalDate inicioVigencia;
	
	private LocalDate fimVigencia;
	
	private List<ArrecadacaoAliquotaEncargoDto> encargos;
	
	public ArrecadacaoAliquotaDto() {}
	
	public ArrecadacaoAliquotaDto(ArrecadacaoAliquotaPeriodo obj) {
		setId(obj.getId());
		setInicioVigencia(obj.getInicioVigencia());
		setFimVigencia(obj.getFimVigencia());
		
		if(Collections.isNotEmpty(obj.getEncargos())) {
			setEncargos(new ArrayList<>());
			for(ArrecadacaoAliquotaEncargo e : obj.getEncargos()) {
				getEncargos().add(new ArrecadacaoAliquotaEncargoDto(e));
			}
		}
	}
	
}
