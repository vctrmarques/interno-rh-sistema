package com.rhlinkcon.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import com.rhlinkcon.payload.certidaoExSegurado.RelacaoRemuneracaoCertidaoExSeguradoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Relação Remuneração Certidão Ex Segurado")
@Table(name = "relacao_remuneracao_certidao_ex_segurado")
public class RelacaoRemuneracaoCertidaoExSegurado extends UserDateAudit {

	private static final long serialVersionUID = 1954543978815301672L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_ex_segurado_id")
	@NotNull
	private CertidaoExSegurado certidaoExSegurado;

	@Column(name = "ano")
	@NotNull
	private Integer ano;

	@Column(name = "janeiro")
	private BigDecimal janeiro;

	@Column(name = "fevereiro")
	private BigDecimal fevereiro;

	@Column(name = "marco")
	private BigDecimal marco;

	@Column(name = "abril")
	private BigDecimal abril;

	@Column(name = "maio")
	private BigDecimal maio;

	@Column(name = "junho")
	private BigDecimal junho;

	@Column(name = "julho")
	private BigDecimal julho;

	@Column(name = "agosto")
	private BigDecimal agosto;

	@Column(name = "setembro")
	private BigDecimal setembro;

	@Column(name = "outubro")
	private BigDecimal outubro;

	@Column(name = "novembro")
	private BigDecimal novembro;

	@Column(name = "dezembro")
	private BigDecimal dezembro;

	@Column(name = "decimo_terceiro")
	private BigDecimal decimoTerceiro;

	public RelacaoRemuneracaoCertidaoExSegurado() {
	}

	public RelacaoRemuneracaoCertidaoExSegurado(Long id) {
		this.id = id;
	}

	public RelacaoRemuneracaoCertidaoExSegurado(RelacaoRemuneracaoCertidaoExSeguradoRequest request) {
		this.setId(request.getId());
		this.setCertidaoExSegurado(new CertidaoExSegurado(request.getCertidaoExSeguradoId()));
		this.setAno(request.getAno());
		this.setJaneiro(request.getJaneiro());
		this.setFevereiro(request.getFevereiro());
		this.setMarco(request.getMarco());
		this.setAbril(request.getAbril());
		this.setMaio(request.getMaio());
		this.setJunho(request.getJunho());
		this.setJulho(request.getJulho());
		this.setAgosto(request.getAgosto());
		this.setSetembro(request.getSetembro());
		this.setOutubro(request.getOutubro());
		this.setNovembro(request.getNovembro());
		this.setDezembro(request.getDezembro());
		this.setDecimoTerceiro(request.getDecimoTerceiro());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CertidaoExSegurado getCertidaoExSegurado() {
		return certidaoExSegurado;
	}

	public void setCertidaoExSegurado(CertidaoExSegurado certidaoExSegurado) {
		this.certidaoExSegurado = certidaoExSegurado;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public BigDecimal getJaneiro() {
		return janeiro;
	}

	public void setJaneiro(BigDecimal janeiro) {
		this.janeiro = janeiro;
	}

	public BigDecimal getFevereiro() {
		return fevereiro;
	}

	public void setFevereiro(BigDecimal fevereiro) {
		this.fevereiro = fevereiro;
	}

	public BigDecimal getMarco() {
		return marco;
	}

	public void setMarco(BigDecimal marco) {
		this.marco = marco;
	}

	public BigDecimal getAbril() {
		return abril;
	}

	public void setAbril(BigDecimal abril) {
		this.abril = abril;
	}

	public BigDecimal getMaio() {
		return maio;
	}

	public void setMaio(BigDecimal maio) {
		this.maio = maio;
	}

	public BigDecimal getJunho() {
		return junho;
	}

	public void setJunho(BigDecimal junho) {
		this.junho = junho;
	}

	public BigDecimal getJulho() {
		return julho;
	}

	public void setJulho(BigDecimal julho) {
		this.julho = julho;
	}

	public BigDecimal getAgosto() {
		return agosto;
	}

	public void setAgosto(BigDecimal agosto) {
		this.agosto = agosto;
	}

	public BigDecimal getSetembro() {
		return setembro;
	}

	public void setSetembro(BigDecimal setembro) {
		this.setembro = setembro;
	}

	public BigDecimal getOutubro() {
		return outubro;
	}

	public void setOutubro(BigDecimal outubro) {
		this.outubro = outubro;
	}

	public BigDecimal getNovembro() {
		return novembro;
	}

	public void setNovembro(BigDecimal novembro) {
		this.novembro = novembro;
	}

	public BigDecimal getDezembro() {
		return dezembro;
	}

	public void setDezembro(BigDecimal dezembro) {
		this.dezembro = dezembro;
	}

	public BigDecimal getDecimoTerceiro() {
		return decimoTerceiro;
	}

	public void setDecimoTerceiro(BigDecimal decimoTerceiro) {
		this.decimoTerceiro = decimoTerceiro;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}