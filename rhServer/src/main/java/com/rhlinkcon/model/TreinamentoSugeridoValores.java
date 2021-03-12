package com.rhlinkcon.model;

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

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.treinamentoSugeridoValores.TreinamentoSugeridoValoresRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Treinamento Sugerido Valores")
@Table(name = "treinamento_sugerido_valores")
public class TreinamentoSugeridoValores extends UserDateAudit {

	private static final long serialVersionUID = 8200071127439078282L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "id_treinamento_sugerido", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private TreinamentoSugerido treinamentoSugerido;

	@Column(name = "valor_docencia")
	private Double valorDocencia;

	@Column(name = "valor_conducao")
	private Double valorConducao;

	@Column(name = "valor_diarias")
	private Double valorDiarias;

	@Column(name = "valor_material")
	private Double valorMaterial;

	@Column(name = "valor_hospedagem")
	private Double valorHospedagem;

	@Column(name = "valor_individual")
	private Double valorIndividual;

	public TreinamentoSugeridoValores() {

	}

	public TreinamentoSugeridoValores(TreinamentoSugeridoValoresRequest request) {
		this.id = request.getId();
		this.treinamentoSugerido = new TreinamentoSugerido(request.getTreinamentoSugeridoId());
		this.valorDocencia = request.getValorDocencia();
		this.valorConducao = request.getValorConducao();
		this.valorDiarias = request.getValorDiarias();
		this.valorMaterial = request.getValorMaterial();
		this.valorHospedagem = request.getValorHospedagem();
		this.valorIndividual = request.getValorIndividual();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TreinamentoSugerido getTreinamentoSugerido() {
		return treinamentoSugerido;
	}

	public void setTreinamentoSugerido(TreinamentoSugerido treinamentoSugerido) {
		this.treinamentoSugerido = treinamentoSugerido;
	}

	public Double getValorDocencia() {
		return valorDocencia;
	}

	public void setValorDocencia(Double valorDocencia) {
		this.valorDocencia = valorDocencia;
	}

	public Double getValorConducao() {
		return valorConducao;
	}

	public void setValorConducao(Double valorConducao) {
		this.valorConducao = valorConducao;
	}

	public Double getValorDiarias() {
		return valorDiarias;
	}

	public void setValorDiarias(Double valorDiarias) {
		this.valorDiarias = valorDiarias;
	}

	public Double getValorMaterial() {
		return valorMaterial;
	}

	public void setValorMaterial(Double valorMaterial) {
		this.valorMaterial = valorMaterial;
	}

	public Double getValorHospedagem() {
		return valorHospedagem;
	}

	public void setValorHospedagem(Double valorHospedagem) {
		this.valorHospedagem = valorHospedagem;
	}

	public Double getValorIndividual() {
		return valorIndividual;
	}

	public void setValorIndividual(Double valorIndividual) {
		this.valorIndividual = valorIndividual;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
