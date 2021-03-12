package com.rhlinkcon.model;

import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.service.ArrecadacaoIndiceAnoPeriodicidadeDto;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Arrecadação Índice Ano Periodicidade")
@Table(name = "arrecadacao_indice_ano_periodicidade")
public class ArrecadacaoIndiceAnoPeriodicidade {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long ano;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "periodicidade")
	private PeriodicidadeMesEnum periodicidadeMes;
	
	@OneToMany(mappedBy = "arrecadacaoIndiceAnoPeriodicidade", orphanRemoval = true)
	private List<ArrecadacaoIndiceMes> arrecadacaoIndiceMes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "arrecadacao_indice_id", nullable = false)
	private ArrecadacaoIndice arrecadacaoIndice;
	
	public ArrecadacaoIndiceAnoPeriodicidade() {
	}
	
	public ArrecadacaoIndiceAnoPeriodicidade(Long id) {
		setId(id);
	}
	
	public ArrecadacaoIndiceAnoPeriodicidade(ArrecadacaoIndiceAnoPeriodicidadeDto dto, Long id) {
		setId(dto.getId());
		setAno(dto.getAno());
		setArrecadacaoIndice(new ArrecadacaoIndice(id));
		setPeriodicidadeMes(PeriodicidadeMesEnum.getEnumByString(dto.getPeriodicidade()));
		
	}
	
	public ArrecadacaoIndiceAnoPeriodicidade(ArrecadacaoIndiceAnoPeriodicidadeDto dto) {
		setId(dto.getId());
		setAno(dto.getAno());
		setArrecadacaoIndice(new ArrecadacaoIndice(dto.getId()));
		setPeriodicidadeMes(PeriodicidadeMesEnum.getEnumByLabel(dto.getPeriodicidade()));
	}

}
