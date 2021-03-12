package com.rhlinkcon.payload.dependente;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.UnidadeFederativa;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DependenteRequest {
		
		private Long id;
		
		@NotNull
		private Long funcionarioId;
		
		@NotNull
		private String tipo;
		
		@NotNull
		private String nome;
		
		private String naturalidade;
		
		private Instant inicioMotivo;
		
		private Instant fimMotivo;
		
		@NotNull
		private String sexo;
		
		private Instant dataNascimento;
		
		private String numeroCpf;
		
		private String numeroIdentidade;
		
		private String logradouro;
		
		private String numero;
		
		@NotNull
		private Long municipioId;
		
		@NotNull
		private Long unidadeFederativaId;
		
		private String cep;

		private String numDocumentoComprobatorio;
		
		private Instant dataDocumentoComprobatorio;
		
		private Boolean dependenciaIr;
		
		@NotNull
		private Boolean dependenciaSf;
	

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getFuncionarioId() {
			return funcionarioId;
		}

		public void setFuncionarioId(Long funcionarioId) {
			this.funcionarioId = funcionarioId;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
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

		public Long getMunicipioId() {
			return municipioId;
		}

		public void setMunicipioId(Long municipioId) {
			this.municipioId = municipioId;
		}

		public Long getUnidadeFederativaId() {
			return unidadeFederativaId;
		}

		public void setUnidadeFederativaId(Long unidadeFederativaId) {
			this.unidadeFederativaId = unidadeFederativaId;
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

}