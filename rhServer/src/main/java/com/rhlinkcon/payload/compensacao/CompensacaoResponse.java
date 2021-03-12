package com.rhlinkcon.payload.compensacao;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Compensacao;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.tomadorServico.TomadorServicoResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompensacaoResponse extends DadoCadastralResponse {

	private Long id;

	private TomadorServicoResponse tomadorServico;
	
	private Instant competencia;
	
	private Double valorCompensacao;
	
	private Instant dataCompensacaoInicial;
	
	private Instant dataCompensacaoFinal;
	
	private Integer campoSeisGpsAnterior;
	
	private Integer campoNoveGpsAnterior;
	
	public CompensacaoResponse(Compensacao compensacao) {
		setCompensacao(compensacao);
	}

	private void setCompensacao(Compensacao compensacao) {
		this.setId(compensacao.getId());
		this.setCompetencia(compensacao.getCompetencia());
		this.setValorCompensacao(compensacao.getValorCompensacao());
		this.setDataCompensacaoInicial(compensacao.getDataCompensacaoInicial());
		this.setDataCompensacaoFinal(compensacao.getDataCompensacaoFinal());
		this.setCampoNoveGpsAnterior(compensacao.getCampoNoveGpsAnterior());
		this.setCampoSeisGpsAnterior(compensacao.getCampoSeisGpsAnterior());

		if(compensacao.getTomadorServico() != null)
			this.setTomadorServico(new TomadorServicoResponse(compensacao.getTomadorServico()));
		
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Instant competencia) {
		this.competencia = competencia;
	}

	public Double getValorCompensacao() {
		return valorCompensacao;
	}

	public void setValorCompensacao(Double valorCompensacao) {
		this.valorCompensacao = valorCompensacao;
	}

	public Instant getDataCompensacaoInicial() {
		return dataCompensacaoInicial;
	}

	public void setDataCompensacaoInicial(Instant dataCompensacaoInicial) {
		this.dataCompensacaoInicial = dataCompensacaoInicial;
	}

	public Instant getDataCompensacaoFinal() {
		return dataCompensacaoFinal;
	}

	public void setDataCompensacaoFinal(Instant dataCompensacaoFinal) {
		this.dataCompensacaoFinal = dataCompensacaoFinal;
	}

	public Integer getCampoSeisGpsAnterior() {
		return campoSeisGpsAnterior;
	}

	public void setCampoSeisGpsAnterior(Integer campoSeisGpsAnterior) {
		this.campoSeisGpsAnterior = campoSeisGpsAnterior;
	}

	public Integer getCampoNoveGpsAnterior() {
		return campoNoveGpsAnterior;
	}

	public void setCampoNoveGpsAnterior(Integer campoNoveGpsAnterior) {
		this.campoNoveGpsAnterior = campoNoveGpsAnterior;
	}

	public TomadorServicoResponse getTomadorServico() {
		return tomadorServico;
	}

	public void setTomadorServico(TomadorServicoResponse tomadorServico) {
		this.tomadorServico = tomadorServico;
	}

}
