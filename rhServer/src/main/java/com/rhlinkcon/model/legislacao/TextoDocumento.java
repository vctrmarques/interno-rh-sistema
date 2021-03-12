package com.rhlinkcon.model.legislacao;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.PersistentId;

@Entity
@Table(name = "texto_documento")
public class TextoDocumento implements PersistentId{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo", unique = true)
	private Integer codigo;

	@NotNull
	@Column(name = "descricao", unique = true)
	private String descricao;

	@NotNull
	@Column(name = "vigencia")
	private Instant vigencia;

	public TextoDocumento() {

	}

	public TextoDocumento(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Instant getVigencia() {
		return vigencia;
	}

	public void setVigencia(Instant vigencia) {
		this.vigencia = vigencia;
	}

	@Override
	public String getLabel() {
		return this.descricao;
	}

}
