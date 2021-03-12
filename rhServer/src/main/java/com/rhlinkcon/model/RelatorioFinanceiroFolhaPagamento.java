package com.rhlinkcon.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.UserDateAudit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "relatorio_financeiro_folha_pagamento")
@Getter
@Setter
public class RelatorioFinanceiroFolhaPagamento extends UserDateAudit {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name="descricao")
	private String descricao;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "anexo_id")
	private Anexo anexo;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "folha_competencia_id")
	private FolhaCompetencia folhaCompetencia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "historico_atual_id")
	private RelatorioFinanceiroFolhaPagamentoHistorico historicoAtual;
	
	@OneToMany(mappedBy = "relatorioFinanceiroFolhaPagamento", cascade=CascadeType.ALL)
	private List<RelatorioFinanceiroFolhaPagamentoHistorico> historicos;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "relatorio_financeiro_folha_pagamento_filial", joinColumns = @JoinColumn(name = "relatorio_financeiro_folha_pagamento_id"), inverseJoinColumns = @JoinColumn(name = "empresa_filial_id"))
	private Set<EmpresaFilial> filiais = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "relatorio_financeiro_folha_pagamento_situacao_funcional", joinColumns = @JoinColumn(name = "relatorio_financeiro_folha_pagamento_id"), inverseJoinColumns = @JoinColumn(name = "situacao_funcional_id"))
	private Set<SituacaoFuncional> situacoesFuncionais = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "relatorio_financeiro_folha_pagamento_lotacao", joinColumns = @JoinColumn(name = "relatorio_financeiro_folha_pagamento_id"), inverseJoinColumns = @JoinColumn(name = "lotacao_id"))
	private Set<Lotacao> lotacoes = new HashSet<>();
	
	public List<Long> getFiliaisId(){
		return getFiliais().stream().map(f -> f.getId()).collect(Collectors.toList());
	}
	
	public List<Long> getSituacoesFuncionaisId(){
		return getSituacoesFuncionais().stream().map(sf -> sf.getId()).collect(Collectors.toList());
	}

	public List<Long> getLotacoesId(){
		return getLotacoes().stream().map(l -> l.getId()).collect(Collectors.toList());
	}
	
	public List<String> getLotacoesString(){
		return getLotacoes().stream().map(l -> l.getDescricao()).collect(Collectors.toList());
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}
