package com.rhlinkcon.payload.processo;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProcessoRequest {
	
	private Long id;
	
	@NotNull
	private Long funcionarioId;
	
	@NotNull
	@NotBlank
	private String numeroProcesso;
	
	@NotNull
	@NotBlank
	private String assunto;
	
	@NotNull
	private Instant dataInicio;
	
	private Instant dataFim;
	
	private String requerente;
	
	private Long atoPortaria;
	
	@NotBlank
	private String situacao;

	private Instant dataAto;
	
	private String doe; 

	private Instant dataDoe;
	
	private Boolean impactoFinanceiro;
	
	private String tipoReflexo;
	
	private Instant inicioImpactoFinanco;
	
	private Instant fimImpactoFinanco;
	
	private List<Long> anexos;
	

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public Instant getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Instant dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Instant getDataFim() {
		return dataFim;
	}

	public void setDataFim(Instant dataFim) {
		this.dataFim = dataFim;
	}

	public String getRequerente() {
		return requerente;
	}

	public void setRequerente(String requerente) {
		this.requerente = requerente;
	}

	public Long getAtoPortaria() {
		return atoPortaria;
	}

	public void setAtoPortaria(Long atoPortaria) {
		this.atoPortaria = atoPortaria;
	}

	public Instant getDataAto() {
		return dataAto;
	}

	public void setDataAto(Instant dataAto) {
		this.dataAto = dataAto;
	}

	public String getDoe() {
		return doe;
	}

	public void setDoe(String doe) {
		this.doe = doe;
	}

	public Instant getDataDoe() {
		return dataDoe;
	}

	public void setDataDoe(Instant dataDoe) {
		this.dataDoe = dataDoe;
	}

	public Boolean getImpactoFinanceiro() {
		return impactoFinanceiro;
	}

	public void setImpactoFinanceiro(Boolean impactoFinanceiro) {
		this.impactoFinanceiro = impactoFinanceiro;
	}

	public String getTipoReflexo() {
		return tipoReflexo;
	}

	public void setTipoReflexo(String tipoReflexo) {
		this.tipoReflexo = tipoReflexo;
	}

	public Instant getInicioImpactoFinanco() {
		return inicioImpactoFinanco;
	}

	public void setInicioImpactoFinanco(Instant inicioImpactoFinanco) {
		this.inicioImpactoFinanco = inicioImpactoFinanco;
	}

	public Instant getFimImpactoFinanco() {
		return fimImpactoFinanco;
	}

	public void setFimImpactoFinanco(Instant fimImpactoFinanco) {
		this.fimImpactoFinanco = fimImpactoFinanco;
	}

	public List<Long> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Long> anexos) {
		this.anexos = anexos;
	}
	
	
}
