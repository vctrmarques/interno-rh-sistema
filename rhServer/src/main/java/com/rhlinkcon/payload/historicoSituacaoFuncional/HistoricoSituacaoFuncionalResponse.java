package com.rhlinkcon.payload.historicoSituacaoFuncional;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.HistoricoSituacaoFuncional;
import com.rhlinkcon.payload.DadoCadastralResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricoSituacaoFuncionalResponse extends DadoCadastralResponse {

	private Long id;

	private Long funcionarioId;

	private String tipoSituacaoFuncional;

	private String funcao;

	private String nivelSalarial;

	private String motivo;

	private String ato;

	private Instant dataAto;

	private Instant dataDiarioOficial;

	private Instant dataInicio;

	private Instant dataFim;

	private String observacaoCancelamento;

	private String filialDestino;

	private String situacao;

	private String tipoExoneracaoDemissao;

	private Instant dataRetorno;

	private String situacaoFuncional;

	private Long situacaoFuncionalId;

	public HistoricoSituacaoFuncionalResponse() {

	}

	public HistoricoSituacaoFuncionalResponse(HistoricoSituacaoFuncional historicoSituacaoFuncional) {
		setSituacaoFuncionalResponse(historicoSituacaoFuncional);
	}

	public HistoricoSituacaoFuncionalResponse(HistoricoSituacaoFuncional historicoSituacaoFuncional, Instant criadoEm,
			String criadoPor, Instant alteradoEm, String alteradoPor) {
		setSituacaoFuncionalResponse(historicoSituacaoFuncional);
		this.setCriadoEm(criadoEm);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoPor(criadoPor);
	}

	private void setSituacaoFuncionalResponse(HistoricoSituacaoFuncional historicoSituacaoFuncional) {

		this.setId(historicoSituacaoFuncional.getId());
		this.setTipoSituacaoFuncional(historicoSituacaoFuncional.getTipoHistoricoSituacaoFuncional().toString());

		if (Objects.nonNull(historicoSituacaoFuncional.getTipoExoneracaoDemissao()))
			this.setTipoExoneracaoDemissao(historicoSituacaoFuncional.getTipoExoneracaoDemissao().toString());

		this.setAto(historicoSituacaoFuncional.getAto());
		this.setDataAto(historicoSituacaoFuncional.getDataAto());
		this.setDataDiarioOficial(historicoSituacaoFuncional.getDataDiarioOficial());
		this.setDataInicio(historicoSituacaoFuncional.getDataInicio());
		this.setDataFim(historicoSituacaoFuncional.getDataFim());
		this.setObservacaoCancelamento(historicoSituacaoFuncional.getObservacaoCancelamento());
		this.setFuncionarioId(historicoSituacaoFuncional.getFuncionario().getId());
		this.setDataRetorno(historicoSituacaoFuncional.getDataRetorno());

		if (Objects.nonNull(historicoSituacaoFuncional.getFuncao()))
			this.setFuncao(historicoSituacaoFuncional.getFuncao().getNome());

		if (Objects.nonNull(historicoSituacaoFuncional.getNivelSalarial()))
			this.setNivelSalarial(historicoSituacaoFuncional.getNivelSalarial().getDescricao());

		if (Objects.nonNull(historicoSituacaoFuncional.getMotivo()))
			this.setMotivo(historicoSituacaoFuncional.getMotivo().getDescricao());

		if (Objects.nonNull(historicoSituacaoFuncional.getFilialDestino()))
			this.setFilialDestino(historicoSituacaoFuncional.getFilialDestino().getNomeFilial());

		if (Objects.nonNull(historicoSituacaoFuncional.getSituacao()))
			this.setSituacao(historicoSituacaoFuncional.getSituacao().getDescricao());

		if (Objects.nonNull(historicoSituacaoFuncional.getSituacaoFuncional())) {
			this.setSituacaoFuncional(historicoSituacaoFuncional.getSituacaoFuncional().getDescricao());
			this.setSituacaoFuncionalId(historicoSituacaoFuncional.getSituacaoFuncional().getId());
		}

	}

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

	public String getTipoSituacaoFuncional() {
		return tipoSituacaoFuncional;
	}

	public void setTipoSituacaoFuncional(String tipoSituacaoFuncional) {
		this.tipoSituacaoFuncional = tipoSituacaoFuncional;
	}

	public String getAto() {
		return ato;
	}

	public void setAto(String ato) {
		this.ato = ato;
	}

	public Instant getDataAto() {
		return dataAto;
	}

	public void setDataAto(Instant dataAto) {
		this.dataAto = dataAto;
	}

	public Instant getDataDiarioOficial() {
		return dataDiarioOficial;
	}

	public void setDataDiarioOficial(Instant dataDiarioOficial) {
		this.dataDiarioOficial = dataDiarioOficial;
	}

	public Instant getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Instant dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Instant getDataFim() {
		return dataFim;
	}

	public void setDataFim(Instant dataFim) {
		this.dataFim = dataFim;
	}

	public String getObservacaoCancelamento() {
		return observacaoCancelamento;
	}

	public void setObservacaoCancelamento(String observacaoCancelamento) {
		this.observacaoCancelamento = observacaoCancelamento;
	}

	public String getTipoExoneracaoDemissao() {
		return tipoExoneracaoDemissao;
	}

	public void setTipoExoneracaoDemissao(String tipoExoneracaoDemissao) {
		this.tipoExoneracaoDemissao = tipoExoneracaoDemissao;
	}

	public Instant getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Instant dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getNivelSalarial() {
		return nivelSalarial;
	}

	public void setNivelSalarial(String nivelSalarial) {
		this.nivelSalarial = nivelSalarial;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getFilialDestino() {
		return filialDestino;
	}

	public void setFilialDestino(String filialDestino) {
		this.filialDestino = filialDestino;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getSituacaoFuncional() {
		return situacaoFuncional;
	}

	public void setSituacaoFuncional(String situacaoFuncional) {
		this.situacaoFuncional = situacaoFuncional;
	}

	public Long getSituacaoFuncionalId() {
		return situacaoFuncionalId;
	}

	public void setSituacaoFuncionalId(Long situacaoFuncionalId) {
		this.situacaoFuncionalId = situacaoFuncionalId;
	}

}
