package com.rhlinkcon.model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Requisição de Pessoal")
@Table(name = "requisicao_pessoal")
public class RequisicaoPessoal extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	private static final String STATUS_TEMPO_NA_DATA_LIMITE = "Na Data Limite";
	private static final String STATUS_TEMPO_APOS_A_DATA_LIMITE = "Após a Data Limite";
	private static final String STATUS_TEMPO_ANTES_DA_DATA_LIMITE = "Antes da Data Limite";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "solicitante_id", nullable = false)
	private Funcionario solicitante;

	private String justificativa;

	@NotNull
	@Enumerated(EnumType.STRING)
	private SituacaoRequisicaoPessoalEnum situacao;

	@Column(name = "data_entrada")
	private Instant dataEntrada;

	@Column(name = "data_limite")
	private Instant dataLimite;

	@Column(name = "data_conclusao")
	private Instant dataConclusao;

	@Column(name = "data_prevista_adimissao")
	private Instant dataPrevistaAdimissao;

	@Column(name = "motivo_solicitacao")
	private String motivoSolicitacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_substituido_id")
	private Funcionario funcionarioSubstituido;

	@OneToMany(mappedBy = "requisicaoPessoal")
	private List<RequisicaoPessoalCandidato> candidatos;

	public RequisicaoPessoal(RequisicaoPessoalRequest request) {
		this.id = request.getId();
		this.solicitante = new Funcionario(request.getSolicitanteId());
		this.situacao = SituacaoRequisicaoPessoalEnum.getEnumByString(request.getSituacao());
		this.dataEntrada = request.getDataEntrada();
		this.dataLimite = request.getDataLimite();
		this.motivoSolicitacao = request.getMotivoSolicitacao();
		this.funcionarioSubstituido = Objects.nonNull(request.getFuncionarioSubstituidoId())
				? new Funcionario(request.getFuncionarioSubstituidoId())
				: null;
		this.dataPrevistaAdimissao = request.getDataPrevistaAdimissao();
		this.justificativa = request.getJustificativa();
		this.candidatos = request.getCandidatos();
	}

	public RequisicaoPessoal(Long id) {
		this.id = id;
	}

	public RequisicaoPessoal() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Funcionario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Funcionario solicitante) {
		this.solicitante = solicitante;
	}

	public SituacaoRequisicaoPessoalEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoRequisicaoPessoalEnum situacao) {
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

	public Instant getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(Instant dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public String getMotivoSolicitacao() {
		return motivoSolicitacao;
	}

	public void setMotivoSolicitacao(String motivoSolicitacao) {
		this.motivoSolicitacao = motivoSolicitacao;
	}

	public Funcionario getFuncionarioSubstituido() {
		return funcionarioSubstituido;
	}

	public void setFuncionarioSubstituido(Funcionario funcionarioSubstituido) {
		this.funcionarioSubstituido = funcionarioSubstituido;
	}

	public Instant getDataPrevistaAdimissao() {
		return dataPrevistaAdimissao;
	}

	public void setDataPrevistaAdimissao(Instant dataPrevistaAdimissao) {
		this.dataPrevistaAdimissao = dataPrevistaAdimissao;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public List<RequisicaoPessoalCandidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(List<RequisicaoPessoalCandidato> candidatos) {
		this.candidatos = candidatos;
	}

	public String getStatusTempo() {
		int comparacao = Instant.now().compareTo(dataLimite);

		if (comparacao > 0) {
			return STATUS_TEMPO_APOS_A_DATA_LIMITE;

		} else if (comparacao < 0) {
			return STATUS_TEMPO_ANTES_DA_DATA_LIMITE;

		} else {
			return STATUS_TEMPO_NA_DATA_LIMITE;
		}
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}
