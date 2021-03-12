package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.especialidadeMedica.EspecialidadeMedicaDto;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Especialidade Médica")
@Table(name = "especialidade_medica")
public class EspecialidadeMedica extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3675396825029566782L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	@Size(max = 255)
	@Column(name = "codigo", unique = true)
	private String codigo;

	@NotNull
	@NotBlank
	@Size(max = 255)
	@Column(name = "nome", unique = true)
	private String nome;

	public EspecialidadeMedica() {

	}

	public EspecialidadeMedica(Long id) {
		setId(id);
	}
	
	public EspecialidadeMedica(EspecialidadeMedicaDto request) {
		this.setCodigo(request.getCodigo());
		this.setNome(request.getNome());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getLabel() {
		return this.nome;
	}

}
