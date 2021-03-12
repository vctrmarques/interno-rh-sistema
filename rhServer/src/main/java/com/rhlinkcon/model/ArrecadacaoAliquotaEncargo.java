package com.rhlinkcon.model;

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
import com.rhlinkcon.payload.arrecadacaoAliquota.ArrecadacaoAliquotaEncargoDto;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Arrecadação Alíquota Encargo")
@Table(name = "arrecadacao_aliquota_encargo")
public class ArrecadacaoAliquotaEncargo extends UserDateAudit {

	private static final long serialVersionUID = -7795727488498862365L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "arrecadacao_aliquota_periodo_id", nullable = false)
	private ArrecadacaoAliquotaPeriodo arrecadacaoAliquotaPeriodo;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "aliquota_engargo")
	private ArrecadacaoAliquotaEncargoEnum aliquotaEncargo;

	@NotNull
	private Double aliquota;

	public ArrecadacaoAliquotaEncargo() {
	}

	public ArrecadacaoAliquotaEncargo(ArrecadacaoAliquotaEncargoDto dto, Long id) {
		setId(dto.getId());
		setArrecadacaoAliquotaPeriodo(new ArrecadacaoAliquotaPeriodo(id));
		setAliquotaEncargo(ArrecadacaoAliquotaEncargoEnum.getEnumByString(dto.getAliquotaEncargo()));
		setAliquota(dto.getAliquota());
	}

	public ArrecadacaoAliquotaEncargo(ArrecadacaoAliquotaEncargoDto dto) {
		setId(dto.getId());
		setArrecadacaoAliquotaPeriodo(new ArrecadacaoAliquotaPeriodo(dto.getPeriodoId()));
		setAliquotaEncargo(ArrecadacaoAliquotaEncargoEnum.getEnumByString(dto.getAliquotaEncargo()));
		setAliquota(dto.getAliquota());
	}

	@Override
	public String getLabel() {
		return "Arrecadação Alíquota Encargo";
	}

}
