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
import com.rhlinkcon.payload.certidaoCompensacaoAssinatura.CertidaoCompensacaoAssinaturaRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Certidão Compensação Assinatura")
@Table(name = "certidao_compensacao_assinatura")
public class CertidaoCompensacaoAssinatura extends UserDateAudit {

	private static final long serialVersionUID = 3174585656533268158L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_compensacao_id", nullable = false)
	private CertidaoCompensacao certidaoCompensacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", nullable = true)
	private Funcionario funcionario;

	@NotNull
	@Enumerated(EnumType.STRING)
	private FuncaoCertidaoCompensacaoEnum funcao;

	public CertidaoCompensacaoAssinatura() {
	}

	public CertidaoCompensacaoAssinatura(CertidaoCompensacaoAssinaturaRequest request) {
		this.id = request.getId();
		this.certidaoCompensacao = new CertidaoCompensacao(request.getCertidaoCompensacaoId());
		this.funcionario = new Funcionario(request.getFuncionarioId());
		this.funcao = FuncaoCertidaoCompensacaoEnum.getEnumByString(request.getFuncao());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CertidaoCompensacao getCertidaoCompensacao() {
		return certidaoCompensacao;
	}

	public void setCertidaoCompensacao(CertidaoCompensacao certidaoCompensacao) {
		this.certidaoCompensacao = certidaoCompensacao;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public FuncaoCertidaoCompensacaoEnum getFuncao() {
		return funcao;
	}

	public void setFuncao(FuncaoCertidaoCompensacaoEnum funcao) {
		this.funcao = funcao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
