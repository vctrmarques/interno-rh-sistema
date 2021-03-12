package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.motivoAfastamento.MotivoAfastamentoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Motivo de Afastamento")
@Table(name = "motivo_afastamento")
public class MotivoAfastamento extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "disponivel_para_pericia")
	private boolean disponivelParaPericia;

	public MotivoAfastamento() {
	}

	public MotivoAfastamento(Long id) {
		this.id = id;
	}

	public MotivoAfastamento(MotivoAfastamentoRequest motivoAfastamentoRequest) {
		this.setCodigo(motivoAfastamentoRequest.getCodigo());
		this.setDescricao(motivoAfastamentoRequest.getDescricao());
		this.setDisponivelParaPericia(motivoAfastamentoRequest.isDisponivelParaPericia());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public boolean isDisponivelParaPericia() {
		return disponivelParaPericia;
	}

	public void setDisponivelParaPericia(boolean disponivelParaPericia) {
		this.disponivelParaPericia = disponivelParaPericia;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}