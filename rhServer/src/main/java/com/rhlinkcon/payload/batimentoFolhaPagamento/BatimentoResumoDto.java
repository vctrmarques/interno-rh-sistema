package com.rhlinkcon.payload.batimentoFolhaPagamento;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatimentoResumoDto {
	
	private String descricao;

	private List<BatimentoVerbaDto> vantagens;

	private List<BatimentoVerbaDto> descontos;

	private Double totalProventos;

	private Double totalDescontos;

	private Double totalLiquido;
	
	private BatimentoTotalFuncionarioDto totalFuncionarios;
	
	public BatimentoResumoDto() {
		this.setTotalProventos(0.0);
		this.setTotalLiquido(0.0);
		this.setTotalDescontos(0.0);
	}

	public BatimentoResumoDto(List<BatimentoVerbaDto> vantagens2, List<BatimentoVerbaDto> descontos2, BatimentoFuncionarioCountDto dto) {
		this.descricao = dto.getDescricao();
		this.vantagens = vantagens2;
		this.descontos = descontos2;
		this.totalProventos = getTotal(vantagens2);
		this.totalDescontos = getTotal(descontos2);
		this.totalLiquido = this.totalProventos - this.totalDescontos;
		this.totalFuncionarios = new BatimentoTotalFuncionarioDto(dto);	
	}
	
	public BatimentoResumoDto(List<BatimentoVerbaDto> vantagens2, List<BatimentoVerbaDto> descontos2, BatimentoTotalFuncionarioDto dto) {
		this.vantagens = vantagens2;
		this.descontos = descontos2;
		this.totalProventos = getTotal(vantagens2);
		this.totalDescontos = getTotal(descontos2);
		this.totalLiquido = this.totalProventos - this.totalDescontos;
		this.totalFuncionarios = dto;	
	}

	private Double getTotal(List<BatimentoVerbaDto> lista) {
		Double valor = 0.0;
		for (BatimentoVerbaDto e : lista) {
			valor += e.getValor();
		}
		return valor;
	}
	
}
