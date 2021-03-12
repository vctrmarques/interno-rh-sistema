package com.rhlinkcon.payload.aliquota;

import java.util.ArrayList;
import java.util.List;

import com.rhlinkcon.model.Aliquota;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.util.Projecao;

public class AliquotaResponse extends DadoCadastralResponse {
	private Long id;
	private Double valorInicial;
	private Double valorFinal;
	private Double deducao;
	private Double aliquota;
	private Integer ano;
	private String faixa;
	private String faixaFormatada;
	private List<Aliquota> aliquotas;

	public AliquotaResponse(Aliquota aliquota, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			this.id = aliquota.getId();
			this.valorInicial = aliquota.getValorInicial();
			this.valorFinal = aliquota.getValorFinal();
			this.deducao = aliquota.getDeducao();
			this.aliquota = aliquota.getAliquota();
			this.ano = aliquota.getAno();
			this.faixa = aliquota.getFaixa().toString();
			this.aliquota = aliquota.getAliquota();
			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {

				if (projecao.equals(Projecao.COMPLETA)) {
					setCriadoEm(aliquota.getCreatedAt());
					setAlteradoEm(aliquota.getUpdatedAt());

				}
			}
			this.faixaFormatada = "a{" + aliquota.getFaixa() + "}";
		}
	}

	public AliquotaResponse(String faixa, Integer ano, List<Aliquota> aliquotas) {
		this.ano = ano;
		this.faixa = faixa;
		this.aliquotas = aliquotas;
		this.faixaFormatada = "a{" + faixa + "}";
	}

	public AliquotaResponse() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(Double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public Double getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Double valorFinal) {
		this.valorFinal = valorFinal;
	}

	public Double getDeducao() {
		return deducao;
	}

	public void setDeducao(Double deducao) {
		this.deducao = deducao;
	}

	public Double getAliquota() {
		return aliquota;
	}

	public void setAliquota(Double aliquota) {
		this.aliquota = aliquota;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getFaixa() {
		return faixa;
	}

	public void setFaixa(String faixa) {
		this.faixa = faixa;
	}

	public List<Aliquota> getAliquotas() {
		return aliquotas;
	}

	public void setAliquotas(List<Aliquota> aliquotas) {
		this.aliquotas = aliquotas;
	}

	public String getFaixaFormatada() {
		return faixaFormatada;
	}

	public void setFaixaFormatada(String faixaFormatada) {
		this.faixaFormatada = faixaFormatada;
	}



}
