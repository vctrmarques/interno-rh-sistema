package com.rhlinkcon.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.simuladorNivelSalarial.SimuladorNivelSalarialRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Simulador de Nível Salarial")
@Table(name = "simulador_nivel_salarial")
public class SimuladorNivelSalarial extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8770252859250324943L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	@Column(name = "descricao")
	private String descricao;

	@Column(name = "data_competencia")
	private Instant dataCompetencia;

	@Enumerated(EnumType.STRING)
	@Column(name = "situacao")
	private SituacaoSimuladorNivelSalarialEnum situacao;

	@Column(name = "programar_ajuste")
	private boolean programarAjuste;

	@Enumerated(EnumType.STRING)
	@Column(name = "motivo_ajuste")
	private MotivoAjusteSalarialEnum motivoAjuste;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_ajuste")
	private TipoAjusteSalarialEnum tipoAjuste;

	@NotNull
	@Column(name = "valor_ajuste")
	private Double valorAjuste;

	@Column(name = "data_ajuste")
	private Instant dataAjuste;

	public SimuladorNivelSalarial() {
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

	public Instant getDataCompetencia() {
		return dataCompetencia;
	}

	public void setDataCompetencia(Instant dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}

	public SituacaoSimuladorNivelSalarialEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoSimuladorNivelSalarialEnum situacao) {
		this.situacao = situacao;
	}

	public boolean isProgramarAjuste() {
		return programarAjuste;
	}

	public void setProgramarAjuste(boolean programarAjuste) {
		this.programarAjuste = programarAjuste;
	}

	public MotivoAjusteSalarialEnum getMotivoAjuste() {
		return motivoAjuste;
	}

	public void setMotivoAjuste(MotivoAjusteSalarialEnum motivoAjuste) {
		this.motivoAjuste = motivoAjuste;
	}

	public TipoAjusteSalarialEnum getTipoAjuste() {
		return tipoAjuste;
	}

	public void setTipoAjuste(TipoAjusteSalarialEnum tipoAjuste) {
		this.tipoAjuste = tipoAjuste;
	}

	public Double getValorAjuste() {
		return valorAjuste;
	}

	public void setValorAjuste(Double valorAjuste) {
		this.valorAjuste = valorAjuste;
	}

	public Instant getDataAjuste() {
		return dataAjuste;
	}

	public void setDataAjuste(Instant dataAjuste) {
		this.dataAjuste = dataAjuste;
	}

	public void populate(SimuladorNivelSalarialRequest request) {
		this.setDescricao(request.getDescricao());

		this.setProgramarAjuste(request.isProgramarAjuste());

		if (this.isProgramarAjuste()) {
			this.setSituacao(SituacaoSimuladorNivelSalarialEnum.PROGRAMADO);
		} else {
			this.setSituacao(SituacaoSimuladorNivelSalarialEnum.NAO_PROGRAMADO);
		}

		this.setDataCompetencia(request.getDataCompetencia());
		this.setMotivoAjuste(MotivoAjusteSalarialEnum.getEnumByString(request.getMotivoAjuste()));
		this.setTipoAjuste(TipoAjusteSalarialEnum.getEnumByString(request.getTipoAjuste()));
		this.setValorAjuste(request.getValorAjuste());
		this.setDataAjuste(request.getDataAjuste());
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
