package com.rhlinkcon.payload.lancamento;

import java.util.Objects;

import com.rhlinkcon.model.Lancamento;
import com.rhlinkcon.model.RecorrenciaEnum;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.folhaPagamento.FolhaPagamentoResponse;
import com.rhlinkcon.payload.verba.VerbaResponse;

public class LancamentoResponse extends DadoCadastralResponse {

	private Long id;

	private VerbaResponse verba;

	private Double valorReferencia;

	private FolhaPagamentoResponse folhaPagamento;

	private Double valor;

	private Boolean adicionadoManualmente;

	private DadoBasicoDto pensaoAlimenticia;

	private String numeroParcela;

	public LancamentoResponse(Lancamento lancamento) {
		this.id = lancamento.getId();
		if (Objects.nonNull(lancamento.getVerba()))
			this.verba = new VerbaResponse(lancamento.getVerba());
		this.valorReferencia = lancamento.getValorReferencia();
		this.valor = lancamento.getValor();
		if (Objects.nonNull(lancamento.getPensaoAlimenticia()))
			this.pensaoAlimenticia = new DadoBasicoDto(lancamento.getPensaoAlimenticia());

		if (Objects.nonNull(lancamento.getFuncionarioVerba()))
			if (lancamento.getFuncionarioVerba().getRecorrencia().equals(RecorrenciaEnum.EM_PARCELA))
				this.numeroParcela = lancamento.getNumeroParcela().toString() + " de "
						+ lancamento.getFuncionarioVerba().getParcelas().toString();

		if (Objects.nonNull(lancamento.getPensionistaVerba()))
			if (lancamento.getPensionistaVerba().getRecorrencia().equals(RecorrenciaEnum.EM_PARCELA))
				this.numeroParcela = lancamento.getNumeroParcela().toString() + " de "
						+ lancamento.getPensionistaVerba().getParcelas().toString();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VerbaResponse getVerba() {
		return verba;
	}

	public void setVerba(VerbaResponse verba) {
		this.verba = verba;
	}

	public Double getValorReferencia() {
		return valorReferencia;
	}

	public void setValorReferencia(Double valorReferencia) {
		this.valorReferencia = valorReferencia;
	}

	public FolhaPagamentoResponse getFolhaPagamento() {
		return folhaPagamento;
	}

	public void setFolhaPagamento(FolhaPagamentoResponse folhaPagamento) {
		this.folhaPagamento = folhaPagamento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Boolean getAdicionadoManualmente() {
		return adicionadoManualmente;
	}

	public void setAdicionadoManualmente(Boolean adicionadoManualmente) {
		this.adicionadoManualmente = adicionadoManualmente;
	}

	public DadoBasicoDto getPensaoAlimenticia() {
		return pensaoAlimenticia;
	}

	public void setPensaoAlimenticia(DadoBasicoDto pensaoAlimenticia) {
		this.pensaoAlimenticia = pensaoAlimenticia;
	}

	public String getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(String numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

}
