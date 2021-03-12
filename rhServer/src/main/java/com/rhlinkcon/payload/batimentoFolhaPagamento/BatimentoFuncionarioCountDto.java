package com.rhlinkcon.payload.batimentoFolhaPagamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatimentoFuncionarioCountDto {

	private Long entitiId;
	
	private String descricao;
	
	private Long count;
	
	public BatimentoFuncionarioCountDto(Long id, String descricao, Long valor) {
		this.entitiId = id;
		this.descricao = descricao;
		this.count = valor;
	}
}
