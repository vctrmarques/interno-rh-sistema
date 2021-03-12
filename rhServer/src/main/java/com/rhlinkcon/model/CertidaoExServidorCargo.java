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

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.certidaoExSegurado.CertidaoExServidorCargoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Certidão Ex Segurado Cargos")
@Table(name = "certidao_ex_servidor_cargos")
public class CertidaoExServidorCargo extends UserDateAudit {

	private static final long serialVersionUID = 7369751155743241537L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_ex_servidor_id")
	private CertidaoExSegurado certidaoExServidor;

	@Column(name = "descricao_cargo")
	private String descricaoCargo;

	public CertidaoExServidorCargo() {
	}

	public CertidaoExServidorCargo(CertidaoExServidorCargoRequest request) {
		this.id = request.getId();
		this.certidaoExServidor = new CertidaoExSegurado(request.getCertidaoExSeguradoId());
		this.descricaoCargo = request.getDescricaoCargo();
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

	public String getDescricaoCargo() {
		return descricaoCargo;
	}

	public void setDescricaoCargo(String descricaoCargo) {
		this.descricaoCargo = descricaoCargo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
