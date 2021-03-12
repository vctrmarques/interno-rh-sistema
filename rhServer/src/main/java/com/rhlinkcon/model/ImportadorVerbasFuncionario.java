package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Importador de Verbas de Funcionário")
@Table(name = "importador_verbas_funcionario")
public class ImportadorVerbasFuncionario extends UserDateAudit {

	private static final long serialVersionUID = 1911010862993070308L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "anexo_arquivo_importacao_id")
	private Anexo arquivoImportacao;

	@NotNull
	@Column(name = "excluido")
	private Boolean excluido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
