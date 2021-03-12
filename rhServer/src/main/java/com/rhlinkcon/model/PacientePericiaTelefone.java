package com.rhlinkcon.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Paciente Perícia Telefone")
@Table(name = "paciente_pericia_telefone")
public class PacientePericiaTelefone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotBlank
	private String telefone;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paciente_pericia_medica_id", nullable = false)
	private PacientePericiaMedica pacientePericiaMedica;
	
	public PacientePericiaTelefone() {
	}
	
	public PacientePericiaTelefone(Long id) {
		setId(id);
	}
}
