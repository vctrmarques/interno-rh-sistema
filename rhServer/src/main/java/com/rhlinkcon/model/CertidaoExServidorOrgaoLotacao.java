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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.certidaoExSegurado.CertidaoExServidorOrgaoLotacaoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Certidão Ex Servidor Orgão Lotação")
@Table(name = "certidao_ex_servidor_orgao_lotacao")
public class CertidaoExServidorOrgaoLotacao extends UserDateAudit {

	private static final long serialVersionUID = 4364988217753672546L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_ex_servidor_id")
	@NotNull
	private CertidaoExSegurado certidaoExServidor;

	@NotNull
	@NotBlank
	@Column(name = "descricao_orgao_lotacao")
	private String descricaoOrgaoLotacao;

	public CertidaoExServidorOrgaoLotacao() {
	}

	public CertidaoExServidorOrgaoLotacao(CertidaoExServidorOrgaoLotacaoRequest request) {
		this.id = request.getId();
		this.certidaoExServidor = new CertidaoExSegurado(request.getCertidaoExSeguradoId());
		this.descricaoOrgaoLotacao = request.getDescricaoOrgaoLotacao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CertidaoExSegurado getCertidaoExServidor() {
		return certidaoExServidor;
	}

	public void setCertidaoExServidor(CertidaoExSegurado certidaoExServidor) {
		this.certidaoExServidor = certidaoExServidor;
	}

	public String getDescricaoOrgaoLotacao() {
		return descricaoOrgaoLotacao;
	}

	public void setDescricaoOrgaoLotacao(String descricaoOrgaoLotacao) {
		this.descricaoOrgaoLotacao = descricaoOrgaoLotacao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
