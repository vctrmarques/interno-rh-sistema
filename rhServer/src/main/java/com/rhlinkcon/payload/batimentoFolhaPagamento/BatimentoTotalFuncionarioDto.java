package com.rhlinkcon.payload.batimentoFolhaPagamento;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatimentoTotalFuncionarioDto {

	private Integer total;
	
	private List<BatimentoFuncionarioCountDto> lista;
	
	public BatimentoTotalFuncionarioDto(List<BatimentoFuncionarioCountDto> lista) {
		this.lista = lista;
		this.total = getTotal(lista);
	}

	public BatimentoTotalFuncionarioDto(BatimentoFuncionarioCountDto dto) {
		List<BatimentoFuncionarioCountDto> lista = new ArrayList<>();
		lista.add(dto);
		this.lista = lista;
		this.total = Math.toIntExact(dto.getCount());
	}

	private Integer getTotal(List<BatimentoFuncionarioCountDto> lista) {
		Integer valor = 0;
		for(BatimentoFuncionarioCountDto e : lista) {
			valor += Math.toIntExact(e.getCount());
		}
		
		return valor;
	}
}
