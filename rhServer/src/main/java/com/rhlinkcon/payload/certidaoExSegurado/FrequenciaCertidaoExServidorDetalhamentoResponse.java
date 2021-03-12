package com.rhlinkcon.payload.certidaoExSegurado;

import java.time.Instant;

import com.rhlinkcon.model.FrequenciaCertidaoExServidorDetalhamento;
import com.rhlinkcon.util.Projecao;

public class FrequenciaCertidaoExServidorDetalhamentoResponse {

	private Long id;
	
	private CertidaoExSeguradoResponse certidaoExSegurado;
	
	private Instant periodoInicio;
	
	private Instant periodoFinal;
	
	private Integer tempo;
	
	private String descricao;
	
	private String tipo;
	
	public FrequenciaCertidaoExServidorDetalhamentoResponse(FrequenciaCertidaoExServidorDetalhamento obj) {
		setId(obj.getId());
		setCertidaoExSegurado(new CertidaoExSeguradoResponse(obj.getCertidaoExSegurado(), Projecao.BASICA));
		setPeriodoInicio(obj.getPeriodoInicio());
		setPeriodoFinal(obj.getPeriodoFinal());
		setTempo(obj.getTempo());
		setDescricao(obj.getDescricao());
		setTipo(obj.getTipo().name());
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

	public Instant getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(Instant periodoInicio) {
		this.periodoInicio = periodoInicio;
	}

	public Instant getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Instant periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
