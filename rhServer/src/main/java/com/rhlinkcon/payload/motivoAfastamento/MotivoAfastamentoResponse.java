package com.rhlinkcon.payload.motivoAfastamento;

import java.time.Instant;

import com.rhlinkcon.model.MotivoAfastamento;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class MotivoAfastamentoResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;
	
	private boolean disponivelParaPericia;  

	public MotivoAfastamentoResponse(MotivoAfastamento motivoAfastamento) {
		setAfastamento(motivoAfastamento);
	}

	public MotivoAfastamentoResponse(MotivoAfastamento motivoAfastamento, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setAfastamento(motivoAfastamento);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}
	
	private void setAfastamento(MotivoAfastamento motivoAfastamento) {
		this.setId(motivoAfastamento.getId());
		this.setCodigo(motivoAfastamento.getCodigo());
		this.setDescricao(motivoAfastamento.getDescricao());
		this.setDisponivelParaPericia(motivoAfastamento.isDisponivelParaPericia());
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

	public boolean isDisponivelParaPericia() {
		return disponivelParaPericia;
	}

	public void setDisponivelParaPericia(boolean disponivelParaPericia) {
		this.disponivelParaPericia = disponivelParaPericia;
	}
	
}
