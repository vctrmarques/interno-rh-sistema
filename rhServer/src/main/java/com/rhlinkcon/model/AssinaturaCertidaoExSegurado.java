package com.rhlinkcon.model;

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
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.certidaoExSegurado.AssinaturaCertidaoExSeguradoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Certificado Ex Segurado Assinatura")
@Table(name = "certidao_ex_segurado_assinatura")
public class AssinaturaCertidaoExSegurado extends UserDateAudit {

	private static final long serialVersionUID = -5688250058470832082L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_funcionario")
	@NotNull
	private Funcionario funcionario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_ex_segurado_id")
	@NotNull
	private CertidaoExSegurado certidaoExSegurado;

	@Column(name = "funcao")
	@Enumerated(EnumType.STRING)
	private FuncaoDeclaracaoAposentadoriaEnum funcaoAssinaturaCertidao;

	@Column(name = "aba")
	@Enumerated(EnumType.STRING)
	private AbaAssinaturaCertidao abaAssinaturaCertidao;

	public AssinaturaCertidaoExSegurado() {
	}

	public AssinaturaCertidaoExSegurado(AssinaturaCertidaoExSeguradoRequest assinaturaCertidaoExSeguradoRequest) {
		this.setId(assinaturaCertidaoExSeguradoRequest.getId());
		this.setFuncionario(new Funcionario(assinaturaCertidaoExSeguradoRequest.getFuncionarioId()));
		this.setCertidaoExSegurado(
				new CertidaoExSegurado(assinaturaCertidaoExSeguradoRequest.getCertidaoExSeguradoId()));
		this.setFuncaoAssinaturaCertidao(FuncaoDeclaracaoAposentadoriaEnum
				.getEnumByString(assinaturaCertidaoExSeguradoRequest.getFuncaoAssinaturaCertidao()));
		this.setAbaAssinaturaCertidao(
				AbaAssinaturaCertidao.getEnumByString(assinaturaCertidaoExSeguradoRequest.getAbaAssinaturaCertidao()));
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

	public CertidaoExSegurado getCertidaoExSegurado() {
		return certidaoExSegurado;
	}

	public void setCertidaoExSegurado(CertidaoExSegurado certidaoExSegurado) {
		this.certidaoExSegurado = certidaoExSegurado;
	}

	public FuncaoDeclaracaoAposentadoriaEnum getFuncaoAssinaturaCertidao() {
		return funcaoAssinaturaCertidao;
	}

	public void setFuncaoAssinaturaCertidao(FuncaoDeclaracaoAposentadoriaEnum funcaoAssinaturaCertidao) {
		this.funcaoAssinaturaCertidao = funcaoAssinaturaCertidao;
	}

	public AbaAssinaturaCertidao getAbaAssinaturaCertidao() {
		return abaAssinaturaCertidao;
	}

	public void setAbaAssinaturaCertidao(AbaAssinaturaCertidao abaAssinaturaCertidao) {
		this.abaAssinaturaCertidao = abaAssinaturaCertidao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}