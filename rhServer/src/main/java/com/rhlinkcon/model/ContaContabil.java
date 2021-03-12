package com.rhlinkcon.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import com.rhlinkcon.payload.contaContabil.ContaContabilRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Conta Contábil")
@Table(name = "conta_contabil")
public class ContaContabil extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")
	private EmpresaFilial empresa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_verba")
	private Verba verba;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "filial_id")
	private EmpresaFilial filial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "centro_custo_id")
	private CentroCusto centroCusto;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_conta")
	private TipoContaLotacaoEnum tipoConta;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "conta_contabil_lotacao", joinColumns = @JoinColumn(name = "conta_contabil_id"), inverseJoinColumns = @JoinColumn(name = "lotacao_id"))
	private Set<Lotacao> lotacoes = new HashSet<>();

	@NotNull
	@Column(name = "conta")
	private Integer conta;

	@NotNull
	@Column(name = "rateio")
	private Double rateio;

	@NotNull
	@Column(name = "rateio_total")
	private Double rateioTotal;

	public ContaContabil() {

	}

	public ContaContabil(ContaContabilRequest contaContabilRequest) {
		this.setTipoConta(TipoContaLotacaoEnum.valueOf(contaContabilRequest.getTipoConta()));
		this.setConta(contaContabilRequest.getConta());
		this.setRateio(contaContabilRequest.getRateio());
		this.setRateioTotal(contaContabilRequest.getRateioTotal());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmpresaFilial getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaFilial empresa) {
		this.empresa = empresa;
	}

	public Verba getVerba() {
		return verba;
	}

	public void setVerba(Verba verba) {
		this.verba = verba;
	}

	public EmpresaFilial getFilial() {
		return filial;
	}

	public void setFilial(EmpresaFilial filial) {
		this.filial = filial;
	}

	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public TipoContaLotacaoEnum getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoContaLotacaoEnum tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Set<Lotacao> getLotacoes() {
		return lotacoes;
	}

	public void setLotacoes(Set<Lotacao> lotacoes) {
		this.lotacoes = lotacoes;
	}

	public Integer getConta() {
		return conta;
	}

	public void setConta(Integer conta) {
		this.conta = conta;
	}

	public Double getRateio() {
		return rateio;
	}

	public void setRateio(Double rateio) {
		this.rateio = rateio;
	}

	public Double getRateioTotal() {
		return rateioTotal;
	}

	public void setRateioTotal(Double rateioTotal) {
		this.rateioTotal = rateioTotal;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
