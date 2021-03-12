package com.rhlinkcon.model;

import java.time.Instant;

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
import com.rhlinkcon.payload.licencaPremio.LicencaPremioRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Licença Prêmio")
@Table(name = "licenca_premio")
public class LicencaPremio extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_exercicio_id")
	private FuncionarioExercicio funcionarioExercicio;

	@NotNull
	@Column(name = "observacao")
	private String observacao;

	@NotNull
	@Column(name = "data_inicio")
	private Instant dataInicio;

	@NotNull
	@Column(name = "data_retorno")
	private Instant dataRetorno;

	@NotNull
	@Column(name = "dias")
	private String dias;

	public LicencaPremio() {

	}

	public LicencaPremio(LicencaPremioRequest licencaPremioRequest) {

		this.setDataInicio(licencaPremioRequest.getDataInicio());

		this.setDataRetorno(licencaPremioRequest.getDataRetorno());

		if (Utils.checkStr(licencaPremioRequest.getObservacao()))
			this.setObservacao(licencaPremioRequest.getObservacao());

		if (Utils.checkStr(licencaPremioRequest.getDias()))
			this.setDias(licencaPremioRequest.getDias());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioExercicio getFuncionarioExercicio() {
		return funcionarioExercicio;
	}

	public void setFuncionarioExercicio(FuncionarioExercicio funcionarioExercicio) {
		this.funcionarioExercicio = funcionarioExercicio;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
