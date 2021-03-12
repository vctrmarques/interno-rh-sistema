package com.rhlinkcon.payload.certidaoExSegurado;

public class CertidaoExServidorCargoRequest {

	private Long id;

	private Long certidaoExSeguradoId;
	
	private String descricaoCargo;

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

	public String getDescricaoCargo() {
		return descricaoCargo;
	}

	public void setDescricaoCargo(String descricaoCargo) {
		this.descricaoCargo = descricaoCargo;
	}
	
}
