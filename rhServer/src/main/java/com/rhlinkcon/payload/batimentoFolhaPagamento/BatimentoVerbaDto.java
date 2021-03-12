package com.rhlinkcon.payload.batimentoFolhaPagamento;

import com.rhlinkcon.model.Verba;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatimentoVerbaDto {

	private String rubrica;
	
	private String descricao;
	
	private String tipoVerba;
	
	private Double valor;
	
	public BatimentoVerbaDto(Double valorVerba, Verba verba) {
		this.rubrica = verba.getCodigo();
		this.descricao = verba.getDescricaoVerba();
		this.tipoVerba = verba.getTipoVerba().name();
		this.valor = valorVerba;
	}
	
}
