package com.rhlinkcon.payload.sefip;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Sefip;
import com.rhlinkcon.payload.DadoCadastralResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SefipResponse extends DadoCadastralResponse {

	private Long id;
	
	private String codigo;

	private String descricao;

	private String tipo;
	
	public SefipResponse(Sefip sefip) {
		setSefip(sefip);
	}
	
	public SefipResponse(Sefip sefip, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setSefip(sefip);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);		
	}
	
	private void setSefip(Sefip sefip) {
		this.setId(sefip.getId());
		this.setCodigo(sefip.getCodigo());
		this.setDescricao(sefip.getDescricao());
		this.setTipo(sefip.getTipo().getLabel());

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
