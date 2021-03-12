package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.rhlinkcon.audit.UserDateAudit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "relatorio_financeiro_folha_pagamento_historico")
public class RelatorioFinanceiroFolhaPagamentoHistorico extends UserDateAudit{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2397312125317880535L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "relatorio_financeiro_folha_pagamento_id")
	private RelatorioFinanceiroFolhaPagamento relatorioFinanceiroFolhaPagamento;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusRelatorioFinanceiroEnum status;

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
