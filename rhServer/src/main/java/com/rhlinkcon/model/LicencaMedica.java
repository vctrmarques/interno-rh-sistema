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
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.licencaMedica.LicencaMedicaRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Licença Médica")
@Table(name = "licenca_medica")
public class LicencaMedica extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", nullable = false)
	private Funcionario funcionario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "crm_id")
	private CrmCrea crm;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "afastamento_id", nullable = false)
	private SituacaoFuncional situacaoFuncional;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "motivo_afastamento_id", nullable = false)
	private MotivoAfastamento motivoAfastamento;

	@NotNull
	@Column(name = "periodo_inicial")
	private Instant periodoInicial;

	@Column(name = "periodo_final")
	private Instant periodoFinal;

	@NotNull
	@Column(name = "data_inspecao")
	private Instant dataInspecao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cid_id")
	private ClassificacaoInternacionalDoenca cid;

	public LicencaMedica(LicencaMedicaRequest request) {
		this.id = request.getId();
		this.funcionario = new Funcionario(request.getFuncionarioId());
		this.crm = Objects.nonNull(request.getCrmId()) ? new CrmCrea(request.getCrmId()) : null;
		this.situacaoFuncional = new SituacaoFuncional(request.getAfastamentoId());
		this.motivoAfastamento = new MotivoAfastamento(request.getMotivoAfastamentoId());
		this.periodoInicial = request.getPeriodoInicial();
		this.periodoFinal = request.getPeriodoFinal();
		this.dataInspecao = request.getDataInspecao();
		this.cid = new ClassificacaoInternacionalDoenca(request.getCidId());
	}

	public LicencaMedica() {
		super();
	}

	public ClassificacaoInternacionalDoenca getCid() {
		return cid;
	}

	public void setCid(ClassificacaoInternacionalDoenca cid) {
		this.cid = cid;
	}

	public Instant getDataInspecao() {
		return dataInspecao;
	}

	public void setDataInspecao(Instant dataInspecao) {
		this.dataInspecao = dataInspecao;
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

	public CrmCrea getCrm() {
		return crm;
	}

	public void setCrm(CrmCrea crm) {
		this.crm = crm;
	}

	public SituacaoFuncional getAfastamento() {
		return situacaoFuncional;
	}

	public void setAfastamento(SituacaoFuncional situacaoFuncional) {
		this.situacaoFuncional = situacaoFuncional;
	}

	public MotivoAfastamento getMotivoAfastamento() {
		return motivoAfastamento;
	}

	public void setMotivoAfastamento(MotivoAfastamento motivoAfastamento) {
		this.motivoAfastamento = motivoAfastamento;
	}

	public Instant getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Instant periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Instant getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Instant periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
