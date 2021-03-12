package com.rhlinkcon.model;

import java.time.LocalDate;
import java.util.List;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Arquivo de Remessa Pagamento")
@Table(name = "arquivo_remessa_pagamento")
public class ArquivoRemessaPagamento extends UserDateAudit {

	private static final long serialVersionUID = -481917445612536732L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "folha_pagamento_id", nullable = false)
	private FolhaPagamento folhaPagamento;

	@NotNull
	@Column(name = "numero_remessa")
	private Integer numeroRemessa;

	@NotNull
	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ArquivoRemessaPagamentoSituacaoEnum situacao;

	private String motivo;

	@ManyToMany
	@JoinTable(name = "arquivo_remessa_pagamento_anexo", joinColumns = {
			@JoinColumn(name = "arquivo_remessa_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "anexo_id", referencedColumnName = "id") })
	private List<Anexo> anexos;

	public ArquivoRemessaPagamento() {
	}

	public ArquivoRemessaPagamento(Long id) {
		setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FolhaPagamento getFolhaPagamento() {
		return folhaPagamento;
	}

	public void setFolhaPagamento(FolhaPagamento folhaPagamento) {
		this.folhaPagamento = folhaPagamento;
	}

	public Integer getNumeroRemessa() {
		return numeroRemessa;
	}

	public void setNumeroRemessa(Integer numeroRemessa) {
		this.numeroRemessa = numeroRemessa;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public ArquivoRemessaPagamentoSituacaoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(ArquivoRemessaPagamentoSituacaoEnum situacao) {
		this.situacao = situacao;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public List<Anexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
