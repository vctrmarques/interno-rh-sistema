package com.rhlinkcon.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.equipamentoProtecaoColetiva.EquipamentoProtecaoColetivaRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Equipamento de Proteção Coletiva")
@Table(name = "equipamento_protecao_coletiva")
public class EquipamentoProtecaoColetiva extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "tipoProtecao")
	private String tipoProtecao;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@Column(name = "validade")
	private Instant validade;

	@NotNull
	@Column(name = "certificado")
	private String certificado;

	@NotNull
	@Column(name = "referencia")
	private String referencia;

	private Integer minimo;

	private Integer livre;

	private Integer atual;

	public EquipamentoProtecaoColetiva() {
	}

	public EquipamentoProtecaoColetiva(EquipamentoProtecaoColetivaRequest equipamentoProtecaoColetivaRequest) {
		this.setId(equipamentoProtecaoColetivaRequest.getId());
		this.setCodigo(equipamentoProtecaoColetivaRequest.getCodigo());
		this.setTipoProtecao(equipamentoProtecaoColetivaRequest.getTipoProtecao());
		this.setDescricao(equipamentoProtecaoColetivaRequest.getDescricao());
		this.setValidade(equipamentoProtecaoColetivaRequest.getValidade());
		this.setCertificado(equipamentoProtecaoColetivaRequest.getCertificado());
		this.setReferencia(equipamentoProtecaoColetivaRequest.getReferencia());
		this.setMinimo(equipamentoProtecaoColetivaRequest.getMinimo());
		this.setLivre(equipamentoProtecaoColetivaRequest.getLivre());
		this.setAtual(equipamentoProtecaoColetivaRequest.getAtual());
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

	public String getTipoProtecao() {
		return tipoProtecao;
	}

	public void setTipoProtecao(String tipoProtecao) {
		this.tipoProtecao = tipoProtecao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Instant getValidade() {
		return validade;
	}

	public void setValidade(Instant validade) {
		this.validade = validade;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Integer getMinimo() {
		return minimo;
	}

	public void setMinimo(Integer minimo) {
		this.minimo = minimo;
	}

	public Integer getLivre() {
		return livre;
	}

	public void setLivre(Integer livre) {
		this.livre = livre;
	}

	public Integer getAtual() {
		return atual;
	}

	public void setAtual(Integer atual) {
		this.atual = atual;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}