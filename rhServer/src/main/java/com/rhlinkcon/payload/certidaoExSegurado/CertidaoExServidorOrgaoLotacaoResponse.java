package com.rhlinkcon.payload.certidaoExSegurado;

import com.rhlinkcon.model.CertidaoExServidorOrgaoLotacao;
import com.rhlinkcon.util.Projecao;

public class CertidaoExServidorOrgaoLotacaoResponse {

	private Long id;

	private CertidaoExSeguradoResponse certidaoExSegurado;
	
	private String descricaoOrgaoLotacao;
	
	public CertidaoExServidorOrgaoLotacaoResponse(CertidaoExServidorOrgaoLotacao obj) {
		setId(obj.getId());
		setCertidaoExSegurado(new CertidaoExSeguradoResponse(obj.getCertidaoExServidor(), Projecao.BASICA));
		setDescricaoOrgaoLotacao(obj.getDescricaoOrgaoLotacao());
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

	public String getDescricaoOrgaoLotacao() {
		return descricaoOrgaoLotacao;
	}

	public void setDescricaoOrgaoLotacao(String descricaoOrgaoLotacao) {
		this.descricaoOrgaoLotacao = descricaoOrgaoLotacao;
	}
	
}
