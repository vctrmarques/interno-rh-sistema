package com.rhlinkcon.model;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.falta.FaltaRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Falta")
@Table(name = "falta")
public class Falta extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", nullable = false)
	private Funcionario funcionario;

	@NotNull
	@Column(name = "data_inicio")
	private Instant dataInicio;

	@Column(name = "data_retorno")
	private Instant dataRetorno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "motivo_afastamento_id", nullable = false)
	private MotivoAfastamento motivoAfastamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "afastamento_id", nullable = false)
	private SituacaoFuncional situacaoFuncional;

	@NotNull
	@NotBlank
	private String diagnosticoMedico;

	private String observacaoDocumento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "anexo_id")
	private Anexo anexo;

	public Falta(Long id) {
		this.id = id;
	}

	public Falta() {

	}

	public Falta(FaltaRequest faltaRequest) {
		this.id = faltaRequest.getId();
		this.funcionario = new Funcionario(faltaRequest.getFuncionarioId());
		this.dataInicio = faltaRequest.getDataInicio();
		this.dataRetorno = faltaRequest.getDataRetorno();
		this.motivoAfastamento = new MotivoAfastamento(faltaRequest.getMotivoAfastamentoId());
		this.situacaoFuncional = new SituacaoFuncional(faltaRequest.getAfastamentoId());
		this.diagnosticoMedico = faltaRequest.getDiagnosticoMedico();
		this.observacaoDocumento = faltaRequest.getObservacaoDocumento();
		if (Objects.nonNull(faltaRequest.getAnexoId()))
			this.anexo = new Anexo(faltaRequest.getAnexoId());
	}

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

	public Instant getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Instant dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Instant getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Instant dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public MotivoAfastamento getMotivoAfastamento() {
		return motivoAfastamento;
	}

	public void setMotivoAfastamento(MotivoAfastamento motivoAfastamento) {
		this.motivoAfastamento = motivoAfastamento;
	}

	public SituacaoFuncional getAfastamento() {
		return situacaoFuncional;
	}

	public void setAfastamento(SituacaoFuncional situacaoFuncional) {
		this.situacaoFuncional = situacaoFuncional;
	}

	public String getDiagnosticoMedico() {
		return diagnosticoMedico;
	}

	public void setDiagnosticoMedico(String diagnosticoMedico) {
		this.diagnosticoMedico = diagnosticoMedico;
	}

	public String getObservacaoDocumento() {
		return observacaoDocumento;
	}

	public void setObservacaoDocumento(String observacaoDocumento) {
		this.observacaoDocumento = observacaoDocumento;
	}

	public Anexo getAnexo() {
		return anexo;
	}

	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
