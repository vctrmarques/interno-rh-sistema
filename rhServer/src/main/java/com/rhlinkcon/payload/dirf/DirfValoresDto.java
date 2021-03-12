package com.rhlinkcon.payload.dirf;

import com.rhlinkcon.util.Utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DirfValoresDto {

	private Integer mes;
	private Double valor;
	
	public DirfValoresDto(Integer mes, Double valor) {
		this.mes = mes;
		this.valor = Utils.roundValue(valor);
	}

	
}
