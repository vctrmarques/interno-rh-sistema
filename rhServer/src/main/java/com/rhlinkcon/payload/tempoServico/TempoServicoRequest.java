package com.rhlinkcon.payload.tempoServico;

import java.time.Instant;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.payload.funcionario.FuncionarioRequest;

public class TempoServicoRequest {

	private Long id;

	//Início Aba Dados do Funcionário
	
	@NotNull
	private FuncionarioRequest funcionario;

	@NotNull
	private String protocoloCtcCts;
	
	@NotNull
	private Double salario;
	
	@NotNull
	private String indiceContribuicao;
	
	@NotNull
	private String ultimoCargo;
	
	@NotNull
	private Long categoriaSefipId;
	
	private String descricaoCertidao;
	
	//Fim Aba Dados do Funcionário
	//Início Aba Dados da Averbação
	
	@NotNull
	private String descricaoEmpresa;
	
	@NotNull
	private Long efeitoAverbacaoId;
	
	@NotNull
	private String tipoAverbacao;
	
	@NotNull
	private Boolean averbado;
	
	@NotNull
	private Instant dataInicio;
	
	@NotNull
	private Instant dataTermino;
	
	@NotNull
	private Integer qtdDias;
	
	//Fim aba Dados da Averbação
	//Início aba Dados da Publicação
	
	@NotNull
	private String numeroProcesso;
	
	private Long classificacaoAtoId;
	
	@NotNull
	private String numeroDiarioOficial;
	
	@NotNull
	private Integer anoPublicacao;
	
	@NotNull
	private Instant dataDiarioOficial;
	
	@NotNull
	private String numeroAto;
	
	@NotNull
	private Instant data;
	
	//Fim Aba Dados da Publicação
	//Início aba Dados da Instituição
	
	@NotNull
	private String endereco;
	
	@NotNull
	private String cidade;
	
	@NotNull
	private String numero;
	
	private String complemento;
	
	@NotNull
	private String bairro;
	
	@NotNull
	private Long ufId;
	
	@NotNull
	private String cep;
	
	@NotNull
	private String telefone;
	
	@NotNull
	private String cnpj;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProtocoloCtcCts() {
		return protocoloCtcCts;
	}

	public void setProtocoloCtcCts(String protocoloCtcCts) {
		this.protocoloCtcCts = protocoloCtcCts;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public String getIndiceContribuicao() {
		return indiceContribuicao;
	}

	public void setIndiceContribuicao(String indiceContribuicao) {
		this.indiceContribuicao = indiceContribuicao;
	}

	public String getUltimoCargo() {
		return ultimoCargo;
	}

	public void setUltimoCargo(String ultimoCargo) {
		this.ultimoCargo = ultimoCargo;
	}

	public Long getCategoriaSefipId() {
		return categoriaSefipId;
	}

	public void setCategoriaSefipId(Long categoriaSefipId) {
		this.categoriaSefipId = categoriaSefipId;
	}

	public String getDescricaoCertidao() {
		return descricaoCertidao;
	}

	public void setDescricaoCertidao(String descricaoCertidao) {
		this.descricaoCertidao = descricaoCertidao;
	}

	public String getDescricaoEmpresa() {
		return descricaoEmpresa;
	}

	public void setDescricaoEmpresa(String descricaoEmpresa) {
		this.descricaoEmpresa = descricaoEmpresa;
	}

	public Long getEfeitoAverbacaoId() {
		return efeitoAverbacaoId;
	}

	public void setEfeitoAverbacaoId(Long efeitoAverbacaoId) {
		this.efeitoAverbacaoId = efeitoAverbacaoId;
	}

	public String getTipoAverbacao() {
		return tipoAverbacao;
	}

	public void setTipoAverbacao(String tipoAverbacao) {
		this.tipoAverbacao = tipoAverbacao;
	}

	public Boolean getAverbado() {
		return averbado;
	}

	public void setAverbado(Boolean averbado) {
		this.averbado = averbado;
	}

	public Instant getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Instant dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Instant getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Instant dataTermino) {
		this.dataTermino = dataTermino;
	}

	public Integer getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(Integer qtdDias) {
		this.qtdDias = qtdDias;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public Long getClassificacaoAtoId() {
		return classificacaoAtoId;
	}

	public void setClassificacaoAtoId(Long classificacaoAtoId) {
		this.classificacaoAtoId = classificacaoAtoId;
	}

	public String getNumeroDiarioOficial() {
		return numeroDiarioOficial;
	}

	public void setNumeroDiarioOficial(String numeroDiarioOficial) {
		this.numeroDiarioOficial = numeroDiarioOficial;
	}

	public Integer getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(Integer anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	public Instant getDataDiarioOficial() {
		return dataDiarioOficial;
	}

	public void setDataDiarioOficial(Instant dataDiarioOficial) {
		this.dataDiarioOficial = dataDiarioOficial;
	}

	public String getNumeroAto() {
		return numeroAto;
	}

	public void setNumeroAto(String numeroAto) {
		this.numeroAto = numeroAto;
	}

	public Instant getData() {
		return data;
	}

	public void setData(Instant data) {
		this.data = data;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
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

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Long getUfId() {
		return ufId;
	}

	public void setUfId(Long ufId) {
		this.ufId = ufId;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public FuncionarioRequest getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioRequest funcionario) {
		this.funcionario = funcionario;
	}
	
}
