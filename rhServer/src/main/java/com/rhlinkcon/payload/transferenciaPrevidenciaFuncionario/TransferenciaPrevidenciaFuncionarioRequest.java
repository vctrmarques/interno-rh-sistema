package com.rhlinkcon.payload.transferenciaPrevidenciaFuncionario;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TransferenciaPrevidenciaFuncionarioRequest {

	private Long id;
	
	@NotNull
	private Long funcionarioId;
	
	@NotNull
	private Long filialDestinoId;
	
	@NotNull
	private Long lotacaoDestinoId;
	
	@NotNull
	private Long situacaoFuncionalId;

	private Long tipoAposentadoriaId;
	
	@NotNull
	private LocalDate dataSolicitacao;

	private LocalDate dataAposentadoria;
	
	@NotEmpty
	private String tipoProporcao;
	
	private Integer proporcao;
	
	@NotEmpty
	private String processo;
	
	private String observacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Long getTipoAposentadoriaId() {
		return tipoAposentadoriaId;
	}

	public void setTipoAposentadoriaId(Long tipoAposentadoriaId) {
		this.tipoAposentadoriaId = tipoAposentadoriaId;
	}

	public LocalDate getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(LocalDate dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public String getTipoProporcao() {
		return tipoProporcao;
	}

	public void setTipoProporcao(String tipoProporcao) {
		this.tipoProporcao = tipoProporcao;
	}

	public Integer getProporcao() {
		return proporcao;
	}

	public void setProporcao(Integer proporcao) {
		this.proporcao = proporcao;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Long getFilialDestinoId() {
		return filialDestinoId;
	}

	public void setFilialDestinoId(Long filialDestinoId) {
		this.filialDestinoId = filialDestinoId;
	}

	public LocalDate getDataAposentadoria() {
		return dataAposentadoria;
	}

	public void setDataAposentadoria(LocalDate dataAposentadoria) {
		this.dataAposentadoria = dataAposentadoria;
	}

	public Long getSituacaoFuncionalId() {
		return situacaoFuncionalId;
	}

	public void setSituacaoFuncionalId(Long situacaoFuncionalId) {
		this.situacaoFuncionalId = situacaoFuncionalId;
	}

	public Long getLotacaoDestinoId() {
		return lotacaoDestinoId;
	}

	public void setLotacaoDestinoId(Long lotacaoDestinoId) {
		this.lotacaoDestinoId = lotacaoDestinoId;
	}

	
}
