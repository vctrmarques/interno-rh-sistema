package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import com.rhlinkcon.payload.funcionario.FuncionarioValeTransporteRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Funcionário Vale Transporte")
@Table(name = "funcionario_vale_transporte")
public class FuncionarioValeTransporte extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@NotNull
	@Column(name = "quantidade")
	private Integer quantidade;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vale_transporte_id")
	private ValeTransporte valeTransporte;

	private FuncionarioValeTransporte() {

	}

	public FuncionarioValeTransporte(FuncionarioValeTransporteRequest funcionarioValeTransporteRequest) {
		this.quantidade = funcionarioValeTransporteRequest.getQuantidade();
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

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public ValeTransporte getValeTransporte() {
		return valeTransporte;
	}

	public void setValeTransporte(ValeTransporte valeTransporte) {
		this.valeTransporte = valeTransporte;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}