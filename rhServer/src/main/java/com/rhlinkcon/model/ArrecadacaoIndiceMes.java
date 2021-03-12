package com.rhlinkcon.model;

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
import com.rhlinkcon.service.ArrecadacaoIndiceMesDto;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Arrecadação Índices Mês Valor Período")
@Table(name = "arrecadacao_indice_mes_valor")
public class ArrecadacaoIndiceMes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	private MesEnum mes;

	@NotNull
	private Double aliquota;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "arrecadacao_indice_ano_periodicidade_id", nullable = false)
	private ArrecadacaoIndiceAnoPeriodicidade arrecadacaoIndiceAnoPeriodicidade;
	
	public ArrecadacaoIndiceMes() {}
	
	public ArrecadacaoIndiceMes(ArrecadacaoIndiceMesDto dto, Long id) {
		setId(dto.getId());
		setMes(MesEnum.getEnumByLabel(dto.getMes()));
		setAliquota(dto.getAliquota());
		setArrecadacaoIndiceAnoPeriodicidade(new ArrecadacaoIndiceAnoPeriodicidade(id));
	}
	
	public ArrecadacaoIndiceMes(ArrecadacaoIndiceMesDto dto) {
		setId(dto.getId());
		setMes(MesEnum.getEnumByString(dto.getMes()));
		setAliquota(dto.getAliquota());
		setArrecadacaoIndiceAnoPeriodicidade(new ArrecadacaoIndiceAnoPeriodicidade(dto.getId()));
	}

}
