package com.rhlinkcon.payload.codigoRecolhimento;

import java.time.Instant;

import com.rhlinkcon.model.CodigoRecolhimento;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class CodigoRecolhimentoResponse extends DadoCadastralResponse {

	private Long id;

	private Integer codigo;

	private String descricao;


	public CodigoRecolhimentoResponse(CodigoRecolhimento codigoRecolhimento) {
		this.setId(codigoRecolhimento.getId());
		this.setCodigo(codigoRecolhimento.getCodigo());
		this.setDescricao(codigoRecolhimento.getDescricao());
	}

	public CodigoRecolhimentoResponse(CodigoRecolhimento codigoRecolhimento, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(codigoRecolhimento.getId());
		this.setCodigo(codigoRecolhimento.getCodigo());
		this.setDescricao(codigoRecolhimento.getDescricao());
		
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

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
