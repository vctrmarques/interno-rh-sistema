package com.rhlinkcon.filtro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatimentoFolhaCustomizacaoFiltro {

	//filtros
	
	private Long id;

	private boolean orgao;

	private Integer competencia;
	
	private Long tipoProcessamentoId;
	
	private Integer ano;

	//contracheque
	private boolean dadosBancariosContracheque;
	
	private boolean matriculaContracheque;
	
	private boolean funcionarioContracheque;
	
	private boolean lotacaoContracheque;
	
	private boolean funcaoContracheque;
	
	private boolean dependenteIrContracheque;
	
	private boolean dependenteSalarioFamiliaContracheque;
	
	private boolean dataAdmissaoContracheque;
	
	private boolean situacaoFuncionalContracheque;
	
	private boolean cargaHorariaContracheque;
	
	private boolean vantagensContracheque;
	
	private boolean descontosContracheque;
	
	private boolean totalizadoresContracheque;
	
	//situacao
	
	private boolean resumoLotacaoSituacao;
	
	private boolean resumoFilialSituacao;
	
	private boolean resumoEmpresaSituacao;
	
	private boolean vantagensSituacao;
	
	private boolean descontosSituacao;
	
	private boolean totaisFuncionariosSituacao;
	
	private boolean totalizadoresSituacao;
	
	//lotação
	
	private boolean vantagensLotacao;
	
	private boolean descontosLotacao;
	
	private boolean totaisFuncionariosLotacao;

	private boolean totalizadoresLotacao;
	
	//filial

	private boolean vantagensFilial;
	
	private boolean descontosFilial;
	
	private boolean totalizadoresFilial;
	
	private boolean totaisFuncionariosFilial;

	//empresa

	private boolean vantagensEmpresa;
	
	private boolean descontosEmpresa;
	
	private boolean totalizadoresEmpresa;
	
	private boolean totaisFuncionariosEmpresa;
	
	public boolean possuiContracheque() {
		return (this.cargaHorariaContracheque || this.dadosBancariosContracheque || this.dataAdmissaoContracheque || this.dependenteIrContracheque 
				|| this.dependenteSalarioFamiliaContracheque || this.descontosContracheque || this.funcaoContracheque || this.funcionarioContracheque
				|| this.lotacaoContracheque || this.matriculaContracheque || this.situacaoFuncionalContracheque || this.totalizadoresContracheque
				|| this.vantagensContracheque);
	}
	
	public boolean possuiSituacao() {
		return (this.descontosSituacao || this.resumoEmpresaSituacao || this.resumoFilialSituacao || this.resumoLotacaoSituacao
				|| this.totaisFuncionariosSituacao || this.totalizadoresSituacao || this.vantagensSituacao);
	}
	
	public boolean possuiLotacao() {
		return (this.descontosLotacao || this.totaisFuncionariosLotacao || this.totalizadoresLotacao || this.vantagensLotacao);
	}
	
	public boolean possuiFilial() {
		return (this.descontosFilial || this.totaisFuncionariosFilial || this.totalizadoresFilial || this.vantagensFilial);
	}
	
	public boolean possuiEmpresa() {
		return (this.orgao && (this.descontosEmpresa || this.totaisFuncionariosEmpresa || this.totalizadoresEmpresa || this.vantagensEmpresa));
	}

	
}
