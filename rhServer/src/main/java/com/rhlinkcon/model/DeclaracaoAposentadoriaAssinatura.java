package com.rhlinkcon.model;

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
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.declaracaoAposentadoriaAssinatura.DeclaracaoAposentadoriaAssinaturaRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Declaração Aposentadoria Assinatura")
@Table(name = "declaracao_aposentadoria_assinatura")
public class DeclaracaoAposentadoriaAssinatura extends UserDateAudit {

	private static final long serialVersionUID = 693151167983277257L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "declaracao_aposentadoria_id", nullable = false)
	private DeclaracaoAposentadoria declaracaoAposentadoria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", nullable = true)
	private Funcionario funcionario;

	@NotNull
	@Enumerated(EnumType.STRING)
	private FuncaoDeclaracaoAposentadoriaEnum funcao;

	public DeclaracaoAposentadoriaAssinatura() {
	}

	public DeclaracaoAposentadoriaAssinatura(DeclaracaoAposentadoriaAssinaturaRequest request) {
		this.id = request.getId();
		this.declaracaoAposentadoria = new DeclaracaoAposentadoria(request.getDeclaracaoAposentadoriaId());
		this.funcionario = new Funcionario(request.getFuncionarioId());
		this.funcao = FuncaoDeclaracaoAposentadoriaEnum.getEnumByString(request.getFuncao());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeclaracaoAposentadoria getDeclaracaoAposentadoria() {
		return declaracaoAposentadoria;
	}

	public void setDeclaracaoAposentadoria(DeclaracaoAposentadoria declaracaoAposentadoria) {
		this.declaracaoAposentadoria = declaracaoAposentadoria;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public FuncaoDeclaracaoAposentadoriaEnum getFuncao() {
		return funcao;
	}

	public void setFuncao(FuncaoDeclaracaoAposentadoriaEnum funcao) {
		this.funcao = funcao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
