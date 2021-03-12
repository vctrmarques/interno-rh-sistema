package com.rhlinkcon.payload.arrecadacaoAliquota;

import com.rhlinkcon.model.ArrecadacaoAliquotaEncargo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrecadacaoAliquotaEncargoDto {

	private Long id;
	
	private Long periodoId;
	
	private String aliquotaEncargo;
	
	private Double aliquota;
	
	private String other;
	
	public ArrecadacaoAliquotaEncargoDto() {}
	
	public ArrecadacaoAliquotaEncargoDto(ArrecadacaoAliquotaEncargo e) {
		setId(e.getId());
		setPeriodoId(e.getArrecadacaoAliquotaPeriodo().getId());
		setAliquotaEncargo(e.getAliquotaEncargo().getLabel());
		setAliquota(e.getAliquota());
		setOther(e.getAliquotaEncargo().getOther());
	}
	
}
