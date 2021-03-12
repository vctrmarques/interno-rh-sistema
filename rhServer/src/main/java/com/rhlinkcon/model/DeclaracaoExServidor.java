package com.rhlinkcon.model;

import java.util.Set;

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

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Declaração Ex Servidor")
@Table(name = "declaracao_ex_servidor")
public class DeclaracaoExServidor extends UserDateAudit {

	private static final long serialVersionUID = -8277110200832292910L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", nullable = true)
	private Funcionario funcionario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "responsavel_id", nullable = true)
	private Funcionario responsavel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dirigente_id", nullable = true)
	private Funcionario dirigente;

	@Column(name = "status_atual")
	@Enumerated(EnumType.STRING)
	private StatusDeclaracaoExServidorEnum status;

	@OneToMany(mappedBy = "declaracaoExServidor")
	private Set<DeclaracaoExServidorDadosFuncionais> dadosFuncionais;

	public DeclaracaoExServidor() {
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

	public Funcionario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Funcionario responsavel) {
		this.responsavel = responsavel;
	}

	public Funcionario getDirigente() {
		return dirigente;
	}

	public void setDirigente(Funcionario dirigente) {
		this.dirigente = dirigente;
	}

	public StatusDeclaracaoExServidorEnum getStatus() {
		return status;
	}

	public void setStatus(StatusDeclaracaoExServidorEnum status) {
		this.status = status;
	}

	public Set<DeclaracaoExServidorDadosFuncionais> getDadosFuncionais() {
		return dadosFuncionais;
	}

	public void setDadosFuncionais(Set<DeclaracaoExServidorDadosFuncionais> dadosFuncionais) {
		this.dadosFuncionais = dadosFuncionais;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
