package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.etapa.EtapaRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Etapa")
@Table(name = "etapa")
public class Etapa extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private Integer codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "processo")
	private ProcessoEtapaEnum processo;

	public Etapa() {

	}

	public Etapa(EtapaRequest etapaRequest) {
		this.setCodigo(etapaRequest.getCodigo());
		this.setDescricao(etapaRequest.getDescricao());

		if (!etapaRequest.getProcesso().isEmpty())
			this.setProcesso(ProcessoEtapaEnum.getEnumByString(etapaRequest.getProcesso()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ProcessoEtapaEnum getProcesso() {
		return processo;
	}

	public void setProcesso(ProcessoEtapaEnum processo) {
		this.processo = processo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}