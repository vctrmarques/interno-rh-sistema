package com.rhlinkcon.model;

import java.time.Instant;

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

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.certidaoExSegurado.FrequenciaCertidaoExServidorDetalhamentoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Frequência Certidão Ex Servidor Detalhamento")
@Table(name = "frequencia_certidao_ex_servidor_detalhamento")
public class FrequenciaCertidaoExServidorDetalhamento extends UserDateAudit {

	private static final long serialVersionUID = -270588031739750884L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_ex_servidor_id")
	private CertidaoExSegurado certidaoExSegurado;

	@Column(name = "periodo_inicio")
	private Instant periodoInicio;

	@Column(name = "periodo_final")
	private Instant periodoFinal;

	@Column(name = "tempo")
	private Integer tempo;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	private TipoDetalhamentoFrequenciaEnum tipo;

	public FrequenciaCertidaoExServidorDetalhamento() {
	}

	public FrequenciaCertidaoExServidorDetalhamento(FrequenciaCertidaoExServidorDetalhamentoRequest request) {
		setId(request.getId());
		setCertidaoExSegurado(new CertidaoExSegurado(request.getCertidaoExSeguradoId()));
		setPeriodoInicio(request.getPeriodoInicio());
		setPeriodoFinal(request.getPeriodoFinal());
		setTempo(request.getTempo());
		setDescricao(request.getDescricao());
		setTipo(TipoDetalhamentoFrequenciaEnum.getEnumByString(request.getTipo()));
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

	public Instant getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(Instant periodoInicio) {
		this.periodoInicio = periodoInicio;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoDetalhamentoFrequenciaEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoDetalhamentoFrequenciaEnum tipo) {
		this.tipo = tipo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
