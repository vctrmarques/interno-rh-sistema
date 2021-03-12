package com.rhlinkcon.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Histórico de Declaração Aposentadoria")
@Table(name = "historico_declaracao_aposentadoria")
public class HistoricoDeclaracaoAposentadoria extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6538940963336872928L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "declaracao_aposentadoria_id")
	private DeclaracaoAposentadoria declaracaoAposentadoria;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_declaracao")
	private DeclaracaoAposentadoriaTipoEnum tipoDeclaracao;

	public HistoricoDeclaracaoAposentadoria() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeclaracaoAposentadoria getDeclaracaoAposentadoria() {
		return declaracaoAposentadoria;
	}

	public void setDeclaracaoAposentadoria(DeclaracaoAposentadoria declaracaoAposentadoria) {
		this.declaracaoAposentadoria = declaracaoAposentadoria;
	}

	public DeclaracaoAposentadoriaTipoEnum getTipoDeclaracao() {
		return tipoDeclaracao;
	}

	public void setTipoDeclaracao(DeclaracaoAposentadoriaTipoEnum tipoDeclaracao) {
		this.tipoDeclaracao = tipoDeclaracao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
