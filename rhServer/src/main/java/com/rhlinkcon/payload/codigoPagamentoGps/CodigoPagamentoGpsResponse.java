package com.rhlinkcon.payload.codigoPagamentoGps;

import java.time.Instant;

import com.rhlinkcon.model.CodigoPagamentoGps;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class CodigoPagamentoGpsResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;


	public CodigoPagamentoGpsResponse(CodigoPagamentoGps codigoPagamentoGps) {
		this.setId(codigoPagamentoGps.getId());
		this.setCodigo(codigoPagamentoGps.getCodigo());
		this.setDescricao(codigoPagamentoGps.getDescricao());
	}

	public CodigoPagamentoGpsResponse(CodigoPagamentoGps codigoPagamentoGps, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(codigoPagamentoGps.getId());
		this.setCodigo(codigoPagamentoGps.getCodigo());
		this.setDescricao(codigoPagamentoGps.getDescricao());
		
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
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

}
