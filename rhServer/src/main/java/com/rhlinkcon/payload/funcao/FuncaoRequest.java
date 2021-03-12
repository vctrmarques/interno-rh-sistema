package com.rhlinkcon.payload.funcao;

import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotNull;

public class FuncaoRequest {

	private Long id;

	@NotNull
	private String nome;

	private String descricao;
	
	private String codigo;

	@NotNull
	private Long cboId;

	private Instant dataCriacao;

	private Instant dataExtincao;

	private Boolean funcaoExtinta;

	private Long naturezaFuncaoId;

	private Long processoFuncaoId;

	private Long categoriaProfissionalId;

	private String sindicato;

	private List<Long> vinculosIds;

	private List<Long> cursosIds;

	private List<Long> habilidadesIds;

	private List<Long> atividadesIds;
	
	private List<Long> requisitosIds;
	
	private List<Long> verbasIds;

	private Boolean dedicacaoExclusiva;

	private Integer numeroLei;

	private Instant dataLei;

	private String funcaoAcumulavel;

	private String contagemTempoEspecial;

	private String motivoLei;

	private Long grupoSalarialId;

	private Long centroCustoId;

	public Long getCentroCustoId() {
		return centroCustoId;
	}

	public void setCentroCustoId(Long centroCustoId) {
		this.centroCustoId = centroCustoId;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Long getCboId() {
		return cboId;
	}

	public Instant getDataCriacao() {
		return dataCriacao;
	}

	public Instant getDataExtincao() {
		return dataExtincao;
	}

	public Boolean getFuncaoExtinta() {
		return funcaoExtinta;
	}

	public Long getNaturezaFuncaoId() {
		return naturezaFuncaoId;
	}

	public Long getProcessoFuncaoId() {
		return processoFuncaoId;
	}

	public Long getCategoriaProfissionalId() {
		return categoriaProfissionalId;
	}

	public String getSindicato() {
		return sindicato;
	}

	public List<Long> getVinculosIds() {
		return vinculosIds;
	}

	public Boolean getDedicacaoExclusiva() {
		return dedicacaoExclusiva;
	}

	public String getFuncaoAcumulavel() {
		return funcaoAcumulavel;
	}

	public String getContagemTempoEspecial() {
		return contagemTempoEspecial;
	}

	public Integer getNumeroLei() {
		return numeroLei;
	}

	public Instant getDataLei() {
		return dataLei;
	}

	public String getMotivoLei() {
		return motivoLei;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setCboId(Long cboId) {
		this.cboId = cboId;
	}

	public void setDataCriacao(Instant dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setDataExtincao(Instant dataExtincao) {
		this.dataExtincao = dataExtincao;
	}

	public void setFuncaoExtinta(Boolean funcaoExtinta) {
		this.funcaoExtinta = funcaoExtinta;
	}

	public void setNaturezaFuncaoId(Long naturezaFuncaoId) {
		this.naturezaFuncaoId = naturezaFuncaoId;
	}

	public void setProcessoFuncaoId(Long processoFuncaoId) {
		this.processoFuncaoId = processoFuncaoId;
	}

	public void setCategoriaProfissionalId(Long categoriaProfissionalId) {
		this.categoriaProfissionalId = categoriaProfissionalId;
	}

	public void setSindicato(String sindicato) {
		this.sindicato = sindicato;
	}

	public void setVinculosIds(List<Long> vinculosIds) {
		this.vinculosIds = vinculosIds;
	}

	public void setDedicacaoExclusiva(Boolean dedicacaoExclusiva) {
		this.dedicacaoExclusiva = dedicacaoExclusiva;
	}

	public void setFuncaoAcumulavel(String funcaoAcumulavel) {
		this.funcaoAcumulavel = funcaoAcumulavel;
	}

	public void setContagemTempoEspecial(String contagemTempoEspecial) {
		this.contagemTempoEspecial = contagemTempoEspecial;
	}

	public void setNumeroLei(Integer numeroLei) {
		this.numeroLei = numeroLei;
	}

	public void setDataLei(Instant dataLei) {
		this.dataLei = dataLei;
	}

	public void setMotivoLei(String motivoLei) {
		this.motivoLei = motivoLei;
	}


	public Long getGrupoSalarialId() {
		return grupoSalarialId;
	}

	

	public List<Long> getRequisitosIds() {
		return requisitosIds;
	}

	public void setRequisitosIds(List<Long> requisitosIds) {
		this.requisitosIds = requisitosIds;
	}

	public void setGrupoSalarialId(Long grupoSalarialId) {
		this.grupoSalarialId = grupoSalarialId;
	}

	public List<Long> getCursosIds() {
		return cursosIds;
	}

	public void setCursosIds(List<Long> cursosIds) {
		this.cursosIds = cursosIds;
	}

	public List<Long> getHabilidadesIds() {
		return habilidadesIds;
	}

	public void setHabilidadesIds(List<Long> habilidadesIds) {
		this.habilidadesIds = habilidadesIds;
	}

	public List<Long> getAtividadesIds() {
		return atividadesIds;
	}

	public void setAtividadesIds(List<Long> atividadesIds) {
		this.atividadesIds = atividadesIds;
	}

	public List<Long> getVerbasIds() {
		return verbasIds;
	}

	public void setVerbasIds(List<Long> verbasIds) {
		this.verbasIds = verbasIds;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
