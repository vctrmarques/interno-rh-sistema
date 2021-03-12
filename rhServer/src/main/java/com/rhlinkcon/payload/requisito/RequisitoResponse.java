package com.rhlinkcon.payload.requisito;

import java.time.Instant;

import com.rhlinkcon.model.Requisito;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class RequisitoResponse extends DadoCadastralResponse {
	private Long id;

	private String descricao;

	private String dadoComparativo;

	private String comparacao;

	private String valor;

	private String inicioIntervalo;

	private String fimIntervalo;
	
	public RequisitoResponse(Requisito requisito) {
		setRequisito(requisito);
	}

	public RequisitoResponse(Requisito requisito, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {

		setRequisito(requisito);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setRequisito(Requisito requisito) {
		this.setId(requisito.getId());
		this.setDescricao(requisito.getDescricao());
		this.setDadoComparativo(requisito.getDadoComparativo().getLabel());
		this.setComparacao(requisito.getComparacao().getLabel());
		this.setValor(requisito.getValor());
		this.setInicioIntervalo(requisito.getInicioIntervalo());
		this.setFimIntervalo(requisito.getFimIntervalo());
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDadoComparativo() {
		return dadoComparativo;
	}

	public String getComparacao() {
		return comparacao;
	}

	public String getValor() {
		return valor;
	}

	public String getInicioIntervalo() {
		return inicioIntervalo;
	}

	public String getFimIntervalo() {
		return fimIntervalo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setDadoComparativo(String dadoComparativo) {
		this.dadoComparativo = dadoComparativo;
	}

	public void setComparacao(String comparacao) {
		this.comparacao = comparacao;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setInicioIntervalo(String inicioIntervalo) {
		this.inicioIntervalo = inicioIntervalo;
	}

	public void setFimIntervalo(String fimIntervalo) {
		this.fimIntervalo = fimIntervalo;
	}

}
