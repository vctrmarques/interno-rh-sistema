package com.rhlinkcon.payload.tempoServico;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.TempoServico;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TempoServicoResponse extends DadoCadastralResponse {

	private Long id;

	// Início Aba Dados do Funcionário

	private FuncionarioResponse funcionario;
	
	private String protocoloCtcCts;

	private Double salario;

	private String indiceContribuicao;

	private String ultimoCargo;

	private Long categoriaSefipId;

	private String descricaoCertidao;

	// Fim Aba Dados do Funcionário
	// Início Aba Dados da Averbação

	private String descricaoEmpresa;

	private Long efeitoAverbacaoId;

	private String tipoAverbacao;

	private Boolean averbado;

	private Instant dataInicio;

	private Instant dataTermino;

	private Integer qtdDias;

	// Fim aba Dados da Averbação
	// Início aba Dados da Publicação

	private String numeroProcesso;

	private Long classificacaoAtoId;

	private String numeroDiarioOficial;

	private Integer anoPublicacao;

	private Instant dataDiarioOficial;

	private String numeroAto;

	private Instant data;

	// Fim Aba Dados da Publicação
	// Início aba Dados da Instituição

	private String endereco;

	private String cidade;

	private String numero;

	private String complemento;

	private String bairro;

	private Long ufId;

	private String cep;

	private String telefone;

	private String cnpj;
	
	public TempoServicoResponse(TempoServico tempoServico) {
		setTempoServico(tempoServico);
	}
	
	public TempoServicoResponse(TempoServico tempoServico, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setTempoServico(tempoServico);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
	}
	
	public void setTempoServico(TempoServico tempoServico) {
		this.setId(tempoServico.getId());
		this.setProtocoloCtcCts(tempoServico.getProtocoloCtcCts());
		this.setSalario(tempoServico.getSalario());
		
		this.setIndiceContribuicao(tempoServico.getIndiceContribuicao().toString());
		
		this.setUltimoCargo(tempoServico.getUltimoCargo());
		this.setDescricaoCertidao(tempoServico.getDescricaoCertidao());
		
		this.setDescricaoEmpresa(tempoServico.getDescricaoEmpresa());
		this.setTipoAverbacao(tempoServico.getTipoAverbacao().toString());
		this.setAverbado(tempoServico.getAverbado());
		this.setDataInicio(tempoServico.getDataInicio());
		this.setDataTermino(tempoServico.getDataTermino());
		this.setQtdDias(tempoServico.getQtdDias());
		this.setEndereco(tempoServico.getEndereco());
		this.setCidade(tempoServico.getCidade());
		this.setNumero(tempoServico.getNumero());
		this.setComplemento(tempoServico.getComplemento());
		this.setBairro(tempoServico.getBairro());
		this.setCep(tempoServico.getCep());
		this.setTelefone(tempoServico.getTelefone());
		this.setCnpj(tempoServico.getCnpj());
		this.setNumeroProcesso(tempoServico.getNumeroProcesso());
		this.setNumeroDiarioOficial(tempoServico.getNumeroDiarioOficial());
		this.setAnoPublicacao(tempoServico.getAnoPublicacao());
		this.setDataDiarioOficial(tempoServico.getDataDiarioOficial());
		this.setData(tempoServico.getData());
		this.setNumeroAto(tempoServico.getNumeroAto());
		
		this.setFuncionario(new FuncionarioResponse(tempoServico.getFuncionario()));
		this.setCategoriaSefipId(tempoServico.getCategoriaSefip().getId());
		this.setEfeitoAverbacaoId(tempoServico.getEfeitoAverbacao().getId());
		this.setClassificacaoAtoId(tempoServico.getClassificacaoAto().getId());
		this.setUfId(tempoServico.getUf().getId());
		
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
	
}
