package com.rhlinkcon.payload.referenciaSalarial;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.NivelSalarialHistorico;
import com.rhlinkcon.model.NivelSalarialHistoricoOrigemAjusteEnum;
import com.rhlinkcon.model.SituacaoSimuladorNivelSalarialEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.simuladorNivelSalarial.SimuladorNivelSalarialResponse;
import com.rhlinkcon.util.Projecao;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NivelSalarialHistoricoResponse extends DadoCadastralResponse {

	private Long id;

	private Date dataAjuste;

	private NivelSalarialHistoricoOrigemAjusteEnum origemAjuste;

	private Double valorOriginal;

	private Double valorAjustado;

	private Double valorRetroativo;

	private SimuladorNivelSalarialResponse simulacao;

	private Long simuladorNivelSalarialId;

	private ReferenciaSalarialResponse nivelSalarial;

	private String resultado;

	public NivelSalarialHistoricoResponse() {
	}

	public NivelSalarialHistoricoResponse(NivelSalarialHistorico nivelSalarialHistorico, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			this.id = nivelSalarialHistorico.getId();
			this.dataAjuste = nivelSalarialHistorico.getDataAjuste();
			this.origemAjuste = nivelSalarialHistorico.getOrigemAjuste();
			this.valorOriginal = nivelSalarialHistorico.getValorOriginal();
			this.valorAjustado = nivelSalarialHistorico.getValorAjustado();
			this.valorRetroativo = nivelSalarialHistorico.getValorRetroativo();

			if (nivelSalarialHistorico.getOrigemAjuste()
					.equals(NivelSalarialHistoricoOrigemAjusteEnum.AJUSTE_PROGRAMADO)) {
				this.simuladorNivelSalarialId = nivelSalarialHistorico.getSimulacao().getId();
				if (this.origemAjuste.equals(NivelSalarialHistoricoOrigemAjusteEnum.AJUSTE_PROGRAMADO)
						&& nivelSalarialHistorico.getSimulacao().getSituacao()
								.equals(SituacaoSimuladorNivelSalarialEnum.APLICADO)) {
					this.resultado = "Aplicado com Sucesso";
				} else {
					this.resultado = "Não aplicado";
				}
			} else {
				this.resultado = "Não se aplica";
			}

			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {

				setCriadoEm(nivelSalarialHistorico.getCreatedAt());
				setAlteradoEm(nivelSalarialHistorico.getUpdatedAt());

				if (projecao.equals(Projecao.COMPLETA)) {

				}
			}
		}
	}

	public Date getDataAjuste() {
		return dataAjuste;
	}

	public void setDataAjuste(Date dataAjuste) {
		this.dataAjuste = dataAjuste;
	}

	public NivelSalarialHistoricoOrigemAjusteEnum getOrigemAjuste() {
		return origemAjuste;
	}

	public void setOrigemAjuste(NivelSalarialHistoricoOrigemAjusteEnum origemAjuste) {
		this.origemAjuste = origemAjuste;
	}

	public Double getValorOriginal() {
		return valorOriginal;
	}

	public void setValorOriginal(Double valorOriginal) {
		this.valorOriginal = valorOriginal;
	}

	public Double getValorAjustado() {
		return valorAjustado;
	}

	public void setValorAjustado(Double valorAjustado) {
		this.valorAjustado = valorAjustado;
	}

	public Double getValorRetroativo() {
		return valorRetroativo;
	}

	public void setValorRetroativo(Double valorRetroativo) {
		this.valorRetroativo = valorRetroativo;
	}

	public SimuladorNivelSalarialResponse getSimulacao() {
		return simulacao;
	}

	public void setSimulacao(SimuladorNivelSalarialResponse simulacao) {
		this.simulacao = simulacao;
	}

	public ReferenciaSalarialResponse getNivelSalarial() {
		return nivelSalarial;
	}

	public void setNivelSalarial(ReferenciaSalarialResponse nivelSalarial) {
		this.nivelSalarial = nivelSalarial;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSimuladorNivelSalarialId() {
		return simuladorNivelSalarialId;
	}

	public void setSimuladorNivelSalarialId(Long simuladorNivelSalarialId) {
		this.simuladorNivelSalarialId = simuladorNivelSalarialId;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
