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
import com.rhlinkcon.payload.classificacaoAgenteNocivo.ClassificacaoAgenteNocivoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Classificação de Agente Nocivo")
@Table(name = "classificacao_agente_nocivo")
public class ClassificacaoAgenteNocivo extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@Column(name = "tempo_exposicao")
	private Integer tempoExposicao;

	public ClassificacaoAgenteNocivo() {
	}

	public ClassificacaoAgenteNocivo(ClassificacaoAgenteNocivoRequest classificacaoAgenteNocivoRequest) {
		this.setCodigo(classificacaoAgenteNocivoRequest.getCodigo());
		this.setDescricao(classificacaoAgenteNocivoRequest.getDescricao());
		this.setTempoExposicao(classificacaoAgenteNocivoRequest.getTempoExposicao());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getTempoExposicao() {
		return tempoExposicao;
	}

	public void setTempoExposicao(Integer tempoExposicao) {
		this.tempoExposicao = tempoExposicao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}