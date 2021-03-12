package com.rhlinkcon.payload.contaContabil;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ContaContabilRequest {

	private Long id;
	
	private Long empresaId;
	
	private Long filialId;
	
	private Long verbaId;
		
	private Long centroCustoId;
	
	@NotNull
	private String tipoConta;
	
	private List<Long> lotacoesIds;
	
	@NotNull
	private Integer conta;
	
	@NotNull
	private Double rateio;
	
	@NotNull
	private Double rateioTotal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Long getFilialId() {
		return filialId;
	}

	public void setFilialId(Long filialId) {
		this.filialId = filialId;
	}

	public Long getVerbaId() {
		return verbaId;
	}

	public void setVerbaId(Long verbaId) {
		this.verbaId = verbaId;
	}

	public Long getCentroCustoId() {
		return centroCustoId;
	}

	public void setCentroCustoId(Long centroCustoId) {
		this.centroCustoId = centroCustoId;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public List<Long> getLotacoesIds() {
		return lotacoesIds;
	}

	public void setLotacoesIds(List<Long> lotacoesIds) {
		this.lotacoesIds = lotacoesIds;
	}

	public Integer getConta() {
		return conta;
	}

	public void setConta(Integer conta) {
		this.conta = conta;
	}

	public Double getRateio() {
		return rateio;
	}

	public void setRateio(Double rateio) {
		this.rateio = rateio;
	}

	public Double getRateioTotal() {
		return rateioTotal;
	}

	public void setRateioTotal(Double rateioTotal) {
		this.rateioTotal = rateioTotal;
	}

}
