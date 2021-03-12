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
import com.rhlinkcon.payload.tipoFerias.TipoFeriasRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Tipo de Férias")
@Table(name = "tipo_ferias")
public class TipoFerias extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@Column(name = "abono")
	private Integer abono;

	@NotNull
	@Column(name = "dias")
	private Integer dias;

	@NotNull
	@Column(name = "saldo")
	private Integer saldo;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "decimo_terceiro")
	private DecimoTerceiroEnum decimoTerceiro;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "coletivo")
	private ColetivoEnum coletivo;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "licenca_premio")
	private LicencaPremioEnum licencaPremio;

	public TipoFerias() {
	}

	public TipoFerias(TipoFeriasRequest tipoFeriasRequest) {

		this.setDescricao(tipoFeriasRequest.getDescricao());
		this.setAbono(tipoFeriasRequest.getAbono());
		this.setDias(tipoFeriasRequest.getDias());
		this.setSaldo(tipoFeriasRequest.getSaldo());
		this.setDecimoTerceiro(DecimoTerceiroEnum.getEnumByString(tipoFeriasRequest.getDecimoTerceiro()));
		this.setColetivo(ColetivoEnum.getEnumByString(tipoFeriasRequest.getColetivo()));
		this.setLicencaPremio(LicencaPremioEnum.getEnumByString(tipoFeriasRequest.getLicencaPremio()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getAbono() {
		return abono;
	}

	public void setAbono(Integer abono) {
		this.abono = abono;
	}

	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}

	public Integer getSaldo() {
		return saldo;
	}

	public void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}

	public DecimoTerceiroEnum getDecimoTerceiro() {
		return decimoTerceiro;
	}

	public void setDecimoTerceiro(DecimoTerceiroEnum decimoTerceiro) {
		this.decimoTerceiro = decimoTerceiro;
	}

	public ColetivoEnum getColetivo() {
		return coletivo;
	}

	public void setColetivo(ColetivoEnum coletivo) {
		this.coletivo = coletivo;
	}

	public LicencaPremioEnum getLicencaPremio() {
		return licencaPremio;
	}

	public void setLicencaPremio(LicencaPremioEnum licencaPremio) {
		this.licencaPremio = licencaPremio;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}