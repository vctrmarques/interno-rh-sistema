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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.tempoServico.TempoServicoRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Tempo de Serviço")
@Table(name = "tempo_servico")
public class TempoServico extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Início Aba Dados do Funcionário
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@NotNull
	@Column(name = "protocolo_ctc_cts")
	private String protocoloCtcCts;

	@NotNull
	@Column(name = "salario")
	private Double salario;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "indice_contribuicao")
	private IndiceContribuicaoPrevidenciaEnum indiceContribuicao;

	@NotNull
	@Column(name = "ultimo_cargo")
	private String ultimoCargo;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sefip_id")
	private Sefip categoriaSefip;

	@Column(name = "descricao_certidao")
	private String descricaoCertidao;

	// Fim Aba Dados do Funcionário
	// Início Aba Dados da Averbação

	@NotNull
	@Column(name = "descricao_empresa")
	private String descricaoEmpresa;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_averbacao_id")
	private TipoAverbacao efeitoAverbacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_averbacao")
	private TipoAverbacaoEnum tipoAverbacao;

	@NotNull
	@Column(name = "averbado")
	private Boolean averbado;

	@NotNull
	@Column(name = "dt_inicio")
	private Instant dataInicio;

	@NotNull
	@Column(name = "dt_termino")
	private Instant dataTermino;

	@NotNull
	@Column(name = "qtd_dias")
	private Integer qtdDias;

	// Fim aba Dados da Averbação
	// Início aba Dados da Publicação

	@NotNull
	@Column(name = "num_processo")
	private String numeroProcesso;

	@OneToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "classificacao_ato_id")
	private ClassificacaoAto classificacaoAto;

	@NotNull
	@Column(name = "num_diario_oficial")
	private String numeroDiarioOficial;

	@NotNull
	@Column(name = "ano_publicacao")
	private Integer anoPublicacao;

	@NotNull
	@Column(name = "dt_diario_oficial")
	private Instant dataDiarioOficial;

	@NotNull
	@Column(name = "num_ato")
	private String numeroAto;

	@NotNull
	@Column(name = "data")
	private Instant data;

	// Fim Aba Dados da Publicação
	// Início aba Dados da Instituição

	@NotNull
	@Column(name = "endereco")
	private String endereco;

	@NotNull
	@Column(name = "cidade")
	private String cidade;

	@NotNull
	@Column(name = "numero")
	private String numero;

	@Column(name = "complemento")
	private String complemento;

	@NotNull
	@Column(name = "bairro")
	private String bairro;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uf_id")
	private UnidadeFederativa uf;

	@NotNull
	@Size(min = 8, max = 8)
	@Column(name = "cep")
	private String cep;

	@NotNull
	@Column(name = "telefone")
	private String telefone;

	@NotNull
	@Size(min = 14, max = 14)
	@Column(name = "cnpj")
	private String cnpj;

	// Fim Aba Dados da Empresa

	public TempoServico() {

	}

	public TempoServico(TempoServicoRequest tempoServicoRequest) {

		this.setProtocoloCtcCts(tempoServicoRequest.getProtocoloCtcCts());
		this.setSalario(tempoServicoRequest.getSalario());

		this.setIndiceContribuicao(
				IndiceContribuicaoPrevidenciaEnum.valueOf(tempoServicoRequest.getIndiceContribuicao()));

		this.setUltimoCargo(tempoServicoRequest.getUltimoCargo());
		this.setDescricaoCertidao(tempoServicoRequest.getDescricaoCertidao());
		this.setNumeroProcesso(tempoServicoRequest.getNumeroProcesso());
		this.setData(tempoServicoRequest.getData());
		this.setAnoPublicacao(tempoServicoRequest.getAnoPublicacao());
		this.setDataDiarioOficial(tempoServicoRequest.getDataDiarioOficial());
		this.setNumeroDiarioOficial(tempoServicoRequest.getNumeroDiarioOficial());
		this.setNumeroAto(tempoServicoRequest.getNumeroAto());

		this.setDescricaoEmpresa(tempoServicoRequest.getDescricaoEmpresa());
		this.setTipoAverbacao(TipoAverbacaoEnum.valueOf(tempoServicoRequest.getTipoAverbacao()));
		this.setAverbado(Utils.setBool(tempoServicoRequest.getAverbado()));
		this.setDataInicio(tempoServicoRequest.getDataInicio());
		this.setDataTermino(tempoServicoRequest.getDataTermino());
		this.setQtdDias(tempoServicoRequest.getQtdDias());
		this.setEndereco(tempoServicoRequest.getEndereco());
		this.setCidade(tempoServicoRequest.getCidade());
		this.setNumero(tempoServicoRequest.getNumero());
		this.setComplemento(tempoServicoRequest.getComplemento());
		this.setBairro(tempoServicoRequest.getBairro());
		this.setCep(tempoServicoRequest.getCep());
		this.setTelefone(tempoServicoRequest.getTelefone());
		this.setCnpj(tempoServicoRequest.getCnpj());

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

	public IndiceContribuicaoPrevidenciaEnum getIndiceContribuicao() {
		return indiceContribuicao;
	}

	public void setIndiceContribuicao(IndiceContribuicaoPrevidenciaEnum indiceContribuicao) {
		this.indiceContribuicao = indiceContribuicao;
	}

	public String getUltimoCargo() {
		return ultimoCargo;
	}

	public void setUltimoCargo(String ultimoCargo) {
		this.ultimoCargo = ultimoCargo;
	}

	public Sefip getCategoriaSefip() {
		return categoriaSefip;
	}

	public void setCategoriaSefip(Sefip categoriaSefip) {
		this.categoriaSefip = categoriaSefip;
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

	public TipoAverbacao getEfeitoAverbacao() {
		return efeitoAverbacao;
	}

	public void setEfeitoAverbacao(TipoAverbacao efeitoAverbacao) {
		this.efeitoAverbacao = efeitoAverbacao;
	}

	public TipoAverbacaoEnum getTipoAverbacao() {
		return tipoAverbacao;
	}

	public void setTipoAverbacao(TipoAverbacaoEnum tipoAverbacao) {
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

	public ClassificacaoAto getClassificacaoAto() {
		return classificacaoAto;
	}

	public void setClassificacaoAto(ClassificacaoAto classificacaoAto) {
		this.classificacaoAto = classificacaoAto;
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

	public UnidadeFederativa getUf() {
		return uf;
	}

	public void setUf(UnidadeFederativa uf) {
		this.uf = uf;
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

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
