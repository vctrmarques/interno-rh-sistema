package com.rhlinkcon.model;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.responsavelLegal.ResponsavelLegalRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Responsável Legal")
@Table(name = "responsavel_legal")
public class ResponsavelLegal extends UserDateAudit {

	private static final long serialVersionUID = 6320518476824868720L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo_responsavel")
	private String codigoResponsavel;

	@Column(name = "nome")
	private String nome;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "id_banco")
	private Banco banco;

	@NotNull
	@Column(name = "agencia")
	private Integer agencia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agencia_id")
	private Agencia agenciaBancaria;

	@NotNull
	@Column(name = "conta_corrente")
	private Integer contaCorrente;

	@Column(name = "digito_conta")
	private String digitoConta;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "tipo_responsavel")
	private TipoResponsavelEnum tipoResponsavel;

	@Size(min = 11, max = 11)
	@Column(name = "cpf")
	private String cpf;

	@NotNull
	@Column(name = "identidade")
	private Integer identidade;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "tipo_conta")
	private TipoContaResponsavelEnum tipoConta;

	@NotNull
	@Column(name = "logradouro")
	private String logradouro;

	@NotNull
	@Column(name = "numero")
	private String numero;

	@NotNull
	@Column(name = "complemento")
	private String complemento;

	@NotNull
	@Column(name = "bairro")
	private String bairro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_municipio")
	private Municipio municipio;

	@NotNull
	@Column(name = "cep")
	private String cep;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "id_unidade_federativa")
	private UnidadeFederativa unidadeFederativa;

	@Column(name = "telefone")
	private String telefone;

	public ResponsavelLegal() {
	}

	public ResponsavelLegal(Long id) {
		this.id = id;
	}

	public ResponsavelLegal(ResponsavelLegalRequest responsavelLegalRequest) {
		this.setCodigoResponsavel(responsavelLegalRequest.getCodigoResponsavel());
		this.setNome(responsavelLegalRequest.getNome());
		this.setAgencia(responsavelLegalRequest.getAgencia());
		this.setContaCorrente(responsavelLegalRequest.getContaCorrente());
		this.setDigitoConta(responsavelLegalRequest.getDigitoConta());

		if (Utils.checkStr(responsavelLegalRequest.getTipoResponsavel()))
			this.setTipoResponsavel(TipoResponsavelEnum.getEnumByString(responsavelLegalRequest.getTipoResponsavel()));

		this.setCpf(responsavelLegalRequest.getCpf());
		this.setIdentidade(responsavelLegalRequest.getIdentidade());

		if (Utils.checkStr(responsavelLegalRequest.getTipoConta()))
			this.setTipoConta(TipoContaResponsavelEnum.getEnumByString(responsavelLegalRequest.getTipoConta()));

		this.setLogradouro(responsavelLegalRequest.getLogradouro());
		this.setNumero(responsavelLegalRequest.getNumero());
		this.setComplemento(responsavelLegalRequest.getComplemento());
		this.setBairro(responsavelLegalRequest.getBairro());
		this.setCep(responsavelLegalRequest.getCep());
		this.setTelefone(responsavelLegalRequest.getTelefone());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoResponsavel() {
		return codigoResponsavel;
	}

	public void setCodigoResponsavel(String codigoResponsavel) {
		this.codigoResponsavel = codigoResponsavel;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Integer getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public Integer getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(Integer contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public TipoResponsavelEnum getTipoResponsavel() {
		return tipoResponsavel;
	}

	public void setTipoResponsavel(TipoResponsavelEnum tipoResponsavel) {
		this.tipoResponsavel = tipoResponsavel;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getIdentidade() {
		return identidade;
	}

	public void setIdentidade(Integer identidade) {
		this.identidade = identidade;
	}

	public TipoContaResponsavelEnum getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoContaResponsavelEnum tipoConta) {
		this.tipoConta = tipoConta;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public UnidadeFederativa getUnidadeFederativa() {
		return unidadeFederativa;
	}

	public void setUnidadeFederativa(UnidadeFederativa unidadeFederativa) {
		this.unidadeFederativa = unidadeFederativa;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Agencia getAgenciaBancaria() {
		return agenciaBancaria;
	}

	public void setAgenciaBancaria(Agencia agenciaBancaria) {
		this.agenciaBancaria = agenciaBancaria;
	}

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}