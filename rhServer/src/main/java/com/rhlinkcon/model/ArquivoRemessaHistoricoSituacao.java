package com.rhlinkcon.model;

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
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Arquivo de Remessa Histórico Situação")
@Table(name = "arquivo_remessa_historico_situacao")
public class ArquivoRemessaHistoricoSituacao extends UserDateAudit {

	private static final long serialVersionUID = -881054873643347120L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "arquivo_remessa_id", nullable = false)
	private ArquivoRemessaPagamento arquivoRemessa;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ArquivoRemessaPagamentoSituacaoEnum situacao;

	public ArquivoRemessaHistoricoSituacao() {
	}

	public ArquivoRemessaHistoricoSituacao(Long id) {
		setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArquivoRemessaPagamento getArquivoRemessa() {
		return arquivoRemessa;
	}

	public void setArquivoRemessa(ArquivoRemessaPagamento arquivoRemessa) {
		this.arquivoRemessa = arquivoRemessa;
	}

	public ArquivoRemessaPagamentoSituacaoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(ArquivoRemessaPagamentoSituacaoEnum situacao) {
		this.situacao = situacao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
