package com.rhlinkcon.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
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
import com.rhlinkcon.payload.banco.BancoRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Banco")
@Table(name = "banco")
public class Banco extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 341728382669934889L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo", unique = true)
	private String codigo;

	@NotBlank
	@NotNull
	@Column(name = "nome", unique = true)
	private String nome;

	@Column(name = "bloqueado")
	private boolean bloqueado;

	private Boolean principal;

	@OneToMany(mappedBy = "banco")
	private Set<Agencia> agencias = new HashSet<>();

	public Banco() {
	}

	public Banco(Long id) {
		this.id = id;
	}

	public Banco(BancoRequest bancoRequest) {
		this.setBloqueado(Utils.setBool(bancoRequest.isBloqueado()));
		this.setCodigo(bancoRequest.getCodigo());
		this.setNome(bancoRequest.getNome());
		this.setPrincipal(bancoRequest.getPrincipal());
		this.setId(bancoRequest.getId());
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

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Set<Agencia> getAgencias() {
		return agencias;
	}

	public void setAgencias(Set<Agencia> agencias) {
		this.agencias = agencias;
	}

	public Boolean getPrincipal() {
		return principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	@Override
	public String getLabel() {
		return this.nome;
	}

}
