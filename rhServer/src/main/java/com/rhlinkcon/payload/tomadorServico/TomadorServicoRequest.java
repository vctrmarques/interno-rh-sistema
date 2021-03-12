package com.rhlinkcon.payload.tomadorServico;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.payload.generico.EnderecoDto;

public class TomadorServicoRequest {

	private Long id;

	@NotNull
	private EnderecoDto endereco;

	private Long codigoRecolhimentoId;

	@NotNull
	private String cnpj;

	private Long codigoPagamentoGpsId;

	@NotNull
	private String tipoInscricao;

	@NotNull
	private String razaoSocial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EnderecoDto getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDto endereco) {
		this.endereco = endereco;
	}

	public Long getCodigoRecolhimentoId() {
		return codigoRecolhimentoId;
	}

	public void setCodigoRecolhimentoId(Long codigoRecolhimentoId) {
		this.codigoRecolhimentoId = codigoRecolhimentoId;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Long getCodigoPagamentoGpsId() {
		return codigoPagamentoGpsId;
	}

	public void setCodigoPagamentoGpsId(Long codigoPagamentoGpsId) {
		this.codigoPagamentoGpsId = codigoPagamentoGpsId;
	}

	public String getTipoInscricao() {
		return tipoInscricao;
	}

	public void setTipoInscricao(String tipoInscricao) {
		this.tipoInscricao = tipoInscricao;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

}