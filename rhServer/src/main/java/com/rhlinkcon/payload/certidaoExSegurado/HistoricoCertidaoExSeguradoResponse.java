package com.rhlinkcon.payload.certidaoExSegurado;

import com.rhlinkcon.model.HistoricoCertidaoExSegurado;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.util.Projecao;

public class HistoricoCertidaoExSeguradoResponse extends DadoCadastralResponse {
	
	private Long id;

	private CertidaoExSeguradoResponse certidaoExSegurado;

	private String status;

	private String observacao;
	
	private Long numeroCertidao;
	
	private Integer anoCertidao;
	
	private Long numeroRetificacao;
	
	public HistoricoCertidaoExSeguradoResponse(HistoricoCertidaoExSegurado obj, String criadoPor) {
		setId(obj.getId());
		setCertidaoExSegurado(new CertidaoExSeguradoResponse(obj.getCertidaoExSegurado(), Projecao.BASICA));
		setNumeroCertidao(obj.getNumeroCertidao());
		setAnoCertidao(obj.getAnoCertidao());
		setNumeroRetificacao(obj.getNumeroRetificacao());
		setStatus(obj.getStatus().getLabel());
		setObservacao(obj.getObservacao());
		
		setCriadoEm(obj.getCreatedAt());
		setCriadoPor(criadoPor);
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Long getNumeroCertidao() {
		return numeroCertidao;
	}

	public void setNumeroCertidao(Long numeroCertidao) {
		this.numeroCertidao = numeroCertidao;
	}

	public Integer getAnoCertidao() {
		return anoCertidao;
	}

	public void setAnoCertidao(Integer anoCertidao) {
		this.anoCertidao = anoCertidao;
	}

	public Long getNumeroRetificacao() {
		return numeroRetificacao;
	}

	public void setNumeroRetificacao(Long numeroRetificacao) {
		this.numeroRetificacao = numeroRetificacao;
	}
	
	
}
