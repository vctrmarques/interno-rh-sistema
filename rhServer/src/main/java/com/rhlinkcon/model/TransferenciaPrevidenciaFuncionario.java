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
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Transferência Previdência Funcionário")
@Table(name = "transferencia_previdencia_funcionario")
public class TransferenciaPrevidenciaFuncionario extends UserDateAudit {

	private static final long serialVersionUID = 2881888884006327942L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", nullable = false)
	private Funcionario funcionario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transferencia_id", nullable = false)
	private TransferenciaFuncionario transferenciaFuncionario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "motivo_aposentadoria")
	private TipoAposentadoria tipoAposentadoria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "historico_situacao_funcional_id", nullable = false)
	private HistoricoSituacaoFuncional historicoSituacaoFuncional;

	@NotNull
	@Column(name = "data_solicitacao")
	private Instant dataSolicitacao;

	@NotNull
	@Column(name = "data_aposentadoria")
	private Instant dataAposentadoria;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_proporcao")
	private TipoProporcaoEnum tipoProporcao;

	@Column(name = "proporcao")
	private Integer proporcao;

	@NotEmpty
	@Column(name = "processo")
	private String processo;

	@Column(name = "observacao")
	private String observacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public TransferenciaFuncionario getTransferenciaFuncionario() {
		return transferenciaFuncionario;
	}

	public void setTransferenciaFuncionario(TransferenciaFuncionario transferenciaFuncionario) {
		this.transferenciaFuncionario = transferenciaFuncionario;
	}

	public TipoAposentadoria getTipoAposentadoria() {
		return tipoAposentadoria;
	}

	public void setTipoAposentadoria(TipoAposentadoria tipoAposentadoria) {
		this.tipoAposentadoria = tipoAposentadoria;
	}

	public HistoricoSituacaoFuncional getHistoricoSituacaoFuncional() {
		return historicoSituacaoFuncional;
	}

	public void setHistoricoSituacaoFuncional(HistoricoSituacaoFuncional historicoSituacaoFuncional) {
		this.historicoSituacaoFuncional = historicoSituacaoFuncional;
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

	public TipoProporcaoEnum getTipoProporcao() {
		return tipoProporcao;
	}

	public void setTipoProporcao(TipoProporcaoEnum tipoProporcao) {
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

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
