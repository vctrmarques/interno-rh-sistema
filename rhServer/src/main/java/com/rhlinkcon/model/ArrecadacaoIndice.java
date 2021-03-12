package com.rhlinkcon.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.service.ArrecadacaoIndiceDto;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Arrecadação Índices")
@Table(name = "arrecadacao_indice")
public class ArrecadacaoIndice extends UserDateAudit {

	private static final long serialVersionUID = -2685932978479861338L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotBlank
	private String indice;
	
	@ElementCollection
	@OneToMany(mappedBy = "arrecadacaoIndice", orphanRemoval = true)
	private List<ArrecadacaoIndiceAnoPeriodicidade> anos;
	
	public ArrecadacaoIndice() {}
	
	public ArrecadacaoIndice(Long id) {
		setId(id);
	}

	public ArrecadacaoIndice(ArrecadacaoIndiceDto dto) {
		setId(dto.getId());
		setIndice(dto.getIndice());
	}

	@Override
	public String getLabel() {
		return "Arrecadação Índices";
	}

}
