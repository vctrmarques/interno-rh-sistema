package com.rhlinkcon.model.legislacao;

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
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Legislação Anexo")
@Table(name = "legislacao_anexo")
public class LegislacaoAnexo extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1714997887106460982L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "texto_documento_id")
	private TextoDocumento textoDocumento;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "anexo_id")
	private Anexo anexo;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legislacao_id")
	private Legislacao legislacao;

	public LegislacaoAnexo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TextoDocumento getTextoDocumento() {
		return textoDocumento;
	}

	public void setTextoDocumento(TextoDocumento textoDocumento) {
		this.textoDocumento = textoDocumento;
	}

	public Anexo getAnexo() {
		return anexo;
	}

	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}

	public Legislacao getLegislacao() {
		return legislacao;
	}

	public void setLegislacao(Legislacao legislacao) {
		this.legislacao = legislacao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
