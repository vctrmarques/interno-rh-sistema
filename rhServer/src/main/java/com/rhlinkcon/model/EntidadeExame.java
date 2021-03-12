package com.rhlinkcon.model;

import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Entidade Exame")
@Table(name = "entidade_exame")
public class EntidadeExame extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2580182041215920453L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tomador_servico_id")
	private TomadorServico tomadorServico;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoEntidadeExameEnum tipo;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "entidade_exame_exame", joinColumns = @JoinColumn(name = "entidade_exame_id"), inverseJoinColumns = @JoinColumn(name = "exame_id"))
	private Set<Exame> exames = new HashSet<>();

	@Column(name = "logradouro")
	private String logradouro;

	@Column(name = "numero")
	private String numero;

	@Column(name = "complemento")
	private String complemento;

	@Column(name = "cep")
	private String cep;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uf_id")
	private UnidadeFederativa uf;

	@Column(name = "bairro")
	private String bairro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio_id")
	private Municipio municipio;

	@Column(name = "telefone")
	private String telefone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TomadorServico getTomadorServico() {
		return tomadorServico;
	}

	public void setTomadorServico(TomadorServico tomadorServico) {
		this.tomadorServico = tomadorServico;
	}

	public TipoEntidadeExameEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoEntidadeExameEnum tipo) {
		this.tipo = tipo;
	}

	public Set<Exame> getExames() {
		return exames;
	}

	public void setExames(Set<Exame> exames) {
		this.exames = exames;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public UnidadeFederativa getUf() {
		return uf;
	}

	public void setUf(UnidadeFederativa uf) {
		this.uf = uf;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
