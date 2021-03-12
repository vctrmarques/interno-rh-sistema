package com.rhlinkcon.model;

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
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Transferência de Funcionário")
@Table(name = "transferencia_funcionario")
public class TransferenciaFuncionario extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")
	private EmpresaFilial empresa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lotacao_id")
	private Lotacao lotacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_anterior_id")
	private EmpresaFilial empresaAnterior;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lotacao_anterior_id")
	private Lotacao lotacaoAnterior;

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

	public EmpresaFilial getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaFilial empresa) {
		this.empresa = empresa;
	}

	public Lotacao getLotacao() {
		return lotacao;
	}

	public void setLotacao(Lotacao lotacao) {
		this.lotacao = lotacao;
	}

	public EmpresaFilial getEmpresaAnterior() {
		return empresaAnterior;
	}

	public void setEmpresaAnterior(EmpresaFilial empresaAnterior) {
		this.empresaAnterior = empresaAnterior;
	}

	public Lotacao getLotacaoAnterior() {
		return lotacaoAnterior;
	}

	public void setLotacaoAnterior(Lotacao lotacaoAnterior) {
		this.lotacaoAnterior = lotacaoAnterior;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}
