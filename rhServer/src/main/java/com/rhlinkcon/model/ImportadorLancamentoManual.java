package com.rhlinkcon.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Importador de Lançamento Manual")
@Table(name = "importador_lancamento_manual")
public class ImportadorLancamentoManual extends UserDateAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1741945533579583006L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "folha_pagamento_id")
	private FolhaPagamento folhaPagamento;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_anexo")
	private Anexo arquivoImportacao;

	private Boolean excluido;

	public ImportadorLancamentoManual() {

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

	public Anexo getArquivoImportacao() {
		return arquivoImportacao;
	}

	public void setArquivoImportacao(Anexo arquivoImportacao) {
		this.arquivoImportacao = arquivoImportacao;
	}

	public Boolean getExcluido() {
		return excluido;
	}

	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
