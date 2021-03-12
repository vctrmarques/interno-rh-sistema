package com.rhlinkcon.payload.simuladorAposentadoria;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.util.Utils;

public class SimuladorAposentadoria {

	// DADOS GERAIS
	@NotNull
	@NotBlank
	private String nome;

	@NotNull
	@NotBlank
	private String sexo;

	@NotNull
	private Date dataNascimento;

	private int idade;

	@NotNull
	private Date dataIngressoServicoPublico;

	@NotNull
	@NotBlank
	private String modalidade;

	// TEMPO DE CONTRIBUIÇÃO NO CARGO ATUAL
	private Date tempoContribuicaoCargoAtualInicio;
	private Date tempoContribuicaoCargoAtualFim;
	private TempoTotal tempoContribuicaoCargoAtualTempoTotal;

	// DEDUÇOES
	private List<Deducao> deducoes;
	private TempoTotal tempoTotalDeducoes;
	private TempoTotal tempoTotalDeducoesSemContribuicao;
	private TempoTotal tempoTotalDeducoesComContribuicao;

	// OUTROS PERIODOS
	private List<OutroPeriodo> outrosPeriodos;
	private TempoTotal tempoTotalOutrosPeriodos;

	// OUTROS PERIODOS PUBLICO
	private List<OutroPeriodoPublico> outrosPeriodosPublicos;
	private TempoTotal tempoTotalOutrosPeriodosPublicos;

	// AVERBAÇÕES
	private List<Averbacao> averbacoes;
	private TempoTotal tempoTotalAverbacoes;

	// LICENÇA PREMIO
	private long qtdLicencaPremioFator1;
	private long diasLicencasPremioFator1;
	private TempoTotal tempoTotalLicencasPremioFator1;

	private long qtdLicencaPremioFator2;
	private long diasLicencasPremioFator2;
	private TempoTotal tempoTotalLicencasPremioFator2;

	// POSSIBILIDADES DE APOSENTADORIA
	private List<PossibilidadeAposentadoria> possibilAposentList;

	private Date possibAbonoPermanencia;
	private String possibFundamLegal;
	private TempoTotal tempoTotalContribTotais;
	private TempoTotal tempoTotalContribTotaisLicenInsalDeduc;

