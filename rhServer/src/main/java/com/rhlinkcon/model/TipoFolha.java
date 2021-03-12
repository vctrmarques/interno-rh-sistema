package com.rhlinkcon.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.tipoFolha.TipoFolhaRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Tipo de Folha")
@Table(name = "tipo_folha")
public class TipoFolha extends UserDateAudit {

	private static final long serialVersionUID = -8839299739908613830L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tipo_folha_verbas", joinColumns = @JoinColumn(name = "id_tipo_folha"), inverseJoinColumns = @JoinColumn(name = "id_verba"))
	private List<Verba> verbas;

	public TipoFolha() {
	}

	public TipoFolha(TipoFolhaRequest tipoFolhaRequest) {
		this.setId(tipoFolhaRequest.getId());
		this.setCodigo(tipoFolhaRequest.getCodigo());
		this.setDescricao(tipoFolhaRequest.getDescricao());

		List<Verba> v = new ArrayList<>();
		if (!tipoFolhaRequest.getVerbasId().isEmpty()) {
			tipoFolhaRequest.getVerbasId().forEach(vId -> {
				v.add(new Verba(vId));
			});
			this.setVerbas(v);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Verba> getVerbas() {
		return verbas;
	}

	public void setVerbas(List<Verba> verbas) {
		this.verbas = verbas;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}