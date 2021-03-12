package com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento;

import java.util.List;

import com.rhlinkcon.util.Utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioFolhaPagamentoDto {

	private String mesAnoCompetencia;
	
	private List<RelatorioFolhaPagamentoResumoFilialDto> resumoFilial;
	
	private List<RelatorioFolhaPagamentoResumoProventosDto> resumoProventosNormal;
	
	private List<RelatorioFolhaPagamentoResumoProventosDto> resumoProventosDiferenca;
	
	private List<RelatorioFolhaPagamentoResumoProventosDto> resumoProventosDevolucao;

	private Double totalLiquido;

	private Double totalBruto;

	private Long TotalFuncionarios;

	public RelatorioFolhaPagamentoDto() {}

	
	public RelatorioFolhaPagamentoDto(Integer mesCompentencia, Integer anoCompetencia, List<RelatorioFolhaPagamentoResumoFilialDto> resumoFilial) {
		this.mesAnoCompetencia = getMesANo(mesCompentencia, anoCompetencia);
		this.resumoFilial = resumoFilial;
		this.totalLiquido = getSomaLiquido(resumoFilial);
		this.totalBruto = getSomaBruto(resumoFilial);
		this.TotalFuncionarios = getSomaFuncionarios(resumoFilial);
	}
	
	public RelatorioFolhaPagamentoDto(List<RelatorioFolhaPagamentoResumoProventosDto> resumoProventosNormal, List<RelatorioFolhaPagamentoResumoProventosDto> resumoProventosDiferenca,
			List<RelatorioFolhaPagamentoResumoProventosDto> resumoProventosDevolucao) {
		
		this.resumoProventosNormal =  resumoProventosNormal;
		this.resumoProventosDiferenca =  resumoProventosDiferenca;
		this.resumoProventosDevolucao =  resumoProventosDevolucao;
	}
	
	// somar valor total liquido
	private Double getSomaLiquido(List<RelatorioFolhaPagamentoResumoFilialDto> lista) {
		Double valor = 0.0;
		for(RelatorioFolhaPagamentoResumoFilialDto e : lista) {
			valor += e.getLiquidos(); 
		}
		return valor;
	}

	// somar valor total bruto
	private Double getSomaBruto(List<RelatorioFolhaPagamentoResumoFilialDto> lista) {
		Double valor = 0.0;
		for(RelatorioFolhaPagamentoResumoFilialDto e : lista) {
			valor += e.getBruto(); 
		}
		return valor;
	}
	
	// somar numero total de funcionarios
	private Long getSomaFuncionarios(List<RelatorioFolhaPagamentoResumoFilialDto> lista) {
		Long valor = 0L;
		for(RelatorioFolhaPagamentoResumoFilialDto e : lista) {
			valor += e.getCountFuncionarios(); 
		}
		return valor;
	}

	private String getMesANo(Integer mesCompentencia, Integer anoCompetencia) {
		return Utils.getMesCompetenciaString(mesCompentencia) + "/" + anoCompetencia.toString();
	}
	
	


}