	public SimuladorAposentadoria() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
		this.idade = Utils.calculateAge(dataNascimento);
	}

	public Date getDataIngressoServicoPublico() {
		return dataIngressoServicoPublico;
	}

	public void setDataIngressoServicoPublico(Date dataIngressoServicoPublico) {
		this.dataIngressoServicoPublico = dataIngressoServicoPublico;
	}

	public List<Deducao> getDeducoes() {
		return deducoes;
	}

	public void setDeducoes(List<Deducao> deducoes) {
		this.deducoes = deducoes;
	}

	public List<OutroPeriodo> getOutrosPeriodos() {
		return outrosPeriodos;
	}

	public void setOutrosPeriodos(List<OutroPeriodo> outrosPeriodos) {
		this.outrosPeriodos = outrosPeriodos;
	}

	public List<OutroPeriodoPublico> getOutrosPeriodosPublicos() {
		return outrosPeriodosPublicos;
	}

	public void setOutrosPeriodosPublicos(List<OutroPeriodoPublico> outrosPeriodosPublicos) {
		this.outrosPeriodosPublicos = outrosPeriodosPublicos;
	}

	public List<Averbacao> getAverbacoes() {
		return averbacoes;
	}

	public void setAverbacoes(List<Averbacao> averbacoes) {
		this.averbacoes = averbacoes;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public TempoTotal getTempoTotalDeducoes() {
		return tempoTotalDeducoes;
	}

	public void setTempoTotalDeducoes(TempoTotal tempoTotalDeducoes) {
		this.tempoTotalDeducoes = tempoTotalDeducoes;
	}

	public TempoTotal getTempoTotalOutrosPeriodos() {
		return tempoTotalOutrosPeriodos;
	}

	public void setTempoTotalOutrosPeriodos(TempoTotal tempoTotalOutrosPeriodos) {
		this.tempoTotalOutrosPeriodos = tempoTotalOutrosPeriodos;
	}

	public TempoTotal getTempoTotalOutrosPeriodosPublicos() {
		return tempoTotalOutrosPeriodosPublicos;
	}

	public void setTempoTotalOutrosPeriodosPublicos(TempoTotal tempoTotalOutrosPeriodosPublicos) {
		this.tempoTotalOutrosPeriodosPublicos = tempoTotalOutrosPeriodosPublicos;
	}

	public TempoTotal getTempoTotalAverbacoes() {
		return tempoTotalAverbacoes;
	}

	public void setTempoTotalAverbacoes(TempoTotal tempoTotalAverbacoes) {
		this.tempoTotalAverbacoes = tempoTotalAverbacoes;
	}

	public List<PossibilidadeAposentadoria> getPossibilAposentList() {
		return possibilAposentList;
	}

	public void setPossibilAposentList(List<PossibilidadeAposentadoria> possibilAposentList) {
		this.possibilAposentList = possibilAposentList;
	}

	public Date getPossibAbonoPermanencia() {
		return possibAbonoPermanencia;
	}

	public void setPossibAbonoPermanencia(Date possibAbonoPermanencia) {
		this.possibAbonoPermanencia = possibAbonoPermanencia;
	}

	public String getPossibFundamLegal() {
		return possibFundamLegal;
	}

	public void setPossibFundamLegal(String possibFundamLegal) {
		this.possibFundamLegal = possibFundamLegal;
	}

	public TempoTotal getTempoTotalContribTotais() {
		return tempoTotalContribTotais;
	}

	public void setTempoTotalContribTotais(TempoTotal tempoTotalContribTotais) {
		this.tempoTotalContribTotais = tempoTotalContribTotais;
	}

	public TempoTotal getTempoTotalContribTotaisLicenInsalDeduc() {
		return tempoTotalContribTotaisLicenInsalDeduc;
	}

	public void setTempoTotalContribTotaisLicenInsalDeduc(TempoTotal tempoTotalContribTotaisLicenInsalDeduc) {
		this.tempoTotalContribTotaisLicenInsalDeduc = tempoTotalContribTotaisLicenInsalDeduc;
	}

	public Date getTempoContribuicaoCargoAtualInicio() {
		return tempoContribuicaoCargoAtualInicio;
	}

	public void setTempoContribuicaoCargoAtualInicio(Date tempoContribuicaoCargoAtualInicio) {
		this.tempoContribuicaoCargoAtualInicio = tempoContribuicaoCargoAtualInicio;
	}

	public Date getTempoContribuicaoCargoAtualFim() {
		return tempoContribuicaoCargoAtualFim;
	}

	public void setTempoContribuicaoCargoAtualFim(Date tempoContribuicaoCargoAtualFim) {
		this.tempoContribuicaoCargoAtualFim = tempoContribuicaoCargoAtualFim;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public long getQtdLicencaPremioFator1() {
		return qtdLicencaPremioFator1;
	}

	public void setQtdLicencaPremioFator1(long qtdLicencaPremioFator1) {
		this.qtdLicencaPremioFator1 = qtdLicencaPremioFator1;
	}

	public long getDiasLicencasPremioFator1() {
		return diasLicencasPremioFator1;
	}

	public void setDiasLicencasPremioFator1(long diasLicencasPremioFator1) {
		this.diasLicencasPremioFator1 = diasLicencasPremioFator1;
	}

	public TempoTotal getTempoTotalLicencasPremioFator1() {
		return tempoTotalLicencasPremioFator1;
	}

	public void setTempoTotalLicencasPremioFator1(TempoTotal tempoTotalLicencasPremioFator1) {
		this.tempoTotalLicencasPremioFator1 = tempoTotalLicencasPremioFator1;
	}

	public long getQtdLicencaPremioFator2() {
		return qtdLicencaPremioFator2;
	}

	public void setQtdLicencaPremioFator2(long qtdLicencaPremioFator2) {
		this.qtdLicencaPremioFator2 = qtdLicencaPremioFator2;
	}

	public long getDiasLicencasPremioFator2() {
		return diasLicencasPremioFator2;
	}

	public void setDiasLicencasPremioFator2(long diasLicencasPremioFator2) {
		this.diasLicencasPremioFator2 = diasLicencasPremioFator2;
	}

	public TempoTotal getTempoTotalLicencasPremioFator2() {
		return tempoTotalLicencasPremioFator2;
	}

	public void setTempoTotalLicencasPremioFator2(TempoTotal tempoTotalLicencasPremioFator2) {
		this.tempoTotalLicencasPremioFator2 = tempoTotalLicencasPremioFator2;
	}

	public TempoTotal getTempoContribuicaoCargoAtualTempoTotal() {
		return tempoContribuicaoCargoAtualTempoTotal;
	}

	public void setTempoContribuicaoCargoAtualTempoTotal(TempoTotal tempoContribuicaoCargoAtualTempoTotal) {
		this.tempoContribuicaoCargoAtualTempoTotal = tempoContribuicaoCargoAtualTempoTotal;
	}

	public TempoTotal getTempoTotalDeducoesSemContribuicao() {
		return tempoTotalDeducoesSemContribuicao;
	}

	public void setTempoTotalDeducoesSemContribuicao(TempoTotal tempoTotalDeducoesSemContribuicao) {
		this.tempoTotalDeducoesSemContribuicao = tempoTotalDeducoesSemContribuicao;
	}

	public TempoTotal getTempoTotalDeducoesComContribuicao() {
		return tempoTotalDeducoesComContribuicao;
	}

	public void setTempoTotalDeducoesComContribuicao(TempoTotal tempoTotalDeducoesComContribuicao) {
		this.tempoTotalDeducoesComContribuicao = tempoTotalDeducoesComContribuicao;
	}

}
