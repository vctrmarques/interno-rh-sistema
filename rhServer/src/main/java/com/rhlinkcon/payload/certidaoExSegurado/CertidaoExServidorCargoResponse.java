package com.rhlinkcon.payload.certidaoExSegurado;

import com.rhlinkcon.model.CertidaoExServidorCargo;
import com.rhlinkcon.util.Projecao;

public class CertidaoExServidorCargoResponse {

	private Long id;

	private CertidaoExSeguradoResponse certidaoExSegurado;
	
	private String descricaoCargo;
	
	public CertidaoExServidorCargoResponse(CertidaoExServidorCargo obj) {
		setId(obj.getId());
		setCertidaoExSegurado(new CertidaoExSeguradoResponse(obj.getCertidaoExServidor(), Projecao.BASICA));
		setDescricaoCargo(obj.getDescricaoCargo());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CertidaoExSeguradoResponse getCertidaoExSegurado() {
		return certidaoExSegurado;
	}

	public void setCertidaoExSegurado(CertidaoExSeguradoResponse certidaoExSegurado) {
		this.certidaoExSegurado = certidaoExSegurado;
	}

	public String getDescricaoCargo() {
		return descricaoCargo;
	}

	public void setDescricaoCargo(String descricaoCargo) {
		this.descricaoCargo = descricaoCargo;
	}
}
