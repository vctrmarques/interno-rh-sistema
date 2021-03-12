package com.rhlinkcon.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.arrecadacaoAliquota.ArrecadacaoAliquotaDto;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Arrecadação Alíquota Período")
@Table(name = "arrecadacao_aliquota_periodo")
public class ArrecadacaoAliquotaPeriodo extends UserDateAudit {

	private static final long serialVersionUID = 4908805186871836211L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "inicio_vigencia")
	private LocalDate inicioVigencia;
	
	@NotNull
	@Column(name = "fim_vigencia")
	private LocalDate fimVigencia;
	
	@ElementCollection
	@OneToMany(mappedBy = "arrecadacaoAliquotaPeriodo")
	private List<ArrecadacaoAliquotaEncargo> encargos;
	
	public ArrecadacaoAliquotaPeriodo() {}
	
	public ArrecadacaoAliquotaPeriodo(Long id) {
		setId(id);
	}

	public ArrecadacaoAliquotaPeriodo(ArrecadacaoAliquotaDto dto) {
		setId(dto.getId());
		setInicioVigencia(dto.getInicioVigencia());
		setFimVigencia(dto.getFimVigencia());
	}


	@Override
	public String getLabel() {
		return "Arrecadação Alíquota Período";
	}
	
}
