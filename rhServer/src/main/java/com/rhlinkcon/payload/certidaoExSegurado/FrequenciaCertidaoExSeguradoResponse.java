package com.rhlinkcon.payload.certidaoExSegurado;

import com.rhlinkcon.model.FrequenciaCertidaoExSegurado;
import com.rhlinkcon.util.Projecao;

public class FrequenciaCertidaoExSeguradoResponse {
	
	private Long id;

	private CertidaoExSeguradoResponse certidaoExSegurado;
	
	private Integer ano;

	private Integer tempoBruto;

	private Integer faltas;

	private Integer licencas;

	private Integer licencasSemVenc;

	private Integer suspensoes;

	private Integer disponibilidade;

	private Integer outros;

	private Integer tempoLiquido;
	
	public FrequenciaCertidaoExSeguradoResponse(FrequenciaCertidaoExSegurado obj, Projecao projecao) {
		setId(obj.getId());
		setCertidaoExSegurado(new CertidaoExSeguradoResponse(obj.getCertidaoExSegurado(), Projecao.BASICA));
		setAno(obj.getAno());
		setTempoBruto(obj.getTempoBruto());
		setFaltas(obj.getFaltas());
		setLicencas(obj.getLicencas());
		setLicencasSemVenc(obj.getLicencasSemVenc());
		setSuspensoes(obj.getSuspensoes());
		setDisponibilidade(obj.getDisponibilidade());
		setOutros(obj.getOutros());
		setTempoLiquido(obj.getTempoLiquido());
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

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getTempoBruto() {
		return tempoBruto;
	}

	public void setTempoBruto(Integer tempoBruto) {
		this.tempoBruto = tempoBruto;
	}

	public Integer getFaltas() {
		return faltas;
	}

	public void setFaltas(Integer faltas) {
		this.faltas = faltas;
	}

	public Integer getLicencas() {
		return licencas;
	}

	public void setLicencas(Integer licencas) {
		this.licencas = licencas;
	}

	public Integer getLicencasSemVenc() {
		return licencasSemVenc;
	}

	public void setLicencasSemVenc(Integer licencasSemVenc) {
		this.licencasSemVenc = licencasSemVenc;
	}

	public Integer getSuspensoes() {
		return suspensoes;
	}

	public void setSuspensoes(Integer suspensoes) {
		this.suspensoes = suspensoes;
	}

	public Integer getDisponibilidade() {
		return disponibilidade;
	}

	public void setDisponibilidade(Integer disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	public Integer getOutros() {
		return outros;
	}

	public void setOutros(Integer outros) {
		this.outros = outros;
	}

	public Integer getTempoLiquido() {
		return tempoLiquido;
	}

	public void setTempoLiquido(Integer tempoLiquido) {
		this.tempoLiquido = tempoLiquido;
	}

}
