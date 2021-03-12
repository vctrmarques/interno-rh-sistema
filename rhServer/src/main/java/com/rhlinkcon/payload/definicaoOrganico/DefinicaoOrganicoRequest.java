package com.rhlinkcon.payload.definicaoOrganico;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.payload.lotacao.LotacaoRequest;


public class DefinicaoOrganicoRequest {

	private Long id;

	@NotNull
	private Long empresaFilialId;	
	
	private List<LotacaoRequest> lotacoes;

	@NotNull
	
	private String primeiroSubstitutoNome;
	
	private String primeiroSubstitutoEmail;	

	private String segundoSubstitutoNome;	
	
	private String segundoSubstitutoEmail;
	
	private Long previsaoFuncionarios;	
	
	private Long funcionariosAtuais;	
	
	private Long totalFuncionarios;	
	
	private BigDecimal previsaoCustos;	
	
	private BigDecimal custosAtuais;	
	
	private BigDecimal custoTotal;
	
	private Boolean confCriticaAvisar;	
	
	private Boolean confCriticaCriticar;	
	
	private Boolean confCriticaNenhum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmpresaFilialId() {
		return empresaFilialId;
	}

	public void setEmpresaFilialId(Long empresaFilialId) {
		this.empresaFilialId = empresaFilialId;
	}
	

	public List<LotacaoRequest> getLotacoes() {
		return lotacoes;
	}

	public void setLotacoes(List<LotacaoRequest> lotacoes) {
		this.lotacoes = lotacoes;
	}

	public String getPrimeiroSubstitutoNome() {
		return primeiroSubstitutoNome;
	}

	public void setPrimeiroSubstitutoNome(String primeiroSubstitutoNome) {
		this.primeiroSubstitutoNome = primeiroSubstitutoNome;
	}

	public String getPrimeiroSubstitutoEmail() {
		return primeiroSubstitutoEmail;
	}

	public void setPrimeiroSubstitutoEmail(String primeiroSubstitutoEmail) {
		this.primeiroSubstitutoEmail = primeiroSubstitutoEmail;
	}

	public String getSegundoSubstitutoNome() {
		return segundoSubstitutoNome;
	}

	public void setSegundoSubstitutoNome(String segundoSubstitutoNome) {
		this.segundoSubstitutoNome = segundoSubstitutoNome;
	}

	public String getSegundoSubstitutoEmail() {
		return segundoSubstitutoEmail;
	}

	public void setSegundoSubstitutoEmail(String segundoSubstitutoEmail) {
		this.segundoSubstitutoEmail = segundoSubstitutoEmail;
	}

	public Long getPrevisaoFuncionarios() {
		return previsaoFuncionarios;
	}

	public void setPrevisaoFuncionarios(Long previsaoFuncionarios) {
		this.previsaoFuncionarios = previsaoFuncionarios;
	}

	public Long getFuncionariosAtuais() {
		return funcionariosAtuais;
	}

	public void setFuncionariosAtuais(Long funcionariosAtuais) {
		this.funcionariosAtuais = funcionariosAtuais;
	}

	public Long getTotalFuncionarios() {
		return totalFuncionarios;
	}

	public void setTotalFuncionarios(Long totalFuncionarios) {
		this.totalFuncionarios = totalFuncionarios;
	}

	public BigDecimal getPrevisaoCustos() {
		return previsaoCustos;
	}

	public void setPrevisaoCustos(BigDecimal previsaoCustos) {
		this.previsaoCustos = previsaoCustos;
	}

	public BigDecimal getCustosAtuais() {
		return custosAtuais;
	}

	public void setCustosAtuais(BigDecimal custosAtuais) {
		this.custosAtuais = custosAtuais;
	}

	public BigDecimal getCustoTotal() {
		return custoTotal;
	}

	public void setCustoTotal(BigDecimal custoTotal) {
		this.custoTotal = custoTotal;
	}

	public Boolean getConfCriticaAvisar() {
		return confCriticaAvisar;
	}

	public void setConfCriticaAvisar(Boolean confCriticaAvisar) {
		this.confCriticaAvisar = confCriticaAvisar;
	}

	public Boolean getConfCriticaCriticar() {
		return confCriticaCriticar;
	}

	public void setConfCriticaCriticar(Boolean confCriticaCriticar) {
		this.confCriticaCriticar = confCriticaCriticar;
	}

	public Boolean getConfCriticaNenhum() {
		return confCriticaNenhum;
	}

	public void setConfCriticaNenhum(Boolean confCriticaNenhum) {
		this.confCriticaNenhum = confCriticaNenhum;
	}



}
