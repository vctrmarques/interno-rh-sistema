package com.rhlinkcon.payload.requisicaoPessoal;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rhlinkcon.model.RequisicaoPessoal;
import com.rhlinkcon.model.RequisicaoPessoalCandidato;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;

public class RequisicaoPessoalReponse extends DadoCadastralResponse {

	private Long id;

	private FuncionarioResponse solicitante;

	private String justificativa;

	private String situacao;

	private Instant dataEntrada;

	private Instant dataLimite;

	private String motivoSolicitacao;

	private FuncionarioResponse funcionarioSubstituido;

	private Instant dataPrevistaAdimissao;

	private List<RequisicaoPessoalCandidatoResponse> candidatos;

	public RequisicaoPessoalReponse(RequisicaoPessoal requisicaoPessoal) {
		this.id = requisicaoPessoal.getId();
		this.solicitante = new FuncionarioResponse(requisicaoPessoal.getSolicitante(), requisicaoPessoal.getSolicitante().getLotacao());
		this.justificativa = requisicaoPessoal.getJustificativa();
		this.situacao = requisicaoPessoal.getSituacao().getLabel();
		this.dataEntrada = requisicaoPessoal.getDataEntrada();
		this.dataLimite = requisicaoPessoal.getDataLimite();
		this.motivoSolicitacao = requisicaoPessoal.getMotivoSolicitacao();
		if (Objects.nonNull(requisicaoPessoal.getFuncionarioSubstituido()))
			this.funcionarioSubstituido = new FuncionarioResponse(requisicaoPessoal.getFuncionarioSubstituido());
		this.dataPrevistaAdimissao = requisicaoPessoal.getDataPrevistaAdimissao();
		this.setCriadoEm(requisicaoPessoal.getCreatedAt());
		this.setAlteradoEm(requisicaoPessoal.getUpdatedAt());

		candidatos = new ArrayList<>();
		if (Objects.nonNull(requisicaoPessoal.getCandidatos())) {

			for (RequisicaoPessoalCandidato candidato : requisicaoPessoal.getCandidatos()) {
				candidatos.add(new RequisicaoPessoalCandidatoResponse(candidato));
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioResponse getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(FuncionarioResponse solicitante) {
		this.solicitante = solicitante;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Instant getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Instant dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Instant getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Instant dataLimite) {
		this.dataLimite = dataLimite;
	}

	public String getMotivoSolicitacao() {
		return motivoSolicitacao;
	}

	public void setMotivoSolicitacao(String motivoSolicitacao) {
		this.motivoSolicitacao = motivoSolicitacao;
	}

	public FuncionarioResponse getFuncionarioSubstituido() {
		return funcionarioSubstituido;
	}

	public void setFuncionarioSubstituido(FuncionarioResponse funcionarioSubstituido) {
		this.funcionarioSubstituido = funcionarioSubstituido;
	}

	public Instant getDataPrevistaAdimissao() {
		return dataPrevistaAdimissao;
	}

	public void setDataPrevistaAdimissao(Instant dataPrevistaAdimissao) {
		this.dataPrevistaAdimissao = dataPrevistaAdimissao;
	}

	public List<RequisicaoPessoalCandidatoResponse> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(List<RequisicaoPessoalCandidatoResponse> candidatos) {
		this.candidatos = candidatos;
	}

	public boolean hasCandidatos() {
		return candidatos != null && !candidatos.isEmpty();
	}
}
