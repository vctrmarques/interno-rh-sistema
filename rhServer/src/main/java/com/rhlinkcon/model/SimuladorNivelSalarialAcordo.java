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
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.simuladorNivelSalarial.SimuladorNivelSalarialAcordoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Simulador de Nível Salarial Acordo")
@Table(name = "simulador_nivel_salarial_acordo")
public class SimuladorNivelSalarialAcordo extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8770252859250324943L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "simulador_id")
	private SimuladorNivelSalarial simulador;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_acordo")
	private TipoAcordoAjusteSalarialEnum tipo;

	@Column(name = "data_acordo")
	private Instant dataAcordo;

	@NotNull
	@Column(name = "data_incial")
	private Instant dataIncial;

	@NotNull
	@Column(name = "data_final")
	private Instant dataFinal;

	@Column(name = "descricao")
	private String descricao;

	public SimuladorNivelSalarialAcordo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SimuladorNivelSalarial getSimulador() {
		return simulador;
	}

	public void setSimulador(SimuladorNivelSalarial simulador) {
		this.simulador = simulador;
	}

	public TipoAcordoAjusteSalarialEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoAcordoAjusteSalarialEnum tipo) {
		this.tipo = tipo;
	}

	public Instant getDataAcordo() {
		return dataAcordo;
	}

	public void setDataAcordo(Instant dataAcordo) {
		this.dataAcordo = dataAcordo;
	}

	public Instant getDataIncial() {
		return dataIncial;
	}

	public void setDataIncial(Instant dataIncial) {
		this.dataIncial = dataIncial;
	}

	public Instant getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Instant dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void populate(SimuladorNivelSalarialAcordoRequest request) {
		setTipo(TipoAcordoAjusteSalarialEnum.getEnumByString(request.getTipo()));
		setDataAcordo(request.getDataAcordo());
		setDataIncial(request.getDataInicial());
		setDataFinal(request.getDataFinal());
		setDescricao(request.getDescricao());
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
