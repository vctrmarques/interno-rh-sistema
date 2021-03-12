package com.rhlinkcon.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.PersistentId;
import com.rhlinkcon.payload.recadastramento.RecadastramentoContatoRequest;
import com.rhlinkcon.payload.telefone.TelefoneRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Recadastramento Contato")
@Table(name = "recadastramento_contato")
public class RecadastramentoContato implements Serializable, PersistentId {

	private static final long serialVersionUID = -422831393177218453L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String email;

	private String observacao;

	@ManyToMany
	@JoinTable(name = "recadastramento_contato_telefone", joinColumns = {
			@JoinColumn(name = "recadastramento_contato_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "telefone_id", referencedColumnName = "id") })
	private List<Telefone> telefones;

	public RecadastramentoContato(RecadastramentoContatoRequest request) {
		setId(request.getId());
		setNome(request.getNome());
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

	public RecadastramentoContato() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
