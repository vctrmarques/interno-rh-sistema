package com.rhlinkcon.model;

import java.time.Instant;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Função Histórico Lei")
@Table(name = "funcao_historico_lei")
public class FuncaoHistoricoLei {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "numero_lei")
	private Integer numeroLei;

	@Column(name = "data_lei")
	private Instant dataLei;

	@Enumerated(EnumType.STRING)
	@Column(name = "motivo_lei")
	private MotivoLeiEnum motivoLei;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcao_id")
	private Funcao funcao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumeroLei() {
		return numeroLei;
	}

	public void setNumeroLei(Integer numeroLei) {
		this.numeroLei = numeroLei;
	}

	public Instant getDataLei() {
		return dataLei;
	}

	public void setDataLei(Instant dataLei) {
		this.dataLei = dataLei;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public MotivoLeiEnum getMotivoLei() {
		return motivoLei;
	}

	public void setMotivoLei(MotivoLeiEnum motivoLei) {
		this.motivoLei = motivoLei;
	}

}
