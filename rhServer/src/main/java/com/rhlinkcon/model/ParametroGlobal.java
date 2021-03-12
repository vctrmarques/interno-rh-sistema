package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Parametro Global")
@Table(name = "parametro_global")
public class ParametroGlobal extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3929981169688457337L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "chave")
	private ParametroGlobalChaveEnum chave;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private ParametroGlobalTipoEnum tipo;

	@NotNull
	@Column(name = "valor")
	private String valor;

	@NotNull
	@Column(name = "ativo")
	private boolean ativo;

	public ParametroGlobal() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ParametroGlobalChaveEnum getChave() {
		return chave;
	}

	public void setChave(ParametroGlobalChaveEnum chave) {
		this.chave = chave;
	}

	public ParametroGlobalTipoEnum getTipo() {
		return tipo;
	}

	public void setTipo(ParametroGlobalTipoEnum tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String getLabel() {
		return this.chave.getLabel();
	}

}
