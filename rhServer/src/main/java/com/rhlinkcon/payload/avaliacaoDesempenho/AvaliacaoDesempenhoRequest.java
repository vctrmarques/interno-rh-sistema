package com.rhlinkcon.payload.avaliacaoDesempenho;

import com.rhlinkcon.model.AvaliacaoDesempenhoModeloEnum;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AvaliacaoDesempenhoRequest {

	private Long id;

	@NotNull
	private Long empresaFilialId;

	@NotNull
	private String codAvaliacao;

	@NotNull
	private String nome;

	@NotNull
	private AvaliacaoDesempenhoModeloEnum modelo;

	private List<Long> lotacoesIds;

	private List<Long> cargosIds;

	private List<Long> funcoesIds;

	private AvaliacaoDesempenhoItemRequest item;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmpresaFilialId() {
		return empresaFilialId;
	}

	public void setEmpresaFilialId(Long empresaFilialId) {
		this.empresaFilialId = empresaFilialId;
	}

	public String getCodAvaliacao() {
		return codAvaliacao;
	}

	public void setCodAvaliacao(String codAvaliacao) {
		this.codAvaliacao = codAvaliacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public AvaliacaoDesempenhoModeloEnum getModelo() {
		return modelo;
	}

	public void setModelo(AvaliacaoDesempenhoModeloEnum modelo) {
		this.modelo = modelo;
	}

	public List<Long> getLotacoesIds() {
		return lotacoesIds;
	}

	public void setLotacoesIds(List<Long> lotacoesIds) {
		this.lotacoesIds = lotacoesIds;
	}

	public List<Long> getCargosIds() {
		return cargosIds;
	}

	public void setCargosIds(List<Long> cargosIds) {
		this.cargosIds = cargosIds;
	}

	public List<Long> getFuncoesIds() {
		return funcoesIds;
	}

	public void setFuncoesIds(List<Long> funcoesIds) {
		this.funcoesIds = funcoesIds;
	}

	public AvaliacaoDesempenhoItemRequest getItem() {
		return item;
	}

	public void setItem(AvaliacaoDesempenhoItemRequest item) {
		this.item = item;
	}
}