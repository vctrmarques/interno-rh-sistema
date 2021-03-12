package com.rhlinkcon.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.treinamentoSugeridoComplemento.TreinamentoSugeridoComplementoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Treinamento Sugerido Complemento")
@Table(name = "treinamento_sugerido_complemento")
public class TreinamentoSugeridoComplemento extends UserDateAudit {

	private static final long serialVersionUID = -2943791420665347722L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "id_treinamento_sugerido", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private TreinamentoSugerido treinamentoSugerido;

	@NotEmpty
	private String local;

	private String endereco;

	private String complemento;

	private String bairro;

	@JoinColumn(name = "id_municipio", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Municipio municipio;

	public TreinamentoSugeridoComplemento() {

	}

	public TreinamentoSugeridoComplemento(TreinamentoSugeridoComplementoRequest request) {
		this.id = request.getId();
		this.treinamentoSugerido = new TreinamentoSugerido(request.getTreinamentoSugeridoId());
		this.local = request.getLocal();
		this.endereco = request.getEndereco();
		this.complemento = request.getComplemento();
		this.bairro = request.getBairro();
		this.municipio = new Municipio(request.getMunicipioId());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TreinamentoSugerido getTreinamentoSugerido() {
		return treinamentoSugerido;
	}

	public void setTreinamentoSugerido(TreinamentoSugerido treinamentoSugerido) {
		this.treinamentoSugerido = treinamentoSugerido;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
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

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
