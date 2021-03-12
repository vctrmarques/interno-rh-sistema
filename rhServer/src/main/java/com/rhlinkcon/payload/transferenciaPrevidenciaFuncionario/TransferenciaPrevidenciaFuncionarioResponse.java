package com.rhlinkcon.payload.transferenciaPrevidenciaFuncionario;

import java.time.Instant;

import com.rhlinkcon.model.TransferenciaPrevidenciaFuncionario;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.historicoSituacaoFuncional.HistoricoSituacaoFuncionalResponse;
import com.rhlinkcon.payload.tipoAposentadoria.TipoAposentadoriaResponse;
import com.rhlinkcon.payload.tranferenciaFuncionario.TransferenciaFuncionarioResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.util.Projecao;

public class TransferenciaPrevidenciaFuncionarioResponse {
	
	private Long id;
	
	private FuncionarioResponse funcionario;

	private TransferenciaFuncionarioResponse transferenciaFuncionario;
	
	private TipoAposentadoriaResponse tipoAposentadoria;
	
	private HistoricoSituacaoFuncionalResponse historicoSituacaoFuncional;
	
	private Instant dataSolicitacao;

	private Instant dataAposentadoria;
	
	private String tipoProporcao;
	
	private Integer proporcao;
	
	private String processo;
	
	private String observacao;
	
	private Long situacaoFuncional;


	public TransferenciaPrevidenciaFuncionarioResponse(TransferenciaPrevidenciaFuncionario item) {
		this.id = item.getId();
		this.transferenciaFuncionario = new TransferenciaFuncionarioResponse(item.getTransferenciaFuncionario());
		this.historicoSituacaoFuncional = new HistoricoSituacaoFuncionalResponse(item.getHistoricoSituacaoFuncional());
		this.tipoAposentadoria = new TipoAposentadoriaResponse(item.getTipoAposentadoria());
		this.funcionario = new FuncionarioResponse(item.getFuncionario());
		this.dataSolicitacao = item.getDataSolicitacao();
		this.dataAposentadoria = item.getDataAposentadoria();
		this.tipoProporcao = item.getTipoProporcao().getLabel();
		this.proporcao = item.getProporcao();
		this.processo = item.getProcesso();
		this.observacao = item.getObservacao();
		this.situacaoFuncional = item.getHistoricoSituacaoFuncional().getSituacaoFuncional().getId();
	}


	public TransferenciaPrevidenciaFuncionarioResponse() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}


	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}


	public Instant getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Instant dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public Instant getDataAposentadoria() {
		return dataAposentadoria;
	}

	public void setDataAposentadoria(Instant dataAposentadoria) {
		this.dataAposentadoria = dataAposentadoria;
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


	public TransferenciaFuncionarioResponse getTransferenciaFuncionario() {
		return transferenciaFuncionario;
	}


	public void setTransferenciaFuncionario(TransferenciaFuncionarioResponse transferenciaFuncionario) {
		this.transferenciaFuncionario = transferenciaFuncionario;
	}


	public TipoAposentadoriaResponse getTipoAposentadoria() {
		return tipoAposentadoria;
	}


	public void setTipoAposentadoria(TipoAposentadoriaResponse tipoAposentadoria) {
		this.tipoAposentadoria = tipoAposentadoria;
	}


	public HistoricoSituacaoFuncionalResponse getHistoricoSituacaoFuncional() {
		return historicoSituacaoFuncional;
	}


	public void setHistoricoSituacaoFuncional(HistoricoSituacaoFuncionalResponse historicoSituacaoFuncional) {
		this.historicoSituacaoFuncional = historicoSituacaoFuncional;
	}
}
