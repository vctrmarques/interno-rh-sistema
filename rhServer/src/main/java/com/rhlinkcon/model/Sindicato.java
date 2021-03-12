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
import com.rhlinkcon.payload.sindicato.SindicatoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Sindicato")
@Table(name = "sindicato")
public class Sindicato extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@Column(name = "mes_base")
	private Integer mesBase;

	@Column(name = "piso_salarial")
	private Integer pisoSalarial;

	@Size(min = 14, max = 14)
	@Column(name = "cnpj")
	private String cnpj;

	@Column(name = "codigo_entidade")
	private String codigoEntidade;

	@Column(name = "ddd")
	private Integer ddd;

	@Column(name = "telefone")
	private String telefone;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "Patronal")
	private PatronalSindicatoEnum patronal;

	@Column(name = "endereco")
	private String endereco;

	@Column(name = "numero")
	private String numero;

	@Column(name = "complemento")
	private String complemento;

	@Column(name = "bairro")
	private String bairro;

	@Column(name = "municipio")
	private String municipio;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "id_unidade_federativa_sindicato")
	private UnidadeFederativa unidadeFederativa;

	@Column(name = "cep")
	private String cep;

	public Sindicato() {

	}

	public Sindicato(SindicatoRequest sindicatoRequest) {
		this.setId(sindicatoRequest.getId());
		this.setDescricao(sindicatoRequest.getDescricao());
		this.setMesBase(sindicatoRequest.getMesBase());
		this.setPisoSalarial(sindicatoRequest.getPisoSalarial());
		this.setCnpj(sindicatoRequest.getCnpj());
		this.setCodigoEntidade(sindicatoRequest.getCodigoEntidade());
		this.setDdd(sindicatoRequest.getDdd());
		this.setTelefone(sindicatoRequest.getTelefone());
		this.setPatronal(PatronalSindicatoEnum.getEnumByString(sindicatoRequest.getPatronal()));
		this.setEndereco(sindicatoRequest.getEndereco());
		this.setNumero(sindicatoRequest.getNumero());
		this.setComplemento(sindicatoRequest.getComplemento());
		this.setBairro(sindicatoRequest.getBairro());
		this.setMunicipio(sindicatoRequest.getMunicipio());
		this.setCep(sindicatoRequest.getCep());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getMesBase() {
		return mesBase;
	}

	public void setMesBase(Integer mesBase) {
		this.mesBase = mesBase;
	}

	public Integer getPisoSalarial() {
		return pisoSalarial;
	}

	public void setPisoSalarial(Integer pisoSalarial) {
		this.pisoSalarial = pisoSalarial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCodigoEntidade() {
		return codigoEntidade;
	}

	public void setCodigoEntidade(String codigoEntidade) {
		this.codigoEntidade = codigoEntidade;
	}

	public Integer getDdd() {
		return ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public PatronalSindicatoEnum getPatronal() {
		return patronal;
	}

	public void setPatronal(PatronalSindicatoEnum patronal) {
		this.patronal = patronal;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
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

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
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

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}