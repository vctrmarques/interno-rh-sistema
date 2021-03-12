package com.rhlinkcon.payload.dependente;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Dependente;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DependenteResponse extends DadoCadastralResponse {
	
	private Long id;
	
	private FuncionarioResponse funcionario;
	
	private String tipo;

	private String nome;
	
	private String naturalidade;
	
	private Instant inicioMotivo;
	
	private Instant fimMotivo;
	
	private String sexo;
	
	private Instant dataNascimento;
	
	private String numeroCpf;
	
	private String numeroIdentidade;
	
	private Long unidadeFederativaId;
	
	private String logradouro;
	
	private String numero;
	
	private Long municipioId;
	
	private String cep;

	private String numDocumentoComprobatorio;
	
	private Instant dataDocumentoComprobatorio;
	
	private Boolean dependenciaIr;
	
	private Boolean dependenciaSf;
	
	private Integer qntdBeneficios;
		
	
	public DependenteResponse(Dependente dependente) {
		setDependente(dependente);
	}
	
	public DependenteResponse(Dependente dependente, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setDependente(dependente);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	public void setDependente(Dependente dependente) {
		this.setId(dependente.getId());
		
		if(Objects.nonNull(dependente.getFuncionario()))
			this.setFuncionario(new FuncionarioResponse(dependente.getFuncionario()));
		
		this.setTipo(dependente.getTipo().toString());
		
		if(Objects.nonNull(dependente.getNome()))
			this.setNome(dependente.getNome());
		
		this.setNaturalidade(dependente.getNaturalidade());
		this.setInicioMotivo(dependente.getInicioMotivo());
		this.setFimMotivo(dependente.getFimMotivo());
		
		if (Objects.nonNull(dependente.getSexo()))
			this.setSexo(dependente.getSexo());
		
		
		this.setDataNascimento(dependente.getDataNascimento());
		this.setNumeroCpf(dependente.getNumeroCpf());
		this.setNumeroIdentidade(dependente.getNumeroIdentidade());
		
		this.setLogradouro(dependente.getLogradouro());
		this.setNumero(dependente.getNumero());
		
		if(Objects.nonNull(dependente.getMunicipio()))
			this.setMunicipioId(dependente.getMunicipio().getId());		
		
		if(Objects.nonNull(dependente.getUnidadeFederativa()))
			this.setUnidadeFederativaId(dependente.getUnidadeFederativa().getId());
		
		this.setCep(dependente.getCep());
		this.setNumDocumentoComprobatorio(dependente.getNumDocumentoComprobatorio());
		this.setDataDocumentoComprobatorio(dependente.getDataDocumentoComprobatorio());
		this.setDependenciaIr(dependente.getDependenciaIr());
		this.setDependenciaSf(dependente.getDependenciaSf());
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
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

	public Long getUnidadeFederativaId() {
		return unidadeFederativaId;
	}

	public void setUnidadeFederativaId(Long unidadeFederativaId) {
		this.unidadeFederativaId = unidadeFederativaId;
	}

	public Long getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(Long municipioId) {
		this.municipioId = municipioId;
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

	public Integer getQntdBeneficios() {
		return qntdBeneficios;
	}

	public void setQntdBeneficios(Integer qntdBeneficios) {
		this.qntdBeneficios = qntdBeneficios;
	}
	
	
}
