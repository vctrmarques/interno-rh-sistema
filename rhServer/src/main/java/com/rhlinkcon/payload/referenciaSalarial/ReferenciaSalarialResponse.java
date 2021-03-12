package com.rhlinkcon.payload.referenciaSalarial;

import java.util.List;

import com.rhlinkcon.model.ReferenciaSalarial;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class ReferenciaSalarialResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;

	private Double valor;

	private boolean nivelReferencia;

	List<NivelSalarialHistoricoResponse> historico;
	
	private String mesAnoCompetencia;

	public ReferenciaSalarialResponse() {
	}

	public ReferenciaSalarialResponse(ReferenciaSalarial nivelSalarial) {
		populate(nivelSalarial);
	}

	private void populate(ReferenciaSalarial nivelSalarial) {
		setId(nivelSalarial.getId());
		setCodigo(nivelSalarial.getCodigo());
		setDescricao(nivelSalarial.getDescricao());
		setValor(nivelSalarial.getValor());
		setNivelReferencia(nivelSalarial.isNivelReferencia());
		setMesAnoCompetencia(nivelSalarial.getMesAnoCompetencia());
		setCriadoEm(nivelSalarial.getCreatedAt());
		setAlteradoEm(nivelSalarial.getUpdatedAt());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public boolean isNivelReferencia() {
		return nivelReferencia;
	}

	public void setNivelReferencia(boolean nivelReferencia) {
		this.nivelReferencia = nivelReferencia;
	}

	public List<NivelSalarialHistoricoResponse> getHistorico() {
		return historico;
	}

	public void setHistorico(List<NivelSalarialHistoricoResponse> historico) {
		this.historico = historico;
	}

	public String getMesAnoCompetencia() {
		return mesAnoCompetencia;
	}

	public void setMesAnoCompetencia(String mesAnoCompetencia) {
		this.mesAnoCompetencia = mesAnoCompetencia;
	}

}
