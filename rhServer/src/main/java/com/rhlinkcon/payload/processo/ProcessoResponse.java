package com.rhlinkcon.payload.processo;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.rhlinkcon.model.Processo;
import com.rhlinkcon.model.SituacaoProcessoEnum;
import com.rhlinkcon.model.TipoReflexoEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.util.Projecao;

public class ProcessoResponse extends DadoCadastralResponse {
	
	private Long id;

	private String numeroProcesso;

	private String assunto;

	private Instant dataInicio;

	private Instant dataFim;

	private String requerente;

	private Long atoPortaria;

	private Instant dataAto;

	private String doe;

	private Instant dataDoe;

	private Boolean impactoFinanceiro;

	@Enumerated(EnumType.STRING)
	private TipoReflexoEnum tipoReflexo;

	private Instant inicioImpactoFinanco;

	private Instant fimImpactoFinanco;
	
	@Enumerated(EnumType.STRING)
	private SituacaoProcessoEnum situacao;

	private FuncionarioResponse funcionario;

	private List<AnexoResponse> anexos;

	public ProcessoResponse() {
	}

	public ProcessoResponse(Processo processo, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			this.id = processo.getId();
			this.numeroProcesso = processo.getNumeroProcesso();
			this.assunto = processo.getAssunto();
			this.dataInicio = processo.getDataInicio();
			this.dataFim = processo.getDataFim();
			this.requerente = processo.getRequerente();
			this.atoPortaria = processo.getAtoPortaria();
			this.dataAto = processo.getDataAto();
			this.doe = processo.getDoe();
			this.dataDoe = processo.getDataDoe();
			this.impactoFinanceiro = processo.getImpactoFinanceiro();
			this.tipoReflexo = processo.getTipoReflexo();
			this.inicioImpactoFinanco = processo.getInicioImpactoFinanco();
			this.fimImpactoFinanco = processo.getFimImpactoFinanco();
			this.funcionario = new FuncionarioResponse(processo.getFuncionario());
			this.situacao = processo.getSituacao();
			if (!processo.getAnexos().isEmpty())
				this.anexos = processo.getAnexos().stream().map(anexo -> new AnexoResponse(anexo)).collect(Collectors.toList());
		}
		if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {

			if (projecao.equals(Projecao.COMPLETA)) {
				setCriadoEm(processo.getCreatedAt());
				setAlteradoEm(processo.getUpdatedAt());
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	

	public TipoReflexoEnum getTipoReflexo() {
		return tipoReflexo;
	}

	public void setTipoReflexo(TipoReflexoEnum tipoReflexo) {
		this.tipoReflexo = tipoReflexo;
	}

	public Instant getInicioImpactoFinanco() {
		return inicioImpactoFinanco;
	}

	
	public SituacaoProcessoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoProcessoEnum situacao) {
		this.situacao = situacao;
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

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public List<AnexoResponse> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<AnexoResponse> anexos) {
		this.anexos = anexos;
	}
	
}
