package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Dirf Responsável Receita")
@Table(name = "dirf_responsavel_receita")
@Getter
@Setter
public class DirfResponsavelReceita extends UserDateAudit {

	private static final long serialVersionUID = 7309045583674351580L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 11, max = 11)
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "nome")
	private String nome;
	
	@Size(min = 2, max = 3)
	@Column(name = "ddd")
	private String ddd;
	
	@Size(min = 9, max = 9)
	@Column(name = "numero_telefone")
	private String numeroTelefone;
	
	@Column(name = "ramal")
	private String ramal;
	
	@Column(name = "email")
	private String email;

	@Override
	public String getLabel() {
		return null;
	}
	
}
