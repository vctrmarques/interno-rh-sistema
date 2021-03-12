package com.rhlinkcon.payload.contaContabil;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.ContaContabil;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.centroCusto.CentroCustoResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.payload.verba.VerbaResponse;

public class ContaContabilResponse extends DadoCadastralResponse {

	private Long id;
	
	private Long empresaId;
	
	private Long filialId;
		
	private Long verbaId;
	
	private CentroCustoResponse centroCusto;
	
	private VerbaResponse verba;
	
	@NotNull
	private String tipoConta;
	
	private List<LotacaoResponse> lotacoes;
	
	@NotNull
	private Integer conta;
	
	@NotNull
	private Double rateio;
	
	@NotNull
	private Double rateioTotal;

	public ContaContabilResponse(ContaContabil contaContabil) {
		setContaContabil(contaContabil);
	}

	public ContaContabilResponse(ContaContabil contaContabil, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setContaContabil(contaContabil);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setContaContabil(ContaContabil contaContabil) {
		this.setId(contaContabil.getId());
		this.setTipoConta(contaContabil.getTipoConta().toString());
		
		if(Objects.nonNull(contaContabil.getCentroCusto()))
			this.setCentroCusto(new CentroCustoResponse(contaContabil.getCentroCusto()));
		
		this.setConta(contaContabil.getConta());
		this.setRateio(contaContabil.getRateio());
		this.setRateioTotal(contaContabil.getRateioTotal());
		
		if(Objects.nonNull(contaContabil.getEmpresa()))
			this.setEmpresaId(contaContabil.getEmpresa().getId());
		
		if(Objects.nonNull(contaContabil.getFilial()))
			this.setFilialId(contaContabil.getFilial().getId());
		
		if(Objects.nonNull(contaContabil.getVerba())) {
			this.setVerbaId(contaContabil.getVerba().getId());
			this.setVerba(new VerbaResponse(contaContabil.getVerba()));
		}

	}

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

	public CentroCustoResponse getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCustoResponse centroCusto) {
		this.centroCusto = centroCusto;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public List<LotacaoResponse> getLotacoes() {
		return lotacoes;
	}

	public void setLotacoes(List<LotacaoResponse> lotacoes) {
		this.lotacoes = lotacoes;
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

	public VerbaResponse getVerba() {
		return verba;
	}

	public void setVerba(VerbaResponse verba) {
		this.verba = verba;
	}

}
