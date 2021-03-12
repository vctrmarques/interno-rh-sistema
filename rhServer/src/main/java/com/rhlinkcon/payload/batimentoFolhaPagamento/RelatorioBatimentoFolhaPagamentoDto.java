package com.rhlinkcon.payload.batimentoFolhaPagamento;

import java.util.List;

import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.folhaCompetencia.FolhaCompetenciaResponse;
import com.rhlinkcon.payload.tipoProcessamento.TipoProcessamentoResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioBatimentoFolhaPagamentoDto {

	private BatimentoDadosOrgaoFilialDto empresaFilial;
	
	private FolhaCompetenciaResponse competencia;
	
	private String tipoProcessamento;
	
	private List<BatimentoDadosFolhaDto> contracheque;
	
	private List<BatimentoResumoDto> resumoSituacoes;
	
	private BatimentoResumoDto resumoFilial;
	
	private List<BatimentoResumoDto> resumoLotacoes;
	
	private BatimentoResumoDto resumoEmpresa;
	
	public RelatorioBatimentoFolhaPagamentoDto() {}

	public RelatorioBatimentoFolhaPagamentoDto(EmpresaFilial orgao, EmpresaFilialResponse filial, FolhaCompetenciaResponse competencia2, TipoProcessamentoResponse tipoProcessamento2,
			List<BatimentoDadosFolhaDto> listaDadosFolhaDto, List<BatimentoResumoDto> listaSituacoesDto, BatimentoResumoDto resumoFilialDto, 
			List<BatimentoResumoDto> listaLotacoesDto, BatimentoResumoDto resumoEmpresaDto) {
		this.empresaFilial = new BatimentoDadosOrgaoFilialDto(orgao, filial);
		this.competencia = competencia2;
		this.tipoProcessamento = tipoProcessamento2.getDescricao();
		this.contracheque = listaDadosFolhaDto;
		this.resumoSituacoes = listaSituacoesDto;
		this.resumoFilial = resumoFilialDto;
		this.resumoLotacoes = listaLotacoesDto;
		this.resumoEmpresa = resumoEmpresaDto;
	}
	
	public RelatorioBatimentoFolhaPagamentoDto(EmpresaFilial orgao, EmpresaFilialResponse filial, FolhaCompetenciaResponse competencia2, TipoProcessamentoResponse tipoProcessamento2,
			List<BatimentoDadosFolhaDto> listaDadosFolhaDto, BatimentoResumoDto resumoDto) {
		this.empresaFilial = new BatimentoDadosOrgaoFilialDto(orgao, filial);
		this.competencia = competencia2;
		this.tipoProcessamento = tipoProcessamento2.getDescricao();
		this.contracheque = listaDadosFolhaDto;
		this.resumoFilial = resumoDto;
	}
	
	
}
