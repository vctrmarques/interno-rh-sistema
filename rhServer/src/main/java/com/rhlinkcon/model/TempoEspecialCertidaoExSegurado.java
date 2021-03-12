package com.rhlinkcon.model;

import java.time.Instant;
import java.util.Objects;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.certidaoExSegurado.TempoEspecialCertidaoExSeguradoRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Tempo Especial Certidão Ex Segurado")
@Table(name = "tempo_especial_certidao_ex_segurado")
public class TempoEspecialCertidaoExSegurado extends UserDateAudit {

	private static final long serialVersionUID = 3936279840891044793L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_ex_segurado_id")
	@NotNull
	private CertidaoExSegurado certidaoExSegurado;

	@Column(name = "periodo_inicial")
	@NotNull
	private Instant periodoInicial;

	@Column(name = "periodo_final")
	@NotNull
	private Instant periodoFinal;

	@Column(name = "tempo")
	private Integer tempo;

	@Column(name = "tipo_tempo_especial")
	@Enumerated(EnumType.STRING)
	private TipoTempoEspecialEnum tipoTempo;

	@Column(name = "grau")
	@Enumerated(EnumType.STRING)
	private GrauDeficienciaEnum grauDeficiencia;

	public TempoEspecialCertidaoExSegurado() {
	}

	public TempoEspecialCertidaoExSegurado(Long id) {
		this.id = id;
	}

	public TempoEspecialCertidaoExSegurado(
			TempoEspecialCertidaoExSeguradoRequest tempoEspecialCertidaoExSeguradoRequest) {
		this.setId(tempoEspecialCertidaoExSeguradoRequest.getId());
		this.setCertidaoExSegurado(
				new CertidaoExSegurado(tempoEspecialCertidaoExSeguradoRequest.getCertidaoExSeguradoId()));
		if (Objects.nonNull(tempoEspecialCertidaoExSeguradoRequest.getGrauDeficiencia())) {
			this.setGrauDeficiencia(
					GrauDeficienciaEnum.getEnumByString(tempoEspecialCertidaoExSeguradoRequest.getGrauDeficiencia()));
		}
		this.setTipoTempo(TipoTempoEspecialEnum.getEnumByString(tempoEspecialCertidaoExSeguradoRequest.getTipoTempo()));
		Utils.setValueFields(tempoEspecialCertidaoExSeguradoRequest, this, "periodoInicial", "periodoFinal", "tempo");
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

	public Instant getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Instant periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Instant getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Instant periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public TipoTempoEspecialEnum getTipoTempo() {
		return tipoTempo;
	}

	public void setTipoTempo(TipoTempoEspecialEnum tipoTempo) {
		this.tipoTempo = tipoTempo;
	}

	public GrauDeficienciaEnum getGrauDeficiencia() {
		return grauDeficiencia;
	}

	public void setGrauDeficiencia(GrauDeficienciaEnum grauDeficiencia) {
		this.grauDeficiencia = grauDeficiencia;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}