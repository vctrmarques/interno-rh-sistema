package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.centroCusto.CentroCustoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Centro de Custo")
@Table(name = "centro_custo")
public class CentroCusto extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@Column(name = "efetivo")
	private Integer efetivo;

	@Column(name = "nivel")
	private Integer nivel;

	@Column(name = "tipo_debito")
	private String tipoDebito;

	@Column(name = "tipo_credito")
	private String tipoCredito;

	@Column(name = "conta_credito")
	private Integer contaCredito;

	@Column(name = "conta_debito")
	private Integer contaDebito;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_centro_custo")
	private TipoCentroCustoEnum tipoCentroCusto;

	private String cnpj;

	public CentroCusto() {
	}

	public CentroCusto(Long id) {
		this.id = id;
	}

	public CentroCusto(CentroCustoRequest centroCustoRequest) {
		this.setCodigo(centroCustoRequest.getCodigo());
		this.setDescricao(centroCustoRequest.getDescricao());
		this.setEfetivo(centroCustoRequest.getEfetivo());
		this.setNivel(centroCustoRequest.getNivel());
		this.setTipoDebito(centroCustoRequest.getTipoDebito());
		this.setTipoCredito(centroCustoRequest.getTipoCredito());
		this.setContaCredito(centroCustoRequest.getContaCredito());
		this.setContaDebito(centroCustoRequest.getContaDebito());
		this.setCnpj(centroCustoRequest.getCnpj());
		if (!centroCustoRequest.getTipoCentroCusto().isEmpty())
			this.setTipoCentroCusto(TipoCentroCustoEnum.getEnumByString(centroCustoRequest.getTipoCentroCusto()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getEfetivo() {
		return efetivo;
	}

	public void setEfetivo(Integer efetivo) {
		this.efetivo = efetivo;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getTipoDebito() {
		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public Integer getContaCredito() {
		return contaCredito;
	}

	public void setContaCredito(Integer contaCredito) {
		this.contaCredito = contaCredito;
	}

	public Integer getContaDebito() {
		return contaDebito;
	}

	public void setContaDebito(Integer contaDebito) {
		this.contaDebito = contaDebito;
	}

	public TipoCentroCustoEnum getTipoCentroCusto() {
		return tipoCentroCusto;
	}

	public void setTipoCentroCusto(TipoCentroCustoEnum tipoCentroCusto) {
		this.tipoCentroCusto = tipoCentroCusto;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String getLabel() {
		return this.descricao;
	}

}