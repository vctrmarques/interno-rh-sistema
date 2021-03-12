package com.rhlinkcon.payload.certidaoExSegurado;

public class CertidaoExServidorOrgaoLotacaoRequest {

	private Long id;

	private Long certidaoExSeguradoId;
	
	private String descricaoOrgaoLotacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCertidaoExSeguradoId() {
		return certidaoExSeguradoId;
	}

	public void setCertidaoExSeguradoId(Long certidaoExSeguradoId) {
		this.certidaoExSeguradoId = certidaoExSeguradoId;
	}

	public String getDescricaoOrgaoLotacao() {
		return descricaoOrgaoLotacao;
	}

	public void setDescricaoOrgaoLotacao(String descricaoOrgaoLotacao) {
		this.descricaoOrgaoLotacao = descricaoOrgaoLotacao;
	}
	
}
