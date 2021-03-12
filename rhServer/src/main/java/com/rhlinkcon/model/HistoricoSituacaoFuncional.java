package com.rhlinkcon.model;

import java.time.Instant;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.historicoSituacaoFuncional.HistoricoSituacaoFuncionalRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Histórico de Situação Funcional")
@Table(name = "historico_situacao_funcional")
public class HistoricoSituacaoFuncional extends UserDateAudit {

	private static final long serialVersionUID = -7071280236382444795L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_situacao_funcional")
	private TipoHistoricoSituacaoFuncionalEnum tipoHistoricoSituacaoFuncional;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcao_id")
	private Funcao funcao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nivel_salarial_id")
	private ReferenciaSalarial nivelSalarial;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "motivo_id")
	private Motivo motivo;

	@Column(name = "ato")
	private String ato;

	@Column(name = "dt_ato")
	private Instant dataAto;

	@Column(name = "dt_diario_oficial")
	private Instant dataDiarioOficial;

	@Column(name = "dt_inicio")
	private Instant dataInicio;

	@Column(name = "dt_fim")
	private Instant dataFim;

	@Column(name = "obs_cancel")
	private String observacaoCancelamento;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_filial_id")
	private EmpresaFilial filialDestino;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sit_motivo_afastamento_id")
	private MotivoAfastamento situacao;

	@Column(name = "dt_retorno")
	private Instant dataRetorno;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_exoneracao_demissao")
	private TipoExoneracaoDemissaoEnum tipoExoneracaoDemissao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "situacao_funcional_id")
	private SituacaoFuncional situacaoFuncional;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "situacao_funcional_anterior_id")
	private SituacaoFuncional situacaoFuncionalAnterior;

	public HistoricoSituacaoFuncional() {

	}

	public HistoricoSituacaoFuncional(HistoricoSituacaoFuncionalRequest historicoSituacaoFuncionalRequest) {

		if (Utils.checkStr(historicoSituacaoFuncionalRequest.getTipoSituacaoFuncional()))
			this.setTipoHistoricoSituacaoFuncional(TipoHistoricoSituacaoFuncionalEnum
					.valueOf(historicoSituacaoFuncionalRequest.getTipoSituacaoFuncional()));

		this.setAto(historicoSituacaoFuncionalRequest.getAto());
		this.setDataAto(historicoSituacaoFuncionalRequest.getDataAto());
		this.setDataDiarioOficial(historicoSituacaoFuncionalRequest.getDataDiarioOficial());
		this.setDataInicio(historicoSituacaoFuncionalRequest.getDataInicio());
		this.setObservacaoCancelamento(historicoSituacaoFuncionalRequest.getObservacaoCancelamento());

		if (Utils.checkStr(historicoSituacaoFuncionalRequest.getTipoExoneracaoDemissao()))
			this.setTipoExoneracaoDemissao(
					TipoExoneracaoDemissaoEnum.valueOf(historicoSituacaoFuncionalRequest.getTipoExoneracaoDemissao()));

		this.setDataRetorno(historicoSituacaoFuncionalRequest.getDataRetorno());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoHistoricoSituacaoFuncionalEnum getTipoHistoricoSituacaoFuncional() {
		return tipoHistoricoSituacaoFuncional;
	}

	public void setTipoHistoricoSituacaoFuncional(TipoHistoricoSituacaoFuncionalEnum tipoSituacaoFuncional) {
		this.tipoHistoricoSituacaoFuncional = tipoSituacaoFuncional;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public ReferenciaSalarial getNivelSalarial() {
		return nivelSalarial;
	}

	public void setNivelSalarial(ReferenciaSalarial nivelSalarial) {
		this.nivelSalarial = nivelSalarial;
	}

	public Motivo getMotivo() {
		return motivo;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
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

	public EmpresaFilial getFilialDestino() {
		return filialDestino;
	}

	public void setFilialDestino(EmpresaFilial filialDestino) {
		this.filialDestino = filialDestino;
	}

	public MotivoAfastamento getSituacao() {
		return situacao;
	}

	public void setSituacao(MotivoAfastamento situacao) {
		this.situacao = situacao;
	}

	public TipoExoneracaoDemissaoEnum getTipoExoneracaoDemissao() {
		return tipoExoneracaoDemissao;
	}

	public void setTipoExoneracaoDemissao(TipoExoneracaoDemissaoEnum tipoExoneracaoDemissao) {
		this.tipoExoneracaoDemissao = tipoExoneracaoDemissao;
	}

	public Instant getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Instant dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public SituacaoFuncional getSituacaoFuncional() {
		return situacaoFuncional;
	}

	public void setSituacaoFuncional(SituacaoFuncional situacaoFuncional) {
		this.situacaoFuncional = situacaoFuncional;
	}

	public SituacaoFuncional getSituacaoFuncionalAnterior() {
		return situacaoFuncionalAnterior;
	}

	public void setSituacaoFuncionalAnterior(SituacaoFuncional situacaoFuncionalAnterior) {
		this.situacaoFuncionalAnterior = situacaoFuncionalAnterior;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}