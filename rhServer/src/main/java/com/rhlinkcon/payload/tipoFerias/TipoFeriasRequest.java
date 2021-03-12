package com.rhlinkcon.payload.tipoFerias;

import javax.validation.constraints.NotNull;

public class TipoFeriasRequest {

	private Long id;
	
	@NotNull
	private String descricao;
	
	@NotNull
	private Integer abono;
	
	@NotNull
	private Integer dias;
	
	@NotNull
	private Integer saldo;

	@NotNull
	private String decimoTerceiro;
	
	@NotNull
	private String coletivo;
	
	@NotNull
	private String licencaPremio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setdescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getAbono() {
		return abono;
	}

	public void setAbono(Integer abono) {
		this.abono = abono;
	}
	
	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}
	
	public Integer getSaldo() {
		return saldo;
	}

	public void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}

	public String getDecimoTerceiro() {
		return decimoTerceiro;
	}

	public void setDecimoTerceiro(String decimoTerceiro) {
		this.decimoTerceiro = decimoTerceiro;
	}
	
	public String getColetivo() {
		return coletivo;
	}

	public void setColetivo(String coletivo) {
		this.coletivo = coletivo;
	}
	
	public String getLicencaPremio() {
		return licencaPremio;
	}

	public void setLicencaPremio(String licencaPremio) {
		this.licencaPremio = licencaPremio;
	}

}