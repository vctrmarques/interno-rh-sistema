package com.rhlinkcon.payload.centroCusto;

import javax.validation.constraints.NotNull;

public class CentroCustoRequest {

	private Long id;
	
	@NotNull
	private String codigo;
	
	@NotNull
	private String descricao;
	
	private Integer efetivo;
	
	private Integer nivel;
	
	private String tipoDebito;
	
	private String tipoCredito;
	
	private Integer contaCredito;
	
	private Integer contaDebito;
	
	private String tipoCentroCusto;
	
	private String cnpj;
	
	

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getEfetivo() {
		return efetivo;
	}

	public void setEfetivo(Integer efetivo) {
		this.efetivo = efetivo;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getTipoDebito() {
		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public Integer getContaCredito() {
		return contaCredito;
	}

	public void setContaCredito(Integer contaCredito) {
		this.contaCredito = contaCredito;
	}

	public Integer getContaDebito() {
		return contaDebito;
	}

	public void setContaDebito(Integer contaDebito) {
		this.contaDebito = contaDebito;
	}

	public String getTipoCentroCusto() {
		return tipoCentroCusto;
	}

	public void setTipoCentroCusto(String tipoCentroCusto) {
		this.tipoCentroCusto = tipoCentroCusto;
	}
	
}