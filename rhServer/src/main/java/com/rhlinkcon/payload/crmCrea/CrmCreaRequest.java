package com.rhlinkcon.payload.crmCrea;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.CrmCreaEnum;

public class CrmCreaRequest {

	private Long id;

	@NotBlank
	@NotNull
	private String nomeConveniado;

	@NotBlank
	@NotNull
	private String numeroCrmCrea;
	
	@NotBlank
	@NotNull
	private String tipo;

	private boolean coordenadorPcmso;

	private boolean responsavelLtcat;

	private List<Long> convenioIds;


	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeConveniado() {
		return nomeConveniado;
	}

	public void setNomeConveniado(String nomeConveniado) {
		this.nomeConveniado = nomeConveniado;
	}

	public String getNumeroCrmCrea() {
		return numeroCrmCrea;
	}

	public void setNumeroCrmCrea(String numeroCrmCrea) {
		this.numeroCrmCrea = numeroCrmCrea;
	}

	public boolean isCoordenadorPcmso() {
		return coordenadorPcmso;
	}

	public void setCoordenadorPcmso(boolean coordenadorPcmso) {
		this.coordenadorPcmso = coordenadorPcmso;
	}

	public boolean isResponsavelLtcat() {
		return responsavelLtcat;
	}

	public void setResponsavelLtcat(boolean responsavelLtcat) {
		this.responsavelLtcat = responsavelLtcat;
	}

	public List<Long> getConvenioIds() {
		return convenioIds;
	}

	public void setConvenioIds(List<Long> convenioIds) {
		this.convenioIds = convenioIds;
	}
	
	

}
