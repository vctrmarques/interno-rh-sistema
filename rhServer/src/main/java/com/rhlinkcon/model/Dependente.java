package com.rhlinkcon.model;

import java.time.Instant;

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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.dependente.DependenteRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Dependente")
@Table(name = "dependente")
public class Dependente extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3141442636618203716L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@Enumerated(EnumType.STRING)
	private DependenteTipoDependenteEnum tipo;

	@NotBlank
	@Column(name = "nome")
	private String nome;

	@Column(name = "naturalidade")
	private String naturalidade;

	@Column(name = "inicio_motivo")
	private Instant inicioMotivo;

	@Column(name = "fim_motivo")
	private Instant fimMotivo;

	@NotNull
	@Column(name = "sexo")
	private String sexo;

	@Column(name = "dt_nascimento")
	private Instant dataNascimento;

	@Size(min = 11, max = 11)
	@Column(name = "num_cpf")
	private String numeroCpf;

	@Column(name = "num_identidade")
	private String numeroIdentidade;

	@Column(name = "logradouro")
	private String logradouro;

	@Column(name = "numero")
	private String numero;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio_id")
	private Municipio municipio;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidade_federativa_id")
	private UnidadeFederativa unidadeFederativa;

	@Column(name = "cep")
	private String cep;

	@Column(name = "num_documento_comprobatorio")
	private String numDocumentoComprobatorio;

	@Column(name = "dt_documento_comprobatorio")
	private Instant dataDocumentoComprobatorio;

	@Column(name = "dependencia_ir")
	private Boolean dependenciaIr;

	@NotNull
	@Column(name = "dependencia_sf")
	private Boolean dependenciaSf;

	public Dependente() {

	}

	public Dependente(DependenteRequest dependenteRequest) {

		this.setTipo(DependenteTipoDependenteEnum.valueOf(dependenteRequest.getTipo()));

		if (Utils.checkStr(dependenteRequest.getNome()))
			this.setNome(dependenteRequest.getNome());

		this.setNaturalidade(dependenteRequest.getNaturalidade());

		this.setInicioMotivo(dependenteRequest.getInicioMotivo());
		this.setFimMotivo(dependenteRequest.getFimMotivo());

		if (Utils.checkStr(dependenteRequest.getSexo()))
			this.setSexo(dependenteRequest.getSexo());

		this.setDataNascimento(dependenteRequest.getDataNascimento());
		this.setNumeroCpf(dependenteRequest.getNumeroCpf());
		this.setNumeroIdentidade(dependenteRequest.getNumeroIdentidade());
		this.setLogradouro(dependenteRequest.getLogradouro());
		this.setNumero(dependenteRequest.getNumero());
		this.setCep(dependenteRequest.getCep());
		this.setNumDocumentoComprobatorio(dependenteRequest.getNumDocumentoComprobatorio());
		this.setDataDocumentoComprobatorio(dependenteRequest.getDataDocumentoComprobatorio());
		this.setDependenciaIr(dependenteRequest.getDependenciaIr());
		this.setDependenciaSf(dependenteRequest.getDependenciaSf());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public Instant getInicioMotivo() {
		return inicioMotivo;
	}

	public void setInicioMotivo(Instant inicioMotivo) {
		this.inicioMotivo = inicioMotivo;
	}

	public Instant getFimMotivo() {
		return fimMotivo;
	}

	public void setFimMotivo(Instant fimMotivo) {
		this.fimMotivo = fimMotivo;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Instant getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Instant dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNumeroCpf() {
		return numeroCpf;
	}

	public void setNumeroCpf(String numeroCpf) {
		this.numeroCpf = numeroCpf;
	}

	public String getNumeroIdentidade() {
		return numeroIdentidade;
	}

	public void setNumeroIdentidade(String numeroIdentidade) {
		this.numeroIdentidade = numeroIdentidade;
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

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public UnidadeFederativa getUnidadeFederativa() {
		return unidadeFederativa;
	}

	public void setUnidadeFederativa(UnidadeFederativa unidadeFederativa) {
		this.unidadeFederativa = unidadeFederativa;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumDocumentoComprobatorio() {
		return numDocumentoComprobatorio;
	}

	public void setNumDocumentoComprobatorio(String numDocumentoComprobatorio) {
		this.numDocumentoComprobatorio = numDocumentoComprobatorio;
	}

	public Instant getDataDocumentoComprobatorio() {
		return dataDocumentoComprobatorio;
	}

	public void setDataDocumentoComprobatorio(Instant dataDocumentoComprobatorio) {
		this.dataDocumentoComprobatorio = dataDocumentoComprobatorio;
	}

	public Boolean getDependenciaIr() {
		return dependenciaIr;
	}

	public void setDependenciaIr(Boolean dependenciaIr) {
		this.dependenciaIr = dependenciaIr;
	}

	public Boolean getDependenciaSf() {
		return dependenciaSf;
	}

	public void setDependenciaSf(Boolean dependenciaSf) {
		this.dependenciaSf = dependenciaSf;
	}

	public DependenteTipoDependenteEnum getTipo() {
		return tipo;
	}

	public void setTipo(DependenteTipoDependenteEnum tipo) {
		this.tipo = tipo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
