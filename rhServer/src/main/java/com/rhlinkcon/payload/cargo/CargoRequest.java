package com.rhlinkcon.payload.cargo;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CargoRequest {

	private Long id;

	@NotNull
	private String nome;

	private String descricao;
	
	@NotNull
	private String codigo;

	private Boolean dedicacaoExclusiva;

	private Long cboId;

	private Long naturezaFuncaoId;

	private Long processoFuncaoId;

	private Long categoriaProfissionalId;

	private Long sindicatoId;
	
	private Long grupoSalarialId;

	@NotNull
	private String contagemTempoEspecial;

	@NotNull
	private String motivoLei;
	
	@NotNull
	private String leiRespaldo;

	private List<Long> vinculosIds;
	
	private List<Long> atividadesIds;
	
	private List<Long> habilidadesIds;
	
	private List <Long> cursosIds;
	
	private List <Long> verbasIds;
	
	public String getLeiRespaldo() {
		return leiRespaldo;
	}

	public void setLeiRespaldo(String leiRespaldo) {
		this.leiRespaldo = leiRespaldo;
	}

	public Long getNaturezaFuncaoId() {
		return naturezaFuncaoId;
	}

	public void setNaturezaFuncaoId(Long naturezaFuncaoId) {
		this.naturezaFuncaoId = naturezaFuncaoId;
	}

	public Long getProcessoFuncaoId() {
		return processoFuncaoId;
	}

	public void setProcessoFuncaoId(Long processoFuncaoId) {
		this.processoFuncaoId = processoFuncaoId;
	}

	public Long getCategoriaProfissionalId() {
		return categoriaProfissionalId;
	}

	public void setCategoriaProfissionalId(Long categoriaProfissionalId) {
		this.categoriaProfissionalId = categoriaProfissionalId;
	}

	public Long getSindicatoId() {
		return sindicatoId;
	}

	public void setSindicatoId(Long sindicatoId) {
		this.sindicatoId = sindicatoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getCboId() {
		return cboId;
	}

	public void setCboId(Long cboId) {
		this.cboId = cboId;
	}

	public String getMotivoLei() {
		return motivoLei;
	}

	public void setMotivoLei(String motivoLei) {
		this.motivoLei = motivoLei;
	}

	public String getContagemTempoEspecial() {
		return contagemTempoEspecial;
	}

	public void setContagemTempoEspecial(String contagemTempoEspecial) {
		this.contagemTempoEspecial = contagemTempoEspecial;
	}

	public Boolean isDedicacaoExclusiva() {
		return dedicacaoExclusiva;
	}

	public void setDedicacaoExclusiva(Boolean dedicacaoExclusiva) {
		this.dedicacaoExclusiva = dedicacaoExclusiva;
	}

	public List<Long> getVinculosIds() {
		return vinculosIds;
	}

	public void setVinculosIds(List<Long> vinculosIds) {
		this.vinculosIds = vinculosIds;
	}

	public List<Long> getAtividadesIds() {
		return atividadesIds;
	}

	public void setAtividadesIds(List<Long> atividadesIds) {
		this.atividadesIds = atividadesIds;
	}

	public List<Long> getHabilidadesIds() {
		return habilidadesIds;
	}

	public void setHabilidadesIds(List<Long> habilidadesIds) {
		this.habilidadesIds = habilidadesIds;
	}

	public List<Long> getCursosIds() {
		return cursosIds;
	}

	public void setCursosIds(List<Long> cursosIds) {
		this.cursosIds = cursosIds;
	}

	public Long getGrupoSalarialId() {
		return grupoSalarialId;
	}

	public void setGrupoSalarialId(Long grupoSalarialId) {
		this.grupoSalarialId = grupoSalarialId;
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

	public Boolean getDedicacaoExclusiva() {
		return dedicacaoExclusiva;
	}

}
