
package com.rhlinkcon.model;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.tipoProcessamento.TipoProcessamentoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Tipo de Processamento")
@Table(name = "tipo_processamento")
public class TipoProcessamento extends UserDateAudit {

	private static final long serialVersionUID = -8770252859250324943L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo", unique = true)
	private String codigo;

	@NotBlank
	@NotNull
	@Column(name = "descricao", unique = true)
	private String descricao;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tipo_processamento_verba", joinColumns = @JoinColumn(name = "tipo_processamento_id"), inverseJoinColumns = @JoinColumn(name = "verba_id"))
	private List<Verba> verbas;

	public TipoProcessamento(Long id) {
		this.id = id;
	}

	public TipoProcessamento(TipoProcessamentoRequest request) {
		this.id = request.getId();
		this.codigo = request.getCodigo();
		this.descricao = request.getDescricao();

	}

	public TipoProcessamento() {
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
