package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Pensionista Verba")
@Table(name = "pensionista_verba")
public class PensionistaVerba extends UserDateAudit {

	private static final long serialVersionUID = -5148181854064199752L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "pensionista_id")
	private Pensionista pensionista;

	@ManyToOne
	@JoinColumn(name = "verba_id")
	private Verba verba;

	@Column(name = "valor")
	private Double valor;
	
	@NotNull
	@Column(name = "ativo")
	private Boolean ativo;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_valor")
	private TipoValorEnum tipoValor;

	@Enumerated(EnumType.STRING)
	@Column(name = "recorrencia")
	private RecorrenciaEnum recorrencia;

	@Column(name = "parcelas")
	private Integer parcelas;

	@Column(name = "parcelas_pagas")
	private Integer parcelasPagas;

	// TODO ANALISAR
	@Transient
	private Boolean verbaManual;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pensionista getPensionista() {
		return pensionista;
	}

	public void setPensionista(Pensionista pensaoPrevidenciaria) {
		this.pensionista = pensaoPrevidenciaria;
	}

	public Verba getVerba() {
		return verba;
	}

	public void setVerba(Verba verba) {
		this.verba = verba;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public TipoValorEnum getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(TipoValorEnum tipoValor) {
		this.tipoValor = tipoValor;
	}

	public RecorrenciaEnum getRecorrencia() {
		return recorrencia;
	}

	public void setRecorrencia(RecorrenciaEnum recorrencia) {
		this.recorrencia = recorrencia;
	}

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}

	public Integer getParcelasPagas() {
		return parcelasPagas;
	}

	public void setParcelasPagas(Integer parcelasPagas) {
		this.parcelasPagas = parcelasPagas;
	}

	public Boolean getVerbaManual() {
		return verbaManual;
	}

	public void setVerbaManual(Boolean verbaManual) {
		this.verbaManual = verbaManual;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
