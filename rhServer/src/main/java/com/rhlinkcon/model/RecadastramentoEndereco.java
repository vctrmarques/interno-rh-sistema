package com.rhlinkcon.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.PersistentId;
import com.rhlinkcon.payload.recadastramento.RecadastramentoEnderecoRequest;
import com.rhlinkcon.payload.telefone.TelefoneRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Recadastramento Endereço")
@Table(name = "recadastramento_endereco")
public class RecadastramentoEndereco implements Serializable, PersistentId {

	private static final long serialVersionUID = -3397291009315350393L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "municipio_id", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	private Municipio municipio;

	private String cep;

	private String bairro;

	private String logradouro;

	private String complemento;

	private String numero;

	private String email;

	private String observacao;

	@ManyToMany
	@JoinTable(name = "recadastramento_endereco_telefone", joinColumns = {
			@JoinColumn(name = "recadastramento_endereco_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "telefone_id", referencedColumnName = "id") })
	private List<Telefone> telefones;

	public RecadastramentoEndereco(RecadastramentoEnderecoRequest request) {
		setId(request.getId());

		if (Objects.nonNull(request.getMunicipioId()))
			setMunicipio(new Municipio(request.getMunicipioId()));

		setCep(request.getCep());
		setBairro(request.getBairro());
		setLogradouro(request.getLogradouro());
		setComplemento(request.getComplemento());
		setNumero(request.getNumero());
		setEmail(request.getEmail());
		setObservacao(request.getObservacao());

		if (Objects.nonNull(request.getTelefones())) {
			setTelefones(new ArrayList<>());
			for (TelefoneRequest tr : request.getTelefones()) {
				Telefone t = new Telefone(tr);
				getTelefones().add(t);
			}
		}
	}

	public RecadastramentoEndereco() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
